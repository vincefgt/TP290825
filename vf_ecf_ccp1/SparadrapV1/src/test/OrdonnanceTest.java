import controller.PharmacieController;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@DisplayName("Tests clas Ordonnance")
public class OrdonnanceTest {
private Ordonnance ordonnance;
private Client patient;
private Medicament medicament1, medicament2, medicament3;

@BeforeEach
void setUp() {
    PharmacieController controller = new PharmacieController();
    patient = new Client("Jean", "Dupont", "123 Rue Test",
            75001, "Paris", "0123456789", "jean@test.com",
            111222333444555L, LocalDate.of(1980, 1, 1), null, null);
    medicament1 = new Medicament("Doliprane", catMed.ANTALGIQUE, 5.20, "2023-01-01", 50);
    medicament2 = new Medicament("Aspirine", catMed.ANALGESIQUE, 3.80, "2023-01-01", 30);
    medicament3 = new Medicament("Ibuprofène", catMed.ANTIINFLAMMATOIRE, 4.50, "2023-01-01", 25);
}

@Test
@DisplayName("Test crea ordo valid")
void testCreaOrdoeValid() {
    LocalDate dateOrdonnance = LocalDate.of(2024, 1, 15);

    assertDoesNotThrow(() -> {
        ordonnance = new Ordonnance("Dubois", "Pierre", "10 Rue Médical",
                "dr.dubois@hopital.fr", 75008, "Paris", "0140506070",
                12345678910L, null, dateOrdonnance, patient);
    });
    assertEquals(dateOrdonnance.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), ordonnance.getDate());
    assertEquals(patient, ordonnance.getPatient());
    assertNotNull(ordonnance.getListMedOrdo());
    assertTrue(ordonnance.getListMedOrdo().isEmpty());
}

@Test
@DisplayName("Test valid date ordo")
void testValidDateOrdo() {
    // Test date valide
    assertDoesNotThrow(() -> {
        ordonnance = new Ordonnance("Martin", "Paul", "5 Avenue Santé",
                "dr.martin@clinique.fr", 69000, "Lyon", "0472345678",
                98765432110L, null, LocalDate.of(2024, 2, 10), patient);
    });

    // Test date null
    assertThrows(NullPointerException.class, () -> {
        new Ordonnance("Test", "Test", "Test", "test@test.com",
                75001, "Paris", "0123456789", 11111111111L,
                null, null, patient);
    });

}

@Test
@DisplayName("Test de validation du patient")
void testValidationPatient() {
    LocalDate dateOrdonnance = LocalDate.of(2024, 1, 15);

    // Test patient null
    assertThrows(IllegalArgumentException.class, () -> {
        new Ordonnance("Test", "Test", "Test", "test@test.com",
                75001, "Paris", "0123456789", 11111111111L,
                null, dateOrdonnance, null);
    });
}

@Test
@DisplayName("Test d'ajout de médicament à l'ordonnance")
void testAjoutMedicament() {
    ordonnance = new Ordonnance("Leblanc", "Marie", "15 Boulevard Médical",
            "dr.leblanc@hopital.fr", 13000, "Marseille", "0491234567",
            55555555555L, null, LocalDate.of(2024, 1, 20), patient);

    // Ajout du premier médicament
    ordonnance.addMedOrdo(medicament1);
    assertEquals(1, ordonnance.getListMedOrdo().size());
    assertTrue(ordonnance.getListMedOrdo().contains(medicament1));

    // Ajout du deuxième médicament
    ordonnance.addMedOrdo(medicament2);
    assertEquals(2, ordonnance.getListMedOrdo().size());
    assertTrue(ordonnance.getListMedOrdo().contains(medicament2));

    // Tentative d'ajout du même médicament (ne doit pas être ajouté deux fois)
    ordonnance.addMedOrdo(medicament1);
    assertEquals(2, ordonnance.getListMedOrdo().size());
}

@Test
@DisplayName("Test d'ajout de médicament null")
void testAjoutMedicamentNull() {
    ordonnance = new Ordonnance("Test", "Test", "Test", "test@test.com",
            75001, "Paris", "0123456789", 11111111111L,
            null, LocalDate.of(2024, 1, 15), patient);

    ordonnance.addMedOrdo(null);
    assertEquals(0, ordonnance.getListMedOrdo().size());
}

@Test
@DisplayName("Test de suppression de médicament")
void testSuppressionMedicament() {
    ordonnance = new Ordonnance("Moreau", "Luc", "8 Rue Santé",
            "dr.moreau@clinique.fr", 31000, "Toulouse", "0534567890",
            77777777777L, null, LocalDate.of(2024, 1, 25), patient);

    // Ajout de médicaments
    ordonnance.addMedOrdo(medicament1);
    ordonnance.addMedOrdo(medicament2);
    ordonnance.addMedOrdo(medicament3);
    assertEquals(3, ordonnance.getListMedOrdo().size());

    // Suppression d'un médicament existant
    boolean resultat = ordonnance.suppMedOrdo(medicament2);
    assertTrue(resultat);
    assertEquals(2, ordonnance.getListMedOrdo().size());
    assertFalse(ordonnance.getListMedOrdo().contains(medicament2));
    assertTrue(ordonnance.getListMedOrdo().contains(medicament1));
    assertTrue(ordonnance.getListMedOrdo().contains(medicament3));

    // Tentative de suppression d'un médicament non présent
    boolean resultat2 = ordonnance.suppMedOrdo(medicament2);
    assertFalse(resultat2);
    assertEquals(2, ordonnance.getListMedOrdo().size());
}

@Test
@DisplayName("Test de calcul du prix total de l'ordonnance")
void testCalculPrixTotal() {
    ordonnance = new Ordonnance("Bernard", "Sophie", "20 Avenue Médecine",
            "dr.bernard@hopital.fr", 33000, "Bordeaux", "0556789012",
            44444444444L, null, LocalDate.of(2024, 2, 1), patient);

    // Ordonnance vide
    assertEquals(0.0, ordonnance.calculerPrixTotal(), 0.01);

    // Ajout de médicaments
    ordonnance.addMedOrdo(medicament1); // 5.20€
    assertEquals(5.20, ordonnance.calculerPrixTotal(), 0.01);

    ordonnance.addMedOrdo(medicament2); // 3.80€
    assertEquals(9.00, ordonnance.calculerPrixTotal(), 0.01);

    ordonnance.addMedOrdo(medicament3); // 4.50€
    assertEquals(13.50, ordonnance.calculerPrixTotal(), 0.01);
}

@Test
@DisplayName("Test de calcul du prix total avec médicaments gratuits")
void testCalculPrixTotalAvecMedicamentsGratuits() {
    // Création d'un médicament gratuit
    Medicament medicamentGratuit = new Medicament("Échantillon", catMed.VITAMINE, 0.0, "2023-01-01", 10);

    ordonnance = new Ordonnance("Test", "Test", "Test", "test@test.com",
            75001, "Paris", "0123456789", 11111111111L,
            null, LocalDate.of(2024, 1, 15), patient);

    ordonnance.addMedOrdo(medicament1); // 5.20€
    ordonnance.addMedOrdo(medicamentGratuit); // 0.00€

    assertEquals(5.20, ordonnance.calculerPrixTotal(), 0.01);
}

@Test
@DisplayName("Test modif date")
void testModifDate() {
    ordonnance = new Ordonnance("Test", "Test", "Test", "test@test.com",
            75001, "Paris", "0123456789", 11111111111L,
            null, LocalDate.of(2024, 1, 15), patient);

    LocalDate nouvelleDate = LocalDate.of(2024, 2, 20);

    assertDoesNotThrow(() -> {
        ordonnance.setDate(nouvelleDate);
    });
    assertEquals(nouvelleDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), ordonnance.getDate());
}

@Test
@DisplayName("Test modif patient")
void testModifPatient() {
    ordonnance = new Ordonnance("Test", "Test", "Test", "test@test.com",
            75001, "Paris", "0123456789", 11111111111L,
            null, LocalDate.of(2024, 1, 15), patient);

    Client nouveauPatient = new Client("Martin", "Marie", "456 Avenue",
            69000, "Lyon", "0987654321", "marie@test.com",
            666777888999000L, LocalDate.of(1975, 5, 15), null, null);

    assertDoesNotThrow(() -> {
        ordonnance.setPatient(nouveauPatient);
    });
    assertEquals(nouveauPatient, ordonnance.getPatient());

    // Test avec patient null
    assertThrows(IllegalArgumentException.class, () -> {
        ordonnance.setPatient(null);
    });
}

@Test
@DisplayName("Test de la méthode toString")
void testToString() {
    Medecin medecin = new Medecin("Pierre", "Rousseau", "10 Boulevard Médical",
            "dr.dubois@hopital.fr", 75008, "Paris", "0140506070",
            12345678910L, null);
    ordonnance = new Ordonnance("Pierre", "Rousseau", "25 Rue Médical",
            "dr.rousseau@clinique.fr", 44000, "Nantes", "0240123456",
            33333333333L, null, LocalDate.of(2024, 1, 30), patient);

    ordonnance.addMedOrdo(medicament1);
    ordonnance.addMedOrdo(medicament2);

    String result = ordonnance.toString();
    assertTrue(result.contains("2024-01-30"));
    assertTrue(result.contains("Rousseau"));
    assertTrue(result.contains("Dupont")); // nom du patient
    assertTrue(result.contains("2 médicaments"));
}

@Test
@DisplayName("Test d'ordonnance avec plusieurs médicaments identiques en catégorie")
void testOrdonnanceMemeCategorie() {
    Medicament doliprane1 = new Medicament("Doliprane 500mg", catMed.ANTALGIQUE, 4.50, "2023-01-01", 40);
    Medicament doliprane2 = new Medicament("Doliprane 1000mg", catMed.ANTALGIQUE, 5.20, "2023-01-01", 30);

    ordonnance = new Ordonnance("Test", "Test", "Test", "test@test.com",
            75001, "Paris", "0123456789", 11111111111L,
            null, LocalDate.of(2024, 1, 15), patient);

    ordonnance.addMedOrdo(doliprane1);
    ordonnance.addMedOrdo(doliprane2);

    assertEquals(2, ordonnance.getListMedOrdo().size());
    assertEquals(9.70, ordonnance.calculerPrixTotal(), 0.01);
}
}