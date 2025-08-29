package model;

import utilities.Regex;
import view.InputAndDisplay;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Subscriber extends Person {
    private LocalDateTime creationDate;
    private String email;
    static List<Subscriber> listSubscriber = new ArrayList<>();

    public Subscriber(String name, String surname, LocalDateTime creationDate, String email) {
        super(name, surname);
        this.setEmail(email);
        this.creationDate = LocalDateTime.now();
        Subscriber.listSubscriber.add(this);
        // this.id=id;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        boolean errorEmail = false;
        do{
           if(!Regex.testEmail(email)){
               this.email = email;
               errorEmail = false;
           } else {
               errorEmail = true; }
        } while (errorEmail);
    }
    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        do{
            if (Regex.testEmptyBlank(String.valueOf(creationDate))) {
                InputAndDisplay.error("Invalid date");
            }
        } while (Regex.testEmptyBlank(String.valueOf(creationDate)));
        this.creationDate = creationDate;
    }
    public static List<Subscriber> getListSubscriber() {
        return listSubscriber;
    }
    public static void setListSubscriber(List<Subscriber> listSubscriber) {
        Subscriber.listSubscriber = listSubscriber;
    }

    @Override
    public String toString() {
        return super.toString()+" email: "+getEmail()+" create by: "+getCreationDate();
        //" id: "+getId();
    }
}
