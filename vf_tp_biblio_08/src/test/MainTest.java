package test;

import controler.Main;
import exception.SaisieException;
import model.Book;
import org.junit.jupiter.api.*;
import view.actionDisplay;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;



public class MainTest {
private Book bookTest;


    @BeforeEach
    void setUp() throws SaisieException {
         bookTest = new Book("test","test",
                "test",5,12345678910111L,null,null);
    }

    @AfterEach
    void tearDown() {
         bookTest = null;
    }

    @Test
    @DisplayName("create in/out date")
    void newBorrowingBook_Success() throws SaisieException {
        long isbn = 12345678910111L;

        String fakeInput = "y\n";
        System.setIn(new ByteArrayInputStream(fakeInput.getBytes()));

        Main.newBorrowingBook(isbn);

        assertNotEquals(null, bookTest.getBorrowDateIn());
        assertNotEquals(null, bookTest.getReturnDate());
        assertEquals(LocalDate.now().plusDays(7), bookTest.getReturnDate());
        assertEquals(LocalDate.now(), bookTest.getBorrowDateIn());
    }
}