package model;

import controler.PharmacieController;
import controler.Regex;
import Exception.InputException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Classe Achat représentant un achat (avec ou sans ordonnance)
public class Achat {
    private LocalDate dateAchat;
    private Client client;
    private java.util.List<Medicament> listMedAchat;
    private java.util.List<Achat> listAchatClient;
    private Ordonnance ordonnance = null; // null si achat direct
    private double Total = 0;
    private double Remb = 0;

    // Constructeur pour achat direct (sans ordonnance)
    public Achat(LocalDate dateAchat, Client client) {
        this.setDateAchat(dateAchat);
        this.setClient(client);
        this.listMedAchat = new java.util.ArrayList<>();
        this.setOrdonnance(null); // Achat direct
        this.setTotal(Total);
        this.setRemb(Remb);
        PharmacieController.getListAchats().add(this);
    }

    // Constructeur pour achat avec ordonnance
    public Achat(LocalDate dateAchat, Client client, Ordonnance ordonnance) {
        this(dateAchat, client); // Appel du constructeur principal
        this.setOrdonnance(ordonnance);
    }

    // Getters et Setters
    public LocalDate getDateAchat() {
        return dateAchat;
    }
    public void setDateAchat(LocalDate dateAchat) {
        if (Regex.testDate(dateAchat)||Regex.testNotEmpty(String.valueOf(dateAchat)))
            throw new InputException("Date required");
        this.dateAchat = LocalDate.parse(dateAchat.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        if (Regex.testNotEmpty(String.valueOf(client)))
            throw new InputException("address required");
        this.client = client;
    }

    public java.util.List<Medicament> getListMedAchat() {
        return new java.util.ArrayList<>(listMedAchat);
    }

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }
    public void setOrdonnance(Ordonnance ordonnance) {
        if (ordonnance != null) {
            this.listMedAchat.addAll(ordonnance.getListMedOrdo());
        } else {
            this.ordonnance = null;
        }
    }

    public double getTotal() {
        return Total;
    }

    public double getRemb() {
        return Remb;
    }

    public void setTotal(double Total) {
        Regex.setParamRegex("^\\d+.?+\\d+?$");
        if (Regex.testNotEmpty(String.valueOf(Total))||!Regex.testDigitDec(Total))
            throw new InputException("invalid total");
        this.Total = Total;
    }

    public void setRemb(double Remb) {
        this.Remb = Remb;
    }

    // Méthode pour ajouter un médicament à l'achat
    public void ajouterMedicament(Medicament medicament) {
        if (medicament != null) {
            listMedAchat.add(medicament);
            calculerMontants();
        }
    }

    // Méthode pour calculer les montants
    public void calculerMontants() {
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

    // Méthode pour savoir si c'est un achat direct ou sous ordonnance
    public boolean estAchatDirect() {
        return ordonnance == null;
    }

    @Override
    public String toString() {
        String type = estAchatDirect() ? "Achat direct" : "Achat sur ordonnance";
        return type+" - "+dateAchat+" - "+client.getLastName()+
                " - Total: "+Total+"€ (Remboursé: "+Remb+"€)";
    }
}
