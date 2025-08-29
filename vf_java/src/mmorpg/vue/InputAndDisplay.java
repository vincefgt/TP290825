package mmorpg.vue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * The class for type Input and display.
 * Classe utilitaire pour le traitement des saisies et affichage console
 * @author jboebion
 */
public class InputAndDisplay {

    private InputAndDisplay() {}

    // Panel de couleur pour modification affichage
    // voir ANSI Color Java
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLUE = "\u001B[34m";

    // pour la gestion du scanner
    private static Scanner scan = new Scanner(System.in);

    public static Scanner getScan() {
        return scan;
    }

    /**
     * Methode d'affichage d'un message et lecture saisie de type chaine de caractère
     *
     * @param message the message
     * @return la saisie lue
     */
    public static String getString(String message) {

        System.out.println(message);
        return scan.nextLine();
    }

    /**
     * Methode d'affichage d'une demande de saisie d'un entier
     *
     * @param message the message
     * @return the int
     */
    public static int getInt(String message) {

        int saisie;

        System.out.println(message);
        saisie =  scan.nextInt();

        // pour palier le soucis de nextline() précédé d'un nextInt()
        scan.nextLine();
        return saisie;
    }

    /**
     * Methode d'affichage d'une demande de saisie d'un double
     *
     * @param message the message
     * @return double
     */
    public static double getDouble(String message) {

        System.out.println(message);
        return scan.nextDouble();
    }

    public static void DisplayTitleExo(String welcome, String titre) {
        message(welcome, 3);
        message(titre, 3);
        message("-----------------------------------------", 4);
        blank();
    }

    /**
     * Methode de femerture du scanner
     */
    public static void close() {
        scan.close();
    }

    /**
     * Methode d'affichage d'une message informatif
     * type par la couleur selon le type de message.
     *
     * @param message the message
     * @param type    the type
     */
    public static void message(String message, int type) {
        Display(message, type);
    }

    /**
     * Methode d'affichage d'une ligne vide
     */
    public static void blank() {
        System.out.println();
    }

    /**
     * Methode d'affichage d'une matrice
     * 1er ligne et 1er colonne en couleur différente du reste du contenu
     * @param matrice the matrice
     */
   /* public static void displayMatrice(int [][] matrice) {
        for (int i = 0; i < matrice.length; i++) {
            // traitement de la 1er ligne
            if (i == 0) {
                for (int j = 0; j < matrice[i].length; j++) {
                    System.out.print(ANSI_BLUE + String.format("%02d",  matrice[i][j]) + " | " + ANSI_RESET);
                }
            } else {
                for (int j = 0; j < matrice[i].length; j++) {
                    // traitement 1er ligne et 1er colonne
                    if (j == 0) {
                        System.out.print(ANSI_BLUE + String.format("%02d",  matrice[i][j]) + " | " + ANSI_RESET);
                    } else {
                        System.out.print(ANSI_GREEN + String.format("%02d", matrice[i][j]) + " | " + ANSI_RESET);
                    }
                }
            }
            System.out.println();
        }
    }*/

    /**
     * Methode d'affichage d'une message d'erreur
     *
     * @param message the message
     */
    public static void error(String message) {
        System.err.println(message);
        System.out.println();
    }

    private static void Display(String message, int type ) {
        switch (type) {
            case 1:
                // affichage message informatif en vert + message + reset
                System.out.println(ANSI_GREEN + message + ANSI_RESET);
                break;
            case 2:
                // affichage message warming en jaune + message + reset
                System.out.println(ANSI_YELLOW + message + ANSI_RESET);
                break;
            case 3:
                // affichage message warming en violet + message + reset
                System.out.println(ANSI_PURPLE + message + ANSI_RESET);
                break;
            case 4:
                // affichage message warming en cyan + message + reset
                System.out.println(ANSI_CYAN + message + ANSI_RESET);
                break;
            case 5:
                // affichage message warming en cyan + message + reset
                System.out.println(ANSI_BLUE + message + ANSI_RESET);
                break;
            default:
                // affichage standard soit blanc + message + reset
                System.out.println(ANSI_WHITE + message + ANSI_RESET);
                break;
        }
    }

    /**
     * formate un LocalDate en string
     * @param date la date à formatter
     * @return une chaine de caractère dans le format demandé
     */
    public static String formattageDate(LocalDate date) {

        // créaton d'un formatteur version français
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // retour avec test dans le cas d'une date vide sinon la date formatés
        return date == null ? "pas de date connue" : date.format(formatter);

    }
}