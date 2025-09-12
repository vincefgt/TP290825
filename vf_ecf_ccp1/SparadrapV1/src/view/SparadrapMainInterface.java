package view;

import controller.DateFilter;
import controller.PharmacieController;
import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SparadrapMainInterface extends JFrame {

    // Panneaux principaux
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel navigationPanel;
    private JPanel statusPanel;
    private JTabbedPane mainTabbedPane;

    // header
    private JLabel titleLabel;
    private JLabel versionLabel;

    // nav
    private JButton clientsButton;
    private JButton medecinsButton;
    private JButton medicamentsButton;
    private JButton mutuellesButton;
    private JButton ordonnancesButton;
    private JButton achatsButton;
    private JButton statistiquesButton;

    // Clients
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
    private JButton updateClientButton;
    private JButton cancelButton;
    private JButton validationButton;
    private JTextField clientNbStateField;
    private JTextField clientCityField;
    private JFormattedTextField clientDateBirthField;
    private JFormattedTextField clientNbSSField;
    private JComboBox<String> clientCombo;


    // Med
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

    // Achats
    private JPanel achatActionsPanel;
    private JButton nouvelAchatButton;
    private JButton achatOrdonnanceButton;
    private JButton calculerRemboursementButton;
    private JScrollPane achatsScrollPane;
    private JTable achatsTable;
    private DefaultTableModel achatsTableModel;
    private JRadioButton ordoRadioButton;
    private JComboBox<String> dateFilterCombo;
    private JPanel sortingPane;

    // Stat
    private JPanel statsCard1, statsCard2, statsCard3, statsCard4;
    private JLabel totalClientsLabel;
    private JLabel totalMedicamentsLabel;
    private JLabel totalAchatsLabel;
    private JLabel chiffreAffairesLabel;
    private JButton refreshStatsButton;

    // Status bar
    private JLabel statusLabel;
    private JLabel timeLabel;

    // Ordo
    private JTable ordoTable;
    private JScrollPane ordoScrollPane;
    private DefaultTableModel ordoTableModel;

    //Mutuelle
    private JTable mutuelleTable;
    private JScrollPane mutuelleScrollPane;
    private DefaultTableModel mutTableModel;
    private JTextField mutNameField;
    private JTextField mutAddressField;
    private JTextField mutNbStateField;
    private JTextField mutCityField;
    private JTextField mutDepField;

    // Medecin
    private JTable medecinTable;
    private JScrollPane medecinScrollPane;
    private JTextField textField6;
    private JPanel tableSortingPane;
    private JRadioButton directRadioButton;
    private JRadioButton allRadioButton;
    private JFormattedTextField startDayTextField;
    private JFormattedTextField endDayTextField;
    private JFormattedTextField ssTextField;
    private DefaultTableModel medecinTableModel;

    // Controller
    private PharmacieController controller;
    private String titleBorder;
    LocalDate now = LocalDate.now();
    ButtonGroup buttonGroup = new ButtonGroup();

    // init components
    public SparadrapMainInterface() {
        initializeFrame();
        initializeComponents();
        setupEventHandlers();
        loadInitialData();
        startTimeUpdater(); // time
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
        cancelButton.setVisible(false); // hide button at start
        validationButton.setVisible(false); // hide button at start
        buttonGroup.add(ordoRadioButton);
        buttonGroup.add(directRadioButton);
        buttonGroup.add(allRadioButton);
        allRadioButton.setSelected(true);
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
        // Ordonnance table
        String[] ordoColumns = {"Date", "Patient", "Medecin", "numero Agreement", "id medecin"};
        ordoTableModel = new DefaultTableModel(ordoColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ordoTable.setModel(ordoTableModel);
        // mut table
        String[] mutColumns = {"Nom","Taux Remb (%)"};
        mutTableModel = new DefaultTableModel(mutColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        mutuelleTable.setModel(mutTableModel);

        // medecin table
        String[] medecincColumns = {"Nom", "Adresse", "email / phone","numero Agreement", "idMedecin"};
        medecinTableModel = new DefaultTableModel(medecincColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        medecinTable.setModel(medecinTableModel);
    }
    private void setupComboBoxes() {
        // med cat
        medicamentCategorieCombo.removeAllItems();
        for (catMed category : catMed.values()) {
            medicamentCategorieCombo.addItem(category);
        }
        // achat > sort achat
        dateFilterCombo.removeAllItems();// = new JComboBox<>();
        for (DateFilter filter : DateFilter.values()) {
            dateFilterCombo.addItem(filter.toString());
        }
        dateFilterCombo.setSelectedItem(DateFilter.ALL_TIME.toString());
        //  client > search client
        clientCombo.removeAllItems();
        clientCombo.addItem("Sélectionner le client");
        clientCombo.setSelectedItem(0);
        for (Client client : PharmacieController.getListClients()) {
            String clientStringCombo = client.getLastName()+" "+client.getFirstName();
            clientCombo.addItem(clientStringCombo);
        }
    }

    //UI
    private void applyModernStyling() {
        // Style tables
        styleTable(clientsTable);
        setupBorderScroll(clientScrollPane,titleCountItem(PharmacieController.getListClients()));
        styleTable(medicamentsTable);
        setupBorderScroll(medicamentScrollPane,titleCountItem(PharmacieController.getListMed()));
        styleTable(achatsTable);
        setupBorderScroll(achatsScrollPane,titleCountItem(PharmacieController.getListAchats()));
        styleTable(medecinTable);
        setupBorderScroll(medecinScrollPane,titleCountItem(PharmacieController.getListMedecins()));
        styleTable(mutuelleTable);
        setupBorderScroll(mutuelleScrollPane,titleCountItem(PharmacieController.getListMutuelles()));
        styleTable(ordoTable);
        setupBorderScroll(ordoScrollPane,titleCountItem(PharmacieController.getListOrdo()));
        // Style buttons with hover effects
        styleNavigationButtons();
        styleActionButtons();
    }
    private void styleTable(JTable table) {
        table.setRowHeight(20);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.DARK_GRAY);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(52, 152, 219, 50));
        table.setSelectionForeground(new Color(52, 73, 94));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);

    }
    public void setupBorderScroll(JScrollPane scroll,String title) {
        TitledBorder border = BorderFactory.createTitledBorder(titleBorder);
        //border.setBorder(BorderFactory.createEmptyBorder());
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleFont(new Font("", Font.BOLD, 10));
        border.setTitleColor(new Color(64, 64, 64));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(229, 195, 183)));
        scroll.setBorder(border);
    }
    public String titleCountItem(List<?> modelDefault){
        if (modelDefault.isEmpty()){
             return titleBorder = "(empty)";
        } else {
            return titleBorder = "(" + modelDefault.size() + ")"; }
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

    //Action buttons
    private void setupEventHandlers() {
        // Navigation buttons
        clientsButton.addActionListener(e -> showTab(0));
        medicamentsButton.addActionListener(e -> showTab(1));
        achatsButton.addActionListener(e -> showTab(2));
        statistiquesButton.addActionListener(e -> showTab(3));
        ordonnancesButton.addActionListener(e -> showTab(4));
        mutuellesButton.addActionListener(e -> showTab(5));
        medecinsButton.addActionListener(e -> showTab(6));

        // Client actions
        addClientButton.addActionListener(e -> addNewClient());
        clearClientButton.addActionListener(e -> delClientFrame());
        updateClientButton.addActionListener(e -> updateClient());
        clientCombo.addActionListener(e -> filterClients());

        // Médicament actions
        addMedicamentButton.addActionListener(e -> addNewMedicament());
        clearMedicamentButton.addActionListener(e -> clearMedicamentForm());

        // Achat actions
        nouvelAchatButton.addActionListener(e -> createNewAchat());
        achatOrdonnanceButton.addActionListener(e -> createAchatWithOrdonnance());
        calculerRemboursementButton.addActionListener(e -> calculateRemboursement());
        dateFilterCombo.addActionListener(e -> filterAchats());
        ActionListener groupListener = e -> filterAchats(); //check group
        ordoRadioButton.addActionListener(groupListener);
        directRadioButton.addActionListener(groupListener);
        allRadioButton.addActionListener(groupListener);
        endDayTextField.addActionListener(e -> {
            if(!startDayTextField.getText().isEmpty()){
                dateFilterCombo.setSelectedItem("Date spe");
                filterAchats();}
        });
        startDayTextField.addActionListener(e -> {
            if(endDayTextField.getText().isEmpty()){
                dateFilterCombo.setSelectedItem("Ce jour");
                filterAchats();}
        });

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
        achatsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showAchatDetails();
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
        medecinTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showMedecinDetails();
                }
            }
        });
    }

    private void showTab(int index) {
        mainTabbedPane.setSelectedIndex(index);
        updateStatusLabel("Onglet sélectionné: " + mainTabbedPane.getTitleAt(index));
    }

    private void delClientFrame() {
        try {
            int selectedClient = clientsTable.getSelectedRow();
            if (selectedClient >= 0) {
                PharmacieController.getListClients().remove(selectedClient);
                clientsTableModel.removeRow(selectedClient);
                showSuccessMessage("Client(e) supprimé(e) avec succès!");
                updateStatusLabel("Client(e) supprimé(e)");
            } else {
                showErrorMessage("Selected Client required");
            }
            PharmacieView.printList(PharmacieController.getListClients());
        } catch (Exception e) {
            showErrorMessage("Erreur lors de la suppression: " + e.getMessage());
        }
    }
    private void addNewClient() {
        try {
            String prenom = clientPrenomField.getText().trim();
            String nom = clientNomField.getText().trim();
            String email = clientEmailField.getText().trim();
            String phone = clientPhoneField.getText().trim();
            String address = clientAddressField.getText().trim();
            int nbState = Integer.parseInt(clientNbStateField.getText());
            String city = clientCityField.getText().trim();
            LocalDate dateBirth = LocalDate.parse(clientDateBirthField.getText());
            Long nbSS = Long.parseLong(clientNbSSField.getText().trim());

            if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty()) {
                showErrorMessage("Veuillez remplir au moins le prénom, nom et email!");
                return;
            }
            
            // Create client with default values for required fields
            Client newClient = new Client(prenom, nom, address.isEmpty() ? "Non renseigné" : address, nbState, city, phone.isEmpty() ? "0000000000" : phone,
                    email,String.valueOf(nbSS).isEmpty() ? 0L : nbSS , dateBirth, null, null);
            
            PharmacieController.addClient(newClient);
            loadClientsData();
            clearClientForm();
            showSuccessMessage("Client ajouté avec succès!");
            updateStatusLabel("Nouveau client ajouté: " + prenom + " " + nom);
            
        } catch (Exception e) {
            showErrorMessage("Erreur lors de l'ajout du client: " + e.getMessage());
        }
    }
    private void updateClient() {
        try {
            int selectedClient = clientsTable.getSelectedRow();
            if (selectedClient >= 0 || selectedClient > clientsTable.getRowCount()) {
                cancelButton.setVisible(true);
                validationButton.setVisible(true);
                addClientButton.setVisible(false);
                clearClientButton.setVisible(false);
                updateClientButton.setVisible(false);
                setClientForm(PharmacieController.getListClients().get(selectedClient));
                validationButton.addActionListener(actionEvent -> {
                    Client client = PharmacieController.getListClients().get(selectedClient);
                    PharmacieController.updateClient(client,
                            clientPrenomField.getText(),
                            clientNomField.getText(),
                            clientAddressField.getText(),
                            Integer.parseInt(clientNbStateField.getText()),
                            clientCityField.getText(),
                            clientPhoneField.getText(),
                            clientEmailField.getText(),
                            Long.parseLong(clientNbSSField.getText()),
                            LocalDate.parse(clientDateBirthField.getText(),DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            null,null
                            );
                    loadClientsData();
                    clearClientForm();
                    cancelButton.setVisible(false);
                    validationButton.setVisible(false);
                    addClientButton.setVisible(true);
                    clearClientButton.setVisible(true);
                    updateClientButton.setVisible(true);
                    showSuccessMessage("Client(e) modifié(e) avec succès!");
                    updateStatusLabel("Client(e) modifié(e) "); //TODO add lastName + FirstName
                });
                cancelButton.addActionListener(e -> {
                    clearClientForm();
                    cancelButton.setVisible(false);
                    validationButton.setVisible(false);
                    addClientButton.setVisible(true);
                    clearClientButton.setVisible(true);
                    updateClientButton.setVisible(true);
                });
            } else {
                showErrorMessage("Selected Client required");
            }
        } catch (Exception e) {
            showErrorMessage("Erreur lors de la modification: " + e.getMessage());
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
            
            PharmacieController.addMed(newMedicament);
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
        AchatDialog dialog = new AchatDialog(this);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            filterAchats();
            updateStatistics();
            updateStatusLabel("Nouvel achat enregistré");
            dateFilterCombo.setSelectedItem(DateFilter.ALL_TIME.toString());
        }
    }
    
    private void createAchatWithOrdonnance() {
        showInfoMessage("Fonctionnalité en développement: Achat avec ordonnance");
    }

    /**
     * TODO: A garder????
     */
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

    // call popup
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
    private void showMedecinDetails() {
        int selectedRow = medecinTable.getSelectedRow();
        if (selectedRow != -1) {
            Long nbAgreement = (Long) medecinTableModel.getValueAt(selectedRow, 3);

            // Find medecin in controller
            for (Medecin medecin : PharmacieController.getListMedecins()) {
                if (medecin.getNbAgreement() == nbAgreement) {
                    MedecinDetailsDialog dialog = new MedecinDetailsDialog(this, medecin);
                    dialog.setVisible(true);
                    break;
                }
            }
        }
    }
    private void showAchatDetails() {
        int selectedRow = achatsTable.getSelectedRow();
        if (selectedRow != -1) {
            LocalDate dateAchat = LocalDate.parse((String)achatsTableModel.getValueAt(selectedRow, 0),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Client client =  PharmacieController.getListClients().get(selectedRow);
            // Find client in controller
            for (Achat achat : PharmacieController.getListAchats()) {
                if (LocalDate.parse(achat.getDateAchat(),DateTimeFormatter.ofPattern("dd/MM/yyyy")).equals(dateAchat) &&
                        achat.getClient().equals(client)) {
                    AchatDetailsDialog dialog = new AchatDetailsDialog(this, achat);
                    dialog.setVisible(true);
                    break;
                }
            }
        }
    }
    private void showMedicamentDetails() {
        int selectedRow = medicamentsTable.getSelectedRow();
        if (selectedRow != -1) {
            Medicament med = PharmacieController.getListMed().get(selectedRow);
            for (Medicament medList : PharmacieController.getListMed()){
                if (med.equals(medList)) {
                    MedDetailsDialog dialog = new MedDetailsDialog(this, medList);
                    dialog.setVisible(true);
                    break;
                }
            }
        }
    }

    // loading data
    private void loadInitialData() {
        loadClientsData();
        loadMedicamentsData();
        filterAchats(); //TODO sort by date in JTable
        loadOrdoData(); //TODO sort by date in JTable
        loadMedecinData(); //TODO sort by Name(a-z) / by cat
        loadMutData(); //TODO sort by Name(a-z)
        updateStatistics();
    }
    private void loadClientsData() {
        /*clientsTableModel.setRowCount(0);
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
        }*/
        filterClients();
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
    private void loadOrdoData() {
        ordoTableModel.setRowCount(0);
        for (Ordonnance ordonnance : PharmacieController.getListOrdo()) {
            Object[] row = {
                    ordonnance.getDateOrdo(),
                    ordonnance.getPatient().getFirstName() + " " + ordonnance.getPatient().getLastName(),
                    "Dr "+ordonnance.getMedecin().getLastName()+" "+ordonnance.getMedecin().getFirstName(),
                    ordonnance.getNbAgreement(),
                    ordonnance.getIdMedecin()
            };
            ordoTableModel.addRow(row);
        }
    }
    private void loadMutData() {
        mutTableModel.setRowCount(0);
        for (Mutuelle mut : PharmacieController.getListMutuelles()) {
            Object[] row = {
                    mut.getLastName(),
                    mut.getTauxRemb()
            };
            mutTableModel.addRow(row);
        }
    }
    private void loadMedecinData() {
        medecinTableModel.setRowCount(0);
        for (Medecin medecin : PharmacieController.getListMedecins()) {
            Object[] row = {
                    "Dr "+medecin.getFirstName()+" "+medecin.getLastName(),
                    medecin.getAddress()+" "+medecin.getNbState()+" "+medecin.getCity(),
                    medecin.getEmail()+"/ "+medecin.getPhone(),
                    medecin.getNbAgreement(),
                    medecin.getIdMedecin()
            };
            medecinTableModel.addRow(row);
        }
    }

    // update
    private void updateStatistics() {
        // stats
        totalClientsLabel.setText(String.valueOf(PharmacieController.getListClients().size()));
        totalMedicamentsLabel.setText(String.valueOf(PharmacieController.getListMed().size()));
        totalAchatsLabel.setText(String.valueOf(PharmacieController.getListAchats().size()));
        //counter Table
        setupBorderScroll(clientScrollPane,titleCountItem(PharmacieController.getListClients()));
        setupBorderScroll(medicamentScrollPane,titleCountItem(PharmacieController.getListMed()));
        setupBorderScroll(achatsScrollPane,titleCountItem(PharmacieController.getListAchats()));
        setupBorderScroll(medecinScrollPane,titleCountItem(PharmacieController.getListMedecins()));
        setupBorderScroll(mutuelleScrollPane,titleCountItem(PharmacieController.getListMutuelles()));
        setupBorderScroll(ordoScrollPane,titleCountItem(PharmacieController.getListOrdo()));
        
        // Calculate total revenue
        double chiffreAffaires = PharmacieController.getListAchats().stream()
                .mapToDouble(Achat::getTotal)
                .sum();
        chiffreAffairesLabel.setText(String.format("%.2f €", chiffreAffaires));
    }
    private void updateStatusLabel(String message) {
        statusLabel.setText(message);
    }

    // set data
    private void setClientForm(Client selectedClient) {
        clientPrenomField.setText(selectedClient.getFirstName());
        clientNomField.setText(selectedClient.getLastName());
        clientEmailField.setText(selectedClient.getEmail());
        clientPhoneField.setText(selectedClient.getPhone());
        clientAddressField.setText(selectedClient.getAddress());
        clientNbStateField.setText(String.valueOf(selectedClient.getNbState()));
        clientCityField.setText(selectedClient.getCity());
        clientDateBirthField.setText(selectedClient.getDateBirth());
        clientNbSSField.setText(String.valueOf(selectedClient.getNbSS()));
        //TODO add mututelle
    }
    // clear data
    private void clearClientForm() {
        clientPrenomField.setText("");
        clientNomField.setText("");
        clientEmailField.setText("");
        clientPhoneField.setText("");
        clientAddressField.setText("");
        clientNbStateField.setText("");
        clientCityField.setText("");
        clientDateBirthField.setText("");
        clientNbSSField.setText("");
    }
    private void clearMedicamentForm() {
        medicamentNomField.setText("");
        medicamentPrixField.setText("");
        medicamentStockField.setText("");
        medicamentCategorieCombo.setSelectedIndex(0);
    }
    //get data
    private void getClientForm() {
        clientPrenomField.getText();
        clientNomField.getText();
        clientEmailField.getText();
        clientPhoneField.getText();
        clientAddressField.getText();
        clientNbStateField.getText();
        clientCityField.getText();
        clientDateBirthField.getText();
        clientNbSSField.getText();
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

    /**
     * DialogBox
     * @param message
     */
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
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Class DialogBox
     */
    private static class AchatDialog extends JDialog {
        private JComboBox<Client> clientCombo;
        private JList<Medicament> medicamentsList;
        private DefaultListModel<Medicament> medicamentsListModel;
        private JLabel totalLabel;
        private JLabel remboursementLabel;
        private boolean confirmed = false;

        
        public AchatDialog(Frame parent) {
            super(parent, "Nouvel Achat", true);
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
            clientPanel.add(clientCombo);
            
            // Medicaments selection
            medicamentsListModel = new DefaultListModel<>();
            for (Medicament med : PharmacieController.getListMed()) {
                if (med.getStock() > 0) {
                    medicamentsListModel.addElement(med);
                }
            }
            medicamentsList = new JList<>(medicamentsListModel);
            medicamentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // selection multiple
            
            JScrollPane scrollPane = new JScrollPane(medicamentsList);
            scrollPane.setBorder(BorderFactory.createTitledBorder("Sélectionner les médicaments"));
            
            // Total panel
            JPanel totalPanel = new JPanel(new GridLayout(2, 1));
            totalLabel = new JLabel("Total: 0.00 €");
            remboursementLabel = new JLabel("Remboursement: 0.00 €");
            totalPanel.add(totalLabel);
            totalPanel.add(remboursementLabel);

            // Écouter les changements de sélection
            ListSelectionListener medicamentsList = new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {

                }
            };
            
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
                PharmacieController.savingAchat(newAchat);
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

    // Dialogue pour créer un achat avec ordonnance
    private class AchatOrdonnanceDialog extends JDialog {
        private JComboBox<Client> clientCombo;
        private JComboBox<Medecin> medecinCombo;
        private JList<Medicament> medicamentsList;
        private DefaultListModel<Medicament> medicamentsModel;
        private JList<Medicament> ordonnanceList;
        private DefaultListModel<Medicament> ordonnanceModel;
        private JButton ajouterButton;
        private JButton retirerButton;
        private JButton creerButton;
        private JButton annulerButton;
        private boolean confirmed = false;
        
        public AchatOrdonnanceDialog(Frame parent) {
            super(parent, "Nouvel Achat avec Ordonnance", true);
            initializeDialog();
            createComponents();
            layoutComponents();
            setupEventHandlers();
            loadData();
        }
        
        private void initializeDialog() {
            setSize(800, 600);
            setLocationRelativeTo(getParent());
            setResizable(false);
        }
        
        private void createComponents() {
            // ComboBoxes
            clientCombo = new JComboBox<>();
            medecinCombo = new JComboBox<>();
            
            // Lists
            medicamentsModel = new DefaultListModel<>();
            medicamentsList = new JList<>(medicamentsModel);
            medicamentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            ordonnanceModel = new DefaultListModel<>();
            ordonnanceList = new JList<>(ordonnanceModel);
            ordonnanceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            // Buttons
            ajouterButton = createStyledButton("Ajouter →", new Color(46, 204, 113));
            retirerButton = createStyledButton("← Retirer", new Color(231, 76, 60));
            creerButton = createStyledButton("Créer Achat", new Color(52, 152, 219));
            annulerButton = createStyledButton("Annuler", new Color(149, 165, 166));
        }
        
        private void layoutComponents() {
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(Color.WHITE);
            
            // Panel de sélection client/médecin
            JPanel selectionPanel = new JPanel(new GridBagLayout());
            selectionPanel.setBackground(Color.WHITE);
            selectionPanel.setBorder(BorderFactory.createTitledBorder("Informations de l'Ordonnance"));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;
            
            // Client
            gbc.gridx = 0; gbc.gridy = 0;
            selectionPanel.add(new JLabel("Client:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            selectionPanel.add(clientCombo, gbc);
            
            // Médecin
            gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            selectionPanel.add(new JLabel("Médecin:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            selectionPanel.add(medecinCombo, gbc);
            
            // Panel des médicaments
            JPanel medicamentsPanel = new JPanel(new BorderLayout());
            medicamentsPanel.setBorder(BorderFactory.createTitledBorder("Sélection des Médicaments"));
            medicamentsPanel.setBackground(Color.WHITE);
            
            // Panel gauche - médicaments disponibles
            JPanel disponiblesPanel = new JPanel(new BorderLayout());
            disponiblesPanel.setBorder(BorderFactory.createTitledBorder("Médicaments Disponibles"));
            disponiblesPanel.add(new JScrollPane(medicamentsList), BorderLayout.CENTER);
            
            // Panel droite - médicaments dans l'ordonnance
            JPanel ordonnancePanel = new JPanel(new BorderLayout());
            ordonnancePanel.setBorder(BorderFactory.createTitledBorder("Médicaments dans l'Ordonnance"));
            ordonnancePanel.add(new JScrollPane(ordonnanceList), BorderLayout.CENTER);
            
            // Panel central - boutons d'ajout/retrait
            JPanel boutonsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            boutonsPanel.setOpaque(false);
            boutonsPanel.add(ajouterButton);
            boutonsPanel.add(retirerButton);
            
            JPanel boutonsContainer = new JPanel(new FlowLayout());
            boutonsContainer.setOpaque(false);
            boutonsContainer.add(boutonsPanel);
            
            medicamentsPanel.add(disponiblesPanel, BorderLayout.WEST);
            medicamentsPanel.add(boutonsContainer, BorderLayout.CENTER);
            medicamentsPanel.add(ordonnancePanel, BorderLayout.EAST);
            
            // Panel des boutons principaux
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(annulerButton);
            buttonPanel.add(creerButton);
            
            mainPanel.add(selectionPanel, BorderLayout.NORTH);
            mainPanel.add(medicamentsPanel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            setContentPane(mainPanel);
        }
        
        private void setupEventHandlers() {
            ajouterButton.addActionListener(e -> ajouterMedicament());
            retirerButton.addActionListener(e -> retirerMedicament());
            creerButton.addActionListener(e -> creerAchatOrdonnance());
            annulerButton.addActionListener(e -> dispose());
        }
        
        private void loadData() {
            // Charger les clients
            for (Client client : PharmacieController.getListClients()) {
                clientCombo.addItem(client);
            }
            
            // Charger les médecins
            for (Medecin medecin : PharmacieController.getListMedecins()) {
                medecinCombo.addItem(medecin);
            }
            
            // Charger les médicaments disponibles
            for (Medicament medicament : PharmacieController.getListMed()) {
                if (medicament.getStock() > 0) {
                    medicamentsModel.addElement(medicament);
                }
            }
        }
        
        private void ajouterMedicament() {
            Medicament selected = medicamentsList.getSelectedValue();
            if (selected != null && !ordonnanceModel.contains(selected)) {
                ordonnanceModel.addElement(selected);
                medicamentsList.clearSelection();
            }
        }
        
        private void retirerMedicament() {
            Medicament selected = ordonnanceList.getSelectedValue();
            if (selected != null) {
                ordonnanceModel.removeElement(selected);
                ordonnanceList.clearSelection();
            }
        }
        
        private void creerAchatOrdonnance() {
            Client client = (Client) clientCombo.getSelectedItem();
            Medecin medecin = (Medecin) medecinCombo.getSelectedItem();
            
            if (client == null || medecin == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client et un médecin!", 
                                             "Sélection manquante", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (ordonnanceModel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez ajouter au moins un médicament à l'ordonnance!", 
                                             "Ordonnance vide", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                // Créer l'achat avec ordonnance
                boolean succes = PharmacieController.createNewAchatWithOrdonnance(
                    java.time.LocalDate.now(), client, java.time.LocalDate.now(), medecin);
                
                if (succes) {
                    // Ajouter les médicaments à l'ordonnance
                    Ordonnance ordonnance = PharmacieController.getListOrdo().get(
                        PharmacieController.getListOrdo().size() - 1);
                    
                    for (int i = 0; i < ordonnanceModel.size(); i++) {
                        Medicament med = ordonnanceModel.getElementAt(i);
                        PharmacieController.addMedicamentToOrdonnance(ordonnance, med);
                    }
                    
                    confirmed = true;
                    JOptionPane.showMessageDialog(this, 
                        "Achat avec ordonnance créé avec succès!\n" +
                        "Ordonnance: " + ordonnanceModel.size() + " médicament(s)\n" +
                        "Client: " + client.getFirstName() + " " + client.getLastName() + "\n" +
                        "Médecin: Dr " + medecin.getFirstName() + " " + medecin.getLastName(),
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la création de l'achat!", 
                                                 "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), 
                                             "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        public boolean isConfirmed() {
            return confirmed;
        }
    }

    private static class ClientDetailsDialog extends JDialog {
        public ClientDetailsDialog(Frame parent, Client client) {
            super(parent, "Détails du Client", true);
            setSize(400, 300);
            setLocationRelativeTo(parent);
            
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(2, 5, 2, 5);
            gbc.anchor = GridBagConstraints.WEST;
            
            // Add client information
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Nom complet:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(client.getFirstName() + " " + client.getLastName()), gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Adresse:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(client.getAddress()), gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel(""), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(client.getNbState()+" "+client.getCity()), gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            panel.add(new JLabel("Email:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(client.getEmail()), gbc);
            
            gbc.gridx = 0; gbc.gridy = 4;
            panel.add(new JLabel("Téléphone:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(client.getPhone()), gbc);
            
            gbc.gridx = 0; gbc.gridy = 5;
            panel.add(new JLabel("N° Sécurité Sociale:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(String.valueOf(client.getNbSS())), gbc);

            gbc.gridx = 0; gbc.gridy = 6;
            panel.add(new JLabel("Date de naissance:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(client.getDateBirth()), gbc);

            // Close button
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dispose());
            gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(closeButton, gbc);
            
            setContentPane(panel);
        }
    }
    private static class MedecinDetailsDialog extends JDialog {
        public MedecinDetailsDialog(Frame parent, Medecin medecin) {
            super(parent, "Détails du Medecin", true);
            setSize(400, 300);
            setLocationRelativeTo(parent);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(2, 5, 2, 5);
            gbc.anchor = GridBagConstraints.WEST;

            // Add client information
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Nom complet:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel("Dr "+medecin.getFirstName() + " " + medecin.getLastName()), gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Adresse:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(medecin.getAddress()), gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel(""), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(medecin.getNbState()+" "+medecin.getCity()), gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            panel.add(new JLabel("Email:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(medecin.getEmail()), gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            panel.add(new JLabel("Téléphone:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(medecin.getPhone()), gbc);

            gbc.gridx = 0; gbc.gridy = 5;
            panel.add(new JLabel("N° Agrement:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(String.valueOf(medecin.getNbAgreement())), gbc);

            gbc.gridx = 0; gbc.gridy = 6;
            panel.add(new JLabel("ID:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(medecin.getIdMedecin()), gbc);

            // Close button
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dispose());
            gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(closeButton, gbc);

            setContentPane(panel);
        }
    }
    private static class AchatDetailsDialog extends JDialog {
        public AchatDetailsDialog(Frame parent, Achat achat) {
            super(parent, "Détails de l'achat", true);
            setSize(400, 300);
            setLocationRelativeTo(parent);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(2, 5, 2, 5);
            gbc.anchor = GridBagConstraints.WEST;

            // Add client information
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Date:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(achat.getDateAchat()), gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Client:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(achat.getClient().getLastName()+" "+achat.getClient().getFirstName()), gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("Adresse:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel("<html>"+achat.getClient().getAddress()+"<br>"
                    +achat.getClient().getNbState()+" "+achat.getClient().getCity()), gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            panel.add(new JLabel("Coord:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(achat.getClient().getEmail()+" / "+achat.getClient().getPhone()), gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            panel.add(new JLabel("Montants:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel("Total: "+achat.getTotal()+" € Remb: "+achat.getRemb()+" €"), gbc);

            gbc.gridx = 0; gbc.gridy = 5;
            panel.add(new JLabel("Ordo:"), gbc);
            gbc.gridx = 1;
            String statusOrdo;
            if(achat.getOrdonnance()==null){
                statusOrdo = "non";
            } else {
                statusOrdo = "<html>"+achat.getOrdonnance().getDateOrdo()+"<br>"
                                    +"Dr "+achat.getOrdonnance().getMedecin().getLastName()+" "+achat.getOrdonnance().getMedecin().getFirstName()+"<br>"
                                    +"Numéro Agrement: "+achat.getOrdonnance().getNbAgreement(); }
            panel.add(new JLabel(statusOrdo), gbc);

            gbc.gridx = 0; gbc.gridy = 6;
            JLabel medLabel = new JLabel("Med:");
            medLabel.setVerticalAlignment(SwingConstants.TOP);
            panel.add(medLabel, gbc);
            gbc.gridx = 1;
            StringBuilder sb = new StringBuilder("<html>");
            for (Medicament med : achat.getListMedAchat()) {
                sb.append(med.getNameMed()).append("<br>"); } //TODO add quantity
            sb.append("</html>");
            panel.add(new JLabel(sb.toString()), gbc);

            // Close button
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dispose());
            gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(closeButton, gbc);

            setContentPane(panel);
        }
    }
    private static class MedDetailsDialog extends JDialog {
        public MedDetailsDialog(Frame parent, Medicament med) {
            super(parent, "Détails du Médicament", true);
            setSize(400, 300);
            setLocationRelativeTo(parent);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(2, 5, 2, 5);
            gbc.anchor = GridBagConstraints.WEST;

            // Add client information
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Nom:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(med.getNameMed()), gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Catégorie:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(String.valueOf(med.getCat())), gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("Prix:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(String.valueOf(med.getPrice()+" €")), gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            panel.add(new JLabel("Stock:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(String.valueOf(med.getStock())), gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            panel.add(new JLabel("Mise en circulation:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(med.getDatOnMarket().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))), gbc);
//DateTimeFormatter.ofPattern("dd/MM/yyyy")
            // Close button
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dispose());
            gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(closeButton, gbc);

            setContentPane(panel);
        }
    }

    /**
     * Sorting Achats / clients
     */
    private void filterAchats() {
        String selectedDateFilter = (String) dateFilterCombo.getSelectedItem();
        assert selectedDateFilter != null; // not be null
        DateFilter dateFilter = DateFilter.getDateFilterFromString(selectedDateFilter);
        ButtonModel selectedRadioButton = buttonGroup.getSelection();
        String typeFilter = selectedRadioButton.getActionCommand();
        achatsTableModel.setRowCount(0);

        for (Achat achat : PharmacieController.getListAchats()) {
            boolean includeByDate = includeByDate(achat, dateFilter);
            boolean includeByType = includeByType(achat, typeFilter);
            if (includeByDate && includeByType || dateFilter==null&&startDayTextField.getText().isEmpty()&&endDayTextField.getText().isEmpty()) {
                Object[] row = {
                        achat.getDateAchat(),
                        achat.getClient().getFirstName() + " " + achat.getClient().getLastName(),
                        achat.IsAchatDirect(achat.getOrdonnance()) ? "Direct" : "Ordonnance",
                        String.format("%.2f €", achat.getTotal()),
                        String.format("%.2f €", achat.getRemb()),
                        achat.getOrdonnance() != null ? "Oui" : "Non"
                };
                achatsTableModel.addRow(row);
            }
        }

        // Update the border title with filtered count
        setupBorderScroll(achatsScrollPane, "("+achatsTableModel.getRowCount()+" / "+
                PharmacieController.getListAchats().size()+")");
        // Create status message showing both active filters
        String statusMessage = "Achats filtrés: ";
        if (!dateFilter.equals(DateFilter.ALL_TIME)) {
            statusMessage += selectedDateFilter;}
        if (!typeFilter.equals("Global")) {
            if (!dateFilter.equals(DateFilter.ALL_TIME)) {
                statusMessage += " + ";}
            statusMessage += typeFilter;}
        if (dateFilter.equals(DateFilter.ALL_TIME) && typeFilter.equals("Global")) {
            statusMessage += "Aucun filtre";}
        statusMessage += " - " + achatsTableModel.getRowCount() + " résultats";
        updateStatusLabel(statusMessage);
    }
    public boolean includeByDate(Achat achat, DateFilter dateFilter) {
    if (dateFilter == DateFilter.ALL_TIME) {
        return true; }
    LocalDate achatDate = LocalDate.parse(achat.getDateAchat(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    switch (dateFilter) {
        case TODAY:
            startDayTextField.setText(LocalDate.now().toString());
            endDayTextField.setText("");
            return achatDate.equals(LocalDate.now());
        case THIS_DAY:
            endDayTextField.setText("");
            return achatDate.equals(LocalDate.parse(startDayTextField.getText(),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        case THIS_WEEK:
            endDayTextField.setText(LocalDate.now().toString());
            startDayTextField.setText(LocalDate.now().minusDays(7).toString());
            LocalDate weekStart = now.minusDays(now.getDayOfWeek().getValue() - 1);
            LocalDate weekEnd = weekStart.plusDays(6);
            return (achatDate.isEqual(weekStart) || achatDate.isAfter(weekStart)) &&
                    (achatDate.isEqual(weekEnd) || achatDate.isBefore(weekEnd));
        /*case LAST_MONTH:
            LocalDate monthStart = now.minusDays(now.getDayOfMonth() - 1);
            LocalDate monthEnd = monthStart.plusDays(30);
            return (achatDate.isEqual(monthStart) || achatDate.isAfter(monthStart)) &&
                    (achatDate.isEqual(monthEnd) || achatDate.isBefore(monthEnd));*/
        case THIS_MONTH:
            endDayTextField.setText(LocalDate.now().toString());
            startDayTextField.setText(LocalDate.of(now.getYear(),now.getMonth(),1).toString());
            return achatDate.getMonth() == now.getMonth() &&
                    achatDate.getYear() == now.getYear();
       /* case LAST_YEAR:
            LocalDate yearStart = now.minusDays(now.getDayOfWeek().getValue() - 1);
            LocalDate yearEnd = yearStart.plusDays(364);
            return (achatDate.isEqual(yearStart) || achatDate.isAfter(yearStart)) &&
                    (achatDate.isEqual(yearEnd) || achatDate.isBefore(yearEnd));*/
        case THIS_YEAR:
            endDayTextField.setText(LocalDate.now().toString());
            startDayTextField.setText(LocalDate.of(now.getYear(),1,1).toString());
            return achatDate.getYear() == now.getYear();
        case MY_DATE:
            LocalDate dateEnd = LocalDate.parse(startDayTextField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate dateStart = LocalDate.parse(endDayTextField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            //TODO regex for input date
        return (achatDate.isEqual(dateStart) || achatDate.isAfter(dateStart)) &&
                (achatDate.isEqual(dateEnd) || achatDate.isBefore(dateEnd));
        default:
            return true;
        }
    }
    private boolean includeByType(Achat achat, String typeFilter) {
        return switch (typeFilter) {
            case "Ordonnances" -> achat.getOrdonnance() != null;
            case "Direct" -> achat.getOrdonnance() == null;
            default -> // "Global" or any other case
                    true;
        };
    }
    private void filterClients() {
        String selectedClientFilter = (String) clientCombo.getSelectedItem();
        assert selectedClientFilter != null; // not be null
        clientsTableModel.setRowCount(0);
        for (Client client : PharmacieController.getListClients()) {
           boolean clientSelected = selectedClientFilter.equals(client.getLastName()+" "+client.getFirstName());
           if  (clientCombo.getSelectedIndex() == 0) {
               clientSelected = true;
           }
            if (clientSelected|| clientCombo==null) {
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

        // Update the border title with filtered count
        setupBorderScroll(clientScrollPane, "("+clientsTableModel.getRowCount()+" / "+
                PharmacieController.getListClients().size()+")");
        // Create status message showing both active filters
        String statusMessage = "Clients filtrés: "+selectedClientFilter;
        updateStatusLabel(statusMessage);


    }
    // TODO filterMed(){}
    // TODO filterMedecins(){}
    // TODO filterMut(){}

    /*// Add method to get filtered achats for statistics
    private java.awt.List<Achat> getFilteredAchats() {
        if (dateFilterCombo == null) {
            return PharmacieController.getListAchats();
        }

        String selectedFilter = (String) dateFilterCombo.getSelectedItem();
        DateFilter filter = getDateFilterFromString(selectedFilter);

        if (filter == DateFilter.ALL_TIME) {
            return PharmacieController.getListAchats();
        }

        LocalDate now = LocalDate.now();
        java.awt.List<Achat> filteredAchats = new ArrayList<>();

        for (Achat achat : PharmacieController.getListAchats()) {
            LocalDate achatDate = LocalDate.parse(achat.getDateAchat(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            boolean shouldInclude = false;

            switch (filter) {
                case TODAY:
                    shouldInclude = achatDate.equals(now);
                    break;
                case THIS_WEEK:
                    LocalDate weekStart = now.minusDays(now.getDayOfWeek().getValue() - 1);
                    LocalDate weekEnd = weekStart.plusDays(6);
                    shouldInclude = (achatDate.isEqual(weekStart) || achatDate.isAfter(weekStart)) &&
                            (achatDate.isEqual(weekEnd) || achatDate.isBefore(weekEnd));
                    break;
                case THIS_MONTH:
                    shouldInclude = achatDate.getMonth() == now.getMonth() &&
                            achatDate.getYear() == now.getYear();
                    break;
                case THIS_YEAR:
                    shouldInclude = achatDate.getYear() == now.getYear();
                    break;
            }

            if (shouldInclude) {
                filteredAchats.add(achat);
            }
        }

        return filteredAchats;
    }

    // Optional: Modify updateStatistics() to use filtered data
    private void updateStatistics() {
        // Existing statistics for all data
        totalClientsLabel.setText(String.valueOf(PharmacieController.getListClients().size()));
        totalMedicamentsLabel.setText(String.valueOf(PharmacieController.getListMed().size()));

        // Use filtered achats for statistics if filter is active
        java.awt.List<Achat> achatsForStats = getFilteredAchats();
        totalAchatsLabel.setText(String.valueOf(achatsForStats.size()));

        // Calculate filtered revenue
        double chiffreAffaires = achatsForStats.stream()
                .mapToDouble(Achat::getTotal)
                .sum();
        chiffreAffairesLabel.setText(String.format("%.2f €", chiffreAffaires));

        // Update table counters
        setupBorderScroll(clientScrollPane, titleCountItem(PharmacieController.getListClients()));
        setupBorderScroll(medicamentScrollPane, titleCountItem(PharmacieController.getListMed()));

        // For achats, show filtered count vs total
        if (dateFilterCombo != null && !dateFilterCombo.getSelectedItem().equals(DateFilter.ALL_TIME.toString())) {
            setupBorderScroll(achatsScrollPane, "(" + achatsForStats.size() + " / " +
                    PharmacieController.getListAchats().size() + ")");
        } else {
            setupBorderScroll(achatsScrollPane, titleCountItem(PharmacieController.getListAchats()));
        }

        setupBorderScroll(medecinScrollPane, titleCountItem(PharmacieController.getListMedecins()));
        setupBorderScroll(mutuelleScrollPane, titleCountItem(PharmacieController.getListMutuelles()));
        setupBorderScroll(ordoScrollPane, titleCountItem(PharmacieController.getListOrdo()));
    }*/
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