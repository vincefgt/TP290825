package model;

import controller.PharmacieController;
import controller.Regex;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ordonnance extends Medecin{
    private LocalDate dateOrdo;
    private Client patient;
    private Medecin medecin;
    public List<Medicament> listMedOrdo;

    // Constructeur
    public Ordonnance(String firstName,String lastName,String address,String email,int nbState,String city,String phone,long nbAgreement,String idMedecin, LocalDate dateOrdo, Client patient) {
        super(firstName,lastName,address,email,nbState,city,phone,nbAgreement,idMedecin);
        this.setDateOrdo(dateOrdo);
        this.setPatient(patient);
        this.listMedOrdo = new ArrayList<>();
    }
    public Ordonnance(LocalDate dateOrdo,Medecin medecin,Client patient) {
        super(medecin.getFirstName(), medecin.getLastName(), medecin.getNbAgreement(), medecin.getIdMedecin());
        this.setMedecin(medecin);
        this.setDateOrdo(dateOrdo);
        this.setPatient(patient);
        this.listMedOrdo = new ArrayList<>();
    }

    // Getters et Setters
    public String getDateOrdo() {
        return this.dateOrdo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public void setDateOrdo(LocalDate dateOrdo) {
        if (Regex.testNotEmpty(String.valueOf(dateOrdo))||Regex.testDate(dateOrdo))
            throw new IllegalArgumentException("dateBirth required DD/MM/YYYY format");
        this.dateOrdo = dateOrdo;
    }

    public Client getPatient() {
        return this.patient;
    }
    public void setPatient(Client patient) {
        if (patient == null) {
            throw new IllegalArgumentException("patient required");
        }
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return this.medecin;
    }
    public void setMedecin(Medecin medecin) {
        if (medecin == null) {
            throw new IllegalArgumentException("medecin required ");
        }
        this.medecin = medecin;
    }


    public List<Medicament> getListMedOrdo() {
        return this.listMedOrdo;
    }

    // add med in ordo
    public void addMedOrdo(Medicament medicament) {
        if (medicament != null && !getListMedOrdo().contains(medicament)) {
            getListMedOrdo().add(medicament);
        }
    }

    public boolean suppMedOrdo(Medicament medicament) {
        return getListMedOrdo().remove(medicament);
    }

    @Override
    public String toString() {
        return "Ordonnance du "+dateOrdo+" - Dr "+medecin.getLastName()+" "+medecin.getFirstName()+
                " - Patient: "+patient.getLastName()+" "+patient.getFirstName()+" ("+listMedOrdo.size()+" médicaments)";
    }
}
