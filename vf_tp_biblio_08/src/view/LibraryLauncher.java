package view;

import controler.Main;
import exception.SaisieException;

import javax.swing.*;
import java.awt.*;

public class LibraryLauncher {
    
    public static void main(String[] args) {
        // Set system look and feel for better integration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Enable font anti-aliasing for crisp text
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        SwingUtilities.invokeLater(() -> {
            try {
                // Show loading splash
                showSplashScreen();
                
                // Initialize application data
                Main.init();
                
                // Launch main interface
                ModernLibraryInterface mainInterface = new ModernLibraryInterface();
                mainInterface.setVisible(true);
                
            } catch (SaisieException e) {
                JOptionPane.showMessageDialog(null, 
                    "❌ Error initializing application: " + e.getMessage(), 
                    "Initialization Error", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
    
    private static void showSplashScreen() {
        JWindow splash = new JWindow();
        splash.setSize(450, 300);
        splash.setLocationRelativeTo(null);
        
        // Create splash content
        JPanel splashPanel = new JPanel(new BorderLayout());
        splashPanel.setBackground(new Color(52, 73, 94));
        splashPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Title section
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(52, 73, 94));
        
        JLabel iconLabel = new JLabel("📚", JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Library Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("BookRent Professional Edition v2.0", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(iconLabel);
        titlePanel.add(Box.createVerticalStrut(15));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);
        
        // Loading section
        JPanel loadingPanel = new JPanel(new FlowLayout());
        loadingPanel.setBackground(new Color(52, 73, 94));
        
        JLabel loadingLabel = new JLabel("Initializing application...");
        loadingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loadingLabel.setForeground(new Color(189, 195, 199));
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(300, 8));
        progressBar.setBackground(new Color(52, 73, 94));
        progressBar.setForeground(new Color(41, 128, 185));
        progressBar.setBorderPainted(false);
        
        loadingPanel.add(loadingLabel);
        
        splashPanel.add(titlePanel, BorderLayout.CENTER);
        splashPanel.add(loadingPanel, BorderLayout.SOUTH);
        splashPanel.add(progressBar, BorderLayout.PAGE_END);
        
        splash.setContentPane(splashPanel);
        splash.setVisible(true);
        
        // Auto-close splash after delay
        Timer timer = new Timer(2500, e -> splash.dispose());
        timer.setRepeats(false);
        timer.start();
        
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}