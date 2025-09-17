package model;

import controller.PharmacieController;
import controller.Regex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Client extends Person {
    private long nbSS;
    private LocalDate dateBirth;
    private Mutuelle mut;
    private Medecin medecinTraitant;
    //private List<Client> listClient;

    public static Map<String, Client> MapClient = new HashMap<>();

    // Constructeur avec validation (code défensif)
    public Client(String firstName, String lastName, String address, int nbState,
                  String city, String phone, String email, long nbSS,
                  LocalDate dateBirth,Mutuelle mut, Medecin medecinTraitant) {
        super (firstName, lastName,address,email,nbState,city,phone);
        this.setNbSS(nbSS);
        this.setDateBirth(dateBirth);
        this.setMutuelle(mut);
        this.setMedecinTraitant(medecinTraitant);
        MapClient.put(lastName+" "+firstName, this);
    }

    public long getNbSS() {
        return nbSS;
    }
    public void setNbSS(long nbSS) {
        Regex.setParamRegex("^\\d{15}$");
        if (Regex.testDigitLong(nbSS))
            throw new IllegalArgumentException(String.valueOf(nbSS+" format invalid 15 numbers required"));
        this.nbSS = nbSS;
    }

    public String getDateBirth() {
        return dateBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public void setDateBirth(LocalDate dateBirth) {
        if (Regex.testDate(dateBirth)||Regex.testNotEmpty(String.valueOf(dateBirth)))
            throw new IllegalArgumentException("dateBirth required");
        this.dateBirth = dateBirth;
    }

    public Mutuelle getMutuelle() {
        return mut;
    }
    public void setMutuelle(Mutuelle mut) {
        this.mut = mut;
    }

    public Medecin getMedecinTraitant() {
        return medecinTraitant;
    }
    public void setMedecinTraitant(Medecin medecinTraitant) {
        this.medecinTraitant = medecinTraitant;
    }


    @Override
    public String toString() {
        if (super.getLastName().equals(PharmacieController.getListClients().getFirst().getLastName())){
            return super.getLastName();
        } else {
            return getLastName()+" "+getFirstName() +" (N° SS: "+getNbSS()+")";
        }
    }
}
