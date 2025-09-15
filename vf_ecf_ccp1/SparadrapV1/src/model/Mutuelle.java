package model;

import controller.PharmacieController;
import controller.Regex;

public class Mutuelle extends Person{
    private Dep dep;
    private int depCode;
    private String nomDep;
    private double tauxRemb =0; // Taux de remboursement en pourcentage (0-100)

    // Constructeur
    public Mutuelle(String firstName,String lastName, String address, String email,int nbState,String city,String phone, double tauxRemb,Dep dep) {
        super("Mutuelle",lastName,address,email,nbState,city,phone);
        this.setTauxRemb(tauxRemb);
        this.setDep(dep);
        //PharmacieController.getListMutuelles().add(this);
    }
    public Mutuelle(String lastName, double tauxRemb) {
        super(lastName);
        this.setFirstName("Mutuelle");
        this.setTauxRemb(tauxRemb);
        //PharmacieController.getListMutuelles().add(this);
    }

    public Dep getDep() {
        return dep;
    }
    public void setDep(Dep dep) {
        if (Regex.testNullObj(dep)){
            throw new IllegalArgumentException("dep required");}
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

    // Calculi refund mutuelle
    public double calcRemb(double montant) {
        if (montant < 0) {
            throw new IllegalArgumentException("montant invalid <0");
        }
        return PharmacieController.formatTwoDec(montant * (tauxRemb / 100.0));
    }

    @Override
    public String toString() {
        return getLastName()+" (Remb: "+getTauxRemb()+"%)";
    }
}
