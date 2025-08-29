package view;

import controler.Main;
import utilities.Regex;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static view.actionDisplay.isbn;

public class DialogFrame extends JDialog {
    public static boolean Result;
    private JPanel contentPane2;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel textLabel;
    private JPanel dialogBoxOkC;
    private JPanel ButtonsGrid;
    private JPanel labelPan;
    private JPanel searchButton;
    private JButton searchButtonBox;
    private JTextField textInputBox;
    private JLabel invalidNumber;
    private String cardName;
    private CardLayout cardLayout;
    private Card card;

    public DialogFrame(String pTextLabel) {
        setContentPane(contentPane2);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.setTextLabel(pTextLabel);
        this.setLocationRelativeTo(null);
        this.setTitle("CONFIRM DELETE BOOK");
        this.setResizable(false);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        searchButtonBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){actionSearchIsbn();}
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane2.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void actionSearchIsbn() {
            isbn = Long.parseLong(this.textInputBox.getText());
            actionDisplay.paramRegex = "^(?:\\d){14}$";
            Regex.setParamRegex(actionDisplay.paramRegex);
            if (Regex.testDigit(isbn) || Regex.testEmptyBlank(String.valueOf(isbn))){
                this.invalidNumber.setVisible(true);
            }else {
        Main.SearchIsbn(isbn);}
    }
    private void onOK() {
        Result = true;
        dispose();
    }
    private void onCancel() {
        // add your code here if necessary
        Result = false;
        dispose();
    }
    public void setTextLabel(String pTextLabel) {
        this.textLabel.setText(pTextLabel);
    }
    public static boolean getResult() {
        return Result;
    }
    public static void confirmButton(String pTextLabel,boolean bCancel,boolean bOk,String cardName) {
        DialogFrame dialog = new DialogFrame(pTextLabel);
        dialog.buttonCancel.setVisible(bCancel);
        dialog.buttonOK.setVisible(bOk);
        dialog.displayCard(dialog.contentPane2,cardName);

        //always at end
        dialog.pack();
        dialog.setVisible(true);
    }
    public void displayCard(JPanel panel,String cardName) {
        CardLayout layout = new CardLayout();
        this.ButtonsGrid.setLayout(layout);
        // ⚡ Ajouter les panneaux dans ButtonsGrid
        this.ButtonsGrid.add(this.dialogBoxOkC, "Card1");
        this.ButtonsGrid.add(this.searchButton, "Card2");
        layout.show(this.ButtonsGrid, cardName);
    }

    public static void main(String[] args) {
        DialogFrame dialog = new DialogFrame("ToEdit");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
