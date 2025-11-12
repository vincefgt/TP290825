package controller;

import model.*;
import java.time.LocalDate;

class SparadrapMainInit {
    public static void main(String[] args) {
        initializeTestData(); // loading data
    }

    public static PharmacieController initializeTestData() {
        try {
            PharmacieController initController = new PharmacieController();
           /*
            // Create mut (4)
            Mutuelle defaultMut = new Mutuelle("Sélectionner mutuelle",0); //default do not delete
            Mutuelle mgen = new Mutuelle("MGEN", 70.0);
            Mutuelle harmonie = new Mutuelle("Harmonie Mutuelle", 60.0);
            Mutuelle secu = new Mutuelle("Sécurité Sociale", 65.0);
            initController.addMutuelle(defaultMut); // do not delete
            initController.addMutuelle(mgen);
            initController.addMutuelle(harmonie);
            initController.addMutuelle(secu);*/
/*
            // Create medecin (3)
            Medecin defaultMedecin = new Medecin("default", "Tous Medecins", "Nc",
                    "default@medecin.fr", 0, "Nc", "0000000000",
                    12345678910L, "MED000"); // do no delete
            Medecin drDupont = new Medecin("Charles", "Dupont", "15 Rue Médicale",
                    "dr.dupont@hopital.fr", 75008, "Paris", "0140506070",
                    12345678910L, "MED001");
            Medecin drMartin = new Medecin("Pierre", "Martin", "25 Avenue Santé",
                    "dr.martin@clinique.fr", 69000, "Lyon", "0472345678",
                    98765432110L, "MED002");
            initController.addMedecin(defaultMedecin);
            initController.addMedecin(drDupont);
            initController.addMedecin(drMartin);
/*
            // Create client (4)
            Client defaultClient = new Client("default", "Tous Clients", "Nc",
                    0, "Nc", "0000000000", "default@client.fr", 123456789101112L,
                    LocalDate.of(1900, 1, 1), null, null); // default do not delete
            Client client1 = new Client("Durand", "Jean", "123 Rue de la République",
                    75001, "Paris", "0123456789", "jean.durand@email.com",
                        111222333444555L, LocalDate.of(1980, 6, 15), null, drDupont);
            Client client2 = new Client("Moreau", "Marie", "456 Boulevard Voltaire",
                    69000, "Lyon", "0987654321", "marie.moreau@email.com",
                    666777888999000L, LocalDate.of(1975, 3, 22), null, drMartin);
            Client client3 = new Client("Bernard", "Pierre", "789 Avenue de la Liberté",
                    13000, "Marseille", "0491234567", "pierre.bernard@email.com",
                    555666777888999L, LocalDate.of(1985, 11, 8), null, drDupont);
            initController.addClient(defaultClient); // do not delete
            initController.addClient(client1);
            initController.addClient(client2);
            initController.addClient(client3);
            /*
            // Create med (5)
            Medicament doliprane = new Medicament("Doliprane 1000mg", catMed.ANTALGIQUE.toString(),
                    15.20, "1986-03-21", 50);
            Medicament aspirine = new Medicament("Aspirine 500mg", catMed.ANALGESIQUE.toString(),
                    3.80, "2023-01-10", 30);
            Medicament ibuprofene = new Medicament("Ibuprofène 200mg", catMed.ANTIINFLAMMATOIRE.toString(),
                    4.50, "2024-01-01", 25);
            Medicament vitamine = new Medicament("Vitamine C", catMed.VITAMINE.toString(),
                    8.90, "2023-02-01", 40);
            Medicament antibiotique = new Medicament("Amoxicilline", catMed.ANTIBIOTIQUE.toString(),
                    12.50, "2025-09-04", 20);
            initController.addMed(doliprane);
            initController.addMed(aspirine);
            initController.addMed(ibuprofene);
            initController.addMed(vitamine);
            initController.addMed(antibiotique);

            // Create ordo (1)
            Ordonnance ordonnance1 = new Ordonnance(LocalDate.parse("2024-01-20"), drDupont, client1); //client1 = durand
            ordonnance1.addMedOrdo(doliprane);
            ordonnance1.addMedOrdo(vitamine);
            initController.addOrdonnance(ordonnance1);
            Ordonnance ordonnance2 = new Ordonnance(LocalDate.parse("2024-08-20"), drMartin, client2); //client1 = durand
            ordonnance2.addMedOrdo(ibuprofene);
            ordonnance2.addMedOrdo(antibiotique);
            initController.addOrdonnance(ordonnance2);

            // Create achat (2)
            Achat achat3 = new Achat(LocalDate.now().minusDays(240), client3);
            achat3.addMedAchat(antibiotique);
            initController.savingAchat(achat3);
            Achat achat1 = new Achat(LocalDate.now().minusDays(28), client1,ordonnance1);
            initController.savingAchat(achat1);
            Achat achat2 = new Achat(LocalDate.now().minusDays(0), client2);
            achat2.addMedAchat(aspirine);
            achat2.addMedAchat(ibuprofene);
            initController.savingAchat(achat2);
            Achat achat4 = new Achat(LocalDate.now().minusDays(2), client2,ordonnance2);
            initController.savingAchat(achat4);

           /* System.out.println("✅ Données de test initialisées avec succès!");
            System.out.println("📊 Statistiques:");
            System.out.println("   - Clients: " + PharmacieController.getListClients().size());
            System.out.println("   - Médecins: " + PharmacieController.getListMedecins().size());
            System.out.println("   - Médicaments: " + PharmacieController.getListMed().size());
            System.out.println("   - Mutuelles: " + PharmacieController.getListMutuelles().size());
            System.out.println("   - Achats: " + PharmacieController.getListAchats().size());

            /**
             * double chiffreAffaires = 0;
            for (Achat achat : listAchats) {
                chiffreAffaires += achat.getMontantTotal(); }
            System.out.println("Chiffre d'affaires total: " + chiffreAffaires + "€");
             **/
            return initController;

        } catch (Exception e) {
           System.err.println("❌ Erreur lors de l'initialisation des données: " + e.getMessage());
            return null;
        }
    }

}