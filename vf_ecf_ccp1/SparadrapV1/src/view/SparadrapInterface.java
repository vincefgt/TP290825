package view;

import controler.PharmacieController;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Interface principale pour le système de gestion Pharmacie Sparadrap
 * Utilise le plugin UI Designer d'IntelliJ IDEA
 */
public class SparadrapInterface extends JFrame {
    
    // Panneaux principaux (générés par UI Designer)
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel navigationPanel;
    private JPanel contentPanel;
    private JPanel statusPanel;
    
    // Labels d'en-tête
    private JLabel titleLabel;
    private JLabel versionLabel;
    
    // Boutons de navigation
    private JButton clientsButton;
    private JButton medecinsButton;
    private JButton medicamentsButton;
    private JButton mutuellesButton;
    private JButton ordonnancesButton;
    private JButton achatsButton;
    private JButton statistiquesButton;
    
    // Panneaux de contenu
    private JPanel clientsPanel;
    private JPanel medicamentsPanel;
    private JPanel achatsPanel;
    private JPanel statistiquesPanel;
    
    // Tables
    private JTable clientsTable;
    private JTable medicamentsTable;
    private JTable achatsTable;
    private JScrollPane clientsScrollPane;
    private JScrollPane medicamentsScrollPane;
    private JScrollPane achatsScrollPane;
    
    // Formulaires clients
    private JPanel clientFormPanel;
    private JTextField clientPrenomField;
    private JTextField clientNomField;
    private JTextField clientEmailField;
    private JTextField clientSSField;
    private JButton addClientButton;
    private JButton clearClientButton;
    
    // Formulaires médicaments
    private JPanel medicamentFormPanel;
    private JTextField medicamentNomField;
    private JComboBox<catMed> medicamentCategorieCombo;
    private JTextField medicamentPrixField;
    private JTextField medicamentStockField;
    private JButton addMedicamentButton;
    private JButton clearMedicamentButton;
    
    // Formulaires achats
    private JPanel achatFormPanel;
    private JComboBox<String> achatClientCombo;
    private JComboBox<String> achatTypeCombo;
    private JButton createAchatButton;
    private JButton calculerTotalButton;
    
    // Statistiques
    private JPanel statsCard1, statsCard2, statsCard3, statsCard4;
    private JLabel totalClientsLabel;
    private JLabel totalMedicamentsLabel;
    private JLabel totalAchatsLabel;
    private JLabel chiffreAffairesLabel;
    private JButton refreshStatsButton;
    private JButton exportStatsButton;
    
    // Barre de statut
    private JLabel statusLabel;
    private JLabel timeLabel;
    
    // Contrôleur
    private PharmacieController controller;
    
    // Modèles de tables
    private DefaultTableModel clientsTableModel;
    private DefaultTableModel medicamentsTableModel;
    private DefaultTableModel achatsTableModel;
    
    public SparadrapInterface() {
        this.controller = new PharmacieController();
        initializeFrame();
        initializeComponents();
        setupEventHandlers();
        loadInitialData();
        startTimeUpdater();
        showClientsPanel(); // Afficher les clients par défaut
    }
    
    private void initializeFrame() {
        setTitle("Pharmacie Sparadrap - Système de Gestion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
    }
    
    private void initializeComponents() {
        // Initialiser les modèles de tables
        initializeTableModels();
        
        // Configurer les ComboBox
        setupComboBoxes();
        
        // Configurer les tables
        setupTables();
        
        // Mettre à jour le statut
        updateStatus("Application initialisée");
    }
    
    private void initializeTableModels() {
        // Modèle table clients
        String[] clientColumns = {"Prénom", "Nom", "Email", "N° SS", "Mutuelle", "Médecin"};
        clientsTableModel = new DefaultTableModel(clientColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        clientsTable.setModel(clientsTableModel);
        
        // Modèle table médicaments
        String[] medicamentColumns = {"Nom", "Catégorie", "Prix (€)", "Stock", "Date Marché"};
        medicamentsTableModel = new DefaultTableModel(medicamentColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        medicamentsTable.setModel(medicamentsTableModel);
        
        // Modèle table achats
        String[] achatColumns = {"Date", "Client", "Type", "Total (€)", "Remboursé (€)", "Médicaments"};
        achatsTableModel = new DefaultTableModel(achatColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        achatsTable.setModel(achatsTableModel);
    }
    
    private void setupComboBoxes() {
        // ComboBox catégories médicaments
        medicamentCategorieCombo.setModel(new DefaultComboBoxModel<>(catMed.values()));
        
        // ComboBox sera mis à jour dynamiquement pour les clients
        updateClientComboBox();
    }
    
    private void setupTables() {
        // Style des tables
        Font tableFont = new Font("Segoe UI", Font.PLAIN, 12);
        Font headerFont = new Font("Segoe UI", Font.BOLD, 12);
        Color headerColor = new Color(52, 73, 94);
        
        JTable[] tables = {clientsTable, medicamentsTable, achatsTable};
        for (JTable table : tables) {
            table.setFont(tableFont);
            table.setRowHeight(25);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getTableHeader().setFont(headerFont);
            table.getTableHeader().setBackground(headerColor);
            table.getTableHeader().setForeground(Color.WHITE);
            table.setGridColor(new Color(230, 230, 230));
        }
    }
    
    private void setupEventHandlers() {
        // Boutons de navigation
        clientsButton.addActionListener(e -> showClientsPanel());
        medecinsButton.addActionListener(e -> showMedecinsPanel());
        medicamentsButton.addActionListener(e -> showMedicamentsPanel());
        mutuellesButton.addActionListener(e -> showMutuellesPanel());
        ordonnancesButton.addActionListener(e -> showOrdonnancesPanel());
        achatsButton.addActionListener(e -> showAchatsPanel());
        statistiquesButton.addActionListener(e -> showStatistiquesPanel());
        
        // Boutons d'action clients
        addClientButton.addActionListener(e -> addNewClient());
        clearClientButton.addActionListener(e -> clearClientForm());
        
        // Boutons d'action médicaments
        addMedicamentButton.addActionListener(e -> addNewMedicament());
        clearMedicamentButton.addActionListener(e -> clearMedicamentForm());
        
        // Boutons d'action achats
        createAchatButton.addActionListener(e -> createNewAchat());
        calculerTotalButton.addActionListener(e -> calculerTotal());
        
        // Boutons statistiques
        refreshStatsButton.addActionListener(e -> updateStatistics());
        exportStatsButton.addActionListener(e -> exportStatistics());
    }
    
    private void showClientsPanel() {
        CardLayout layout = (CardLayout) contentPanel.getLayout();
        layout.show(contentPanel, "clients");
        updateNavigationButtons(clientsButton);
        loadClientsData();
        updateStatus("Gestion des clients");
    }
    
    private void showMedecinsPanel() {
        updateNavigationButtons(medecinsButton);
        updateStatus("Gestion des médecins");
        showInfoMessage("Fonctionnalité en développement", "Médecins");
    }
    
    private void showMedicamentsPanel() {
        CardLayout layout = (CardLayout) contentPanel.getLayout();
        layout.show(contentPanel, "medicaments");
        updateNavigationButtons(medicamentsButton);
        loadMedicamentsData();
        updateStatus("Gestion des médicaments");
    }
    
    private void showMutuellesPanel() {
        updateNavigationButtons(mutuellesButton);
        updateStatus("Gestion des mutuelles");
        showInfoMessage("Fonctionnalité en développement", "Mutuelles");
    }
    
    private void showOrdonnancesPanel() {
        updateNavigationButtons(ordonnancesButton);
        updateStatus("Gestion des ordonnances");
        showInfoMessage("Fonctionnalité en développement", "Ordonnances");
    }
    
    private void showAchatsPanel() {
        CardLayout layout = (CardLayout) contentPanel.getLayout();
        layout.show(contentPanel, "achats");
        updateNavigationButtons(achatsButton);
        loadAchatsData();
        updateStatus("Gestion des achats");
    }
    
    private void showStatistiquesPanel() {
        CardLayout layout = (CardLayout) contentPanel.getLayout();
        layout.show(contentPanel, "statistiques");
        updateNavigationButtons(statistiquesButton);
        updateStatistics();
        updateStatus("Statistiques de la pharmacie");
    }
    
    private void updateNavigationButtons(JButton selectedButton) {
        // Reset all buttons
        JButton[] buttons = {clientsButton, medecinsButton, medicamentsButton, 
                           mutuellesButton, ordonnancesButton, achatsButton, statistiquesButton};
        
        for (JButton button : buttons) {
            button.setBackground(Color.WHITE);
            button.setForeground(new Color(52, 73, 94));
        }
        
        // Highlight selected button
        selectedButton.setBackground(new Color(52, 152, 219));
        selectedButton.setForeground(Color.WHITE);
    }
    
    private void addNewClient() {
        try {
            String prenom = clientPrenomField.getText().trim();
            String nom = clientNomField.getText().trim();
            String email = clientEmailField.getText().trim();
            String ssText = clientSSField.getText().trim();
            
            if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || ssText.isEmpty()) {
                showErrorMessage("Veuillez remplir tous les champs obligatoires!");
                return;
            }
            
            long numeroSS = Long.parseLong(ssText);
            
            Client newClient = new Client(prenom, nom, "Adresse par défaut", 75000, 
                                        "Paris", "0000000000", email, numeroSS, 
                                        LocalDate.now(), null, null);
            
            controller.addClient(newClient);
            clearClientForm();
            loadClientsData();
            updateStatistics();
            showSuccessMessage("Client ajouté avec succès!");
            
        } catch (NumberFormatException e) {
            showErrorMessage("Le numéro de sécurité sociale doit être un nombre valide!");
        } catch (Exception e) {
            showErrorMessage("Erreur lors de l'ajout du client: " + e.getMessage());
        }
    }
    
    private void addNewMedicament() {
        try {
            String nom = medicamentNomField.getText().trim();
            catMed categorie = (catMed) medicamentCategorieCombo.getSelectedItem();
            String prixText = medicamentPrixField.getText().trim();
            String stockText = medicamentStockField.getText().trim();
            
            if (nom.isEmpty() || prixText.isEmpty() || stockText.isEmpty()) {
                showErrorMessage("Veuillez remplir tous les champs!");
                return;
            }
            
            double prix = Double.parseDouble(prixText);
            int stock = Integer.parseInt(stockText);
            
            Medicament newMedicament = new Medicament(nom, categorie, prix, 
                                                    LocalDate.now().toString(), stock);
            
            controller.addMed(newMedicament);
            clearMedicamentForm();
            loadMedicamentsData();
            updateStatistics();
            showSuccessMessage("Médicament ajouté avec succès!");
            
        } catch (NumberFormatException e) {
            showErrorMessage("Veuillez entrer des valeurs numériques valides pour le prix et le stock!");
        } catch (Exception e) {
            showErrorMessage("Erreur lors de l'ajout du médicament: " + e.getMessage());
        }
    }
    
    private void createNewAchat() {
        try {
            String clientName = (String) achatClientCombo.getSelectedItem();
            String achatType = (String) achatTypeCombo.getSelectedItem();
            
            if (clientName == null || clientName.equals("Sélectionner un client")) {
                showErrorMessage("Veuillez sélectionner un client!");
                return;
            }
            
            // Trouver le client sélectionné
            Client selectedClient = null;
            for (Client client : PharmacieController.getListClients()) {
                String fullName = client.getFirstName() + " " + client.getLastName();
                if (fullName.equals(clientName)) {
                    selectedClient = client;
                    break;
                }
            }
            
            if (selectedClient != null) {
                Achat newAchat = new Achat(LocalDate.now(), selectedClient);
                controller.savingAchat(newAchat);
                loadAchatsData();
                updateStatistics();
                showSuccessMessage("Achat créé avec succès!");
            }
            
        } catch (Exception e) {
            showErrorMessage("Erreur lors de la création de l'achat: " + e.getMessage());
        }
    }
    
    private void calculerTotal() {
        if (PharmacieController.getListAchats().isEmpty()) {
            showInfoMessage("Aucun achat à calculer", "Information");
            return;
        }
        
        double total = 0;
        for (Achat achat : PharmacieController.getListAchats()) {
            total += achat.getTotal();
        }
        
        showInfoMessage("Total de tous les achats: " + String.format("%.2f €", total), "Calcul Total");
    }
    
    private void loadInitialData() {
        loadClientsData();
        loadMedicamentsData();
        loadAchatsData();
        updateStatistics();
        updateClientComboBox();
    }
    
    private void loadClientsData() {
        clientsTableModel.setRowCount(0);
        for (Client client : PharmacieController.getListClients()) {
            Object[] row = {
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getNbSS(),
                client.getMutuelle() != null ? client.getMutuelle().getLastName() : "Aucune",
                client.getMedecinTraitant() != null ? client.getMedecinTraitant().getLastName() : "Aucun"
            };
            clientsTableModel.addRow(row);
        }
    }
    
    private void loadMedicamentsData() {
        medicamentsTableModel.setRowCount(0);
        for (Medicament medicament : PharmacieController.getListMed()) {
            Object[] row = {
                medicament.getNameMed(),
                medicament.getCat(),
                String.format("%.2f", medicament.getPrice()),
                medicament.getStock(),
                medicament.getDatOnMarket()
            };
            medicamentsTableModel.addRow(row);
        }
    }
    
    private void loadAchatsData() {
        achatsTableModel.setRowCount(0);
        for (Achat achat : PharmacieController.getListAchats()) {
            String type = Achat.IsAchatDirect() ? "Direct" : "Ordonnance";
            Object[] row = {
                achat.getDateAchat(),
                achat.getClient().getFirstName() + " " + achat.getClient().getLastName(),
                type,
                String.format("%.2f", achat.getTotal()),
                String.format("%.2f", achat.getRemb()),
                achat.getListMedAchat().size() + " médicament(s)"
            };
            achatsTableModel.addRow(row);
        }
    }
    
    private void updateClientComboBox() {
        achatClientCombo.removeAllItems();
        achatClientCombo.addItem("Sélectionner un client");
        
        for (Client client : PharmacieController.getListClients()) {
            String fullName = client.getFirstName() + " " + client.getLastName();
            achatClientCombo.addItem(fullName);
        }
    }
    
    private void updateStatistics() {
        int totalClients = PharmacieController.getListClients().size();
        int totalMedicaments = PharmacieController.getListMed().size();
        int totalAchats = PharmacieController.getListAchats().size();
        
        double chiffreAffaires = 0;
        for (Achat achat : PharmacieController.getListAchats()) {
            chiffreAffaires += achat.getTotal();
        }
        
        totalClientsLabel.setText(String.valueOf(totalClients));
        totalMedicamentsLabel.setText(String.valueOf(totalMedicaments));
        totalAchatsLabel.setText(String.valueOf(totalAchats));
        chiffreAffairesLabel.setText(String.format("%.2f €", chiffreAffaires));
    }
    
    private void clearClientForm() {
        clientPrenomField.setText("");
        clientNomField.setText("");
        clientEmailField.setText("");
        clientSSField.setText("");
    }
    
    private void clearMedicamentForm() {
        medicamentNomField.setText("");
        medicamentCategorieCombo.setSelectedIndex(0);
        medicamentPrixField.setText("");
        medicamentStockField.setText("");
    }
    
    private void exportStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== STATISTIQUES PHARMACIE SPARADRAP ===\n");
        stats.append("Date: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n\n");
        stats.append("Nombre de clients: ").append(PharmacieController.getListClients().size()).append("\n");
        stats.append("Nombre de médicaments: ").append(PharmacieController.getListMed().size()).append("\n");
        stats.append("Nombre d'achats: ").append(PharmacieController.getListAchats().size()).append("\n");
        
        double chiffreAffaires = 0;
        for (Achat achat : PharmacieController.getListAchats()) {
            chiffreAffaires += achat.getTotal();
        }
        stats.append("Chiffre d'affaires: ").append(String.format("%.2f €", chiffreAffaires)).append("\n");
        
        JTextArea textArea = new JTextArea(stats.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Statistiques Exportées", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void startTimeUpdater() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                    timeLabel.setText(currentTime);
                });
            }
        }, 0, 1000);
    }
    
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
        updateStatus(message);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
        updateStatus("Erreur: " + message);
    }
    
    private void showInfoMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
        updateStatus(message);
    }
    
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new SparadrapInterface().setVisible(true);
        });
    }
}