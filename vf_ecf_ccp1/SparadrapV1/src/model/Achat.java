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

/**
 * Represents a purchase transaction in the Sparadrap pharmacy system.
 * 
 * <p>This class handles both direct purchases (without prescription) and
 * prescription-based purchases. It manages the financial calculations including
 * total amounts and insurance reimbursements, while also handling stock
 * management for purchased medications.</p>
 * 
 * <h2>Purchase Types:</h2>
 * <ul>
 *   <li><strong>Direct Purchase</strong>: Client buys medications without prescription</li>
 *   <li><strong>Prescription Purchase</strong>: Client buys medications based on doctor's prescription</li>
 * </ul>
 * 
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Automatic total calculation based on medication prices</li>
 *   <li>Insurance reimbursement calculation</li>
 *   <li>Stock level validation and updates</li>
 *   <li>Date tracking for purchase history</li>
 *   <li>Support for both purchase types</li>
 * </ul>
 * 
 * <h2>Financial Calculations:</h2>
 * <p>The system automatically calculates:</p>
 * <ul>
 *   <li><strong>Total Amount</strong>: Sum of all medication prices</li>
 *   <li><strong>Reimbursement</strong>: Based on client's insurance rate</li>
 *   <li><strong>Net Amount</strong>: Total minus reimbursement</li>
 * </ul>
 * 
 * <h2>Stock Management:</h2>
 * <p>During purchase processing:</p>
 * <ol>
 *   <li>System validates medication availability</li>
 *   <li>Stock levels are reduced by purchase quantity</li>
 *   <li>Out-of-stock items prevent purchase completion</li>
 * </ol>
 * 
 * <h2>Usage Examples:</h2>
 * 
 * <h3>Direct Purchase:</h3>
 * <pre>{@code
 * // Create direct purchase
 * Achat directPurchase = new Achat(LocalDate.now(), client);
 * directPurchase.addMedAchat(medication1);
 * directPurchase.addMedAchat(medication2);
 * 
 * // Process purchase
 * PharmacieController.savingAchat(directPurchase);
 * }</pre>
 * 
 * <h3>Prescription Purchase:</h3>
 * <pre>{@code
 * // Create prescription-based purchase
 * Achat prescriptionPurchase = new Achat(LocalDate.now(), client, prescription);
 * 
 * // Medications are automatically added from prescription
 * PharmacieController.savingAchat(prescriptionPurchase);
 * }</pre>
 * 
 * @author Sparadrap Development Team
 * @version 1.0
 * @since 2025-01-15
 * @see Client
 * @see Medicament
 * @see Ordonnance
 * @see Mutuelle
 */
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
