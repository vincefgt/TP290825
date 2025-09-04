import controller.PharmacieController;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la classe Mutuelle")
class MutuelleTest {
    private List<Mutuelle> listMutuelles;

@BeforeEach
void setup(){
    PharmacieController controller = new PharmacieController();
}


    @Test
    @DisplayName("Test de création d'une mutuelle avec constructeur complet")
    void testCreationMutuelleComplete() {
        assertDoesNotThrow(() -> {
            Mutuelle mutuelle = new Mutuelle("Mutuelle", "MGEN", "123 Avenue Mutuelle",
                    "contact@mgen.fr", 75015, "Paris", "0145678901", 70.0);
            assertEquals("Mgen", mutuelle.getLastName());
            assertEquals(70.0, mutuelle.getTauxRemb());
        });
    }

    @Test
    @DisplayName("Test de création d'une mutuelle avec constructeur simplifié")
    void testCreationMutuelleSimplifie() {
        assertDoesNotThrow(() -> {
            Mutuelle mutuelle = new Mutuelle("Harmonie Mutuelle", 60.0);
            assertEquals("Harmonie Mutuelle", mutuelle.getLastName());
            assertEquals(60.0, mutuelle.getTauxRemb());
        });
    }

    @Test
    @DisplayName("Test de validation du taux de remboursement valide")
    void testValidationTauxRemboursementValide() {
        // Test taux 0
        assertDoesNotThrow(() -> {
            Mutuelle mutuelle = new Mutuelle("Test Mutuelle", 0.0);
            assertEquals(0.0, mutuelle.getTauxRemb());
        });

        // Test taux 100
        assertDoesNotThrow(() -> {
            Mutuelle mutuelle = new Mutuelle("Test Mutuelle", 100.0);
            assertEquals(100.0, mutuelle.getTauxRemb());
        });

        // Test taux moyen
        assertDoesNotThrow(() -> {
            Mutuelle mutuelle = new Mutuelle("Test Mutuelle", 65.5);
            assertEquals(65.5, mutuelle.getTauxRemb());
        });
    }

    @Test
    @DisplayName("Test de validation du taux de remboursement invalide")
    void testValidationTauxRemboursementInvalide() {
        // Test avec taux négatif
        assertThrows(IllegalArgumentException.class, () -> {
            new Mutuelle("Test Mutuelle", -10.0);
        });

        // Test avec taux supérieur à 100%
        assertThrows(IllegalArgumentException.class, () -> {
            new Mutuelle("Test Mutuelle", 105.0);
        });
    }

    @Test
    @DisplayName("Test de modification du taux de remboursement")
    void testModificationTauxRemboursement() {
        Mutuelle mutuelle = new Mutuelle("MAAF", 50.0);

        // Modification avec taux valide
        assertDoesNotThrow(() -> {
            mutuelle.setTauxRemb(75.0);
        });
        assertEquals(75.0, mutuelle.getTauxRemb());

        // Tentative de modification avec taux invalide
        assertThrows(IllegalArgumentException.class, () -> {
            mutuelle.setTauxRemb(120.0);
        });
        // Le taux ne doit pas avoir changé après l'exception
        assertEquals(75.0, mutuelle.getTauxRemb());
    }

    @Test
    @DisplayName("Test de calcul de remboursement")
    void testCalculRemboursement() {
        Mutuelle mutuelle = new Mutuelle("GMF", 80.0);

        // Test avec montant positif
        double remboursement1 = mutuelle.calcRemb(100.0);
        assertEquals(80.0, remboursement1, 0.01);

        // Test avec montant décimal
        double remboursement2 = mutuelle.calcRemb(57.35);
        assertEquals(45.88, remboursement2, 0.01);

        // Test avec montant zéro
        double remboursement3 = mutuelle.calcRemb(0.0);
        assertEquals(0.0, remboursement3, 0.01);
    }

    @Test
    @DisplayName("Test calc remb 0%")
    void testCalculRemboursementTauxZero() {
        Mutuelle mutuelle = new Mutuelle("Aucun Remboursement", 0.0);

        double remboursement = mutuelle.calcRemb(150.0);
        assertEquals(0.0, remboursement, 0.01);
    }

    @Test
    @DisplayName("Test calc remb 100%")
    void testCalculRemboursementTauxMax() {
        Mutuelle mutuelle = new Mutuelle("Remboursement Total", 100.0);

        double remboursement = mutuelle.calcRemb(87.50);
        assertEquals(87.50, remboursement, 0.01);
    }

    @Test
    @DisplayName("Test de calcul de remboursement avec montant négatif")
    void testCalcRembMontantNegatif() {
        Mutuelle mutuelle = new Mutuelle("TestN", 70.0);

        assertThrows(IllegalArgumentException.class, () -> {
            mutuelle.calcRemb(-50.0);
        });
    }

    @Test
    @DisplayName("Test de calcul de remboursement avec différents taux")
    void testCalculRemboursementDifferentsTaux() {
        double montant = 200.0;

        // Test avec différents taux
        Mutuelle mutuelle20 = new Mutuelle("TestA", 20.0);
        assertEquals(40.0, mutuelle20.calcRemb(montant), 0.01);

        Mutuelle mutuelle50 = new Mutuelle("TestB", 50.0);
        assertEquals(100.0, mutuelle50.calcRemb(montant), 0.01);

        Mutuelle mutuelle90 = new Mutuelle("TestC", 90.0);
        assertEquals(180.0, mutuelle90.calcRemb(montant), 0.01);
    }

    @Test
    @DisplayName("Test de gestion du département")
    void testGestionDepartement() {
        Mutuelle mutuelle = new Mutuelle("Test Dept", 60.0);

        // Test de définition du département
        assertDoesNotThrow(() -> {
            mutuelle.setDep(Dep.PARIS);
        });
        assertEquals(Dep.PARIS, mutuelle.getDep());

        // Test avec différents départements
        mutuelle.setDep(Dep.RHONE);
        assertEquals(Dep.RHONE, mutuelle.getDep());
    }

    @Test
    @DisplayName("Test de la méthode toString")
    void testToString() {
        Mutuelle mutuelle = new Mutuelle("Crédit Mutuel", 65.5);
        String result = mutuelle.toString();
        assertTrue(result.contains("Crédit Mutuel"));
        assertTrue(result.contains("65.5%"));
    }

    //@Disabled
    @Test
    @DisplayName("Test cal remb 2 dec")
    void testCalRemb2Dec() {
        Mutuelle mutuelle = new Mutuelle("TestDoubleDec", 33.33);
        double montant = 99.99;
        double remb = mutuelle.calcRemb(montant);
        double expected = 99.99 * 0.3333;
        assertEquals(expected, remb, 0.01);
    }
    @Test
    @DisplayName("Test valid dep null")
    void testValidationDepNull() {
        Mutuelle mutuelle = new Mutuelle("Test Null", 50.0);
        System.out.println(mutuelle.getDep());
        assertThrows(IllegalArgumentException.class, () -> {
            mutuelle.setDep(null);
        });
    }
}