package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Regex {

    static String paramRegex = "^(\\d)$";
    public static final String REGEX_MOTS2 = "^[\\p{L}]+([.\\- ][\\p{L}]*)*$";
    public static final String REGEX_MOTS = "^[\\p{L}][\\p{L} \\-']*[\\p{L}]$";
    public static final String REGEX_NSS = "^[12]\\d{2}(0[1-9]|1[0-2]|2[0-9])\\d{8}\\d{2}$";
    public static final String REGEX_DATE_NAISSANCE = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
    public static final String REGEX_DATE_BIRTH = "^(0[1-9]|[12][0-9]|3[01])([ \\/])?(0[1-9]|1[0-2])([ \\/])?([0-9]{4})$"; //12012025, 12/05/2025, 12 05 2025
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9]+([._%+-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$";
    public static final String REGEX_CODE_POSTAL = "\\d{5}";
    public static final String REGEX_TELEPHONE = "^\\+?\\d{10,15}$";
    public static final String REGEX_ADRESSE = "^\\d{1,4}\\s?(bis|ter)?\\s?(rue|avenue|boulevard|chemin|allée|impasse|route|place)\\s.+$";
    public static final String REGEX_VILLE = "^(Y|Eu|Lu|Ay|Oz|Uz|(?:\\p{L}+(?:[ '\\-’]\\p{L}+)+))$";
    public static final String REGEX_RPPS = "^10\\d{11}$"; //nbAgreement
    public static final String REGEX_AUTEUR = "^(?! )[A-Za-zÀ-ÖØ-öø-ÿ0-9''\"():;!?.,\\- ]+(?<! )$";
    public static final String REGEX_3WORDS = "^(?:[\\w\\d.,;!?'\\\\\"(){}\\[\\]\\-_:@#%&*+=<>|^`~]+(?:\\s+|$)){3}$";


    /**
     * String Empty or null
     */
    public static boolean testNoBlank(String input) {
        return input.isBlank();
    }

    /**
     * null or Empty
     * @param input
     * @return
     */
    public static <T> boolean testNotEmpty(T input) {
        return input == null || String.valueOf(input).trim().isEmpty(); // return true if found
    }
    /**
     * null Objet
     * @param input
     * @return
     */
    public static boolean testNullObj(Object input) {
        return input == null; // return true if found
    }

    /**
     * contain only number(s)
     * value search > true
     */
    private static Pattern pDigit2 = Pattern.compile(paramRegex);
    public static boolean testDigitLong(long input) {
        pDigit2 = Pattern.compile(paramRegex);
        return !pDigit2.matcher(String.valueOf(input).trim()).matches(); //return true if error found
    }

    /**
     * contain only number(s) with decimal
     * value search > true
     */
    private static Pattern pDigit = Pattern.compile(paramRegex);
    public static boolean testDigitDec(double input) {
        pDigit = Pattern.compile(paramRegex);
        return !pDigit.matcher(String.valueOf(input).trim()).matches(); //return true if error found
    }

    /**
     * test email contain ....@...'.'..
     */
    private static final Pattern pEmail = Pattern.compile(REGEX_EMAIL);
    public static boolean testEmail(String input) {
        return !pEmail.matcher(input).matches(); // = error
    }

    /**
     * contain x(3)word(s) all char/digit/symbol
     */
    private static final Pattern w3All = Pattern.compile(REGEX_3WORDS);
    public static boolean test3w(String input) {
        return !w3All.matcher(input).matches(); //=> error = true
    }

    /**
     * valid 1 word contain only letter(char)
     * {x} > number word next first
     */
    private static final Pattern pChar = Pattern.compile(
            //"^(?:[\\w\\d.,()\\-_:@#&^]+)$" // w digit
            //"^(?:[a-zA-Z.,()\\-_:@#&^]+)$" // at least one letters +..
            //"^\\p{L}+$"  only lettre
            //"[a-zA-Z .-]+" // letters + "."," ","-"
            REGEX_MOTS2);
    public static boolean testChar(String input) {
        return !pChar.matcher(input).matches(); //= error
    }

    /**
     * valid seize book TITLE firstNameAuthor lasNameAuthor STOCK ISBN (5 items)
     */
    private static final Pattern pBook = Pattern.compile(
            "^(\\w+\\W+\\s*)+-\\s*[A-Za-z]+\\s+[A-Za-z]+\\s+\\d+\\s+\\d+$");
    public static boolean testBook(String input) {
        return !pBook.matcher(input).matches(); //= error
    }

    /**
     * date
     * @param pParamRegex
     */
    private static final Pattern dateTest = Pattern.compile(REGEX_DATE_BIRTH);
    public static boolean testDate(LocalDate input) {
        return !dateTest.matcher(String.valueOf(input.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))).matches(); //=> error = true
    }

    // ParamRegex is defined in class objet
    public static void setParamRegex(String pParamRegex) {
        paramRegex = pParamRegex;
    }
    public static Pattern getpDigit() {
        return pDigit;
    }
    public static void setpDigit(String paramRegex) {
        pDigit = Pattern.compile(paramRegex);
    }

    /**
    * anything undefined yet
     */
    private static Pattern patternD = Pattern.compile(paramRegex);
    public static <T> boolean testObjet(T input) {
        patternD = Pattern.compile(paramRegex);
        return !patternD.matcher(String.valueOf(input).trim()).matches(); //return true if error found
    }

/*public static void setParamRegex(String paramRegex) {
    Regex.paramRegex = paramRegex;
}*/
}


