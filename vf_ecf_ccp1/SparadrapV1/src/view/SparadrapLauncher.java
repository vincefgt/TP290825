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
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            
            // Configuration pour un meilleur rendu des polices
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                // Afficher l'écran de démarrage
                showSplashScreen();
                
                // Initialiser les données de test
                initializeTestData();
                
                // Lancer l'interface principale
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
    
    private static void initializeTestData() {
        try {
            PharmacieController controller = new PharmacieController();
            
            // Créer des mutuelles de test
            Mutuelle mgen = new Mutuelle("MGEN", 70.0);
            Mutuelle harmonie = new Mutuelle("Harmonie Mutuelle", 60.0);
            Mutuelle secu = new Mutuelle("Sécurité Sociale", 65.0);
            
            controller.addMutuelle(mgen);
            controller.addMutuelle(harmonie);
            controller.addMutuelle(secu);
            
            // Créer des médecins de test
            Medecin drDupont = new Medecin("Dupont", "Dr", "15 Rue Médicale", 
                    "dr.dupont@hopital.fr", 75008, "Paris", "0140506070",
                    12345678910L, "MED001");
            Medecin drMartin = new Medecin("Martin", "Dr", "25 Avenue Santé",
                    "dr.martin@clinique.fr", 69000, "Lyon", "0472345678",
                    98765432110L, "MED002");
            
            controller.addMedecin(drDupont);
            controller.addMedecin(drMartin);
            
            // Créer des clients de test
            Client client1 = new Client("Durand", "Jean", "123 Rue de la République",
                    75001, "Paris", "0123456789", "jean.durand@email.com",
                    111222333444555L, LocalDate.of(1980, 6, 15), mgen, drDupont);
            
            Client client2 = new Client("Moreau", "Marie", "456 Boulevard Voltaire",
                    69000, "Lyon", "0987654321", "marie.moreau@email.com",
                    666777888999000L, LocalDate.of(1975, 3, 22), harmonie, drMartin);
            
            Client client3 = new Client("Bernard", "Pierre", "789 Avenue de la Liberté",
                    13000, "Marseille", "0491234567", "pierre.bernard@email.com",
                    555666777888999L, LocalDate.of(1985, 11, 8), secu, drDupont);
            
            controller.addClient(client1);
            controller.addClient(client2);
            controller.addClient(client3);
            
            // Créer des médicaments de test
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
            
            controller.addMed(doliprane);
            controller.addMed(aspirine);
            controller.addMed(ibuprofene);
            controller.addMed(vitamine);
            controller.addMed(antibiotique);
            
            // Créer quelques achats de test
            Achat achat1 = new Achat(LocalDate.now().minusDays(5), client1);
            achat1.addMedAchat(doliprane);
            achat1.addMedAchat(vitamine);
            controller.savingAchat(achat1);
            
            Achat achat2 = new Achat(LocalDate.now().minusDays(2), client2);
            achat2.addMedAchat(aspirine);
            achat2.addMedAchat(ibuprofene);
            controller.savingAchat(achat2);
            
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