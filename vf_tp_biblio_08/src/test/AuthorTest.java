package test;

import exception.SaisieException;
import model.Author;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
private Author authorTest;

    @BeforeEach
    void setUp() throws SaisieException
    { authorTest = new Author("test", "test");}

    @AfterEach
    void  tearDown() throws SaisieException
    { authorTest = null;}

    @DisplayName("firstNameAuthor test")
    @ParameterizedTest(name = "{0} invalid")
    @ValueSource(strings = {"jK123","jk%","j k",""," "})
    void setFirstNameAuthor_fail(String first) throws SaisieException {
        assertThrows(SaisieException.class, () -> {authorTest.setFirstNameAuthor(first);});
    }

    @DisplayName("firstNameAuthor test")
    @ParameterizedTest(name = "{0} valid")
    @ValueSource(strings = {"j.K","Jk", "j_k"})
    void setFirstNameAuthor_success(String first) throws SaisieException {
        authorTest.setFirstNameAuthor(first);
        assertEquals(first,authorTest.getFirstNameAuthor());
    }

    @DisplayName("lastNameAuthor test")
    @ParameterizedTest(name = "{0} invalid")
    @ValueSource(strings = {"jK123","jk%","j k",""," "})
    void setLastNameAuthor_fail(String last) throws SaisieException {
        assertThrows(SaisieException.class, () -> {authorTest.setLastNameAuthor(last);});
    }

    @DisplayName("lastNameAuthor test")
    @ParameterizedTest(name = "{0} valid")
    @ValueSource(strings = {"j.K","Jk", "j_k"})
    void setLastNameAuthor_success(String last) throws SaisieException {
        authorTest.setLastNameAuthor(last);
        assertEquals(last,authorTest.getLastNameAuthor());
    }
}