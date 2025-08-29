package mmorpg.vue;
import static javax.swing.JOptionPane.*;


/**
 * Classe pour traiter les demandes d'affichage des boîtes de dialogue
 * @author jboebion
 */
public class DialogBox {

    /**
     * Afficher boite de dialogue int.
     *
     * @param message     the message
     * @param title       the titre
     * @param yesNoOption the yes no option
     * @return the int
     */
    public static int displayDialogBox(String message, String title, int yesNoOption) {
        return showConfirmDialog(null, message, title, yesNoOption);
    }
}
