package controller;

import model.*;
//import test.PharmacieTests;
import view.PharmacieView;

class PharmacieMain {
    public static void main(String[] args) {
        try {
            // Initialisation du contrôleur et de la vue
            PharmacieController controller = new PharmacieController();
            PharmacieView view = new PharmacieView(controller);

            // Mutuelles
            Mutuelle mgen = new Mutuelle("MGEN", 70.0);
            Mutuelle harmonie = new Mutuelle("Harmonie Mutuelle", 60.0);
            controller.addMutuelle(mgen);
            controller.addMutuelle(harmonie);

            // Médecins
            Medecin drDupont = new Medecin("Dupont", "Pierre", "10 Rue Médicale",
                    "dr.dupont@hopital.fr", 75008, "Paris", "0140506070",
                    12345678910L, "MED001");
            Medecin drMartin = new Medecin("Martin", "Paul", "25 Avenue Santé",
                    "dr.martin@clinique.fr", 69000, "Lyon", "0472345678",
                    98765432110L, "MED002");
            controller.addMedecin(drDupont);
            controller.addMedecin(drMartin);

            // Clients
            Client client1 = new Client("Jean", "Durand", "123 Rue de la République",
                    75001, "Paris", "0111223344",
                    "jean.durand@email.com", 111222333444555L, 
                    java.time.LocalDate.of(1980, 6, 15), mgen, drDupont);
            client1.setMutuelle(mgen);
            client1.setMedecinTraitant(drDupont);

            Client client2 = new Client("Marie", "Moreau", "456 Boulevard Voltaire",
                    69000, "Lyon", "4455667788",
                    "marie.moreau@email.com", 666777888999000L, 
                    java.time.LocalDate.of(1975, 3, 22), harmonie, drMartin);
            client2.setMutuelle(harmonie);
            client2.setMedecinTraitant(drMartin);

            controller.addClient(client1);
            controller.addClient(client2);

            // Médicaments
            Medicament doliprane = new Medicament("Doliprane 1000mg", catMed.ANTALGIQUE, 5.20,"2023-01-01", 50);
            Medicament aspirine = new Medicament("Aspirine 500mg", catMed.ANALGESIQUE, 3.80, "2023-01-01", 30);
            Medicament ibuprofene = new Medicament("Ibuprofène 200mg", catMed.ANTIINFLAMMATOIRE, 4.50, "2023-01-01", 25);
            Medicament vitamine = new Medicament("Vitamine C", catMed.NUTRITION, 8.90, "2023-01-01", 40);

            controller.addMed(doliprane);
            controller.addMed(aspirine);
            controller.addMed(ibuprofene);
            controller.addMed(vitamine);

            // Démonstration de création d'achat avec ordonnance
            System.out.println("\n=== DÉMONSTRATION ACHAT AVEC ORDONNANCE ===");
            boolean achatCree = controller.createNewAchatWithOrdonnance(
                java.time.LocalDate.now(), client1, java.time.LocalDate.now(), drDupont);
            
            if (achatCree) {
                // Ajouter des médicaments à la dernière ordonnance créée
                Ordonnance derniereOrdonnance = controller.getListOrdo().get(controller.getListOrdo().size() - 1);
                controller.addMedToOrdo(derniereOrdonnance, doliprane);
                controller.addMedToOrdo(derniereOrdonnance, ibuprofene);
                
                System.out.println("✓ Achat avec ordonnance créé avec succès!");
                System.out.println("✓ Médicaments ajoutés à l'ordonnance: Doliprane, Ibuprofène");
            }

            System.out.println("\nMédicaments en stock:");
            for (Medicament med : controller.getListMed()) {
                System.out.println("- " + med);
            }

            // Affichage des statistiques
            System.out.println("");
            //controller.afficherStatistiques();

            System.out.println("\n✓ Démonstration terminée avec succès!");

        } catch (Exception e) {
            System.err.println("Erreur lors de la démonstration: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
