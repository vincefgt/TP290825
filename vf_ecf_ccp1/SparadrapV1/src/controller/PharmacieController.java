package controller;

import model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        /**
         * listMedOrdo > list of med in each ordo > class Ordonnance creaet at Ordo creation.
         */
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
    public boolean addMedecin(Medecin medecin) {
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
    public boolean addMutuelle(Mutuelle mutuelle) {
        if (mutuelle != null && !listMutuelles.contains(mutuelle)) {
            return getListMutuelles().add(mutuelle);
        }
        return false;
    }
    public static List<Mutuelle> getListMutuelles() {
        return listMutuelles;
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
    public static boolean addMedicamentToOrdonnance(Ordonnance ordonnance, Medicament medicament) {
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
    // === MÉTHODES UTILITAIRES ===
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