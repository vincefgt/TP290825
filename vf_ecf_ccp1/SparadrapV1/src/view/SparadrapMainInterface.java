package view;

import controler.PharmacieController;
import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;


public class SparadrapMainInterface extends JFrame {
    
    // Panneaux principaux (liés au .form)
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel navigationPanel;
    private JPanel statusPanel;
    private JTabbedPane mainTabbedPane;
    
    // Labels du header
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
    
    // Onglet Clients
    private JPanel clientFormPanel;
    private JTextField clientPrenomField;
    private JTextField clientNomField;
    private JTextField clientEmailField;
    private JTextField clientPhoneField;
    private JTextField clientAddressField;
    private JButton addClientButton;
    private JButton clearClientButton;
    private JScrollPane clientScrollPane;
    private JTable clientsTable;
    private DefaultTableModel clientsTableModel;
    
    // Onglet Médicaments
    private JPanel medicamentFormPanel;
    private JTextField medicamentNomField;
    private JComboBox<catMed> medicamentCategorieCombo;
    private JTextField medicamentPrixField;
    private JTextField medicamentStockField;
    private JButton addMedicamentButton;
    private JButton clearMedicamentButton;
    private JScrollPane medicamentScrollPane;
    private JTable medicamentsTable;
    private DefaultTableModel medicamentsTableModel;
    
    // Onglet Achats
    private JPanel achatActionsPanel;
    private JButton nouvelAchatButton;
    private JButton achatOrdonnanceButton;
    private JButton calculerRemboursementButton;
    private JScrollPane achatsScrollPane;
    private JTable achatsTable;
    private DefaultTableModel achatsTableModel;
    
    // Onglet Statistiques
    private JPanel statsCard1, statsCard2, statsCard3, statsCard4;
    private JLabel totalClientsLabel;
    private JLabel totalMedicamentsLabel;
    private JLabel totalAchatsLabel;
    private JLabel chiffreAffairesLabel;
    private JButton refreshStatsButton;
    
    // Status bar
    private JLabel statusLabel;
    private JLabel timeLabel;
    
    // Controller
    private PharmacieController controller;
    
    public SparadrapMainInterface() {
        //this.controller = new PharmacieController();
        initializeFrame();
        initializeComponents();
        setupEventHandlers();
        loadInitialData();
        //startTimeUpdater();
    }
    
    private void initializeFrame() {
        setTitle("🏥 Pharmacie SPARADRAP - Système de Gestion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
        setContentPane(mainPanel);
    }
    
    private void initializeComponents() {
        initializeTableModels(); // Initialize table models
        setupComboBoxes(); // Setup combo boxes
        applyModernStyling(); // Apply style
    }
    
    private void initializeTableModels() {
        // Clients table
        String[] clientColumns = {"Prénom", "Nom", "Email", "Téléphone", "N° SS", "Mutuelle"};
        clientsTableModel = new DefaultTableModel(clientColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        clientsTable.setModel(clientsTableModel);
        
        // Médicaments table
        String[] medicamentColumns = {"Nom", "Catégorie", "Prix (€)", "Stock", "Date Marché"};
        medicamentsTableModel = new DefaultTableModel(medicamentColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        medicamentsTable.setModel(medicamentsTableModel);
        
        // Achats table
        String[] achatColumns = {"Date", "Client", "Type", "Montant Total", "Remboursement", "Ordonnance"};
        achatsTableModel = new DefaultTableModel(achatColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        achatsTable.setModel(achatsTableModel);
    }
    
    private void setupComboBoxes() {
        // Populate medicament categories
        medicamentCategorieCombo.removeAllItems();
        for (catMed category : catMed.values()) {
            medicamentCategorieCombo.addItem(category);
        }
    }
    
    private void applyModernStyling() {
        // Style tables
        styleTable(clientsTable);
        styleTable(medicamentsTable);
        styleTable(achatsTable);
        
        // Style buttons with hover effects
        styleNavigationButtons();
        styleActionButtons();
    }
    
    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(52, 152, 219, 50));
        table.setSelectionForeground(new Color(52, 73, 94));
    }
    
    private void styleNavigationButtons() {
        JButton[] navButtons = {clientsButton, medecinsButton, medicamentsButton, 
                               mutuellesButton, ordonnancesButton, achatsButton, statistiquesButton};
        
        for (JButton button : navButtons) {
            addHoverEffect(button, new Color(52, 152, 219), new Color(41, 128, 185));
        }
    }
    
    private void styleActionButtons() {
        // Add hover effects to action buttons
        addHoverEffect(addClientButton, new Color(46, 204, 113), new Color(39, 174, 96));
        addHoverEffect(clearClientButton, new Color(149, 165, 166), new Color(127, 140, 141));
        addHoverEffect(addMedicamentButton, new Color(46, 204, 113), new Color(39, 174, 96));
        addHoverEffect(clearMedicamentButton, new Color(149, 165, 166), new Color(127, 140, 141));
        addHoverEffect(nouvelAchatButton, new Color(46, 204, 113), new Color(39, 174, 96));
        addHoverEffect(achatOrdonnanceButton, new Color(52, 152, 219), new Color(41, 128, 185));
        addHoverEffect(calculerRemboursementButton, new Color(155, 89, 182), new Color(142, 68, 173));
        addHoverEffect(refreshStatsButton, new Color(52, 73, 94), new Color(44, 62, 80));
    }
    
    private void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.setBackground(normalColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }
    
    private void setupEventHandlers() {
        // Navigation buttons
        clientsButton.addActionListener(e -> showTab(0));
        medicamentsButton.addActionListener(e -> showTab(1));
        achatsButton.addActionListener(e -> showTab(2));
        statistiquesButton.addActionListener(e -> showTab(3));
        
        // Client actions
        addClientButton.addActionListener(e -> addNewClient());
        clearClientButton.addActionListener(e -> clearClientForm());
        
        // Médicament actions
        addMedicamentButton.addActionListener(e -> addNewMedicament());
        clearMedicamentButton.addActionListener(e -> clearMedicamentForm());
        
        // Achat actions
        nouvelAchatButton.addActionListener(e -> createNewAchat());
        achatOrdonnanceButton.addActionListener(e -> createAchatWithOrdonnance());
        calculerRemboursementButton.addActionListener(e -> calculateRemboursement());
        
        // Stats
        refreshStatsButton.addActionListener(e -> updateStatistics());
        
        // Table double-click events
        clientsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showClientDetails();
                }
            }
        });
        
        medicamentsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showMedicamentDetails();
                }
            }
        });
    }
    
    private void showTab(int index) {
        mainTabbedPane.setSelectedIndex(index);
        updateStatusLabel("Onglet sélectionné: " + mainTabbedPane.getTitleAt(index));
    }
    
    private void addNewClient() {
        try {
            String prenom = clientPrenomField.getText().trim();
            String nom = clientNomField.getText().trim();
            String email = clientEmailField.getText().trim();
            String phone = clientPhoneField.getText().trim();
            String address = clientAddressField.getText().trim();
            
            if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty()) {
                showErrorMessage("Veuillez remplir au moins le prénom, nom et email!");
                return;
            }
            
            // Create client with default values for required fields
            Client newClient = new Client(prenom, nom, address.isEmpty() ? "Non renseigné" : address,
                    75000, "Paris", phone.isEmpty() ? "0000000000" : phone,
                    email, generateRandomSS(), LocalDate.now(), null, null);
            
            controller.addClient(newClient);
            loadClientsData();
            clearClientForm();
            showSuccessMessage("Client ajouté avec succès!");
            updateStatusLabel("Nouveau client ajouté: " + prenom + " " + nom);
            
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
            loadMedicamentsData();
            clearMedicamentForm();
            showSuccessMessage("Médicament ajouté avec succès!");
            updateStatusLabel("Nouveau médicament ajouté: " + nom);
            
        } catch (NumberFormatException e) {
            showErrorMessage("Veuillez entrer des valeurs numériques valides pour le prix et le stock!");
        } catch (Exception e) {
            showErrorMessage("Erreur lors de l'ajout du médicament: " + e.getMessage());
        }
    }
    
    private void createNewAchat() {
        if (PharmacieController.getListClients().isEmpty()) {
            showErrorMessage("Aucun client enregistré. Veuillez d'abord ajouter des clients.");
            return;
        }
        
        // Open achat dialog
        AchatDialog dialog = new AchatDialog(this, controller);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            loadAchatsData();
            updateStatistics();
            updateStatusLabel("Nouvel achat enregistré");
        }
    }
    
    private void createAchatWithOrdonnance() {
        showInfoMessage("Fonctionnalité en développement: Achat avec ordonnance");
    }
    
    private void calculateRemboursement() {
        int selectedRow = achatsTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("Veuillez sélectionner un achat dans la liste!");
            return;
        }
        
        // Show remboursement details
        String clientName = (String) achatsTableModel.getValueAt(selectedRow, 1);
        String montantTotal = (String) achatsTableModel.getValueAt(selectedRow, 3);
        String remboursement = (String) achatsTableModel.getValueAt(selectedRow, 4);
        
        String message = String.format(
            "Détails du remboursement:\n\n" +
            "Client: %s\n" +
            "Montant total: %s\n" +
            "Remboursement: %s\n" +
            "Reste à charge: %.2f €",
            clientName, montantTotal, remboursement,
            Double.parseDouble(montantTotal.replace(" €", "")) - 
            Double.parseDouble(remboursement.replace(" €", ""))
        );
        
        showInfoMessage(message);
    }
    
    private void showClientDetails() {
        int selectedRow = clientsTable.getSelectedRow();
        if (selectedRow != -1) {
            String prenom = (String) clientsTableModel.getValueAt(selectedRow, 0);
            String nom = (String) clientsTableModel.getValueAt(selectedRow, 1);
            
            // Find client in controller
            for (Client client : PharmacieController.getListClients()) {
                if (client.getFirstName().equals(prenom) && client.getLastName().equals(nom)) {
                    ClientDetailsDialog dialog = new ClientDetailsDialog(this, client);
                    dialog.setVisible(true);
                    break;
                }
            }
        }
    }
    
    private void showMedicamentDetails() {
        int selectedRow = medicamentsTable.getSelectedRow();
        if (selectedRow != -1) {
            String nom = (String) medicamentsTableModel.getValueAt(selectedRow, 0);
            String categorie = (String) medicamentsTableModel.getValueAt(selectedRow, 1);
            String prix = (String) medicamentsTableModel.getValueAt(selectedRow, 2);
            String stock = (String) medicamentsTableModel.getValueAt(selectedRow, 3);
            
            String message = String.format(
                "Détails du médicament:\n\n" +
                "Nom: %s\n" +
                "Catégorie: %s\n" +
                "Prix: %s\n" +
                "Stock: %s unités",
                nom, categorie, prix, stock
            );
            
            showInfoMessage(message);
        }
    }
    
    private void loadInitialData() {
        loadClientsData();
        loadMedicamentsData();
        loadAchatsData();
        updateStatistics();
    }
    
    private void loadClientsData() {
        clientsTableModel.setRowCount(0);
        for (Client client : PharmacieController.getListClients()) {
            Object[] row = {
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhone(),
                client.getNbSS(),
                client.getMutuelle() != null ? client.getMutuelle().getLastName() : "Aucune"
            };
            clientsTableModel.addRow(row);
        }
    }
    
    private void loadMedicamentsData() {
        medicamentsTableModel.setRowCount(0);
        for (Medicament med : PharmacieController.getListMed()) {
            Object[] row = {
                med.getNameMed(),
                med.getCat().toString(),
                String.format("%.2f €", med.getPrice()),
                med.getStock(),
                med.getDatOnMarket().toString()
            };
            medicamentsTableModel.addRow(row);
        }
    }
    
    private void loadAchatsData() {
        achatsTableModel.setRowCount(0);
        for (Achat achat : PharmacieController.getListAchats()) {
            Object[] row = {
                achat.getDateAchat(),
                achat.getClient().getFirstName() + " " + achat.getClient().getLastName(),
                achat.IsAchatDirect() ? "Direct" : "Ordonnance",
                String.format("%.2f €", achat.getTotal()),
                String.format("%.2f €", achat.getRemb()),
                achat.getOrdonnance() != null ? "Oui" : "Non"
            };
            achatsTableModel.addRow(row);
        }
    }
    
    private void updateStatistics() {
        totalClientsLabel.setText(String.valueOf(PharmacieController.getListClients().size()));
        totalMedicamentsLabel.setText(String.valueOf(PharmacieController.getListMed().size()));
        totalAchatsLabel.setText(String.valueOf(PharmacieController.getListAchats().size()));
        
        // Calculate total revenue
        double chiffreAffaires = PharmacieController.getListAchats().stream()
                .mapToDouble(Achat::getTotal)
                .sum();
        chiffreAffairesLabel.setText(String.format("%.2f €", chiffreAffaires));
    }
    
    private void clearClientForm() {
        clientPrenomField.setText("");
        clientNomField.setText("");
        clientEmailField.setText("");
        clientPhoneField.setText("");
        clientAddressField.setText("");
    }
    
    private void clearMedicamentForm() {
        medicamentNomField.setText("");
        medicamentPrixField.setText("");
        medicamentStockField.setText("");
        medicamentCategorieCombo.setSelectedIndex(0);
    }
    
    private void startTimeUpdater() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeLabel.setText(LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                });
            }
        }, 0, 1000);
    }
    
    private void updateStatusLabel(String message) {
        statusLabel.setText(message);
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private long generateRandomSS() {
        // Generate a random 15-digit social security number
        return (long) (Math.random() * 900000000000000L) + 100000000000000L;
    }
    
    // Inner class for Achat Dialog
    private static class AchatDialog extends JDialog {
        private PharmacieController controller;
        private JComboBox<Client> clientCombo;
        private JList<Medicament> medicamentsList;
        private DefaultListModel<Medicament> medicamentsListModel;
        private JLabel totalLabel;
        private JLabel remboursementLabel;
        private boolean confirmed = false;
        
        public AchatDialog(Frame parent, PharmacieController controller) {
            super(parent, "Nouvel Achat", true);
            this.controller = controller;
            initializeDialog();
        }
        
        private void initializeDialog() {
            setSize(500, 400);
            setLocationRelativeTo(getParent());
            
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            
            // Client selection
            JPanel clientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            clientPanel.add(new JLabel("Client:"));
            clientCombo = new JComboBox<>();
            for (Client client : PharmacieController.getListClients()) {
                clientCombo.addItem(client);
            }
            clientPanel.add(clientCombo);
            
            // Medicaments selection
            medicamentsListModel = new DefaultListModel<>();
            for (Medicament med : PharmacieController.getListMed()) {
                if (med.getStock() > 0) {
                    medicamentsListModel.addElement(med);
                }
            }
            medicamentsList = new JList<>(medicamentsListModel);
            medicamentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            JScrollPane scrollPane = new JScrollPane(medicamentsList);
            scrollPane.setBorder(BorderFactory.createTitledBorder("Sélectionner les médicaments"));
            
            // Total panel
            JPanel totalPanel = new JPanel(new GridLayout(2, 1));
            totalLabel = new JLabel("Total: 0.00 €");
            remboursementLabel = new JLabel("Remboursement: 0.00 €");
            totalPanel.add(totalLabel);
            totalPanel.add(remboursementLabel);
            
            // Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton confirmButton = new JButton("Confirmer");
            JButton cancelButton = new JButton("Annuler");
            
            confirmButton.addActionListener(e -> confirmAchat());
            cancelButton.addActionListener(e -> dispose());
            
            buttonPanel.add(confirmButton);
            buttonPanel.add(cancelButton);
            
            // Layout
            mainPanel.add(clientPanel, BorderLayout.NORTH);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(totalPanel, BorderLayout.EAST);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            setContentPane(mainPanel);
        }
        
        private void confirmAchat() {
            Client selectedClient = (Client) clientCombo.getSelectedItem();
            int[] selectedIndices = medicamentsList.getSelectedIndices();
            
            if (selectedClient == null || selectedIndices.length == 0) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client et au moins un médicament!");
                return;
            }
            
            try {
                Achat newAchat = new Achat(LocalDate.now(), selectedClient);
                
                for (int index : selectedIndices) {
                    Medicament med = medicamentsListModel.getElementAt(index);
                    newAchat.addMedAchat(med);
                }
                
                controller.savingAchat(newAchat);
                confirmed = true;
                dispose();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la création de l'achat: " + e.getMessage());
            }
        }
        
        public boolean isConfirmed() {
            return confirmed;
        }
    }
    
    // Inner class for Client Details Dialog
    private static class ClientDetailsDialog extends JDialog {
        public ClientDetailsDialog(Frame parent, Client client) {
            super(parent, "Détails du Client", true);
            setSize(400, 300);
            setLocationRelativeTo(parent);
            
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;
            
            // Add client information
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Nom complet:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(client.getFirstName() + " " + client.getLastName()), gbc);
            
            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Email:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(client.getEmail()), gbc);
            
            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("Téléphone:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(client.getPhone()), gbc);
            
            gbc.gridx = 0; gbc.gridy = 3;
            panel.add(new JLabel("N° Sécurité Sociale:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(String.valueOf(client.getNbSS())), gbc);
            
            gbc.gridx = 0; gbc.gridy = 4;
            panel.add(new JLabel("Date de naissance:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(client.getDateBirth()), gbc);
            
            // Close button
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dispose());
            gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(closeButton, gbc);
            
            setContentPane(panel);
        }
    }
    
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new SparadrapMainInterface().setVisible(true);
        });
    }*/
}