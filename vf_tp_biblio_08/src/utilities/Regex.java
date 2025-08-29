package utilities;

import java.util.regex.Pattern;

public class Regex {
    static String paramRegex = "^(\\d)$";

    /**
     * String Empty or null
     */
    public static boolean testEmptyBlank(String input) {
        return input.isBlank();
    }

    /**
     * contain only number(s)
     * value search > true
     */
    private static Pattern pDigit = Pattern.compile(paramRegex);
    public static boolean testDigit(long input) {
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
            "^(?:[a-zA-Z.,()\\-_:@#&^]+)$" // all letters +..
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

    // to define in Menu
    public static void setParamRegex(String pParamRegex) {
        paramRegex = pParamRegex;
    }

    public static Pattern getpDigit() {
        return pDigit;
    }

    public static void setpDigit(String paramRegex) {
        pDigit = Pattern.compile(paramRegex);
    }

    /*public static void setParamRegex(String paramRegex) {
        Regex.paramRegex = paramRegex;
    }*/
}
