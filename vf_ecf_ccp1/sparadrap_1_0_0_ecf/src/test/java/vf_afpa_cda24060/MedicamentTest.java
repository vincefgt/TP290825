package vf_afpa_cda24060;

import controller.PharmacieController;
import model.Medicament;
import model.catMed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests class Medicament")
public class MedicamentTest {
private Medicament medicament;

@BeforeEach
void setup(){
    PharmacieController controller = new PharmacieController();
}

@Test
@DisplayName("Test creation méd valide")
void testCreationMedicamentValide() {
    assertDoesNotThrow(() -> {
        medicament = new Medicament("Doliprane 1000mg", catMed.ANTALGIQUE, 5.20, "2023-01-01", 50);
    });

    assertEquals("Doliprane 1000mg", medicament.getNameMed());
    assertEquals(catMed.ANTALGIQUE, medicament.getCat());
    assertEquals(5.20, medicament.getPrice(), 0.01);
    assertEquals(50, medicament.getStock());
}

@Test
@DisplayName("Test de validation du nom du médicament")
void testValidationNomMedicament() {
    // Test avec nom vide
    assertThrows(IllegalArgumentException.class, () -> {
        new Medicament("", catMed.ANTALGIQUE, 5.20, "2023-01-01", 50);
    });

    // Test avec nom null
    assertThrows(IllegalArgumentException.class, () -> {
        new Medicament(null, catMed.ANTALGIQUE, 5.20, "2023-01-01", 50);
    });

    // Test avec nom contenant seulement des espaces
    assertThrows(IllegalArgumentException.class, () -> {
        new Medicament("   ", catMed.ANTALGIQUE, 5.20, "2023-01-01", 50);
    });
}

@Test
@DisplayName("Test de validation du prix")
void testValidationPrix() {
    // Test avec prix négatif
    assertThrows(IllegalArgumentException.class, () -> {
        new Medicament("Aspirine", catMed.ANALGESIQUE, -5.0, "2023-01-01", 30);
    });

    // Test avec prix zéro (devrait être accepté)
    assertDoesNotThrow(() -> {
        medicament = new Medicament("Aspirine", catMed.ANALGESIQUE, 0.0, "2023-01-01", 30);
    });
    assertEquals(0.0, medicament.getPrice());
}

@Test
@DisplayName("Test de validation du stock")
void testValidationStock() {
    // Test avec stock négatif
    assertThrows(IllegalArgumentException.class, () -> {
        new Medicament("Ibuprofène", catMed.ANTIINFLAMMATOIRE, 4.50, "2023-01-01", -10);
    });

    // Test avec stock zéro (devrait être accepté)
    assertDoesNotThrow(() -> {
        medicament = new Medicament("Ibuprofène", catMed.ANTIINFLAMMATOIRE, 4.50, "2023-01-01", 0);
    });
    assertEquals(0, medicament.getStock());
}

@Test
@DisplayName("Test de réduction de quantité valide")
void testReductionQuantiteValide() {
    medicament = new Medicament("Paracétamol", catMed.ANTALGIQUE, 3.50, "2023-01-01", 100);

    boolean resultat = medicament.reduireQuantite(20);
    assertTrue(resultat);
    assertEquals(80, medicament.getStock());
}

@Test
@DisplayName("Test de réduction de quantité avec stock insuffisant")
void testReductionQuantiteStockInsuffisant() {
    medicament = new Medicament("Vitamine C", catMed.VITAMINE, 8.90, "2023-01-01", 10);

    boolean resultat = medicament.reduireQuantite(20);
    assertFalse(resultat);
    assertEquals(10, medicament.getStock()); // Le stock ne doit pas changer
}

@Test
@DisplayName("Test de réduction de quantité exacte")
void testReductionQuantiteExacte() {
    medicament = new Medicament("Antibiotique", catMed.ANTIBIOTIQUE, 15.00, "2023-01-01", 5);

    boolean resultat = medicament.reduireQuantite(5);
    assertTrue(resultat);
    assertEquals(0, medicament.getStock());
}

@Test
@DisplayName("Test de réduction avec quantité négative")
void testReductionQuantiteNegative() {
    medicament = new Medicament("Sirop", catMed.ANTALGIQUE, 7.50, "2023-01-01", 25);

    assertThrows(IllegalArgumentException.class, () -> {
        medicament.reduireQuantite(-5);
    });
    assertEquals(25, medicament.getStock()); // Le stock ne doit pas changer
}

@Test
@DisplayName("Test de modification des propriétés")
void testModificationProprietes() {
    medicament = new Medicament("Médicament Test", catMed.ANALGESIQUE, 10.0, "2023-01-01", 50);

    // Modification du nom
    medicament.setNameMed("Nouveau Nom");
    assertEquals("Nouveau Nom", medicament.getNameMed());

    // Modification du prix
    medicament.setPrice(12.5);
    assertEquals(12.5, medicament.getPrice(), 0.01);

    // Modification du stock
    medicament.setStock(75);
    assertEquals(75, medicament.getStock());

    // Modification de la catégorie
    medicament.setCat(catMed.ANTIINFLAMMATOIRE);
    assertEquals(catMed.ANTIINFLAMMATOIRE, medicament.getCat());
}

@Test
@DisplayName("Test de la méthode toString")
void testToString() {
    medicament = new Medicament("Doliprane", catMed.ANTALGIQUE, 5.20, "2023-01-01", 50);

    String expected = "Doliprane (ANTALGIQUE) - Prix: 5.2€ - Stock: 50";
    assertEquals(expected, medicament.toString());
}

@Test
@DisplayName("Test avec différentes catégories de médicaments")
void testDifferentesCategories() {
    // Test avec différentes catégories
    assertDoesNotThrow(() -> {
        new Medicament("Antidépresseur", catMed.ANTIDEPRESSEUR, 25.0, "2023-01-01", 15);
        new Medicament("Insuline", catMed.DIABETIQUE, 45.0, "2023-01-01", 8);
        new Medicament("Vaccin", catMed.VACCINS, 35.0, "2023-01-01", 20);
    });
}
}