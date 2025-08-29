package model;

import exception.SaisieException;
import utilities.Regex;
import java.time.LocalDate;
import java.util.*;

public class Book extends Author {
    private String title;
    private int stock = 0;
    private long isbn;
    private LocalDate borrowDate = null;
    private LocalDate returnDate = null;

    public Book(String firstNameAuthor, String lastNameAuthor,
                String title, int stock, long isbn, LocalDate borrowDate,
                LocalDate returnDate) throws SaisieException {
        super(firstNameAuthor, lastNameAuthor);
        this.setTitle(title);
        this.setStock(stock);
        this.setIsbn(isbn);
        this.setBorrowDateIn(borrowDate);
        this.setReturnDate(returnDate);
        Book.listBooks.add(this);
        Book.MapIsbnBook.put(String.valueOf(getIsbn()),this);

    }

    /**
     * Lists
     */
    static List<Book> listBooks = new ArrayList<>();
    static List<Book> listBooksBorrowed = new ArrayList<>();
    public static Map<String, Book> MapIsbnBook = new HashMap<>();

    /**
     * Getter/Setter
     */
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getStock() {
        return this.stock;
    }
    public void setStock(int stock) throws SaisieException {
        String paramRegex = "^\\d{1,3}$";
        Regex.setParamRegex(paramRegex);
        if (Regex.testDigit(stock)){
            throw new SaisieException("Invalid Stock[3 numbers max]");
        } else {
        this.stock = stock; }
        }
    public long getIsbn() {
        return this.isbn;
    }
    public void setIsbn(long isbn) throws SaisieException {
        String paramRegex = "^(?:\\d){14}$";
        Regex.setParamRegex(paramRegex);
        if (Regex.testDigit(isbn)){
            throw new SaisieException("Invalid isbn[14 numbers]");
        } else {
            this.isbn = isbn;}
    }
    public LocalDate getBorrowDateIn() {
        return borrowDate;
    }
    public void setBorrowDateIn(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
        setReturnDate(this.borrowDate);
    }
    public void setReturnDate(LocalDate borrowDate) {
        if (borrowDate!=null) {
        returnDate = this.borrowDate.plusDays(7);}
    }
    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    /**
     * getLists
     */
    public static List<Book> getListBooks() {
        return listBooks;
    }
    public static List<Book> getListBooksBorrowed() {
        return listBooksBorrowed;
    }

    @Override
    public String toString() {
        return "Book{ " + getTitle() +" } Author{ "+super.toString()+" } ISBN{ "+getIsbn()+" }";
    }

}
