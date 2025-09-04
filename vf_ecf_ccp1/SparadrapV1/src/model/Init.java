package model;

import controller.PharmacieController;

import java.time.LocalDate;

import static controller.PharmacieController.getListMed;

public class Init {
        private PharmacieController controller;
        private Client client1, client2;
        private Medecin medecin1, medecin2;
        private Medicament medicament1, medicament2, medicament3;
        private Mutuelle mutuelle1, mutuelle2;
        private Ordonnance ordonnance;
        private Achat achat;


    void setUp() {
        controller = new PharmacieController();
        mutuelle1 = new Mutuelle("MGEN", 70.0);
        mutuelle2 = new Mutuelle("Harmonie Mutuelle", 60.0);
        medecin1 = new Medecin("Dupont", "Paris", 12345678910L, null);
        medecin2 = new Medecin("Martin", "Lyon", 98765432110L, null);
        client1 = new Client("Durand", "Jean", "123 Rue Test",
                75001, "Paris", "0123456789", "jean@test.com",
                111222333444555L, LocalDate.of(1980, 1, 1), mutuelle1, medecin1);
        client2 = new Client("Moreau", "Marie", "456 Avenue Test",
                69000, "Lyon", "0987654321", "marie@test.com",
                666777888999000L, LocalDate.of(1975, 5, 15), mutuelle2, medecin2);
        medicament1 = new Medicament("Doliprane", catMed.ANTALGIQUE, 5.20, "2023-01-01", 50);
        getListMed().add(medicament1);
        medicament2 = new Medicament("Aspirine", catMed.ANALGESIQUE, 3.80, "2023-01-01", 30);
        getListMed().add(medicament2);
        medicament3 = new Medicament("Ibuprofène", catMed.ANTIINFLAMMATOIRE, 4.50, "2023-01-01", 25);
        getListMed().add(medicament3);
    }
}
