package model;

import controller.Regex;
import Exception.*;

public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private int nbState;
    private String city;
    private String phone;

    //constructor
    public Person(String firstName, String lastName, String address, String email, int nbState, String city, String phone) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.setEmail(email);
        this.setNbState(nbState);
        this.setCity(city);
        this.setPhone(phone);
    }
    public Person(String lastName) {
        this.setLastName(lastName);
    }
    public Person(String lastName, String city) {
        this.setLastName(lastName);
        this.setCity(city);
    }
    public Person(String lastName, String firstName, String city) {
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setCity(city);
    }

    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        //if (Regex.testNotEmpty(firstName) || Regex.testChar(firstName))
        //    throw new IllegalArgumentException("firstName required & without number");
        this.firstName = capitalize(firstName);
    }

    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        if (Regex.testNotEmpty(lastName)||Regex.testChar(lastName))
            throw new IllegalArgumentException("lastName required & without number");
        this.lastName = capitalize(lastName).toUpperCase();
    }

    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        if (Regex.testNotEmpty(address))
            throw new IllegalArgumentException("address required");
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        if (Regex.testEmail(email) || Regex.testNotEmpty(email))
            throw new IllegalArgumentException("email required with @ & .");
        this.email = email;
    }

    public int getNbState() {
        return this.nbState;
    }
    public void setNbState(int nbState) {
        Regex.setParamRegex("^\\d{5}$");
        if (Regex.testNotEmpty(String.valueOf(nbState)) && Regex.testDigitLong(nbState)) // if not empty should be equal to 5 number
            throw new IllegalArgumentException("Nb State 5 numbers required");
        this.nbState = nbState;
    }

    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        if (Regex.testNotEmpty(city))
            throw new IllegalArgumentException("city required");
        this.city = capitalize(city);
    }

    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        Regex.setParamRegex("^\\+?\\w{10}$");
        if (Regex.testObjet(phone))
            throw new InputException("10 numbers required");
        this.phone = phone;
    }

    //LowerCase except firstOne
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return result.toString().trim();
    }


    @Override
    public String toString() {
        return "Personne ["+getFirstName()+getLastName()+getAddress()+getEmail()+getNbState()+getCity()+getPhone()+"]";
    }
}
