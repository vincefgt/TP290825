package view;

import controller.PharmacieController;
import model.*;

import java.util.List;

public class PharmacieView {
    private PharmacieController controller;
    private java.util.Scanner scanner;

    public PharmacieView(PharmacieController controller) {
        this.controller = controller;
        this.scanner = new java.util.Scanner(System.in);
    }

    // Menu principal
    public void afficherMenu() {
        System.out.println("\n=== PHARMACIE SPARADRAP - SYSTÈME DE GESTION ===");
        System.out.println("1. Gestion des clients");
        System.out.println("2. Gestion des médecins");
        System.out.println("3. Gestion des médicaments");
        System.out.println("4. Gestion des mutuelles");
        System.out.println("5. Créer une ordonnance");
        System.out.println("6. Enregistrer un achat");
        System.out.println("7. Enregistrer un achat avec ordonnance");
        System.out.println("8. Consulter les achats");
        System.out.println("9. Afficher les statistiques");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }

    // Méthode pour afficher les informations d'un client
    /*public void afficherClient(Client client) {
        if (client != null) {
            System.out.println("\n=== INFORMATIONS CLIENT ===");
            System.out.println("Nom: " + client.getNom());
            System.out.println("Prénom: " + client.getPrenom());
            System.out.println("Adresse: " + client.getAdresse());
            System.out.println("Téléphone: " + client.getTelephone());
            System.out.println("Email: " + client.getEmail());
            System.out.println("N° Sécurité Sociale: " + client.getNbSS());
            System.out.println("Date de naissance: " + client.getDateBirth());

            if (client.getMutuelle() != null) {
                System.out.println("Mutuelle: " + client.getMutuelle().getNom());
            } else {
                System.out.println("Mutuelle: Aucune");
            }

            if (client.getMedecinTraitant() != null) {
                System.out.println("Médecin traitant: " + client.getMedecinTraitant().toString());
            } else {
                System.out.println("Médecin traitant: Aucun");
            }
        }
    }*/

    // Méthode pour afficher la liste des achats
    public void afficherListeAchats(java.util.List<Achat> achats) {
        System.out.println("\n=== LISTE DES ACHATS ===");
        if (achats.isEmpty()) {
            System.out.println("Aucun achat enregistré.");
            return;
        }

        for (int i = 0; i < achats.size(); i++) {
            System.out.println((i + 1) + ". " + achats.get(i).toString());
        }
    }

    // Méthode pour afficher un message d'erreur
    public void afficherErreur(String message) {
        System.err.println("ERREUR: " + message);
    }

    // Méthode pour afficher un message de succès
    public void afficherSucces(String message) {
        System.out.println("SUCCÈS: " + message);
    }

    // Méthode pour lire une chaîne de caractères
    public String lireChaine(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    // Méthode pour lire un nombre entier
    public int lireEntier(String prompt) {
        System.out.print(prompt + ": ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Veuillez entrer un nombre valide.");
            return -1;
        }
    }

    // Méthode pour lire un nombre décimal
    public double lireDouble(String prompt) {
        System.out.print(prompt + ": ");
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Veuillez entrer un nombre décimal valide.");
            return -1;
        }
    }

    // Méthode pour créer un achat avec ordonnance
    public void creerAchatAvecOrdonnance() {
        System.out.println("\n=== CRÉATION D'UN ACHAT AVEC ORDONNANCE ===");
        
        // Sélection du client
        if (controller.getListClients().isEmpty()) {
            afficherErreur("Aucun client enregistré. Veuillez d'abord ajouter des clients.");
            return;
        }
        
        System.out.println("\nClients disponibles:");
        for (int i = 0; i < controller.getListClients().size(); i++) {
            System.out.println((i + 1) + ". " + controller.getListClients().get(i));
        }
        
        int clientIndex = lireEntier("Sélectionnez un client (numéro)") - 1;
        if (clientIndex < 0 || clientIndex >= controller.getListClients().size()) {
            afficherErreur("Sélection invalide.");
            return;
        }
        Client clientSelectionne = controller.getListClients().get(clientIndex);
        
        // Sélection du médecin
        if (controller.getListMedecins().isEmpty()) {
            afficherErreur("Aucun médecin enregistré. Veuillez d'abord ajouter des médecins.");
            return;
        }
        
        System.out.println("\nMédecins disponibles:");
        for (int i = 0; i < controller.getListMedecins().size(); i++) {
            System.out.println((i + 1) + ". " + controller.getListMedecins().get(i));
        }
        
        int medecinIndex = lireEntier("Sélectionnez un médecin (numéro)") - 1;
        if (medecinIndex < 0 || medecinIndex >= controller.getListMedecins().size()) {
            afficherErreur("Sélection invalide.");
            return;
        }
        Medecin medecinSelectionne = controller.getListMedecins().get(medecinIndex);
        
        // Dates
        java.time.LocalDate dateAchat = java.time.LocalDate.now();
        java.time.LocalDate dateOrdonnance = java.time.LocalDate.now();
        
        // Création de l'achat avec ordonnance
        boolean succes = controller.createNewAchatWithOrdonnance(dateAchat, clientSelectionne, 
                                                                dateOrdonnance, medecinSelectionne);
        
        if (succes) {
            afficherSucces("Achat avec ordonnance créé avec succès!");
            
            // Proposer d'ajouter des médicaments à l'ordonnance
            ajouterMedicamentsOrdonnance();
        } else {
            afficherErreur("Erreur lors de la création de l'achat avec ordonnance.");
        }
    }
    
    // Méthode pour ajouter des médicaments à l'ordonnance
    private void ajouterMedicamentsOrdonnance() {
        if (controller.getListMed().isEmpty()) {
            afficherErreur("Aucun médicament disponible.");
            return;
        }
        
        // Récupérer la dernière ordonnance créée
        if (controller.getListOrdo().isEmpty()) {
            afficherErreur("Aucune ordonnance trouvée.");
            return;
        }
        
        Ordonnance derniereOrdonnance = controller.getListOrdo().get(controller.getListOrdo().size() - 1);
        
        boolean continuerAjout = true;
        while (continuerAjout) {
            System.out.println("\nMédicaments disponibles:");
            for (int i = 0; i < controller.getListMed().size(); i++) {
                System.out.println((i + 1) + ". " + controller.getListMed().get(i));
            }
            
            int medIndex = lireEntier("Sélectionnez un médicament à ajouter (0 pour terminer)");
            
            if (medIndex == 0) {
                continuerAjout = false;
            } else if (medIndex > 0 && medIndex <= controller.getListMed().size()) {
                Medicament medicamentSelectionne = controller.getListMed().get(medIndex - 1);
                
                if (controller.addMedicamentToOrdonnance(derniereOrdonnance, medicamentSelectionne)) {
                    afficherSucces("Médicament ajouté à l'ordonnance: " + medicamentSelectionne.getNameMed());
                } else {
                    afficherErreur("Erreur lors de l'ajout du médicament.");
                }
            } else {
                afficherErreur("Sélection invalide.");
            }
        }
        
        System.out.println("\nOrdonnance finalisée avec " + derniereOrdonnance.getListMedOrdo().size() + " médicament(s).");
    }

    public static <T> void printList(List<T> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Liste vide ou nulle");
            return;
        }

        for (T element : list) {
            System.out.println(element);
        }
    }

}
