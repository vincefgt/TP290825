package controler;

import exception.SaisieException;
import model.Book;
import model.Subscriber;
import view.AppBookRent;
import view.InputAndDisplay;
import view.actionDisplay;
import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SaisieException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");
        Main main = new Main();
        Main.start();
    }

    public static void start() throws SaisieException{
        init();
        //actionDisplay.displayListAuthors();
        SwingUtilities.invokeLater(()->{
                    AppBookRent appBookRent = new AppBookRent();
                    appBookRent.setVisible(true);
        });

        /**
         * boucle de recurrence Menu
         */
        boolean flag = false;
        do {
            actionDisplay.displayMenu();
        } while (!flag);
    }

    /**
     * Initialisation
     */
    public static void init() throws SaisieException {
        Book bk1 = new Book("JK", "Rowling", "Harry potter à l'école des Sorciers", 10, 97820751123131L,null,null);
        Book bk2 = new Book("JK", "Rowling", "Harry potter et la chambre de Secrets", 5, 97820751875415L,null,null);
        Subscriber sb1 = new Subscriber("Dupond", "Paul", LocalDateTime.now(), "dupond_paul@gmail.com");
    }
    public static void newBook(String firstNameAuthor, String lastNameAuthor, String title, int stock, long isbn) throws SaisieException {
    Book bk = new Book(actionDisplay.getFirstNameAuthor(),
            actionDisplay.getLastNameAuthor(),
            actionDisplay.getTitle(),
            actionDisplay.getStock(),
            actionDisplay.getIsbn(),null,null);
    actionDisplay.displayBookList();
    }
    public static void newBorrowingBook(long isbn) throws SaisieException {
        boolean c = false;
        actionDisplay.displaySearchBookIsbnResult(Objects.requireNonNull(SearchIsbn(isbn)));
        if (actionDisplay.displayConfirmationBorrowing()){;
            //set borrow date to return day
            Book bk = SearchIsbn(isbn);
            bk.setBorrowDateIn(LocalDate.now());
            bk.setStock(Objects.requireNonNull(SearchIsbn(isbn)).getStock()-1);
            Book.getListBooksBorrowed().add(bk);
            InputAndDisplay.message("Done !",5);
            InputAndDisplay.message(String.valueOf(bk.toString()+" startDay:"+bk.getBorrowDateIn()+
                    " returnDay:"+bk.getReturnDate()),5);
        }
    //actionDisplay.displayBorrowingList(); //List all borrow
    }
    public static void newSubscriber(String firstName, String lastName, LocalDateTime creationDate, String email){
    Subscriber sb = new Subscriber(actionDisplay.getFirstName(),actionDisplay.getLastName(),
            actionDisplay.getCreationDate(), actionDisplay.getEmail());
    actionDisplay.displaySubscriberList();
    }
    public static List<Book> SearchTitle(String title){
        List<Book> searchBook = new ArrayList<>();
        for (Book bk : Book.getListBooks()) {
            if (bk.getTitle().equalsIgnoreCase(title)) {
               searchBook.add(bk);
            }
        }
        return searchBook;
    }
    public static Book SearchIsbn(long isbn){
        List<Book> searchBook = new ArrayList<>();
        for (Book bk : Book.getListBooks()) {
            if (bk.getIsbn() == isbn) {
                searchBook.add(bk);
               return Book.MapIsbnBook.get(String.valueOf(bk.getIsbn()));
            }
        }
        return null;
    }
    public static void stateBook(long isbn){
        int nb = 0;
        InputAndDisplay.message("Stock available",3);
        for  (Book bk : Book.getListBooks()) {
            if (bk.getIsbn() == isbn) {
                InputAndDisplay.message(bk.getTitle()+" Qt:"+bk.getStock(),3);
            } //else { InputAndDisplay.error("not available");}

        }

        InputAndDisplay.message("Borrowed stock",3);
        for  (Book bk : Book.getListBooksBorrowed()) {
            if (bk.getIsbn() == isbn) {
                nb = nb +1;
                InputAndDisplay.message(bk.getTitle()+" returnDay:"+bk.getReturnDate(),3);
            } else { InputAndDisplay.error("not borrow");
            }
            InputAndDisplay.message("("+nb+")",3);
        }
    }
    public static void returnBook(long isbn) throws SaisieException {
        Objects.requireNonNull(SearchIsbn(isbn)).setStock(SearchIsbn(isbn).getStock()+1);
        Book.getListBooksBorrowed().remove(SearchIsbn(isbn));
        InputAndDisplay.message("Return saved !",3);
        actionDisplay.displayStockBook();
        actionDisplay.displayBorrowingList();
    }

} // END

/** isbn
 * 9782075187541
 * harry potter et la chambre de secrets
 */
