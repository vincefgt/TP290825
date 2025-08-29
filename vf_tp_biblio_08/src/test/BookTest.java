package test;

import exception.SaisieException;
import model.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
private Book bookTest;

    @BeforeEach
    void setUp() throws SaisieException {
        bookTest = new Book("test","test","test",
                0,12345678910111L,null,null);
    }
    @AfterEach
    void tearDown() {
        bookTest = null;
    }

    @DisplayName("test Stock Fail")
    @ParameterizedTest(name = "stock <= {0} isn't 3numbers")
    @ValueSource(ints = {1234})
    void setStock_Fail(int stock) throws SaisieException {
        assertThrows(SaisieException.class, () -> {bookTest.setStock(stock);});
    }

    @DisplayName("test Stock Success")
    @ParameterizedTest(name = "stock <= {0} is 1<number>3")
    @ValueSource(ints = {123,1,12})
    void setStock_Success(int stock) throws SaisieException {
        bookTest.setStock(stock);
        assertEquals(stock,bookTest.getStock());
    }
    @DisplayName("test isbn fail")
    @ParameterizedTest(name = "isbn[number!=14]")
    @ValueSource(longs = {123456789L,1234567891011L,123456789101112L})
        public void setIsbn_Fail(long isbn) throws SaisieException {
            assertThrows(SaisieException.class, () -> {bookTest.setIsbn(isbn);});
    }
    @DisplayName("test isbn success")
    @ParameterizedTest(name = "isbn[number!=14]")
    @ValueSource(longs = {12345678910111L})
    public void setIsbn_Success(long isbn) throws SaisieException {
        bookTest.setIsbn(isbn);
        assertEquals(isbn,bookTest.getIsbn());
    }

    @ParameterizedTest(name = "{0} constructor OK")
    @EmptySource
    @DisplayName(" test constructor")
    void setTitle(String title) throws SaisieException {
        assertThrows(SaisieException.class, () -> {
        new Book(title,"test","test",0,12345678910111L,null,null);
        });
        assertEquals("test",bookTest.getTitle());
        assertEquals("test",bookTest.getLastNameAuthor());
        assertEquals("test",bookTest.getFirstNameAuthor());
        assertEquals(0,bookTest.getStock());
        assertEquals(12345678910111L,bookTest.getIsbn());
        assertNull(bookTest.getBorrowDateIn());
        assertNull(bookTest.getReturnDate());

    }
}