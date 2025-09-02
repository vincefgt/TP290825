package model;

import controler.PharmacieController;
import controler.Regex;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ordonnance extends Medecin{
    private LocalDate dateOrdo;
    private Client patient;
    public java.util.List<Medicament> listMedOrdo;
    //private List<Ordonnance> listClientOrdo;

    // Constructeur
    public Ordonnance(String firstName,String lastName,String address,String email,int nbState,String city,String phone,long nbAgreement,String idMedecin, LocalDate dateOrdo, Client patient) {
        super(firstName,lastName,address,email,nbState,city,phone,nbAgreement,idMedecin);
        this.setDate(dateOrdo);
        this.setPatient(patient);
        this.listMedOrdo = new java.util.ArrayList<>();
        PharmacieController.getListOrdo().add(this);
    }

    // Getters et Setters
    public LocalDate getDate() {
        return dateOrdo;
    }
    public void setDate(LocalDate dateOrdo) {
        if (Regex.testDate(dateOrdo)||Regex.testNotEmpty(String.valueOf(dateOrdo)))
            throw new IllegalArgumentException("dateBirth required DD/MM/YYYY format");
        this.dateOrdo = LocalDate.parse(dateOrdo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public Client getPatient() {
        return patient;
    }
    public void setPatient(Client patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Le patient est obligatoire");
        }
        this.patient = patient;
    }

    public java.util.List<Medicament> getListMedOrdo() {
        return new java.util.ArrayList<>(listMedOrdo); // Retour d'une copie
    }

    // Méthodes pour gérer la liste des médicaments
    public void addMedOrdo(Medicament medicament) {
        if (medicament != null && !listMedOrdo.contains(medicament)) {
            listMedOrdo.add(medicament);
        }
    }

    public boolean suprMedOrdo(Medicament medicament) {
        return listMedOrdo.remove(medicament);
    }

    // Méthode pour calculer le prix total de l'ordonnance
    public double calculerPrixTotal() {
        double total = 0;
        for (Medicament med : listMedOrdo) {
            total += med.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Ordonnance du "+dateOrdo+" - Dr "+getLastName()+
                " - Patient: "+patient.getLastName()+" ("+listMedOrdo.size()+" médicaments)";
    }
}
