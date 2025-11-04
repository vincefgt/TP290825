import controller.PharmacieController;
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
    PharmacieController controller = new PharmacieController();

    // Création de clients de test pour les patients
    patient1 = new Client("Dupont", "Jean", "123 Rue",
            75001, "Paris", "0123456789", "jean@test.com",
            111222333444555L, LocalDate.of(1980, 1, 1), null, null);

    patient2 = new Client("Martin", "Marie", "456 Avenue",
            69000, "Lyon", "0987654321", "marie@test.com",
            666777888999000L, LocalDate.of(1975, 5, 15), null, null);
}

@Test
@DisplayName("Test creation medecin const1")
void testCreationMedecinComplet() {
    assertDoesNotThrow(() -> {
        medecin = new Medecin("Dubois", "Pierre", "10 Boulevard Médical",
                "dr.dubois@hopital.fr", 75008, "Paris", "0140506070",
                12345678910L, null);
    });

    assertEquals("PIERRE", medecin.getLastName());
    assertEquals("Dubois", medecin.getFirstName());
    assertEquals(12345678910L, medecin.getNbAgreement());
    assertNotNull(medecin.getPatients());
    assertTrue(medecin.getPatients().isEmpty());
}

@Test
@DisplayName("Test creation medecin const2")
void testCreationMedecin2() {
    assertDoesNotThrow(() -> {
        medecin = new Medecin("Dupont", "Paris", 12345678910L, null);
    });
    assertEquals("PARIS", medecin.getLastName());
    assertEquals(12345678910L, medecin.getNbAgreement());
}

@Test
@DisplayName("Test nAgreement")
void testValid_NAgreement() {
    // Test valide (11 chiffres)
    assertDoesNotThrow(() -> {
        medecin = new Medecin("Martin", "Lyon", 12345678910L, null);
    });

    // Test invalid (too short)
    assertThrows(IllegalArgumentException.class, () -> {
        new Medecin("TestShort", "Ville", 123L, null);
    });

    // Test invalid (too long)
    assertThrows(IllegalArgumentException.class, () -> {
        new Medecin("TestLong", "Ville", 123456789012L, null);
    });
}

@Test
@DisplayName("Test add patient")
void testAddPatient() {
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
@DisplayName("Test add patient null")
void testAddPatientNull() {
    medecin = new Medecin("TestpNull", "Ville", 11111111111L, "");

    medecin.addPatient(null);
    assertEquals(0, medecin.getPatients().size());
}

@Test
@DisplayName("Test supp patient")
void testSuppPatient() {
    medecin = new Medecin("Moreau", "Toulouse", 55555555555L, "");

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
@DisplayName("Test supp patient null")
void testSuppressionPatientNull() {
    medecin = new Medecin("TestsuppPatient", "Ville", 11111111111L, "");
    medecin.addPatient(patient1);

    boolean resultat = medecin.deletePatient(null);
    assertFalse(resultat);
    assertEquals(1, medecin.getPatients().size());
}

@Test
@DisplayName("Test ID auto")
void testGenerationId() {
    medecin = new Medecin("TestIdA", "TestA", 11111111111L, "");
    Medecin medecin2 = new Medecin("TestIdB", "TestB", 22222222222L, "");

    // IDs should be auto & diff
    assertNotNull(medecin.getIdMedecin());
    assertNotNull(medecin2.getIdMedecin());
}

@Test
@DisplayName("Test getLastNameMedecin")
void testGetLastNameMedecin() {
    assertDoesNotThrow(() -> {
        medecin = new Medecin("Dubois", "Pierre", "10 Boulevard Médical",
                "dr.dubois@hopital.fr", 75008, "Paris", "0140506070",
                12345678910L, null);
    });

    assertEquals("PIERRE", medecin.getLastName());
    assertEquals(medecin.getLastName(), medecin.getLastName());
}

@Test
@DisplayName("Test toString")
void testToString() {
    medecin = new Medecin("Pierre", "Rousseau", "15 Rue Médicale",
            "dr.rousseau@clinique.fr", 33000, "Bordeaux", "0556789012",
            44444444444L, "");

    String result = medecin.toString();
    assertTrue(result.contains("Dr ROUSSEAU Pierre"));
    assertFalse(result.contains("44444444444"));
}

@Test
@DisplayName("Test modif nAgrement")
void testModifNAgrement() {
    medecin = new Medecin("TestnAgreement", "Ville", 11111111111L, "");

    // Modif nb valid
    assertDoesNotThrow(() -> {
        medecin.setNbAgreement(99999999999L);
    });
    assertEquals(99999999999L, medecin.getNbAgreement());

    // Mod nb invalide
    assertThrows(IllegalArgumentException.class, () -> {
        medecin.setNbAgreement(123L);
    });
    assertEquals(99999999999L, medecin.getNbAgreement());
}

@Test
@DisplayName("Test lists patients immutable")
void testListePatientsImmutable() {
    medecin = new Medecin("TestLists", "Ville", 11111111111L, null);
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