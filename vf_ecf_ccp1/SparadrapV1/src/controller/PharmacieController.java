package controller;

import model.*;
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
    public boolean addClient(Client client) {
        if (client != null && !listClients.contains(client)) {
            return listClients.add(client);
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
    public List<Client> getListClients() {
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
    public boolean addMed(Medicament medicament) {
        if (medicament != null && !getListMed().contains(medicament)) {
            return listMed.add(medicament);
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
            return listMutuelles.add(mutuelle);
        }
        return false;
    }
    public static List<Mutuelle> getListMutuelles() {
        return listMutuelles;
    }

    // ORDO
    public boolean addOrdonnance(Ordonnance ordonnance) {
        if (ordonnance != null && !listOrdonnances.contains(ordonnance)) {
            return listOrdonnances.add(ordonnance);
        }
        //TODO: message already exist
        return false;
    }
    public static List<Ordonnance> getListOrdo() {
        return listOrdonnances;
    }

    //ACHATS
    public boolean savingAchat(Achat achat) {
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
            med.reduireQuantite(1); // -1 by default
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
