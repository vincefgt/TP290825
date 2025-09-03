package view;

import controler.PharmacieController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Classe de lancement pour l'interface Sparadrap
 * Initialise les données de test et lance l'interface
 */
public class SparadrapLauncher {
    
    public static void main(String[] args) {
        // Configuration du Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Configuration des propriétés pour un meilleur rendu
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
                
                // Créer et afficher l'interface principale
                SparadrapInterface mainInterface = new SparadrapInterface();
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
        
        // Titre principal
        JLabel titleLabel = new JLabel("Pharmacie Sparadrap", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        // Sous-titre
        JLabel subtitleLabel = new JLabel("Système de Gestion Pharmaceutique", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        
        // Message de chargement
        JLabel loadingLabel = new JLabel("Initialisation en cours...", JLabel.CENTER);
        loadingLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        loadingLabel.setForeground(new Color(149, 165, 166));
        
        // Barre de progression
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(new Color(52, 73, 94));
        progressBar.setForeground(new Color(46, 204, 113));
        progressBar.setBorderPainted(false);
        
        // Panel central
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 0, 15));
        centerPanel.setOpaque(false);
        centerPanel.add(titleLabel);
        centerPanel.add(subtitleLabel);
        centerPanel.add(loadingLabel);
        
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
            controller.addMutuelle(mgen);
            controller.addMutuelle(harmonie);
            
            // Créer des médecins de test
            Medecin drDupont = new Medecin("Dupont", "Paris", 12345678910L, null);
            Medecin drMartin = new Medecin("Martin", "Lyon", 98765432110L, null);
            controller.addMedecin(drDupont);
            controller.addMedecin(drMartin);
            
            // Créer des clients de test
            Client client1 = new Client("Jean", "Durand", "123 Rue de la République",
                    75001, "Paris", "0123456789", "jean.durand@email.com",
                    111222333444555L, LocalDate.of(1980, 6, 15), mgen, drDupont);
            
            Client client2 = new Client("Marie", "Moreau", "456 Boulevard Voltaire",
                    69000, "Lyon", "0987654321", "marie.moreau@email.com",
                    666777888999000L, LocalDate.of(1975, 3, 22), harmonie, drMartin);
            
            controller.addClient(client1);
            controller.addClient(client2);
            
            // Créer des médicaments de test
            Medicament doliprane = new Medicament("Doliprane 1000mg", catMed.ANTALGIQUE, 
                                                5.20, LocalDate.now().toString(), 50);
            Medicament aspirine = new Medicament("Aspirine 500mg", catMed.ANALGESIQUE, 
                                               3.80, LocalDate.now().toString(), 30);
            Medicament ibuprofene = new Medicament("Ibuprofène 200mg", catMed.ANTIINFLAMMATOIRE, 
                                                 4.50, LocalDate.now().toString(), 25);
            
            controller.addMed(doliprane);
            controller.addMed(aspirine);
            controller.addMed(ibuprofene);
            
            // Créer quelques achats de test
            Achat achat1 = new Achat(LocalDate.now(), client1);
            achat1.addMedAchat(doliprane);
            achat1.addMedAchat(aspirine);
            controller.savingAchat(achat1);
            
            Achat achat2 = new Achat(LocalDate.now().minusDays(1), client2);
            achat2.addMedAchat(ibuprofene);
            controller.savingAchat(achat2);
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation des données de test: " + e.getMessage());
        }
    }
}