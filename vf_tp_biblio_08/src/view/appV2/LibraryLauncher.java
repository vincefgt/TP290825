package view.appV2;

import controler.Main;
import exception.SaisieException;
import javax.swing.*;
import java.awt.*;
import static javax.swing.UIManager.setLookAndFeel;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;

public class LibraryLauncher {
    
    public static void main(String[] args) {
        // Set system look and feel
        try {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Set font rendering hints for better text quality
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        SwingUtilities.invokeLater(() -> {
            try {
                // Show splash screen
                showSplashScreen();
                
                // Initialize data
                Main.init();
                
                // Create and show main frame
                MainLibraryFrame mainFrame = new MainLibraryFrame();
                mainFrame.setVisible(true);
                
            } catch (SaisieException e) {
                JOptionPane.showMessageDialog(null, 
                    "Error initializing application: " + e.getMessage(), 
                    "Initialization Error", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
    
    private static void showSplashScreen() {
        JWindow splash = new JWindow();
        splash.setSize(400, 250);
        splash.setLocationRelativeTo(null);
        
        JPanel splashPanel = new JPanel(new BorderLayout());
        splashPanel.setBackground(new Color(52, 73, 94));
        splashPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel("Library Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("BookRent v2.0 - Loading...", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(new Color(52, 73, 94));
        progressBar.setForeground(Color.BLACK);
        
        splashPanel.add(titleLabel, BorderLayout.NORTH);
        splashPanel.add(subtitleLabel, BorderLayout.CENTER);
        splashPanel.add(progressBar, BorderLayout.SOUTH);
        
        splash.setContentPane(splashPanel);
        splash.setVisible(true);
        
        // Simulate loading time
        Timer timer = new Timer(2000, e -> splash.dispose());
        timer.setRepeats(false);
        timer.start();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}