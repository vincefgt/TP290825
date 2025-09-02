package controler;

import exception.SaisieException;
import model.Book;
import model.Subscriber;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryController {
    
    /*private ModernLibraryInterface view;
    
   // public LibraryController(ModernLibraryInterface view) {
        this.view = view;
    }*/
    
    public void addBook(String title, String authorFirst, String authorLast, int stock, long isbn) throws SaisieException {
        // Validate input
        if (title == null || title.trim().isEmpty()) {
            throw new SaisieException("Title cannot be empty");
        }
        if (authorFirst == null || authorFirst.trim().isEmpty()) {
            throw new SaisieException("Author first name cannot be empty");
        }
        if (authorLast == null || authorLast.trim().isEmpty()) {
            throw new SaisieException("Author last name cannot be empty");
        }
        if (stock < 0) {
            throw new SaisieException("Stock cannot be negative");
        }
        
        // Check if book with same ISBN already exists
        Book existingBook = Main.SearchIsbn(isbn);
        if (existingBook != null) {
            throw new SaisieException("A book with this ISBN already exists");
        }
        
        // Create new book
        new Book(authorFirst, authorLast, title, stock, isbn, null, null);
    }
    
    public void addSubscriber(String firstName, String lastName, String email) throws SaisieException {
        // Validate input
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new SaisieException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new SaisieException("Last name cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new SaisieException("Email cannot be empty");
        }
        if (!isValidEmail(email)) {
            throw new SaisieException("Invalid email format");
        }
        
        // Check if subscriber with same email already exists
        boolean emailExists = Subscriber.getListSubscriber().stream()
            .anyMatch(sub -> sub.getEmail().equalsIgnoreCase(email.trim()));
        
        if (emailExists) {
            throw new SaisieException("A subscriber with this email already exists");
        }
        
        // Create new subscriber
        new Subscriber(firstName, lastName, LocalDateTime.now(), email);
    }
    
    public void borrowBook(long isbn) throws SaisieException {
        Book book = Main.SearchIsbn(isbn);
        if (book == null) {
            throw new SaisieException("Book not found");
        }
        
        if (book.getStock() <= 0) {
            throw new SaisieException("Book is not available for borrowing");
        }
        
        Main.newBorrowingBook(isbn);
    }
    
    public void returnBook(long isbn) throws SaisieException {
        Book book = Main.SearchIsbn(isbn);
        if (book == null) {
            throw new SaisieException("Book not found");
        }
        
        // Check if book is actually borrowed
        boolean isBorrowed = Book.getListBooksBorrowed().stream()
            .anyMatch(b -> b.getIsbn() == isbn);
        
        if (!isBorrowed) {
            throw new SaisieException("This book is not currently borrowed");
        }
        
        Main.returnBook(isbn);
    }
    
    public List<Book> searchBooksByTitle(String title) {
        return Book.getListBooks().stream()
            .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    public List<Book> searchBooksByAuthor(String authorName) {
        return Book.getListBooks().stream()
            .filter(book -> {
                String fullName = book.getFirstNameAuthor() + " " + book.getLastNameAuthor();
                return fullName.toLowerCase().contains(authorName.toLowerCase());
            })
            .collect(Collectors.toList());
    }
    
    public Book searchBookByISBN(long isbn) {
        return Main.SearchIsbn(isbn);
    }
    
    public int getTotalBooks() {
        return Book.getListBooks().size();
    }
    
    public int getAvailableBooks() {
        return (int) Book.getListBooks().stream()
            .filter(book -> book.getStock() > 0)
            .count();
    }
    
    public int getBorrowedBooksCount() {
        return Book.getListBooksBorrowed().size();
    }
    
    public int getTotalSubscribers() {
        return Subscriber.getListSubscriber().size();
    }
    
    public long getTotalAuthors() {
        return Book.getListBooks().stream()
            .map(book -> book.getFirstNameAuthor() + " " + book.getLastNameAuthor())
            .distinct()
            .count();
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && 
               email.indexOf("@") < email.lastIndexOf(".");
    }
    
    /*public void showNotification(String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(view, message, title, messageType);
        });
    }*/
}