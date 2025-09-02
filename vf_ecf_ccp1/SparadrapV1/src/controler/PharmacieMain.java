package controler;

import model.*;
//import test.PharmacieTests;
import view.PharmacieView;
import java.time.LocalDate;

class PharmacieMain {
    public static void main(String[] args) {

    // Méthode de démonstration du fonctionnement du système
    public static void demonstrationSysteme() {
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
            Medecin drDupont = new Medecin("Dupont","Paris",12345678910L,null);
            Medecin drMartin = new Medecin("Martin", "Lyon",12345678913L,null);
            controller.addMedecin(drDupont);
            controller.addMedecin(drMartin);

            // Clients
            Client client1 = new Client("Durand", "Jean", "123 Rue de la République",
                    75001, "Paris", "0111223344",
                    "jean.durand@email.com", 111222333444555L, LocalDate.parse("15/06/1980"),null,null);
            client1.setMutuelle(mgen);
            client1.setMedecinTraitant(drDupont);

            Client client2 = new Client("Moreau", "Marie", "456 Boulevard Voltaire",
                    69000, "Lyon", "4455667788",
                    "marie.moreau@email.com", 666777888999000L, LocalDate.parse("22/03/1975"),null,null);
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

            // Création d'une ordonnance
            /*Ordonnance ordonnance1 = new Ordonnance(2024-01-20, drDupont, client1);
            ordonnance1.addMedOrdo(doliprane);
            ordonnance1.addMedOrdo(ibuprofene);
            controller.addOrdonnance(ordonnance1);

            // Achat avec ordonnance
            Achat achatOrdonnance = new Achat(2024-01-20, client1, ordonnance1);
            controller.savingAchat(achatOrdonnance);

            // Achat direct (sans ordonnance)
            Achat achatDirect = new Achat(2024-01-21, client2);
            achatDirect.ajouterMedicament(vitamine);
            achatDirect.ajouterMedicament(aspirine);
            controller.savingAchat(achatDirect);

            // Affichage des résultats
            System.out.println("\n=== DONNÉES CRÉÉES ===");

            System.out.println("\nClients enregistrés:");
            for (Client client : controller.getListClients()) {
                view.afficherClient(client);
            }*/

            System.out.println("\nMédicaments en stock:");
            for (Medicament med : controller.getListMed()) {
                System.out.println("- " + med);
            }

            System.out.println("\nOrdonnances créées:");
            for (Ordonnance ord : controller.getListOrdo()) {
                System.out.println("- " + ord);
                System.out.println("  Prix total: " + ord.calculerPrixTotal() + "€");
            }

            System.out.println("\nAchats enregistrés:");
            view.afficherListeAchats(controller.getListAchats());

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
