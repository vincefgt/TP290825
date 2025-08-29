package view;

import exception.SaisieException;
import model.Author;
import model.Book;
import model.Subscriber;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AppBookRent extends JFrame {

    private JPanel contentPane;
    private JPanel menu;
    private JLabel title;
    private JScrollPane scrollListBook;
    private JPanel titleFrame;
    private JList<Book> listBook;
    private JButton BOOKButton;
    private JButton SUBButton;
    private JButton AUTHORButton;
    private JButton BORROWButton;
    private JList<String> listAuthor;
    private JScrollPane scrollListAuthor;
    private JScrollPane scrollListSub;
    private JList<String> listSub;
    private JTextArea byVinceFgtTextArea;
    private JTextArea v100TextArea;
    private JScrollPane scrollListBorrow;
    private JList<String> listBorrow;
    private JTextField inputTitle;
    private JPanel bookPane;
    private JTextPane textTitle;
    private JPanel inputBook;
    private JTextPane textStock;
    private JTextField inputStock;
    private JTextPane textIsbn;
    private JTextField inputIsbn;
    private JTextPane textFirstName;
    private JTextField inputFirstName;
    private JTextPane textLastName;
    private JTextField inputLastName;
    private JButton buttonNewBook;
    private JPanel crud;
    private JButton MODIFIEDButton;
    private JButton DELETEButton;
    private JButton SEARCHButton;
    private JPanel lists;
    private JPanel mainBody;
    private JButton BORROWINGButton;
    private JPanel listsBooks;
    private JTable tableBook;
    private JScrollPane scrollTable;
    private String titleBorder;
    private DefaultListModel<Book> modelBook;
    private DefaultListModel<?> modelDefault;
    private int selectedIndex;
    private final String[] AttAuthor = new String[]{"FirstName", "LastName"};
    private JList<?> gotList;

    private final String[] h1Book = new String[]{"FirstNameAuthor", "LastNameAuthor","Title","ISBN","Stock"};

    //Constructor
    public AppBookRent() {
        Dimension dimension = new Dimension(1200, 700);

        this.setTitle("BOOKRENT");
        /**
         *         Mise en place de l'action de la croix par défaut
         */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // taille de la fenetre
        this.setPreferredSize(dimension);
        // ajout du contentPane à la fenetre
        this.setContentPane(contentPane);
        // redimensionnement interdit
        this.setResizable(false);
        // Set the frame location to the center of the screen
        //this.setLocationRelativeTo(null);
        this.setLocation(400, 250);
        // création de la fenêtre
        this.pack();
        this.setVisible(true);
        DataListAuthors();
        DataListBooks();
        DataListSubscribers();
        DataListBorrow();
        this.bookPane.setVisible(true); // setup by default

        /**
         * Listener
         * author
         * sub
         * book
         * newbook
         * borrow
         */
        AUTHORButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){actionAuthorButton();}
        });
        BOOKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){actionBookButton();}
        });
        SUBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){actionSubButton();}
        });
        BORROWButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){actionBorrowButton();}
        });
        buttonNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                try {
                    actionNewBookButton();
                } catch (SaisieException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){actionDeleteButton();}
        });
        MODIFIEDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){actionModifiedButton();}
        });
        SEARCHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){actionSearchButton();}
        });
        BORROWINGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){actionBorrowingButton();}
        });
    }

    /**
     * Create list swing
     * Authors
     * Borrow
     * Books
     * Subscribers
     */
    /*public void DataListAuthors() {
        DefaultListModel<String> modelAuthor = new DefaultListModel<>();
        for (Author author : Author.getListAuthors()) {
            modelAuthor.addElement(author.getFirstNameAuthor() + " " + author.getLastNameAuthor());
        }
        this.listAuthor.setModel(modelAuthor);
        String titleBorder = "(" + modelAuthor.size() + ")";
        this.scrollListAuthor.setBorder(setupBorderScroll(titleBorder));
    }*/
    public void DataListAuthors() {
        DefaultListModel<String> modelAuthor = new DefaultListModel<>();
        for (Author author : Author.getListAuthors()) {
            modelAuthor.addElement(author.getFirstNameAuthor() + " " + author.getLastNameAuthor());
        }
        this.listAuthor.setModel(modelAuthor);
        String titleBorder = "(" + modelAuthor.size() + ")";
        this.scrollListAuthor.setBorder(setupBorderScroll(titleBorder));
    }
    public void DataListBorrow() {
        DefaultListModel<String> modelBorrow = new DefaultListModel<>();
        for (Book bkb : Book.getListBooksBorrowed()) {
            modelBorrow.addElement(bkb.getTitle());
        }
        this.listBorrow.setModel(modelBorrow);
        this.scrollListBorrow.setBorder(setupBorderScroll(titleBorder));
    }
    public void DataListBooks() {
        modelBook = new DefaultListModel<Book>();
        for (Book bk : Book.getListBooks()) {
            modelBook.addElement(bk);
        }
        this.listBook.setModel(modelBook);
        titleCountItem(modelBook);
    }
    public void DataListSubscribers() {
        DefaultListModel<String> modelSub = new DefaultListModel<>();
        for (Subscriber subs : Subscriber.getListSubscriber()) {
            modelSub.addElement(subs.toString());
        }
        this.listSub.setModel(modelSub);
        String titleBorder = "(" + modelSub.size() + ")";
        this.scrollListSub.setBorder(setupBorderScroll(titleBorder));

    }

    /**
     * ActionButton
     * author
     * borrow
     * books
     * sub
     */
    private void actionAuthorButton(){
        allListHide();
        this.scrollListAuthor.setVisible(true);
        this.BORROWINGButton.setVisible(true);
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }
    private void actionBorrowButton(){
        allListHide();
        this.scrollListBorrow.setVisible(true);
        this.BORROWINGButton.setVisible(true);
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }
    private void actionBookButton(){
        allListHide();
        this.bookPane.setVisible(true);
        this.BORROWINGButton.setVisible(true);
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }
    private void actionSubButton(){
        allListHide();
        this.scrollListSub.setVisible(true);
        this.BORROWINGButton.setVisible(false); //hide borrow menu right
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }
    private void actionNewBookButton() throws SaisieException {
        Book nbk = new Book(inputFirstName.getText(),
                inputLastName.getText(),
                inputTitle.getText(),
                Integer.parseInt(inputStock.getText()),
                Long.parseLong(inputIsbn.getText()),
                null,null);
        modelBook.addElement(nbk);
        for (Component c : inputBook.getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField) c).setText("");
            }
        }
        //DataListBooks();
        contentPane.revalidate();
    }
    private void actionModifiedButton(){
        // tableau qui contient toutes tes JList
        JList<?>[] allLists = { listBook, listAuthor, listSub };

        for (JList<?> currentList : allLists) {
            int selectedIndex = currentList.getSelectedIndex();
            if (selectedIndex != -1) {  // if at least one item selected
                DefaultListModel<?> model = (DefaultListModel<?>) currentList.getModel();
                //DialogFrame.confirmButton("ARE YOU SURE?",true,true);
                if (DialogFrame.getResult()) {
                    //model.remove(currentList.getSelectedIndex());
                    // TODO: open new windows w data book
                    // TODO: get new data > save data to book
                    currentList.clearSelection();
                } else { currentList.clearSelection();}
            } else {
                DialogFrame.confirmButton("PLEASE SELECT ANY ITEM!",false,false,"Card1");
            }
            break;
        }
        contentPane.revalidate();
    }
    private void actionDeleteButton(){
        /*// tableau qui contient toutes tes JList
        JList<?>[] allLists = { listBook, listAuthor, listSub };
        for (JList<?> currentList : allLists) {
            int selectedIndex = currentList.getSelectedIndex();
            if (selectedIndex != -1) {  // if at least one item selected
                DefaultListModel<?> model = (DefaultListModel<?>) currentList.getModel();
                DialogFrame.confirmButton("ARE YOU SURE?",true,true);
                if (DialogFrame.getResult()) {
                    model.remove(currentList.getSelectedIndex());
                    titleCountItem(model);
                    currentList.clearSelection();
                } else { currentList.clearSelection();}
            } else {
            DialogFrame.confirmButton("PLEASE SELECT ANY ITEM!",false,false);
            }
        break;
        }*/
        if(getCurrentModel()!=null){
        DialogFrame.confirmButton("ARE YOU SURE?",true,true,"Card1");
            if (DialogFrame.getResult()) {
                modelDefault.remove(selectedIndex);
                titleCountItem(modelDefault);
            }
        }
        contentPane.revalidate();
        contentPane.repaint();
    }
    private void actionSearchButton(){
        DialogFrame.confirmButton("PLEASE INSERT ISBN NUMBER",true,true,"Card2");
    }
    private void actionBorrowingButton(){
        // tableau qui contient toutes tes JList
        JList<?>[] allLists = { listBook, listAuthor };

        for (JList<?> currentList : allLists) {
            int selectedIndex = currentList.getSelectedIndex();
            if (selectedIndex != -1) {  // if at least one item selected
                DefaultListModel<?> model = (DefaultListModel<?>) currentList.getModel();
                //DialogFrame.confirmButton("ARE YOU SURE?",true,true);
                if (DialogFrame.getResult()) {
                    //model.remove(currentList.getSelectedIndex());
                    // TODO:
                    currentList.clearSelection();
                } else { currentList.clearSelection();}
            } else {
                DialogFrame.confirmButton("PLEASE SELECT ANY ITEM!",false,false,"Card1");
            }
            break;
        }
        contentPane.revalidate();
    }

    /**
     * param extra
     */
    private void allListHide(){
        this.bookPane.setVisible(false);
        this.scrollListBorrow.setVisible(false);
        this.scrollListSub.setVisible(false);
        this.scrollListAuthor.setVisible(false);
    }
    public Border setupBorderScroll(String titleBorder) {
        TitledBorder border = BorderFactory.createTitledBorder(titleBorder);
        //border.setBorder(BorderFactory.createEmptyBorder());
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleFont(new Font("", Font.BOLD, 16));
        border.setTitleColor(new Color(229, 195, 183));
        border.setBorder(BorderFactory.createLineBorder(new Color(229, 195, 183)));
        return border;
    }
    public void titleCountItem(DefaultListModel<?> modelDefault){
        if (modelDefault.isEmpty()){
            titleBorder = "(empty)";
        } else {
            titleBorder = "(" + modelDefault.size() + ")"; }
        this.scrollListBook.setBorder(setupBorderScroll(this.titleBorder));
    }
    public DefaultListModel<?> getCurrentModel() {
        // tableau qui contient toutes tes JList
        JList<?>[] allLists = {listBook, listAuthor, listSub};
        for (JList<?> currentList : allLists) {
            selectedIndex = currentList.getSelectedIndex();
            if (selectedIndex != -1) {  // if at least one item selected
                modelDefault = (DefaultListModel<?>) currentList.getModel();
                currentList.clearSelection();
                break;
            }
        }
        if (modelDefault == null) {
            DialogFrame.confirmButton("PLEASE SELECT ANY ITEM!", false, false,"Card1");
        }
        return modelDefault;
    }

  /*  private <T> void configureTable(List<T> dataList, String[] headers, Class<?>[] columnClasses) {
        TableModel model = new TableModele(dataList, headers, columnClasses);
        this.table.setModel(model);
        this.table.revalidate();
        this.table.repaint();
    }*/

    private void constructDataTable(List<?> dataList, String[] headers) {

        // Création du tableau de données
        String[][] data = new String[Book.getListBooks().size()][h1Book.length];

        // Remplissage du tableau selon le type d'objet
        for (int i = 0; i < Book.getListBooks().size(); i++) {
            Book book = Book.getListBooks().get(i);
            data[i][0] = book.getFirstNameAuthor();
            data[i][1] = book.getLastNameAuthor();
            data[i][2] = book.getTitle();
            data[i][3] = String.valueOf(book.getIsbn());
            data[i][4] = String.valueOf(book.getStock());
            }

        // ajout de la table dans le scrollpane
        scrollTable.setViewportView(tableBook);
        // on redefinie le panelTable avec les modifications
        contentPane.revalidate();
        contentPane.repaint();

    }

}
