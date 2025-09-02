package model;

import controler.PharmacieController;
import controler.Regex;

import java.util.ArrayList;

public class Mutuelle extends Person{
    private Dep dep;
    private int depCode;
    private String nomDep;
    private double tauxRemb; // Taux de remboursement en pourcentage (0-100)

    // Constructeur
    public Mutuelle(String firstName,String lastName, String address, String email,int nbState,String city,String phone, double tauxRemb) {
        super("Mutuelle",lastName,address,email,nbState,city,phone);
        this.setTauxRemb(tauxRemb);
        PharmacieController.listMutuelles.add(this);
    }

    public Mutuelle(String lastName, double tauxRemb) {
        super(lastName);
        this.setTauxRemb(tauxRemb);
        PharmacieController.getListMutuelles().add(this);
    }


    public Dep getDep() {
        return dep;
    }
    public void setDep(Dep dep) {
        if (Regex.testNotEmpty(String.valueOf(dep))){
            throw new IllegalArgumentException("nbAgreement format invalid 4numbers required");}
        this.dep = dep;
    }

    public double getTauxRemb() {
        return tauxRemb;
    }
    public void setTauxRemb(double tauxRemb) {
        if (tauxRemb < 0 || tauxRemb > 100) {
            throw new IllegalArgumentException("Le taux de remboursement doit être entre 0 et 100");
        }
        this.tauxRemb = tauxRemb;
    }

    // Calculi refund
    public double calcRemb(double montant) {
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant ne peut pas être négatif");
        }
        return montant * (tauxRemb / 100.0);
    }

    @Override
    public String toString() {
        return getLastName() + " (Remboursement: " + tauxRemb + "%)";
    }
}
