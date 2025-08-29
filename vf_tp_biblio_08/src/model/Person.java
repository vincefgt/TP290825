package model;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private String surname;


    //constructor
    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
        Person.listPersons.add(this);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    static List<Person> listPersons = new ArrayList<>();


    @Override
    public String toString() {
        return "person{" + name + " " + surname + '}';
    }
}
