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
    public Medecin(String firstName, String lastName, String address,String email, int nbState,String city, String phone, long nbAgreement,String idMedecin) {
        super(firstName,lastName,address,email,nbState,city,phone);
        this.setNbAgreement(nbAgreement);
        this.idMedecin = generateId(idMedecin);
        addMedecin(this);
        this.patients = new java.util.ArrayList<>();
        //PharmacieController.getListMedecins().add(this);
    }
    public Medecin(String lastName, String city, long nbAgreement,String idMedecin) {
        super(lastName,city);
        this.setNbAgreement(nbAgreement);
        this.setIdMedecin(idMedecin);
        addMedecin(this);
        this.patients = new java.util.ArrayList<>();
        //TODO:control if medecin existe > true > error
        //PharmacieController.getListMedecins().add(this);
    }
    public Medecin(String lastName, long nbAgreement, String idMedecin) {
        super(lastName);
        this.setNbAgreement(nbAgreement);
        this.setIdMedecin(idMedecin);
        addMedecin(this);
        this.patients = new java.util.ArrayList<>();
    }

    public String getIdMedecin() {
        return this.idMedecin;
    }
    public void setIdMedecin(String idMedecin) {
        this.idMedecin = generateId(idMedecin);
    }

    public long getNbAgreement() {
        return this.nbAgreement;
    }
    public void setNbAgreement(long nbAgreement) {
        Regex.setParamRegex("^\\d{11}$");
        if (Regex.testDigitLong(nbAgreement)||Regex.testNotEmpty(String.valueOf(nbAgreement)))
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
    private String generateId(String idMedecin) {
        if (Regex.testNotEmpty(idMedecin)) {
            return "MED" + format("%04d", PharmacieController.getListMedecins().size() + 1); //Date.now().toString() + '_' + Math.random().toString(36).substr(2, 9);
        } else { return idMedecin; }
    }


    @Override
    public String toString() {
        return "Dr "+getLastName()+" "+getFirstName()+" (Agrement: "+nbAgreement+")";
    }
}
