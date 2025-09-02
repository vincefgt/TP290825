package model;

import controler.PharmacieController;
import controler.Regex;
import java.util.Date;
import java.util.List;
import static java.lang.String.format;

public class Medecin extends Person {

    private long nbAgreement;
    private java.util.List<Client> patients; // List of patients
    //private java.util.List<Medecin> listMedecin; //List of madecins
    private String idMedecin;

    // Constructor
    public Medecin(String firstName, String lastName, String address,String email, int nbState,String city, long phone, long nbAgreement,String idMedecin) {
        super(firstName,lastName,address,email,nbState,city,phone);
        this.setNbAgreement(nbAgreement);
        this.idMedecin = generateId();
        addMedecin(this);
        this.patients = new java.util.ArrayList<>();
        PharmacieController.getListMedecins().add(this);
    }

    public Medecin(String lastName, String city, long nbAgreement,String idMedecin) {
        super(lastName,city);
        this.setNbAgreement(nbAgreement);
        this.setIdMedecin(idMedecin);
        addMedecin(this);
        this.patients = new java.util.ArrayList<>();
        PharmacieController.getListMedecins().add(this);
    }

    public void setIdMedecin(String idMedecin) {
        this.idMedecin = generateId();
    }

    public long getNbAgreement() {
        return nbAgreement;
    }
    public void setNbAgreement(long nbAgreement) {
/*        Regex.setParamRegex("^\\d{11}$"); // strictly 11 numbers
        if (Regex.testNotEmpty(String.valueOf(nbAgreement))||Regex.testDigit(nbAgreement)) {
            throw new IllegalArgumentException("11 numbers required");
        }
*/  Regex.setParamRegex("^\\d{11}$");
        if (Regex.testDigit(nbAgreement)||Regex.testNotEmpty(String.valueOf(nbAgreement)))
            throw new IllegalArgumentException("nbAgreement format invalid 11numbers required");
        this.nbAgreement = nbAgreement;
    }

    public java.util.List<Client> getPatients() {
        return new java.util.ArrayList<>(patients); // Retour d'une copie pour la sécurité
    }

    // add patient
    public void addPatient(Client patient) {
        if (patient != null && !patients.contains(patient)) {
            patients.add(patient);
        }
    }

    // delete un patient
    public boolean deletePatient(Client patient) {
        return patients.remove(patient);
    }

    // delete medecin
    public boolean deleteMedecin(Medecin pMedecin) {
        return PharmacieController.getListMedecins().remove(pMedecin);
    }
    // add patient
    public void addMedecin(Medecin pMedecin) {
            PharmacieController.getListMedecins().add(pMedecin);
    }

    // Méthodes utilitaires
    private String generateId() {
        return idMedecin = "MED" + format("%04d",PharmacieController.getListMedecins().size()+1); //Date.now().toString() + '_' + Math.random().toString(36).substr(2, 9);
    }

    public String getLastNameMedecin (){
        return super.getLastName();
    }

    @Override
    public String toString() {
        return "Dr "+getFirstName()+" "+getLastName()+" (Agrement: "+nbAgreement+")";
    }
}
