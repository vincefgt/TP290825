package view;

import controler.Main;
import exception.SaisieException;
import model.Author;
import model.Book;
import model.Subscriber;
import utilities.Regex;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Pattern;

public class actionDisplay {
    static Scanner sc = new Scanner(System.in);
    private static String title;
    private static int stock;
    public static long isbn;
    private static String firstNameAuthor;
    private static String lastNameAuthor;
    private static String email;
    private static String firstName;
    private static String lastName;
    public static int choiceMenu;
    public static boolean error = false;
    public static String paramRegex;

    public static void displayMenu() throws SaisieException {
        InputAndDisplay.message("------ MENU -----", 0);
        InputAndDisplay.message("1 - New Subscriber", 2);
        InputAndDisplay.message("2 - New Book", 4);
        InputAndDisplay.message("3 - New borrowing", 5);
        InputAndDisplay.message("4 - Subscribers List", 2);
        InputAndDisplay.message("5 - Books list", 4);
        InputAndDisplay.message("6 - Borrowings list", 5);
        InputAndDisplay.message("7 - Stock list", 3);
        InputAndDisplay.message("8 - State Book", 4);
        InputAndDisplay.message("9 - Return Book", 3);
        InputAndDisplay.message("-----------------", 0);

        do {
            choiceMenu = sc.nextInt();
            sc.nextLine();
           Regex.setParamRegex("^(\\d)$");
            if (Regex.testDigit(choiceMenu) || choiceMenu < 1 || choiceMenu > 9) {
                    InputAndDisplay.error("Please enter a valid number [1-9]");
                    error = true;
            } else {
                error = false;
            }
        } while (error);

        switch (choiceMenu) {
            case 1:
                displayNewSubscriber();
                displaySubscriberList();
                break;
            case 2:
                displayNewBook();
                break;
            case 3: displayNewBorrowingBook();
                break;
            case 4:
                displaySubscriberList();
                break;
            case 5:
                displayBookList();
                break;
            case 6:
                displayBorrowingList();
                break;
            case 7:
                displayStockBook();
                break;
            case 8:
                displayStateBook();
                break;
            case 9:
                displayReturnBook();
                break;
            default:
                break;
        }
    }

    /**
     * List
     */
    public static void displayBookList(){
    InputAndDisplay.message("ListOfBooks",3);
    for (Book bk : Book.getListBooks()) {
        InputAndDisplay.message(String.valueOf(bk), 3);
    }}
    public static void displayBorrowingList(){
        InputAndDisplay.message("ListOfBorrowing",4);
        for (Book bkb : Book.getListBooksBorrowed()) {
        InputAndDisplay.message(bkb+" startDay:"+bkb.getBorrowDateIn()+" returnDay:"+bkb.getReturnDate(), 4);
    }}
    public static void displaySubscriberList(){
        InputAndDisplay.message("ListOfSubscribers",5);
        for (Subscriber sb : Subscriber.getListSubscriber()) {
        InputAndDisplay.message(String.valueOf(sb), 5);
    }}

    /**
     * Action
     */
    public static void displayNewBook() throws SaisieException {
    InputAndDisplay.message("New Book saving",4);
    InputAndDisplay.message("ENTER: TITLE - firstNameAuthor LastNameAuthor STOCK ISBN" ,4);
    String newBook = sc.nextLine().trim();

    do{
        if (Regex.testBook(newBook)){
            InputAndDisplay.error("Invalid seizing");
            InputAndDisplay.error("Enter valid content/format(type)[TITLE -" +
                    " FIRST(letter only) LAST(letter only) STOCK(number) ISBN(13numbers)]");
        newBook = sc.nextLine().trim();
        }
    } while (Regex.testBook(newBook));

        String[]  dataBook= newBook.split("-",2);
        title = dataBook[0].toLowerCase();
        String[]  dataBook2= dataBook[1].trim().split("\\s+");
        firstNameAuthor = dataBook2[0].toLowerCase();
        lastNameAuthor = dataBook2[1].toLowerCase();
        stock = Integer.parseInt(dataBook2[2]);
        isbn = Long.parseLong(dataBook2[3]);

        do{
            if(Regex.testChar(firstNameAuthor)){
                InputAndDisplay.error("Enter valid firstNameAuthor[letter only]");
                firstNameAuthor = sc.nextLine().trim();
            }
        } while (Regex.testChar(firstNameAuthor));

        do{
            if(Regex.testChar(lastNameAuthor)){
                InputAndDisplay.error("Enter valid lastNameAuthor[letter only]");
                lastNameAuthor = sc.nextLine().trim();
            }
        } while (Regex.testChar(lastNameAuthor));

        do{
            paramRegex = "^\\d{1,3}$";
            if (Regex.testDigit(stock)||stock <=0){
                InputAndDisplay.error("Enter valid stock[3 numbers max]");
                stock = sc.nextInt();
                sc.nextLine();
            }
        } while (Regex.testDigit(stock));

        do{
            paramRegex = "^(?:\\d){13}$";
            Regex.setParamRegex(paramRegex);
            if (Regex.testDigit(isbn) || Regex.testEmptyBlank(String.valueOf(isbn))){
                InputAndDisplay.error("Enter valid isbn[13 numbers]");
                isbn = sc.nextLong();
                sc.nextLine();
            }
        } while (Regex.testDigit(isbn));

        //call create book
        Main.newBook(getTitle(),getFirstNameAuthor(),getLastNameAuthor(),getStock(),getIsbn());
    }
    public static void  displayNewSubscriber(){
        InputAndDisplay.message("New Subscriber saving",2);
        InputAndDisplay.message("ENTER: firstName / LastName / email",2);
        String newBook = sc.nextLine().trim(); //cleaning seize

        // def 3 word > first / last / email spacing by whitespace
        do {
            if (Regex.test3w(newBook)){
                InputAndDisplay.error("Enter invalid! 3 enter[first last email] needed");
                newBook = sc.nextLine().trim();
            }
        } while (Regex.test3w(newBook));

        String[]  dataBook= newBook.split("\\s+");
        firstName = dataBook[0].toLowerCase();
        lastName = dataBook[1].toLowerCase();
        email = dataBook[2].toLowerCase();

        do{
          if(Regex.testChar(firstName)){
              InputAndDisplay.error("Enter valid firstName[letter only]");
              firstName = sc.nextLine().trim();
          }
        } while (Regex.testChar(firstName));

        do{
            if(Regex.testChar(lastName)){
                InputAndDisplay.error("Enter valid LastName[letter only]");
                lastName = sc.nextLine().trim();
            }
        } while (Regex.testChar(lastName));

        // def test format email ...@...'.'...
        do{
           if (Regex.testEmail(email)){
               InputAndDisplay.error("Enter valid email");
               email = sc.nextLine().trim();
           }
        } while (Regex.testEmail(email));

        //call create book
        Main.newSubscriber(getFirstName(),getLastName(),getCreationDate(),getEmail());
    }
    public static void displayNewBorrowingBook() throws SaisieException {
        paramRegex = "^(?:\\d){13}$";
        InputAndDisplay.message("Enter Isbn !", 2);
        isbn = sc.nextLong();
        sc.nextLine();
        do{
            if (Regex.testDigit(isbn)){
                InputAndDisplay.error("Enter valid isbn [13 numbers]");
                isbn = sc.nextLong();
                sc.nextLine();
            }
        } while (Regex.testDigit(isbn));
        Main.newBorrowingBook(isbn);
    }

    /**
     * display
     */
    public static void displayStockBook(){
        InputAndDisplay.message("Stock available("+Book.getListBooks().size()+")",1);
        for  (Book bkb : Book.getListBooks()) {
            InputAndDisplay.message(bkb.getTitle()+" Qt: "+bkb.getStock()+" ex.", 1);
        }
    }
    public static void displayReturnBook() throws SaisieException {
        InputAndDisplay.message("Enter this Isbn",1);
        isbn = sc.nextLong();
        Main.returnBook(isbn);
    }
    public static void displaySearchBookIsbnResult(Book bkb){
        InputAndDisplay.message("Search Result",1);
        InputAndDisplay.message(bkb.getTitle()+" Qt: "+bkb.getStock()+" ex.", 1);
    }
    public static boolean displayConfirmationBorrowing() {
        InputAndDisplay.message("Confirm borrowing [y/n]", 0);
        //sc.nextLine();
        return sc.nextLine().equals("y");
    }
    public static void displayStateBook(){
        InputAndDisplay.message("Enter Isbn !",2);
        isbn = sc.nextLong();
        Main.stateBook(isbn);
    }

    public static void displayListAuthors(){
        InputAndDisplay.message("Author List ("+ Author.getListAuthors().size()+")",1);
        for  (Author authorBook : Author.getListAuthors()) {
            InputAndDisplay.message(authorBook.getFirstNameAuthor()+" "+authorBook.getLastNameAuthor(), 1);
        }
    }
    /*
     * not use
     *    public static void displaySearchBook(String title){
     *         InputAndDisplay.message("Search ("+Main.SearchTitle(title).size()+")",1);
     *         for (Book bkb : Main.SearchTitle(title)) {
     *             InputAndDisplay.message(bkb.getTitle()+" Qt: "+bkb.getStock()+" ex.", 1);
     *         }
     *     }
     */

    /**
     *  getInput
      */
    public static String getTitle() {
        return title;
    }
    public static String getFirstNameAuthor() {
        return firstNameAuthor;
    }
    public static String getLastNameAuthor() {
        return lastNameAuthor;
    }
    public static long getIsbn() {
        return isbn;
    }
    public static int getStock() {
        return stock;
    }
    public static String getEmail() {
        return email;
    }
    public static String getFirstName() {
        return firstName;
    }
    public static String getLastName() {
        return lastName;
    }
    public static LocalDateTime getCreationDate() {
        return LocalDateTime.now();
    }

    public static String getParamRegex() {
        return paramRegex;
    }

    /*public static void setScanner(Scanner newScanner) {
        sc = newScanner;
    }*/
}


