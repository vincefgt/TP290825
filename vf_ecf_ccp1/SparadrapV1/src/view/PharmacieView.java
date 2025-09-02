package view;

import controler.PharmacieController;
import model.Achat;
import model.Client;

import javax.swing.*;

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
        System.out.println("7. Consulter les achats");
        System.out.println("8. Afficher les statistiques");
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


    }
