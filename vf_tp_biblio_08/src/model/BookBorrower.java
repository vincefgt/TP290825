package model;

import java.util.ArrayList;
import java.util.List;

public class BookBorrower extends Person {
    private String id;

    public BookBorrower(String name, String surname) {
        super(name, surname);
        this.id = id;
       // BookBorrower.listBookBorrower.add(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //static List<BookBorrower> listBookBorrower = new ArrayList<>();

    @Override
    public String toString() {
        return super.toString()+" id: "+getId();
    }

}
