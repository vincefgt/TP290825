package model;

import controller.PharmacieController;
import controller.Regex;
import Exception.InputException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Classe Achat représentant un achat (avec ou sans ordonnance)
public class Achat {
    private LocalDate dateAchat;
    private Client client;
    private List<Medicament> listMedAchat;
    private List<Achat> listAchatClient;
    private Ordonnance ordonnance; // null si achat direct
    private double Total = 0;
    private double Remb = 0;

    //TODO add quantity by achat

    // Constructeur achat direct
    public Achat(LocalDate dateAchat, Client client) {
        this.setDateAchat(dateAchat);
        this.setClient(client);
        this.listMedAchat = new ArrayList<>();
        this.setOrdonnance(null); // Achat direct
        this.setTotal(Total);
        this.setRemb(Remb);
    }
    // Constructeur achat ordonnance
    public Achat(LocalDate dateAchat, Client client, Ordonnance ordonnance) {
        this(dateAchat, client);
        this.setOrdonnance(ordonnance);
        this.listMedAchat = new ArrayList<>();
        this.setListMedAchat(ordonnance); // recup listMedOrdo to listMedAchat
    }

    // Getters et Setters
    public String getDateAchat() {
        return dateAchat.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));}
    public void setDateAchat(LocalDate dateAchat) {
        if (Regex.testDate(dateAchat)||Regex.testNotEmpty(String.valueOf(dateAchat)))
            throw new InputException("Date required");
        this.dateAchat = dateAchat;
    }

    public Client getClient() {
        return this.client;
    }
    public void setClient(Client client) {
        if (Regex.testNotEmpty(client))
            throw new InputException("client required");
        this.client = client;
    }

    public List<Medicament> getListMedAchat() {
        return this.listMedAchat;
    }

    public  void setListMedAchat(Ordonnance ordonnance) { // from Ordo
        this.listMedAchat.addAll(ordonnance.getListMedOrdo());
    }

    public Ordonnance getOrdonnance() {
        return this.ordonnance;
    }
    public void setOrdonnance(Ordonnance ordonnance) {
            this.ordonnance = ordonnance;
    }

    public double getTotal() {
        return PharmacieController.formatTwoDec(Total);
    }
    public void setTotal(double Total) {
        this.Total = PharmacieController.formatTwoDec(Total);
    }

    public double getRemb() {
        return PharmacieController.formatTwoDec(Remb);
    }
    public void setRemb(double Remb) {
        this.Remb = PharmacieController.formatTwoDec(Remb);
    }

    // Add Med in Achat
    public boolean addMedAchat(Medicament medicament) {
        if (medicament != null) {
            this.listMedAchat.add(medicament);
        } else if (ordonnance != null) {
            this.listMedAchat.addAll(ordonnance.getListMedOrdo());
        }
        return false;
    }

    // cal montants
    public void calMontants() {
        for (Medicament med : this.listMedAchat) {
            PharmacieController.formatTwoDec(Total += med.getPrice());
        }

    // Cal remb if mutuelle
    if (client.getMutuelle() != null) {
        Remb = client.getMutuelle().calcRemb(Total);
        PharmacieController.formatTwoDec(Remb);
    } else {
        Remb = 0;
    }
}

    // Direct vs Ordo
    public static boolean IsAchatDirect(Ordonnance ordonnance) {
        return ordonnance == null;
    }

    @Override
    public String toString() {
        String type = IsAchatDirect(ordonnance) ? "Achat direct" : "Achat sur ordonnance";
        return type+" - "+dateAchat+" - "+client.getLastName()+
                " - Total: "+Total+"€ (Remboursé: "+Remb+"€) "+ordonnance;
    }
}
