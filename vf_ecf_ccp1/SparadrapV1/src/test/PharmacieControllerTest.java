import controller.PharmacieController;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import view.PharmacieView;
import static controller.PharmacieController.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@DisplayName("Tests class PharmacieController")
public class PharmacieControllerTest {
    private PharmacieController controller;
    private Client client1, client2;
    private Medecin medecin1, medecin2;
    private Medicament medicament1, medicament2, medicament3;
    private Mutuelle mutuelle1, mutuelle2;
    private Ordonnance ordonnance;
    private Achat achat;

    @BeforeEach
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

    @Test
    @DisplayName("Test d'ajout de client valide")
    void testAjoutClientValide() {
        assertTrue(controller.addClient(client1));
        assertEquals(1, controller.getListClients().size());
        assertTrue(controller.getListClients().contains(client1));

        assertTrue(controller.addClient(client2));
        assertEquals(2, controller.getListClients().size());
        assertTrue(controller.getListClients().contains(client2));
    }

    @Test
    @DisplayName("Test d'ajout de client null")
    void testAjoutClientNull() {
        assertFalse(controller.addClient(null));
        assertEquals(0, controller.getListClients().size());
    }

    @Test
    @DisplayName("Test d'ajout de client en double")
    void testAjoutClientDouble() {
        assertTrue(controller.addClient(client1));
        assertEquals(1, controller.getListClients().size());

        // Tentative d'ajout du même client
        assertFalse(controller.addClient(client1));
        assertEquals(1, controller.getListClients().size());
    }

    @Test
    @DisplayName("Test de recherche client par numéro SS")
    void testRechercheClientParSS() {
        controller.addClient(client1);
        controller.addClient(client2);

        Client clientTrouve = controller.SearchSS(111222333444555L);
        assertNotNull(clientTrouve);
        assertEquals(client1, clientTrouve);

        Client clientTrouve2 = controller.SearchSS(666777888999000L);
        assertNotNull(clientTrouve2);
        assertEquals(client2, clientTrouve2);

        // Recherche avec numéro SS inexistant
        Client clientInexistant = controller.SearchSS(999999999999999L);
        assertNull(clientInexistant);
    }

    @Test
    @DisplayName("Test add medecin valid")
    void testAddMedecinValid() {
        listMedecins.clear(); // reset listMedecins
        assertTrue(controller.addMedecin(medecin1));
        assertEquals(1, getListMedecins().size());
        assertTrue(getListMedecins().contains(medecin1));

        assertTrue(controller.addMedecin(medecin2));
        assertEquals(2, getListMedecins().size());
        assertTrue(getListMedecins().contains(medecin2));
    }

    @Test
    @DisplayName("Test add medecin null")
    void testAddMedecinNull() {
        listMedecins.clear();
        assertFalse(controller.addMedecin(null));
        assertEquals(0, getListMedecins().size());
    }

    @Test
    @DisplayName("Test add medecinx2")
    void testAddMedecinDouble() {
        listMedecins.clear();
        assertTrue(controller.addMedecin(medecin1));
        assertEquals(1, getListMedecins().size());

        // Tentative d'ajout du même médecin
        assertFalse(controller.addMedecin(medecin1));
        assertEquals(1, getListMedecins().size());
    }

    @Test
    @DisplayName("Test search medecin by nbAgreement")
    void testSearcheMedecinAgreement() {
        controller.addMedecin(medecin1);
        controller.addMedecin(medecin2);

        Medecin medecinTrouve = controller.searchAgreement(12345678910L);
        assertNotNull(medecinTrouve);
        assertEquals(medecin1, medecinTrouve);

        Medecin medecinTrouve2 = controller.searchAgreement(98765432110L);
        assertNotNull(medecinTrouve2);
        assertEquals(medecin2, medecinTrouve2);

        // Recherche avec numéro d'agrément inexistant
        Medecin medecinInexistant = controller.searchAgreement(99999999999L);
        assertNull(medecinInexistant);
    }

    @Test
    @DisplayName("Test add med valid")
    void testAddMedValid() {
        listMed.clear(); //reset list

        assertTrue(controller.addMed(medicament1));
        assertEquals(1, getListMed().size());
        assertTrue(getListMed().contains(medicament1));

        assertTrue(controller.addMed(medicament2));
        assertEquals(2, getListMed().size());
        assertTrue(getListMed().contains(medicament2));
    }

    @Test
    @DisplayName("Test add med null")
    void testAddMedNull() {
        listMed.clear(); //reset list
        assertFalse(controller.addMed(null));
        assertEquals(0, getListMed().size());
    }

    @Test
    @DisplayName("Test add medX2")
    void testAjoutMedicamentDouble() {
        listMed.clear(); //reset list
        assertTrue(controller.addMed(medicament1));
        assertEquals(1, getListMed().size());

        // try add same x2
        assertFalse(controller.addMed(medicament1));
        assertEquals(1, getListMed().size());
    }

    @Test
    @DisplayName("Test de recherche médicament par nom")
    void testRechercheMedicamentParNom() {
        listMed.clear();
        controller.addMed(medicament1);
        controller.addMed(medicament2);
        PharmacieView.printList(listMed);
        Medicament medTrouve = controller.searchLastName("Doliprane");
        assertNotNull(medTrouve);
        assertEquals(medicament1, medTrouve);

        Medicament medTrouve2 = controller.searchLastName("aspirine"); // test insensible à la casse
        assertNotNull(medTrouve2);
        assertEquals(medicament2, medTrouve2);

        // Recherche avec nom inexistant
        Medicament medInexistant = controller.searchLastName("Inexistant");
        assertNull(medInexistant);
    }

    @Test
    @DisplayName("Test d'ajout de mutuelle valide")
    void testAjoutMutuelleValide() {
        assertTrue(controller.addMutuelle(mutuelle1));
        assertEquals(1, controller.getListMutuelles().size());
        assertTrue(controller.getListMutuelles().contains(mutuelle1));

        assertTrue(controller.addMutuelle(mutuelle2));
        assertEquals(2, controller.getListMutuelles().size());
        assertTrue(controller.getListMutuelles().contains(mutuelle2));
    }

    @Test
    @DisplayName("Test d'ajout de mutuelle null")
    void testAjoutMutuelleNull() {
        assertFalse(controller.addMutuelle(null));
        assertEquals(0, controller.getListMutuelles().size());
    }

    @Test
    @DisplayName("Test d'ajout d'ordonnance valide")
    void testAjoutOrdonnanceValide() {
        ordonnance = new Ordonnance("Dubois", "Pierre", "10 Rue Médical",
                "dr.dubois@hopital.fr", 75008, "Paris", "0140506070",
                12345678910L, null, LocalDate.of(2024, 1, 15), client1);
        assertTrue(controller.addOrdonnance(ordonnance));
        assertEquals(1, controller.getListOrdo().size());
        assertTrue(controller.getListOrdo().contains(ordonnance));
    }

    @Test
    @DisplayName("Test d'ajout d'ordonnance null")
    void testAjoutOrdonnanceNull() {
        assertFalse(controller.addOrdonnance(null));
        assertEquals(0, controller.getListOrdo().size());
    }

    @Test
    @DisplayName("Test de sauvegarde d'achat valide")
    void testSauvegardeAchatValide() {
        controller.addClient(client1);
        controller.addMed(medicament1);
        controller.addMed(medicament2);

        achat = new Achat(LocalDate.of(2024, 1, 20), client1);
        achat.addMedAchat(medicament1);
        achat.addMedAchat(medicament2);

        assertTrue(controller.savingAchat(achat));
        assertEquals(1, controller.getListAchats().size());
        assertTrue(controller.getListAchats().contains(achat));

        // Vérifier que les stocks ont été réduits
        assertEquals(49, medicament1.getStock()); // était 50, réduit de 1
        assertEquals(29, medicament2.getStock()); // était 30, réduit de 1
    }

    @Test
    @DisplayName("Test de sauvegarde d'achat null")
    void testSauvegardeAchatNull() {
        assertFalse(controller.savingAchat(null));
        assertEquals(0, controller.getListAchats().size());
    }

    @Test
    @DisplayName("Test de sauvegarde d'achat avec stock insuffisant")
    void testSauvegardeAchatStockInsuffisant() {
        // Créer un médicament avec stock 0
        Medicament medicamentStockZero = new Medicament("Stock Zéro", catMed.ANTALGIQUE, 5.0, "2023-01-01", 0);
        controller.addMed(medicamentStockZero);

        achat = new Achat(LocalDate.of(2024, 1, 20), client1);
        achat.addMedAchat(medicamentStockZero);

        assertFalse(controller.savingAchat(achat));
        assertEquals(0, controller.getListAchats().size());
        assertEquals(0, medicamentStockZero.getStock()); // Le stock ne doit pas changer
    }

    @Test
    @DisplayName("Test de récupération des achats d'un client")
    void testRecuperationAchatsClient() {
        // Créer des achats pour client1
        Achat achat1 = new Achat(LocalDate.of(2024, 1, 20), client1);
        //achat1.addMed(medicament1); // add list Med
        controller.savingAchat(achat1);

        Achat achat2 = new Achat(LocalDate.of(2024, 1, 21), client1);
        achat2.getListMedAchat().add(medicament2);
        controller.savingAchat(achat2);

        // Créer un achat pour client2
        Achat achat3 = new Achat(LocalDate.of(2024, 1, 22), client2);
        achat2.getListMedAchat().add(medicament2);
        controller.savingAchat(achat3);

        // Récupérer les achats de client1
        var achatsClient1 = controller.getAchatsClient(client1);
        assertEquals(2, achatsClient1.size());
        assertTrue(achatsClient1.contains(achat1));
        assertTrue(achatsClient1.contains(achat2));
        assertFalse(achatsClient1.contains(achat3));

        // Récupérer les achats de client2
        var achatsClient2 = controller.getAchatsClient(client2);
        assertEquals(1, achatsClient2.size());
        assertTrue(achatsClient2.contains(achat3));
    }

    @Test
    @DisplayName("Test de récupération des achats d'un client null")
    void testRecuperationAchatsClientNull() {
        var achats = controller.getAchatsClient(null);
        assertNotNull(achats);
        assertTrue(achats.isEmpty());
    }

    @Test
    @DisplayName("Test de récupération des achats d'un client sans achats")
    void testRecuperationAchatsClientSansAchats() {
        controller.addClient(client1);

        var achats = controller.getAchatsClient(client1);
        assertNotNull(achats);
        assertTrue(achats.isEmpty());
    }

    @Test
    @DisplayName("Test init lists")
    void testInitialisationListes() {
        controller = null; //clear app
        controller = new PharmacieController(); // init app
        // verified creation empty lists
        assertTrue(controller.getListClients().isEmpty());

        assertNotNull(getListMedecins());
        assertTrue(getListMedecins().isEmpty());

        assertNotNull(getListMed());
        assertTrue(getListMed().isEmpty());

        assertNotNull(getListMutuelles());
        assertTrue(getListMutuelles().isEmpty());

        assertNotNull(getListOrdo());
        assertTrue(getListOrdo().isEmpty());

        assertNotNull(getListAchats());
        assertTrue(getListAchats().isEmpty());
    }

    @Test
    @DisplayName("Test achat w ordo")
    void testAchatWOrdo() {
        ordonnance = new Ordonnance("Dubois", "Pierre", "10 Rue Médical",
                "dr.dubois@hopital.fr", 75008, "Paris", "0140506070",
                12345678910L, null, LocalDate.of(2024, 1, 15), client1);
        ordonnance.addMedOrdo(medicament1);
        ordonnance.addMedOrdo(medicament2);
        controller.addOrdonnance(ordonnance);

        // Create Achat w this Ordo
        achat = new Achat(LocalDate.of(2024, 1, 20), client1, ordonnance);
        assertTrue(controller.savingAchat(achat));
        assertEquals(1, getListAchats().size());

        // Verified Achat contain med Ordonnance
        assertTrue(ordonnance.getListMedOrdo().contains(medicament1));
        assertTrue(ordonnance.getListMedOrdo().contains(medicament2));

        // Vérifier que les stocks ont été réduits
        assertEquals(49, medicament1.getStock());
        assertEquals(29, medicament2.getStock());
    }

    @Test
    @DisplayName("Test de recherche avec paramètres invalides")
    void testRechercheParametresInvalides() {
        // Test de recherche client avec numéro SS 0
        Client clientTrouve = controller.SearchSS(0L);
        assertNull(clientTrouve);

        // Test de recherche médecin avec numéro agrément 0
        Medecin medecinTrouve = controller.searchAgreement(0L);
        assertNull(medecinTrouve);

        // Test de recherche médicament avec nom vide
        Medicament medTrouve = controller.searchLastName("");
        assertNull(medTrouve);

        // Test de recherche médicament avec nom null
        Medicament medTrouve2 = controller.searchLastName(null);
        assertNull(medTrouve2);
    }
}