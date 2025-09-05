package model;

import controler.Regex;
import Exception.InputException;
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
    private static Ordonnance ordonnance; // null si achat direct
    private double Total = 0;
    private double Remb = 0;

    // Constructeur achat direct
    public Achat(LocalDate dateAchat, Client client) {
        this.setDateAchat(dateAchat);
        this.setClient(client);
        this.listMedAchat = new ArrayList<>();
        this.setOrdonnance(null); // Achat direct
        this.setTotal(Total);
        this.setRemb(Remb);
        // adding when saving > PharmacieController
    }

    // Constructeur achat ordonnance
    public Achat(LocalDate dateAchat, Client client, Ordonnance ordonnance) {
        this(dateAchat, client); // Appel du constructeur principal
        this.setOrdonnance(ordonnance);
        this.listMedAchat = new ArrayList<>();
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
        if (Regex.testNotEmpty(String.valueOf(client)))
            throw new InputException("address required");
        this.client = client;
    }

    public List<Medicament> getListMedAchat() {
        return this.listMedAchat;
    }

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }
    public void setOrdonnance(Ordonnance ordonnance) {
        // create doublons
        //if (ordonnance != null) {
        //    this.listMedAchat.addAll(ordonnance.getListMedOrdo());
        // }
    }

    public double getTotal() {
        return Total;
    }
    public void setTotal(double Total) {
        this.Total = Total;
    }

    public double getRemb() {
        return Remb;
    }
    public void setRemb(double Remb) {
        this.Remb = Remb;
    }

    // Add Med in Achat
    public boolean addMedAchat(Medicament medicament) {
        if (medicament != null) {
            this.listMedAchat.add(medicament);
            calMontants();
        }
        return false;
    }

    // Méthode pour calculer les montants
    public void calMontants() {
        Total = 0;
        for (Medicament med : listMedAchat) {
            Total += med.getPrice();
        }

        // Calculer le remboursement si le client a une mutuelle
        if (client.getMutuelle() != null) {
            Remb = client.getMutuelle().calcRemb(Total);
        } else {
            Remb = 0;
        }
    }

    public void setListMedOrdo(){
        if (IsAchatDirect()) {
            ordonnance.listMedOrdo = getListMedAchat();
        }
    }

    // Direct vs Ordo
    public static boolean IsAchatDirect() {
        return ordonnance == null;
    }

    @Override
    public String toString() {
        String type = IsAchatDirect() ? "Achat direct" : "Achat sur ordonnance";
        return type+" - "+dateAchat+" - "+client.getLastName()+
                " - Total: "+Total+"€ (Remboursé: "+Remb+"€)";
    }
}
