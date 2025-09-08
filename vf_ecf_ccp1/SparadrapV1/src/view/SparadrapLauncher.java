package view;

import controler.PharmacieController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Classe de lancement pour l'interface Sparadrap
 * Initialise les données de test et lance l'interface principale
 */
public class SparadrapLauncher {
    
    public static void main(String[] args) {
        // Configuration du Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                //showSplashScreen();
                initializeTestData();
                SparadrapMainInterface mainInterface = new SparadrapMainInterface();
                mainInterface.setVisible(true);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Erreur lors de l'initialisation: " + e.getMessage(), 
                    "Erreur de démarrage", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
    
    private static void showSplashScreen() {
        JWindow splash = new JWindow();
        splash.setSize(450, 300);
        splash.setLocationRelativeTo(null);
        
        JPanel splashPanel = new JPanel(new BorderLayout());
        splashPanel.setBackground(new Color(52, 73, 94));
        splashPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Logo et titre
        JLabel logoLabel = new JLabel("🏥", JLabel.CENTER);
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setForeground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Pharmacie SPARADRAP", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Système de Gestion Pharmaceutique", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        
        JLabel versionLabel = new JLabel("Version 1.0 - Chargement...", JLabel.CENTER);
        versionLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        versionLabel.setForeground(new Color(149, 165, 166));
        
        // Barre de progression
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(new Color(52, 73, 94));
        progressBar.setForeground(new Color(46, 204, 113));
        progressBar.setBorderPainted(false);
        
        // Layout du splash
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(logoLabel);
        centerPanel.add(titleLabel);
        centerPanel.add(subtitleLabel);
        centerPanel.add(versionLabel);
        
        splashPanel.add(centerPanel, BorderLayout.CENTER);
        splashPanel.add(progressBar, BorderLayout.SOUTH);
        
        splash.setContentPane(splashPanel);
        splash.setVisible(true);
        
        // Simuler le temps de chargement
        Timer timer = new Timer(3000, e -> splash.dispose());
        timer.setRepeats(false);
        timer.start();
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void initializeTestData() {
        try {
            PharmacieController initController = new PharmacieController();
            
            // Create mut (3)
            Mutuelle mgen = new Mutuelle("MGEN", 70.0);
            Mutuelle harmonie = new Mutuelle("Harmonie Mutuelle", 60.0);
            Mutuelle secu = new Mutuelle("Sécurité Sociale", 65.0);
            initController.addMutuelle(mgen);
            initController.addMutuelle(harmonie);
            initController.addMutuelle(secu);
            
            // Create medecin (2)
            Medecin drDupont = new Medecin("Charles", "Dupont", "15 Rue Médicale",
                    "dr.dupont@hopital.fr", 75008, "Paris", "0140506070",
                    12345678910L, "MED001");
            Medecin drMartin = new Medecin("Pierre", "Martin", "25 Avenue Santé",
                    "dr.martin@clinique.fr", 69000, "Lyon", "0472345678",
                    98765432110L, "MED002");
            initController.addMedecin(drDupont);
            initController.addMedecin(drMartin);
            
            // Create client (3)
            Client client1 = new Client("Durand", "Jean", "123 Rue de la République",
                    75001, "Paris", "0123456789", "jean.durand@email.com",
                    111222333444555L, LocalDate.of(1980, 6, 15), mgen, drDupont);
            Client client2 = new Client("Moreau", "Marie", "456 Boulevard Voltaire",
                    69000, "Lyon", "0987654321", "marie.moreau@email.com",
                    666777888999000L, LocalDate.of(1975, 3, 22), harmonie, drMartin);
            Client client3 = new Client("Bernard", "Pierre", "789 Avenue de la Liberté",
                    13000, "Marseille", "0491234567", "pierre.bernard@email.com",
                    555666777888999L, LocalDate.of(1985, 11, 8), secu, drDupont);
            initController.addClient(client1);
            initController.addClient(client2);
            initController.addClient(client3);
            
            // Create med (5)
            Medicament doliprane = new Medicament("Doliprane 1000mg", catMed.ANTALGIQUE, 
                    5.20, "2023-01-01", 50);
            Medicament aspirine = new Medicament("Aspirine 500mg", catMed.ANALGESIQUE, 
                    3.80, "2023-01-01", 30);
            Medicament ibuprofene = new Medicament("Ibuprofène 200mg", catMed.ANTIINFLAMMATOIRE, 
                    4.50, "2023-01-01", 25);
            Medicament vitamine = new Medicament("Vitamine C", catMed.VITAMINE, 
                    8.90, "2023-01-01", 40);
            Medicament antibiotique = new Medicament("Amoxicilline", catMed.ANTIBIOTIQUE, 
                    12.50, "2023-01-01", 20);
            initController.addMed(doliprane);
            initController.addMed(aspirine);
            initController.addMed(ibuprofene);
            initController.addMed(vitamine);
            initController.addMed(antibiotique);

            // Create ordo (1)
            Ordonnance ordonnance1 = new Ordonnance(LocalDate.parse("2024-01-20"), drDupont, client1);
            ordonnance1.addMedOrdo(doliprane);
            ordonnance1.addMedOrdo(ibuprofene);
            initController.addOrdonnance(ordonnance1);

            // Create achat (2)
            Achat achat1 = new Achat(LocalDate.now().minusDays(5), client1,ordonnance1);
            achat1.addMedAchat(doliprane);
            achat1.addMedAchat(vitamine);
            initController.savingAchat(achat1);
            Achat achat2 = new Achat(LocalDate.now().minusDays(2), client2);
            achat2.addMedAchat(aspirine);
            achat2.addMedAchat(ibuprofene);
            initController.savingAchat(achat2);

            PharmacieView.printList(initController.getListMedecins());

            System.out.println("✅ Données de test initialisées avec succès!");
            System.out.println("📊 Statistiques:");
            System.out.println("   - Clients: " + PharmacieController.getListClients().size());
            System.out.println("   - Médecins: " + PharmacieController.getListMedecins().size());
            System.out.println("   - Médicaments: " + PharmacieController.getListMed().size());
            System.out.println("   - Mutuelles: " + PharmacieController.getListMutuelles().size());
            System.out.println("   - Achats: " + PharmacieController.getListAchats().size());
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'initialisation des données: " + e.getMessage());
            e.printStackTrace();
        }
    }
}