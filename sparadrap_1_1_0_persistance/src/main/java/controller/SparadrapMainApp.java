package controller;

import model.*;
import view.SparadrapMainInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Classe de lancement pour l'interface Sparadrap
 * Initialise les données de test et lance l'interface principale
 */
public class SparadrapMainApp {
    
    public static void main(String[] args) {
        // Configuration du Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                //showSplashScreen(); loading frame
                SparadrapMainInit.main(args); // initialisation data from main init
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
    
    /*private static void showSplashScreen() {
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
    }*/
}