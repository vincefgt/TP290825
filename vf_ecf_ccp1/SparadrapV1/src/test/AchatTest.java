import controler.PharmacieController;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@DisplayName("Tests de la classe Achat")
public class AchatTest {

    private Achat achat;
    private Client client;
    private Mutuelle mutuelle;
    private Medecin medecin;
    private Ordonnance ordonnance;
    private Medicament medicament1, medicament2, medicament3;

    @BeforeEach
    void setUp() {
        PharmacieController controller = new PharmacieController();

        mutuelle = new Mutuelle("MGEN", 70.0);
        medecin = new Medecin("Dubois", "Paris", 12345678910L, null);
        client = new Client("Dupont", "Jean", "123 Rue Test",
                75001, "Paris", "0123456789", "jean@test.com",
                111222333444555L, LocalDate.of(1980, 1, 1), mutuelle, medecin);
        medicament1 = new Medicament("Doliprane", catMed.ANTALGIQUE, 5.20, "2023-01-01", 50);
        medicament2 = new Medicament("Aspirine", catMed.ANALGESIQUE, 3.80, "2023-01-01", 30);
        medicament3 = new Medicament("Ibuprofène", catMed.ANTIINFLAMMATOIRE, 4.50, "2023-01-01", 25);
        ordonnance = new Ordonnance("Dubois", "Pierre", "10 Rue Médical",
                "dr.dubois@hopital.fr", 75008, "Paris", "0140506070",
                12345678910L, null, LocalDate.of(2024, 1, 15), client);
        ordonnance.addMedOrdo(medicament1);
        ordonnance.addMedOrdo(medicament2);
    }

    @Test
    @DisplayName("Test de création d'un achat direct (sans ordonnance)")
    void testCreationAchatDirect() {
        LocalDate dateAchat = LocalDate.of(2024, 1, 20);
        assertDoesNotThrow(() -> {
            achat = new Achat(dateAchat, client);
        });

        assertEquals(dateAchat, achat.getDateAchat());
        assertEquals(client, achat.getClient());
        assertNull(achat.getOrdonnance());
        assertTrue(achat.IsAchatDirect());
        assertTrue(achat.getListMedAchat().isEmpty());
        assertEquals(0.0, achat.getTotal(), 0.01);
    }

    @Test
    @DisplayName("Test de création d'un achat avec ordonnance")
    void testCreationAchatAvecOrdonnance() {
        LocalDate dateAchat = LocalDate.of(2024, 1, 20);

        assertDoesNotThrow(() -> {
            achat = new Achat(dateAchat, client, ordonnance);
        });

        assertEquals(dateAchat, achat.getDateAchat());
        assertEquals(client, achat.getClient());
        assertEquals(ordonnance, achat.getOrdonnance());
        assertFalse(achat.IsAchatDirect());

        // Vérifier que les médicaments de l'ordonnance sont dans l'achat
        assertEquals(2, achat.getListMedAchat().size());
        assertTrue(achat.getListMedAchat().contains(medicament1));
        assertTrue(achat.getListMedAchat().contains(medicament2));
    }

    @Test
    @DisplayName("Test de validation de la date d'achat")
    void testValidationDateAchat() {
        // Test avec date null
        assertThrows(Exception.class, () -> {
            new Achat(null, client);
        });
    }

    @Test
    @DisplayName("Test de validation du client")
    void testValidationClient() {
        LocalDate dateAchat = LocalDate.of(2024, 1, 20);

        // Test avec client null
        assertThrows(Exception.class, () -> {
            new Achat(dateAchat, null);
        });
    }

    @Test
    @DisplayName("Test d'ajout de médicament à un achat direct")
    void testAjoutMedicamentAchatDirect() {
        achat = new Achat(LocalDate.of(2024, 1, 20), client);

        // Add med1
        achat.addMedAchat(medicament1);
        assertEquals(1, achat.getListMedAchat().size());
        assertTrue(achat.getListMedAchat().contains(medicament1));
        assertEquals(5.20, achat.getTotal(), 0.01);

        // Add med2
        achat.addMedAchat(medicament2);
        assertEquals(2, achat.getListMedAchat().size());
        assertTrue(achat.getListMedAchat().contains(medicament2));
        assertEquals(9.00, achat.getTotal(), 0.01);
    }

    @Test
    @DisplayName("Test d'ajout de médicament null")
    void testAjoutMedicamentNull() {
        achat = new Achat(LocalDate.of(2024, 1, 20), client);

        achat.addMedAchat(null);
        assertEquals(0, achat.getListMedAchat().size());
        assertEquals(0.0, achat.getTotal(), 0.01);
    }

    @Test
    @DisplayName("Test de calcul des montants avec mutuelle")
    void testCalculMontantsAvecMutuelle() {
        achat = new Achat(LocalDate.of(2024, 1, 20), client);
        achat.addMedAchat(medicament1); // 5.20€
        achat.addMedAchat(medicament2); // 3.80€

        // Total: 9.00€
        assertEquals(9.00, achat.getTotal(), 0.01);

        // Remboursement avec mutuelle 70%: 6.30€
        assertEquals(6.30, achat.getRemb(), 0.01);
    }

    @Test
    @DisplayName("Test de calcul des montants sans mutuelle")
    void testCalculMontantsSansMutuelle() {
        // Client sans mutuelle
        Client clientSansMutuelle = new Client("Martin", "Paul", "456 Avenue",
                69000, "Lyon", "0987654321", "paul@test.com",
                666777888999000L, LocalDate.of(1975, 5, 15), null, medecin);

        achat = new Achat(LocalDate.of(2024, 1, 20), clientSansMutuelle);
        achat.addMedAchat(medicament1); // 5.20€
        achat.addMedAchat(medicament3); // 4.50€

        // Total: 9.70€
        assertEquals(9.70, achat.getTotal(), 0.01);

        // Pas de remboursement sans mutuelle
        assertEquals(0.0, achat.getRemb(), 0.01);
    }

    @Test
    @DisplayName("Test de recalcul automatique des montants")
    void testRecalculAutomatiqueMontants() {
        achat = new Achat(LocalDate.of(2024, 1, 20), client);

        // Premier médicament
        achat.addMedAchat(medicament1);
        double total1 = achat.getTotal();
        double remb1 = achat.getRemb();

        // Deuxième médicament
        achat.addMedAchat(medicament2);
        double total2 = achat.getTotal();
        double remb2 = achat.getRemb();

        // Vérifier que les montants ont été recalculés
        assertTrue(total2 > total1);
        assertTrue(remb2 > remb1);
        assertEquals(9.00, total2, 0.01);
        assertEquals(6.30, remb2, 0.01);
    }

    @Test
    @DisplayName("Test d'identification d'achat direct vs ordonnance")
    void testIdentificationTypeAchat() {
        // Achat direct
        Achat achatDirect = new Achat(LocalDate.of(2024, 1, 20), client);
        assertNull(achatDirect.getOrdonnance());

        // Achat Ordo
        Achat achatOrdonnance = new Achat(LocalDate.of(2024, 1, 20), client, ordonnance);
        System.out.println(ordonnance);
        System.out.println(achatOrdonnance.getOrdonnance());
        assertNotNull(achatOrdonnance.getOrdonnance());
    }

    @Test
    @DisplayName("Test de la méthode toString pour achat direct")
    void testToStringAchatDirect() {
        achat = new Achat(LocalDate.of(2024, 1, 20), client);
        achat.addMedAchat(medicament1);

        String result = achat.toString();
        assertTrue(result.contains("Achat direct"));
        assertTrue(result.contains("2024-01-20"));
        assertTrue(result.contains("Dupont"));
        assertTrue(result.contains("5.2€"));
        assertTrue(result.contains("3.64€")); // Remboursement de 70% de 5.20€
    }

    @Test
    @DisplayName("Test de la méthode toString pour achat avec ordonnance")
    void testToStringAchatOrdonnance() {
        achat = new Achat(LocalDate.of(2024, 1, 20), client, ordonnance);

        String result = achat.toString();
        assertTrue(result.contains("Achat sur ordonnance"));
        assertTrue(result.contains("2024-01-20"));
        assertTrue(result.contains("Dupont"));
        assertFalse(result.contains("Achat direct"));
    }

    @Test
    @DisplayName("Test de gestion de liste immutable")
    void testListeMedicamentsImmutable() {
        achat = new Achat(LocalDate.of(2024, 1, 20), client);
        achat.addMedAchat(medicament1);

        // La méthode getListMedAchat() doit retourner une copie
        var medicaments = achat.getListMedAchat();
        int tailleBefore = medicaments.size();

        // Tentative de modification directe de la liste retournée
        medicaments.add(medicament2);

        // La liste interne de l'achat ne doit pas être modifiée
        assertEquals(tailleBefore, achat.getListMedAchat().size());
        assertFalse(achat.getListMedAchat().contains(medicament2));
    }

    @Test
    @DisplayName("Test de calcul avec médicaments gratuits")
    void testCalculAvecMedicamentsGratuits() {
        Medicament medicamentGratuit = new Medicament("Échantillon", catMed.VITAMINE, 0.0, "2023-01-01", 10);

        achat = new Achat(LocalDate.of(2024, 1, 20), client);
        achat.addMedAchat(medicament1); // 5.20€
        achat.addMedAchat(medicamentGratuit); // 0.00€

        assertEquals(5.20, achat.getTotal(), 0.01);
        assertEquals(3.64, achat.getRemb(), 0.01); // 70% de 5.20€
    }

    @Test
    @DisplayName("Test de calcul avec différents taux de remboursement")
    void testCalculDifferentsTauxRemboursement() {
        // Mutuelle avec taux différent
        Mutuelle mutuelle50 = new Mutuelle("Autre Mutuelle", 50.0);
        Client clientMutuelle50 = new Client("Test", "User", "789 Rue",
                13000, "Marseille", "0444555666", "test@test.com",
                999888777666555L, LocalDate.of(1985, 3, 20), mutuelle50, medecin);

        achat = new Achat(LocalDate.of(2024, 1, 20), clientMutuelle50);
        achat.addMedAchat(medicament1); // 5.20€

        assertEquals(5.20, achat.getTotal(), 0.01);
        assertEquals(2.60, achat.getRemb(), 0.01); // 50% de 5.20€
    }

    @Test
    @DisplayName("Test de modification de la date d'achat")
    void testModificationDateAchat() {
        achat = new Achat(LocalDate.of(2024, 1, 20), client);
        LocalDate nouvelleDate = LocalDate.of(2024, 2, 15);

        assertDoesNotThrow(() -> {
            achat.setDateAchat(nouvelleDate);
        });
        assertEquals(nouvelleDate, achat.getDateAchat());
    }

    @Test
    @DisplayName("Test de définition d'ordonnance null")
    void testDefinitionOrdonnanceNull() {
        achat = new Achat(LocalDate.of(2024, 1, 20), client, ordonnance);
        assertFalse(achat.IsAchatDirect());

        // Définir ordonnance à null devrait transformer l'achat en achat direct
        achat.setOrdonnance(null);
        assertTrue(achat.IsAchatDirect());
    }
}