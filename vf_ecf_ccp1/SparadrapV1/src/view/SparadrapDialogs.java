package view;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

/**
 * Class dialogBox
 */
public class SparadrapDialogs {
    
    /**
     * add / update
     */
    public static class ClientDialog extends JDialog {
        private JTextField prenomField;
        private JTextField nomField;
        private JTextField adresseField;
        private JTextField emailField;
        private JTextField telephoneField;
        private JTextField numeroSSField;
        private JComboBox<Mutuelle> mutuelleCombo;
        private JComboBox<Medecin> medecinCombo;
        private JButton saveButton;
        private JButton cancelButton;
        
        private Client client;
        private boolean confirmed = false;
        
        public ClientDialog(Frame parent, Client client) {
            super(parent, client == null ? "Nouveau Client" : "Modifier Client", true);
            this.client = client;
            initializeDialog();
            createComponents();
            layoutComponents();
            setupEventHandlers();
            if (client != null) {
                loadClientData();
            }
        }
        
        private void initializeDialog() {
            setSize(500, 400);
            setLocationRelativeTo(getParent());
            setResizable(false);
        }
        
        private void createComponents() {
            prenomField = new JTextField();
            nomField = new JTextField();
            adresseField = new JTextField();
            emailField = new JTextField();
            telephoneField = new JTextField();
            numeroSSField = new JTextField();
            
            // ComboBox pour mutuelles
            mutuelleCombo = new JComboBox<>();
            mutuelleCombo.addItem(null); // Option "Aucune"
            for (Mutuelle mutuelle : controller.PharmacieController.getListMutuelles()) {
                mutuelleCombo.addItem(mutuelle);
            }
            
            // ComboBox pour médecins
            medecinCombo = new JComboBox<>();
            medecinCombo.addItem(null); // Option "Aucun"
            for (Medecin medecin : controller.PharmacieController.getListMedecins()) {
                medecinCombo.addItem(medecin);
            }
            
            saveButton = new JButton("Enregistrer");
            cancelButton = new JButton("Annuler");
            
            // Style des boutons
            saveButton.setBackground(new Color(46, 204, 113));
            saveButton.setForeground(Color.WHITE);
            saveButton.setBorderPainted(false);
            saveButton.setFocusPainted(false);
            
            cancelButton.setBackground(new Color(231, 76, 60));
            cancelButton.setForeground(Color.WHITE);
            cancelButton.setBorderPainted(false);
            cancelButton.setFocusPainted(false);
        }
        
        private void layoutComponents() {
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(Color.WHITE);
            
            // Panel de formulaire
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;
            
            // Prénom
            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(new JLabel("Prénom:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            formPanel.add(prenomField, gbc);
            
            // Nom
            gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
            formPanel.add(new JLabel("Nom:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            formPanel.add(nomField, gbc);
            
            // Adresse
            gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
            formPanel.add(new JLabel("Adresse:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            formPanel.add(adresseField, gbc);
            
            // Email
            gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
            formPanel.add(new JLabel("Email:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            formPanel.add(emailField, gbc);
            
            // Téléphone
            gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
            formPanel.add(new JLabel("Téléphone:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            formPanel.add(telephoneField, gbc);
            
            // Numéro SS
            gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
            formPanel.add(new JLabel("N° Sécurité Sociale:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            formPanel.add(numeroSSField, gbc);
            
            // Mutuelle
            gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
            formPanel.add(new JLabel("Mutuelle:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            formPanel.add(mutuelleCombo, gbc);
            
            // Médecin
            gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE;
            formPanel.add(new JLabel("Médecin traitant:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
            formPanel.add(medecinCombo, gbc);
            
            // Panel de boutons
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            
            mainPanel.add(formPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            setContentPane(mainPanel);
        }
        
        private void setupEventHandlers() {
            saveButton.addActionListener(e -> saveClient());
            cancelButton.addActionListener(e -> dispose());
        }
        
        private void loadClientData() {
            if (client != null) {
                prenomField.setText(client.getFirstName());
                nomField.setText(client.getLastName());
                adresseField.setText(client.getAddress());
                emailField.setText(client.getEmail());
                telephoneField.setText(client.getPhone());
                numeroSSField.setText(String.valueOf(client.getNbSS()));
                mutuelleCombo.setSelectedItem(client.getMutuelle());
                medecinCombo.setSelectedItem(client.getMedecinTraitant());
            }
        }
        
        private void saveClient() {
            try {
                String prenom = prenomField.getText().trim();
                String nom = nomField.getText().trim();
                String adresse = adresseField.getText().trim();
                String email = emailField.getText().trim();
                String telephone = telephoneField.getText().trim();
                String numeroSSText = numeroSSField.getText().trim();
                
                if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || numeroSSText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires!", 
                                                 "Champs manquants", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                long numeroSS = Long.parseLong(numeroSSText);
                Mutuelle mutuelle = (Mutuelle) mutuelleCombo.getSelectedItem();
                Medecin medecin = (Medecin) medecinCombo.getSelectedItem();
                
                if (client == null) {
                    // Nouveau client
                    client = new Client(prenom, nom, adresse, 75000, "Paris", telephone, 
                                      email, numeroSS, LocalDate.now(), mutuelle, medecin);
                } else {
                    // Modifier client existant
                    client.setFirstName(prenom);
                    client.setLastName(nom);
                    client.setAddress(adresse);
                    client.setEmail(email);
                    client.setPhone(telephone);
                    client.setNbSS(numeroSS);
                    client.setMutuelle(mutuelle);
                    client.setMedecinTraitant(medecin);
                }
                
                confirmed = true;
                dispose();
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Le numéro de sécurité sociale doit être un nombre valide!", 
                                             "Erreur de format", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), 
                                             "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        public boolean isConfirmed() {
            return confirmed;
        }
        
        public Client getClient() {
            return client;
        }
    }
    
    /**
     * Dialogue de confirmation personnalisé
     */
    public static class ConfirmDialog extends JDialog {
        private boolean confirmed = false;
        
        public ConfirmDialog(Frame parent, String message, String title) {
            super(parent, title, true);
            initializeDialog(message);
        }
        
        private void initializeDialog(String message) {
            setSize(350, 150);
            setLocationRelativeTo(getParent());
            setResizable(false);
            
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(Color.WHITE);
            
            JLabel messageLabel = new JLabel(message, JLabel.CENTER);
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(Color.WHITE);
            
            JButton yesButton = new JButton("Oui");
            JButton noButton = new JButton("Non");
            
            yesButton.setBackground(new Color(46, 204, 113));
            yesButton.setForeground(Color.WHITE);
            yesButton.setBorderPainted(false);
            yesButton.setFocusPainted(false);
            
            noButton.setBackground(new Color(231, 76, 60));
            noButton.setForeground(Color.WHITE);
            noButton.setBorderPainted(false);
            noButton.setFocusPainted(false);
            
            yesButton.addActionListener(e -> {
                confirmed = true;
                dispose();
            });
            
            noButton.addActionListener(e -> dispose());
            
            buttonPanel.add(yesButton);
            buttonPanel.add(noButton);
            
            mainPanel.add(messageLabel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            setContentPane(mainPanel);
        }
        
        public boolean isConfirmed() {
            return confirmed;
        }
    }
}