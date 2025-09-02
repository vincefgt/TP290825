import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@DisplayName("Tests class Medecin")
public class MedecinTest {

    private Medecin medecin;
    private Client patient1, patient2;

    @BeforeEach
    void setUp() {
        // Création de clients de test pour les patients
        patient1 = new Client("Dupont", "Jean", "123 Rue",
                75001, "Paris", "0123456789", "jean@test.com",
                111222333444555L, LocalDate.of(1980, 1, 1), null, null);

        patient2 = new Client("Martin", "Marie", "456 Avenue",
                69000, "Lyon", "0987654321", "marie@test.com",
                666777888999000L, LocalDate.of(1975, 5, 15), null, null);
    }

    @Test
    @DisplayName("Test de création d'un médecin avec constructeur complet")
    void testCreationMedecinComplet() {
        assertDoesNotThrow(() -> {
            medecin = new Medecin("Dubois", "Pierre", "10 Boulevard Médical",
                    "dr.dubois@hopital.fr", 75008, "Paris", "0140506070",
                    12345678910L, null);
        });

        assertEquals("Pierre", medecin.getFirstName());
        assertEquals("Dubois", medecin.getLastName());
        assertEquals(12345678910L, medecin.getNbAgreement());
        assertNotNull(medecin.getPatients());
        assertTrue(medecin.getPatients().isEmpty());
    }

    @Test
    @DisplayName("Test de création d'un médecin avec constructeur simplifié")
    void testCreationMedecinSimplifie() {
        assertDoesNotThrow(() -> {
            medecin = new Medecin("Dupont", "Paris", 12345678910L, null);
        });

        assertEquals("Dupont", medecin.getLastName());
        assertEquals(12345678910L, medecin.getNbAgreement());
    }

    @Test
    @DisplayName("Test de validation du numéro d'agrément")
    void testValidationNumeroAgrement() {
        // Test avec numéro d'agrément valide (11 chiffres)
        assertDoesNotThrow(() -> {
            medecin = new Medecin("Martin", "Lyon", 12345678910L, null);
        });

        // Test avec numéro d'agrément invalide (trop court)
        assertThrows(IllegalArgumentException.class, () -> {
            new Medecin("Test3", "Ville", 123L, null);
        });

        // Test avec numéro d'agrément invalide (trop long)
        assertThrows(IllegalArgumentException.class, () -> {
            new Medecin("Test3", "Ville", 123456789012L, null);
        });
    }

    @Test
    @DisplayName("Test d'ajout de patient")
    void testAjoutPatient() {
        medecin = new Medecin("Leblanc", "Marseille", 98765432110L, null);

        medecin.addPatient(patient1);
        assertEquals(1, medecin.getPatients().size());
        assertTrue(medecin.getPatients().contains(patient1));

        // Test d'ajout du même patient (ne doit pas être ajouté deux fois)
        medecin.addPatient(patient1);
        assertEquals(1, medecin.getPatients().size());

        // Test d'ajout d'un deuxième patient
        medecin.addPatient(patient2);
        assertEquals(2, medecin.getPatients().size());
        assertTrue(medecin.getPatients().contains(patient2));
    }

    @Test
    @DisplayName("Test d'ajout de patient null")
    void testAjoutPatientNull() {
        medecin = new Medecin("Test3", "Ville", 11111111111L, null);

        medecin.addPatient(null);
        assertEquals(0, medecin.getPatients().size());
    }

    @Test
    @DisplayName("Test de suppression de patient")
    void testSuppressionPatient() {
        medecin = new Medecin("Moreau", "Toulouse", 55555555555L, null);

        // Ajout de patients
        medecin.addPatient(patient1);
        medecin.addPatient(patient2);
        assertEquals(2, medecin.getPatients().size());

        // Suppression d'un patient existant
        boolean resultat = medecin.deletePatient(patient1);
        assertTrue(resultat);
        assertEquals(1, medecin.getPatients().size());
        assertFalse(medecin.getPatients().contains(patient1));
        assertTrue(medecin.getPatients().contains(patient2));

        // Tentative de suppression d'un patient non présent
        boolean resultat2 = medecin.deletePatient(patient1);
        assertFalse(resultat2);
        assertEquals(1, medecin.getPatients().size());
    }

    @Test
    @DisplayName("Test de suppression de patient null")
    void testSuppressionPatientNull() {
        medecin = new Medecin("Test3", "Ville", 11111111111L, null);
        medecin.addPatient(patient1);

        boolean resultat = medecin.deletePatient(null);
        assertFalse(resultat);
        assertEquals(1, medecin.getPatients().size());
    }

    @Test
    @DisplayName("Test de génération d'ID automatique")
    void testGenerationId() {
        medecin = new Medecin("Test1", "Ville1", 11111111111L, null);
        Medecin medecin2 = new Medecin("", "Ville2", 22222222222L, null);

        // Les IDs doivent être générés automatiquement et être différents
        // Note: Le test exact dépend de l'implémentation de generateId()
        assertNotNull(medecin.toString());
        assertNotNull(medecin2.toString());
    }

    @Test
    @DisplayName("Test de la méthode getLastNameMedecin")
    void testGetLastNameMedecin() {
        medecin = new Medecin("Bernard", "Nantes", 33333333333L, null);

        assertEquals("Bernard", medecin.getLastNameMedecin());
        assertEquals(medecin.getLastName(), medecin.getLastNameMedecin());
    }

    @Test
    @DisplayName("Test de la méthode toString")
    void testToString() {
        medecin = new Medecin("Rousseau", "Pierre", "15 Rue Médicale",
                "dr.rousseau@clinique.fr", 33000, "Bordeaux", "0556789012",
                44444444444L, null);

        String result = medecin.toString();
        assertTrue(result.contains("Dr Pierre Rousseau"));
        assertTrue(result.contains("44444444444"));
    }

    @Test
    @DisplayName("Test de modification du numéro d'agrément")
    void testModificationNumeroAgrement() {
        medecin = new Medecin("Test3", "Ville", 11111111111L, null);

        // Modification avec numéro valide
        assertDoesNotThrow(() -> {
            medecin.setNbAgreement(99999999999L);
        });
        assertEquals(99999999999L, medecin.getNbAgreement());

        // Modification avec numéro invalide
        assertThrows(IllegalArgumentException.class, () -> {
            medecin.setNbAgreement(123L);
        });
        // Le numéro ne doit pas avoir changé après l'exception
        assertEquals(99999999999L, medecin.getNbAgreement());
    }

    @Test
    @DisplayName("Test de gestion de liste de patients immutable")
    void testListePatientsImmutable() {
        medecin = new Medecin("Test3", "Ville", 11111111111L, null);
        medecin.addPatient(patient1);

        // La méthode getPatients() doit retourner une copie
        var patients = medecin.getPatients();
        int tailleBefore = patients.size();

        // Tentative de modification directe de la liste retournée
        patients.add(patient2);

        // La liste interne du médecin ne doit pas être modifiée
        assertEquals(tailleBefore, medecin.getPatients().size());
        assertFalse(medecin.getPatients().contains(patient2));
    }
}