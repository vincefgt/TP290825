package test2;

import controler.Regex;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@DisplayName("Tests de la classe Regex")
public class RegexTest {

    @Test
    @DisplayName("Test de validation de chaîne vide ou null")
    void testValidationChaineVide() {
        // Test avec chaîne vide
        assertTrue(Regex.testNotEmpty(""));
        assertTrue(Regex.testNotEmpty("   ")); // Espaces seulement

        // Test avec chaîne null
        assertTrue(Regex.testNotEmpty(null));

        // Test avec chaîne valide
        assertFalse(Regex.testNotEmpty("texte valide"));
        assertFalse(Regex.testNotEmpty("123"));
    }

    @Test
    @DisplayName("Test de validation de chaîne blank")
    void testValidationChaineBlank() {
        // Test avec chaîne vide
        assertTrue(Regex.testNoBlank(""));
        assertTrue(Regex.testNoBlank("   ")); // Espaces seulement

        // Test avec chaîne valide
        assertFalse(Regex.testNoBlank("texte valide"));
        assertFalse(Regex.testNoBlank("123"));
    }

    @Test
    @DisplayName("Test int")
    void testValidInt() {

        Regex.setParamRegex("^\\d$");
        assertFalse(Regex.testDigitLong(1L));
        assertFalse(Regex.testDigitLong(9L));
        assertTrue(Regex.testDigitLong(10L));
        assertTrue(Regex.testDigitLong(123L));

        Regex.setParamRegex("^\\d{4}$");
        assertFalse(Regex.testDigitLong(1234L));
        assertTrue(Regex.testDigitLong(123L)); // Trop court
        assertTrue(Regex.testDigitLong(12345L)); // Trop long
    }

    @Test
    @DisplayName("Test de validation de nombres décimaux")
    void testValidationNombresDecimaux() {
        Regex.setParamRegex("^\\d+(\\.\\d+)?$");
        assertFalse(Regex.testDigitDec(123.45));
        assertFalse(Regex.testDigitDec(0.5));
        assertFalse(Regex.testDigitDec(100.0));
    }

    @Test
    @DisplayName("Test de validation d'email")
    void testValidationEmail() {
        // valid
        assertFalse(Regex.testEmail("test@example.com"));
        assertFalse(Regex.testEmail("user.name@domain.co.uk"));
        assertFalse(Regex.testEmail("test123@test-domain.org"));

        // fail
        assertTrue(Regex.testEmail("email_invalide"));
        assertTrue(Regex.testEmail("@domain.com"));
        assertTrue(Regex.testEmail("user@"));
        assertTrue(Regex.testEmail("user.domain.com"));
        assertTrue(Regex.testEmail(""));
    }

    @Test
    @DisplayName("Test char")
    void testChar() {
        // valid
        assertFalse(Regex.testChar("Jean"));
        assertFalse(Regex.testChar("Marie-Claire"));

        // fail
        assertTrue(Regex.testChar("Jean123"));
        assertTrue(Regex.testChar("123"));
        assertTrue(Regex.testChar(""));
        assertTrue(Regex.testChar("Test@123"));
        assertTrue(Regex.testChar("O'Connor"));
    }

    @Test
    @DisplayName("Tes date")
    void testDate() {
        // Test avec dates valides
        LocalDate dateValide1 = LocalDate.of(2024, 1, 15);
        LocalDate dateValide2 = LocalDate.of(2023, 12, 31);

        // Note: Le test exact dépend de l'implémentation de testDate
        // qui utilise un pattern pour valider le format de date
        assertFalse(Regex.testDate(dateValide1));
        assertFalse(Regex.testDate(dateValide2));
    }

    @Test
    @DisplayName("Test set param regex")
    void testSetParamRegex() {
        // Test de modification du paramètre regex
        String originalRegex = "^\\d$";
        Regex.setParamRegex(originalRegex);

        // Vérification que le pattern est mis à jour
        assertNotNull(Regex.getpDigit());

        // Test avec un nouveau pattern
        String nouveauRegex = "^\\d{5}$";
        Regex.setParamRegex(nouveauRegex);
        Regex.setpDigit(nouveauRegex);

        assertNotNull(Regex.getpDigit());
    }

    @Test
    @DisplayName("Test de validation avec chaînes contenant des espaces")
    void testValidationAvecEspaces() {
        // Test testNotEmpty avec espaces
        assertTrue(Regex.testNotEmpty("   ")); // Espaces seulement
        assertFalse(Regex.testNotEmpty("  texte  ")); // Texte avec espaces

        // Test testChar avec espaces
        assertFalse(Regex.testChar("Jean Paul")); // Nom composé valide
        assertTrue(Regex.testChar("Jean123 Paul")); // Contient des chiffres
    }

    @Test
    @DisplayName("Test de cas limites")
    void testCasLimites() {
       // valid
        Regex.setParamRegex("^\\d$");
        assertFalse(Regex.testDigitLong(0L));
        Regex.setParamRegex("^\\d+\\.?\\d*$");
        assertFalse(Regex.testDigitDec(0.00));

        // fail
        assertTrue(Regex.testDigitLong(-1L));
    }


    @Test
    @DisplayName("Test null")
    void testInputNull() {
        // Test testNotEmpty avec null
        assertTrue(Regex.testNotEmpty(null));

        // Test testChar avec null
        assertThrows(Exception.class, () -> {
            Regex.testChar(null);
        });

        // Test testEmail avec null
        assertThrows(Exception.class, () -> {
            Regex.testEmail(null);
        });
    }
}