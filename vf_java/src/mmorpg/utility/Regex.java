package mmorpg.utility;

import java.util.regex.Pattern;

public class Regex {
    public static String testEmptyBlank(String input) {
       if (input.isBlank() || input.isEmpty()) {
           System.err.println("test failed");
       }
        return input;
    }

    private static final Pattern digit = Pattern.compile("\\d"); // équivalent à [0-9]
    public static String testNumber(String input) {
        if (digit.matcher(input).find()) {
            System.err.println("test failed");
        }
        return input;
    }
    //^[[word]]+(?:\s\w+){1}$
    private static final Pattern nbWord = Pattern.compile("^\\w+(?:\\s\\w+){2}$"); // équivalent à [0-9]
    public static String testNumber2(String input) {
        if (nbWord.matcher(input).find()) {
            System.err.println("test failed");
        }
        return input;
    }

}
