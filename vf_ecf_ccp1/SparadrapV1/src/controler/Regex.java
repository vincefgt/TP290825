package controler;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Regex {

    static String paramRegex = "^(\\d)$";

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
    public static boolean testNotEmpty(String input) {
        return input == null || input.trim().isEmpty(); // return true if found
    }

    /**
     * contain only number(s)
     * value search > true
     */
    private static Pattern pDigit2 = Pattern.compile(paramRegex);
    public static boolean testDigit(long input) {
        pDigit2 = Pattern.compile(paramRegex);
        return !pDigit2.matcher(String.valueOf(input).trim()).find(); //return true if error found
    }

    /**
     * contain only number(s) with decimal
     * value search > true
     */
    private static Pattern pDigit = Pattern.compile(paramRegex);
    public static boolean testDigitDec(double input) {
        pDigit = Pattern.compile(paramRegex);
        return !pDigit.matcher(String.valueOf(input).trim()).find(); //return true if error found
    }

    /**
     * test email contain ....@...'.'..
     */
    private static final Pattern pEmail = Pattern.compile(
            "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$");
    public static boolean testEmail(String input) {
        return !pEmail.matcher(input).matches(); // = error
    }

    /**
     * contain x(3)word(s) all char/digit/symbol
     */
    private static final Pattern w3All = Pattern.compile(
            "^(?:[\\w\\d.,;!?'\\\"(){}\\[\\]\\-_:@#%&*+=<>|^`~]+(?:\\s+|$)){3}$");
    public static boolean test3w(String input) {
        return !w3All.matcher(input).find(); //=> error = true
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
            "^[\\p{L}]+([.\\- ][\\p{L}]*)*$"
    );
    //  "^[^\\W\\d_]+(?:\\s+[^\\W\\d_]+){0}$");
    public static boolean testChar(String input) {
        return !pChar.matcher(input).find(); //= error
    }

    /**
     * valid seize book TITLE firstNameAuthor lasNameAuthor STOCK ISBN (5 items)
     */
    private static final Pattern pBook = Pattern.compile(
            "^(\\w+\\W+\\s*)+-\\s*[A-Za-z]+\\s+[A-Za-z]+\\s+\\d+\\s+\\d+$");
    public static boolean testBook(String input) {
        return !pBook.matcher(input).find(); //= error
    }

    /**
     * date
     * @param pParamRegex
     */
    private static final Pattern dateTest = Pattern.compile(
            "^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$");
    public static boolean testDate(LocalDate input) {
        return !dateTest.matcher(String.valueOf(input)).find(); //=> error = true
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


