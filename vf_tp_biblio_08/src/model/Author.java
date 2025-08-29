package model;

import exception.SaisieException;
import utilities.Regex;
import view.InputAndDisplay;
import java.util.ArrayList;
import java.util.List;

public class Author {
    private String firstNameAuthor;
    private String lastNameAuthor;

    public Author(String firstNameAuthor, String lastNameAuthor) throws SaisieException {
        this.setFirstNameAuthor(firstNameAuthor);
        this.setLastNameAuthor(lastNameAuthor);
        Author.listAuthors.add(this);
    }

    public String getFirstNameAuthor() {
        return this.firstNameAuthor;
    }
    public void setFirstNameAuthor(String firstNameAuthor) throws SaisieException {
        if(Regex.testChar(firstNameAuthor) || Regex.testEmptyBlank(firstNameAuthor)){
            throw new SaisieException("Enter valid firstNameAuthor[letter only]");
            //InputAndDisplay.error();
        } else {
            this.firstNameAuthor = firstNameAuthor;
        }
    }
    public String getLastNameAuthor() {
        return this.lastNameAuthor;
    }
    public void setLastNameAuthor(String lastNameAuthor) throws SaisieException {
        if(Regex.testChar(lastNameAuthor)||Regex.testEmptyBlank(lastNameAuthor)){
            throw new SaisieException("Enter valid lastNameAuthor[letter only]");
        } else {
            this.lastNameAuthor = lastNameAuthor; }
    }

    public static List<Author> getListAuthors() {
        return listAuthors;
    }

    public static List<Author> listAuthors = new ArrayList<>();

    @Override
    public String toString() {
        return getFirstNameAuthor() + " " + getLastNameAuthor();
    }
}


