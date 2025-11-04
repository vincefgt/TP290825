import controller.PharmacieController;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@DisplayName("Tests de la classe Client")
public class ClientTest {

private Client client;
private Mutuelle mutuelle;
private Medecin medecin;

@BeforeEach
void setUp() {
    PharmacieController controller = new PharmacieController();
    // Initialisation des objets de test avant chaque test
    mutuelle = new Mutuelle("MGEN", 70.0);
    medecin = new Medecin("Dupont", "Paris", 12345678910L, null);
}

@Test
@DisplayName("Test de création d'un client valide")
void testCreationClientValide() {
    assertDoesNotThrow(() -> {
        client = new Client("Dupont", "Jean", "123 Rue de la République",
                75001, "Paris", "0123456789", "jean.dupont@email.com",
                111222333444555L, LocalDate.of(1980, 6, 15), mutuelle, medecin);
    });

    assertEquals("JEAN", client.getLastName());
    assertEquals("Dupont", client.getFirstName());
    assertEquals(111222333444555L, client.getNbSS());
    assertEquals(mutuelle, client.getMutuelle());
    assertEquals(medecin, client.getMedecinTraitant());
}

@Test
@DisplayName("Test de validation du numéro de sécurité sociale")
void testValidationNumeroSS() {
    // Test avec numéro SS valide
    assertDoesNotThrow(() -> {
        client = new Client("Dupont", "Jean", "123 Rue Test",
                75001, "Paris", "0123456789", "jean@test.com",
                123456789012321L, LocalDate.of(1980, 1, 1), null, null);
    });

    // Test avec numéro SS invalide (trop court)
    assertThrows(IllegalArgumentException.class, () -> {
        new Client("Martin", "Paul", "456 Avenue Test",
                69000, "Lyon", "0987654321", "paul@test.com",
                123L, LocalDate.of(1975, 5, 10), null, null);
    });
}

@Test
@DisplayName("Test de modification de la mutuelle")
void testModificationMutuelle() {
    client = new Client("Moreau", "Marie", "789 Boulevard Test",
            13000, "Marseille", "0555666777", "marie@test.com",
            987654321098765L, LocalDate.of(1985, 3, 20), null, null);

    assertNull(client.getMutuelle());

    client.setMutuelle(mutuelle);
    assertEquals(mutuelle, client.getMutuelle());
}

@Test
@DisplayName("Test de modification du médecin traitant")
void testModificationMedecinTraitant() {
    client = new Client("Bernard", "Pierre", "321 Rue Test",
            31000, "Toulouse", "0444555666", "pierre@test.com",
            555666777888999L, LocalDate.of(1970, 12, 25), null, null);

    assertNull(client.getMedecinTraitant());

    client.setMedecinTraitant(medecin);
    assertEquals(medecin, client.getMedecinTraitant());
}

@Test
@DisplayName("Test de validation de l'email")
void testValidationEmail() {
    // Test avec email invalide
    assertThrows(IllegalArgumentException.class, () -> {
        new Client("Test3", "User", "123 Rue",
                75001, "Paris", "0123456789", "email_invalide",
                123456789012345L, LocalDate.of(1990, 1, 1), null, null);
    });
}

@Test
@DisplayName("Test de validation des noms")
void testValidationNoms() {
    // Test avec prénom contenant des chiffres
    assertThrows(IllegalArgumentException.class, () -> {
        new Client("Jean123", "Dupont", "123 Rue",
                75001, "Paris", "0123456789", "test@email.com",
                123456789012345L, LocalDate.of(1990, 1, 1), null, null);
    });

    // Test avec nom vide
    assertThrows(IllegalArgumentException.class, () -> {
        new Client("", "Dupont", "123 Rue",
                75001, "Paris", "0123456789", "test@email.com",
                123456789012345L, LocalDate.of(1990, 1, 1), null, null);
    });
}

@Test
@DisplayName("Test de la méthode toString")
void testToString() {
    client = new Client("Dupont", "Jean", "123 Rue",
            75001, "Paris", "0123456789", "jean@test.com",
            111222333444555L, LocalDate.of(1980, 6, 15), null, null);

    String expected = "JEAN Dupont (N° SS: 111222333444555)";
    assertEquals(expected, client.toString());
}
}