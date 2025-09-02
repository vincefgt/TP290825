/*package test;

import controler.PharmacieController;
import model.*;

import java.time.LocalDate;

public class PharmacieTests {

    // Test de la classe Client
    public static void testerClient() {
        System.out.println("=== TEST CLASSE CLIENT ===");

        try {
            // Test création client valide
            Client client = new Client("Dupont", "Jean", "123 Rue de la Paix",
                    "75001", "Paris", "0123456789",
                    "jean.dupont@email.com", "123456789012345", "01/01/1980");
            System.out.println("✓ Création client valide: " + client);

            // Test validation nom vide
            try {
                Client clientInvalide = new Client("", "Jean", "123 Rue", "75001", "Paris",
                        "", "", "123456789012345", "01/01/1980");
                System.out.println("✗ Erreur: Client avec nom vide accepté");
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Validation nom vide: " + e.getMessage());
            }

            // Test modification nom valide
            client.setNom("Martin");
            System.out.println("✓ Modification nom: " + client.getNom());

        } catch (Exception e) {
            System.out.println("✗ Erreur test client: " + e.getMessage());
        }
    }

    // Test de la classe Medicament
    public static void testerMedicament() {
        System.out.println("\n=== TEST CLASSE MEDICAMENT ===");

        try {
            // Test création médicament valide
            Medicament med = new Medicament("Doliprane", "Antalgique", 5.50, "2023-01-01", 100);
            System.out.println("✓ Création médicament valide: " + med);

            // Test réduction quantité valide
            boolean resultat = med.reduireQuantite(10);
            if (resultat && med.getStock() == 90) {
                System.out.println("✓ Réduction quantité valide: " + med.getStock());
            } else {
                System.out.println("✗ Erreur réduction quantité");
            }

            // Test réduction quantité insuffisante
            boolean resultatInsuffisant = med.reduireQuantite(200);
            if (!resultatInsuffisant && med.getStock() == 90) {
                System.out.println("✓ Gestion stock insuffisant correcte");
            } else {
                System.out.println("✗ Erreur gestion stock insuffisant");
            }

            // Test prix négatif
            try {
                med.setPrice(-5.0);
                System.out.println("✗ Erreur: Prix négatif accepté");
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Validation prix négatif: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("✗ Erreur test médicament: " + e.getMessage());
        }
    }

    // Test de la classe Mutuelle
    public static void testerMutuelle() {
        System.out.println("\n=== TEST CLASSE MUTUELLE ===");

        try {
            // Test création mutuelle valide
            Mutuelle mutuelle = new Mutuelle("MGEN", 70.0);
            System.out.println("✓ Création mutuelle valide: " + mutuelle);

            // Test calcul remboursement
            double remboursement = mutuelle.calculerRemboursement(100.0);
            if (remboursement == 70.0) {
                System.out.println("✓ Calcul remboursement correct: " + remboursement + "€");
            } else {
                System.out.println("✗ Erreur calcul remboursement: " + remboursement);
            }

            // Test taux invalide
            try {
                mutuelle.setTauxRemb(150.0);
                System.out.println("✗ Erreur: Taux > 100% accepté");
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Validation taux invalide: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("✗ Erreur test mutuelle: " + e.getMessage());
        }
    }

    // Test de la classe PharmacieController
    public static void testerController() {
        System.out.println("\n=== TEST PHARMACIE CONTROLLER ===");

        try {
            PharmacieController controller = new PharmacieController();

            // Test ajout client
            Client client = new Client("Durand", "Marie", "456 Avenue", "69000",
                    "Lyon", "", "", "987654321098765", "15/03/1985");
            boolean ajoutClient = controller.addClient(client);
            if (ajoutClient && controller.getListClients().size() == 1) {
                System.out.println("✓ Ajout client dans controller");
            } else {
                System.out.println("✗ Erreur ajout client dans controller");
            }

            // Test recherche client
            Client clientTrouve = controller.SearchSS(Long.parseLong("987654321098765"));
            if (clientTrouve != null && clientTrouve.equals(client)) {
                System.out.println("✓ Recherche client par numéro SS");
            } else {
                System.out.println("✗ Erreur recherche client par numéro SS");
            }

            // Test ajout médicament
            Medicament med = new Medicament("Aspirine", catMed.ANALGESIQUE, 3.20, "2023-02-01", 50);
            boolean ajoutMed = controller.addMed(med);
            if (ajoutMed && controller.getListMed().size() == 1) {
                System.out.println("✓ Ajout médicament dans controller");
            } else {
                System.out.println("✗ Erreur ajout médicament dans controller");
            }

        } catch (Exception e) {
            System.out.println("✗ Erreur test controller: " + e.getMessage());
        }
    }

    // Test de création d'un achat complet
    public static void testerAchatComplet() {
        System.out.println("\n=== TEST ACHAT COMPLET ===");

        try {
            PharmacieController controller = new PharmacieController();

            // Création des objets nécessaires
            Client client = new Client("Lemaire", "Paul", "789 Boulevard", 13000,
                    "Marseille", 0000000000L, "", 456789123456789L, LocalDate.parse("20/07/1975"),null,null);
            Mutuelle mutuelle = new Mutuelle("Harmonie Mutuelle", 60.0);
            client.setMutuelle(mutuelle);

            Medecin medecin = new Medecin("Smith","Nancy", 12345678915L,null);

            Medicament med1 = new Medicament("Paracétamol", catMed.ANALGESIQUE, 4.50, "2023-01-01", 20);
            Medicament med2 = new Medicament("Ibuprofène", catMed.ANTIINFLAMMATOIRE, 6.80, "2023-01-01", 15);

            // Ajout dans le controller
            controller.addClient(client);
            controller.addMedecin(medecin);
            controller.addMed(med1);
            controller.addMed(med2);
            controller.addMutuelle(mutuelle);

            // Création d'une ordonnance
            Ordonnance ordonnance = new Ordonnance("2024-01-15", medecin, client);
            ordonnance.addMedOrdo(med1);
            ordonnance.addMedOrdo(med2);
            controller.addOrdonnance(ordonnance);

            // Création d'un achat avec ordonnance
            Achat achat = new Achat("2024-01-15", client, ordonnance);
            boolean achatEnregistre = controller.savingAchat(achat);

            if (achatEnregistre) {
                System.out.println("✓ Achat enregistré avec succès");
                System.out.println("  Montant total: " + achat.getMontantTotal() + "€");
                System.out.println("  Montant remboursé: " + achat.getMontantRembourse() + "€");
                System.out.println("  Stock Paracétamol après achat: " + med1.getStock());
                System.out.println("  Stock Ibuprofène après achat: " + med2.getStock());
            } else {
                System.out.println("✗ Erreur enregistrement achat");
            }

        } catch (Exception e) {
            System.out.println("✗ Erreur test achat complet: " + e.getMessage());
        }
    }

    // Méthode principale pour exécuter tous les tests
    public static void executerTousLesTests() {
        System.out.println("===== EXÉCUTION DES TESTS UNITAIRES =====");
        testerClient();
        testerMedicament();
        testerMutuelle();
        testerController();
        testerAchatComplet();
        System.out.println("===== FIN DES TESTS =====");
    }
}
*/