package model;

import controler.Regex;

public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private int nbState;
    private String city;
    private long phone;

    //constructor
    public Person(String firstName, String lastName, String address, String email, int nbState, String city, long phone) {
        /*// check if dataPerson is empty
        String[]listData = {firstName, lastName, address, email,String.valueOf(nbState),String.valueOf(phone),city};
        for (int i = 0; i < listData.length; i++) {
        if (Regex.testNotEmpty(listData[i]))
            throw new IllegalArgumentException(String.valueOf(listData[i]+"required"));
        }
        Regex.setParamRegex("^\\d{4}$");
        if (Regex.testDigit(nbState))
            throw new IllegalArgumentException(String.valueOf(nbState+"/ format invalid 4numbers required"));
        Regex.setParamRegex("^\\d{10}$");
        if (Regex.testDigit(phone))
            throw new IllegalArgumentException(String.valueOf(phone+"/ format invalid 10numbers required"));
        Regex.setParamRegex("^\\d{10}$");
        if (Regex.testEmail(email))
            throw new IllegalArgumentException(String.valueOf(email+"/ format invalid 10numbers required"));
*/
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.setEmail(email);
        this.setNbState(nbState);
        this.setCity(city);
        this.setPhone(phone);
    }

    public Person(String lastName) {
    }

    public Person(String lastName, String city) {
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        if (Regex.testNotEmpty(firstName)||!Regex.testChar(firstName))
            throw new IllegalArgumentException("firstName required without number");
        this.firstName = capitalize(firstName);
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        if (Regex.testNotEmpty(lastName)||!Regex.testChar(lastName))
            throw new IllegalArgumentException("lastName required without number");
        this.lastName = capitalize(lastName);
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        if (Regex.testNotEmpty(address))
            throw new IllegalArgumentException("address required");
        this.address = address;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if (Regex.testNotEmpty(email)||Regex.testEmail(email))
            throw new IllegalArgumentException("email required with @ & .");
        this.email = email;
    }

    public int getNbState() {
        return nbState;
    }
    public void setNbState(int nbState) {
        Regex.setParamRegex("^\\d{4}$");
        if (Regex.testNotEmpty(String.valueOf(nbState))||Regex.testDigit(nbState))
            throw new IllegalArgumentException("4 numbers required");
        this.nbState = nbState;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        if (Regex.testNotEmpty(city))
            throw new IllegalArgumentException("city required");
        this.city = capitalize(city);
    }

    public long getPhone() {
        return phone;
    }
    public void setPhone(long phone) {
        Regex.setParamRegex("^\\d{10}$");
        if (Regex.testDigit(phone))
            throw new IllegalArgumentException("10 numbers required");
        this.phone = phone;
    }

    //LowerCase except firstOne
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.trim().substring(0, 1).toUpperCase() + input.trim().substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return "Personne ["+firstName+lastName+address+email+nbState+city+phone+"]";
    }
}
