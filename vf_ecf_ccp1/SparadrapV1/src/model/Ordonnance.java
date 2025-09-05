package model;

import controler.Regex;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static model.Achat.*;

public class Ordonnance extends Medecin{
    private LocalDate dateOrdo;
    private Client patient;
    public List<Medicament> listMedOrdo;
    //private List<Ordonnance> listClientOrdo;

    // Constructeur
    public Ordonnance(String firstName,String lastName,String address,String email,int nbState,String city,String phone,long nbAgreement,String idMedecin, LocalDate dateOrdo, Client patient) {
        super(firstName,lastName,address,email,nbState,city,phone,nbAgreement,idMedecin);
        this.setDate(dateOrdo);
        this.setPatient(patient);
        this.listMedOrdo = new ArrayList<>();
        //PharmacieController.getListOrdo().add(this);
    }

    // Getters et Setters
    public String getDate() {
        return dateOrdo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public void setDate(LocalDate dateOrdo) {
        if (Regex.testNotEmpty(String.valueOf(dateOrdo))||Regex.testDate(dateOrdo))
            throw new IllegalArgumentException("dateBirth required DD/MM/YYYY format");
        this.dateOrdo = dateOrdo;
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
    /*public void setListMedOrdo() {
        if (!IsAchatDirect()) {
            listMedOrdo = Achat.getListMedAchat();
            return;
        }
        listMedOrdo = new ArrayList<>();
    }*/


    public List<Medicament> getListMedOrdo() {
        return this.listMedOrdo;
    }
    /*public static void setListMedOrdo(){
        if (IsAchatDirect()) {
            listMedOrdo.addAll(getListMedAchat());
        }
    }*/
    // add med in ordo
    public void addMedOrdo(Medicament medicament) {
        if (medicament != null && !getListMedOrdo().contains(medicament)) {
            getListMedOrdo().add(medicament);
        }
    }

    public boolean suppMedOrdo(Medicament medicament) {
        return getListMedOrdo().remove(medicament);
    }

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
