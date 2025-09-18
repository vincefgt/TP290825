package controller;

import model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Main controller class for the Sparadrap Pharmacy Management System.
 * 
 * <p>This class serves as the central business logic controller, managing all
 * pharmacy operations including client registration, medication inventory,
 * prescription handling, and purchase processing. It implements a singleton-like
 * pattern using static collections to ensure centralized data management.</p>
 * 
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *   <li>Client lifecycle management (CRUD operations)</li>
 *   <li>Doctor registration and patient assignment</li>
 *   <li>Medication inventory management</li>
 *   <li>Insurance company management</li>
 *   <li>Prescription creation and medication assignment</li>
 *   <li>Purchase processing (direct and prescription-based)</li>
 *   <li>Stock management and validation</li>
 *   <li>Financial calculations and reimbursements</li>
 * </ul>
 * 
 * <h2>Data Collections:</h2>
 * <p>The controller maintains static collections for all entities:</p>
 * <ul>
 *   <li>{@code listClients} - All registered clients</li>
 *   <li>{@code listMedecins} - All registered doctors</li>
 *   <li>{@code listMed} - All available medications</li>
 *   <li>{@code listMutuelles} - All insurance companies</li>
 *   <li>{@code listOrdonnances} - All prescriptions</li>
 *   <li>{@code listAchats} - All purchase transactions</li>
 * </ul>
 * 
 * <h2>Business Rules:</h2>
 * <ul>
 *   <li>Clients must have valid social security numbers (15 digits)</li>
 *   <li>Doctors must have valid agreement numbers (11 digits)</li>
 *   <li>Stock levels are automatically updated during purchases</li>
 *   <li>Insurance reimbursements are calculated automatically</li>
 *   <li>Prescriptions link doctors, clients, and medications</li>
 * </ul>
 * 
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Initialize controller
 * PharmacieController controller = new PharmacieController();
 * 
 * // Add entities
 * Client client = new Client("John", "Doe", ...);
 * controller.addClient(client);
 * 
 * // Create prescription-based purchase
 * boolean success = controller.createNewAchatWithOrdonnance(
 *     LocalDate.now(), client, LocalDate.now(), doctor);
 * }</pre>
 * 
 * @author Sparadrap Development Team
 * @version 1.0
 * @since 2025-01-15
 * @see model.Client
 * @see model.Medecin
 * @see model.Medicament
 * @see model.Achat
 * @see model.Ordonnance
 */
public class PharmacieController {
    // Lists
    public static java.util.List<Client> listClients;
    public static java.util.List<Medecin> listMedecins;
    public static java.util.List<Medicament> listMed;
    public static java.util.List<Mutuelle> listMutuelles;
    public static java.util.List<Ordonnance> listOrdonnances;
    public static java.util.List<Achat> listAchats;

    // Constructeur
    public PharmacieController() {
        listClients = new ArrayList<>(); // historic clients
        listMedecins = new ArrayList<>(); // historic medecins
        listMed = new ArrayList<>(); // historic meds
        listMutuelles = new ArrayList<>(); // historic mutuelles
        listOrdonnances = new ArrayList<>(); // historic ordos
        listAchats = new ArrayList<>(); // historic achats
    }

    // CLIENTS
    public static boolean addClient(Client client) {
        if (client != null && !getListClients().contains(client)) {
            return getListClients().add(client);
        }
        return false;
    }
    public static boolean getClient(int row) {
        return getListClients().get(row) != null;
    }
    public static void updateClient(Client client, String firstName, String lastName, String address, int nbState,
                                       String city, String phone, String email, long nbSS,
                                       LocalDate dateBirth, Mutuelle mut, Medecin medecinTraitant) {
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setNbState(nbState);
        client.setMutuelle(mut);
        client.setDateBirth(dateBirth);
        client.setNbSS(nbSS);
        //client.setMedecinTraitant(medecinTraitant);
        client.setEmail(email);
        client.setAddress(address);
        client.setCity(city);
        client.setPhone(phone);
    }
    public static boolean deleteClient(Client client){
        if (client != null && !listClients.contains(client)) {
            return listClients.remove(client);
        }
        return false;
    }
    public Client SearchSS(long numeroSS) {
        if (Regex.testNotEmpty(String.valueOf(numeroSS))) {
            return null;
        }
        for (Client client : listClients) {
            if (client.getNbSS() == numeroSS) {
                return client;
            }
        }
        return null;
    }
    public static List<Client> getListClients() {
        return listClients;
    }

    //MEDECIN
    public static boolean addMedecin(Medecin medecin) {
        if (medecin != null && !getListMedecins().contains(medecin)) {
            return getListMedecins().add(medecin);
        }
        return false;
    }
    public Medecin searchAgreement(long nbAgreement) {
        if (Regex.testDigitLong(nbAgreement)||Regex.testNotEmpty(String.valueOf(nbAgreement))) {
            return null;
        }
        for (Medecin medecin : listMedecins) {
            if (medecin.getNbAgreement()==nbAgreement) {
                return medecin;
            }
        }
        return null;
    }
    public static List<Medecin> getListMedecins() {
        return listMedecins;
    }
    public static void updateMedecin(Medecin medecin, String firstName, String lastName, String address, int nbState,
                                    String city, String phone, String email, Long nbAgreement) {
        medecin.setFirstName(firstName);
        medecin.setLastName(lastName);
        medecin.setNbState(nbState);
        medecin.setNbAgreement(nbAgreement);
        medecin.setEmail(email);
        medecin.setAddress(address);
        medecin.setCity(city);
        medecin.setPhone(phone);
    }

    // MED add in med list = ALL med
    public static boolean addMed(Medicament medicament) {
        if (medicament != null && !getListMed().contains(medicament)) {
            return getListMed().add(medicament);
        }
        return false;
    }
    // Med List
    public Medicament searchLastName(String lastName) {
        if (Regex.testNotEmpty(lastName)||Regex.testChar(lastName)) {
            return null;
        }
        for (Medicament med : listMed) {
            if (med.getNameMed().equalsIgnoreCase(lastName.trim())) {
                return med;
            }
        }
        return null;
    }
    public static List<Medicament> getListMed() {
        return listMed;
    }

    // MUTUELLES
    public static boolean addMutuelle(Mutuelle mutuelle) {
        if (mutuelle != null && !getListMutuelles().contains(mutuelle)) {
            return getListMutuelles().add(mutuelle);
        }
        return false;
    }
    public static List<Mutuelle> getListMutuelles() {
        return listMutuelles;
    }
    public static void updateMutuelle(Mutuelle mut, String lastName, String address, int nbState,
                                     String city, String phone, String email, Double tauxRemb) {
        mut.setLastName(lastName);
        mut.setTauxRemb(tauxRemb);
        if (address == null) {
            mut.setNbState(nbState);
            mut.setEmail(email);
            mut.setAddress(address);
            mut.setCity(city);
            mut.setPhone(phone);
            //mut.setDep(dep);
        }




    }

    // ACHAT WITH ORDONNANCE
    public static boolean createNewAchatWithOrdonnance(LocalDate dateAchat, Client client, 
                                                       LocalDate dateOrdo, Medecin medecin) {
        if (client == null || medecin == null) {
            return false;
        }
        try {
            // Create new ordonnance
            Ordonnance ordonnance = new Ordonnance(dateOrdo, medecin, client);
            
            // Add ordonnance to list
            if (addOrdonnance(ordonnance)) {
                // Create achat with ordonnance
                Achat achat = new Achat(dateAchat, client, ordonnance);
                
                // Save achat
                return savingAchat(achat);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de l'achat avec ordonnance: " + e.getMessage());
        }
        
        return false;
    }
    
    // Add medication to existing ordonnance
    public static boolean addMedToOrdo(Ordonnance ordonnance, Medicament medicament) {
        if (ordonnance != null && medicament != null) {
            ordonnance.addMedOrdo(medicament);
            return true;
        }
        return false;
    }

    // ORDO
    public static boolean addOrdonnance(Ordonnance ordonnance) {
        if (ordonnance != null && !listOrdonnances.contains(ordonnance)) {
            return getListOrdo().add(ordonnance);
        }
        //TODO: popup message already exist
        return false;
    }
    public static List<Ordonnance> getListOrdo() {
        return listOrdonnances;
    }

    //ACHATS
    public static boolean savingAchat(Achat achat) {
        if (achat == null) {
            return false;
        }
        // availability med
        for (Medicament med : getListMed()) {
            if (med.getStock() <= 0) {
                System.out.println("Out of Stock: "+med.getNameMed());
                return false;
            }
        }
        // down stock
        for (Medicament med : getListMed()) {
            med.reduireQuantite(1); // Réduction d'1 unité par défaut
        }
        // Recal montants
        achat.calMontants();
        // Add to list ALL Achats
        return listAchats.add(achat);
    }

    // Historic Achats
    public static List<Achat> getListAchats() {
        return listAchats;
    }

    // List of Client's Achat
    public List<Achat> getAchatsClient(Client client) {
        List<Achat> achatsClient = new ArrayList<>();
        if (client != null) {
            for (Achat achat : listAchats) {
                if (achat.getClient().equals(client)) {
                    achatsClient.add(achat);
                }
            }
        }
        return achatsClient;
    }

    public static double formatTwoDec(double nb){
        BigDecimal bd = new BigDecimal(nb).setScale(2, RoundingMode.HALF_UP);
        return nb = bd.doubleValue();
    }
/*
    // === MÉTHODES ===
    public void afficherStatistiques() {
        System.out.println("=== STATISTIQUES PHARMACIE SPARADRAP ===");
        System.out.println("Nombre de clients: " + listClients.size());
        System.out.println("Nombre de médecins: " + listMedecins.size());
        System.out.println("Nombre de médicaments: " + listMed.size());
        System.out.println("Nombre d'ordonnances: " + listOrdonnances.size());
        System.out.println("Nombre d'achats: " + listAchats.size());

        double chiffreAffaires = 0;
        for (Achat achat : listAchats) {
            chiffreAffaires += achat.getMontantTotal();
        }
        System.out.println("Chiffre d'affaires total: " + chiffreAffaires + "€");
    }*/


}