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
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.Timer;


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
    private JComboBox<Medecin> medecinsCombo;
    public boolean typeAchat;

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
    private JPanel SortOrdoPane;
    private JComboBox patientComboSort;
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
            clientCombo.addItem(client.toString());
        }
        // medecin > ordo frame
        medecinsCombo.removeAllItems();
        medecinsCombo.setSelectedItem(-1);
        for (Medecin medecin : PharmacieController.getListMedecins()) {
            //String medecinStringCombo = "Dr "+medecin.getLastName()+" "+medecin.getFirstName();
            medecinsCombo.addItem(medecin);
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

        //ordo actions
        medecinsCombo.addActionListener(e -> {
            filterOrdoByMedecins();
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
        ordoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showOrdoDetails();
                }
            }
        });
        mutuelleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showMutDetails();
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
            filterClients();//loadClientsData();
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
                    filterClients();//loadClientsData();
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

    private void delMedecin() {
        try {
            int selectedMedecin = medecinTable.getSelectedRow();
            if (selectedMedecin >= 0) {
                PharmacieController.getListMedecins().remove(selectedMedecin);
                clientsTableModel.removeRow(selectedMedecin);
                showSuccessMessage("Medecin supprimé avec succès!");
                updateStatusLabel("Medecin supprimé");
            } else {
                showErrorMessage("Selected Medecin required");
            }
        } catch (Exception e) {
            showErrorMessage("Erreur lors de la suppression: " + e.getMessage());
        }
    }
    private void addNewMedecin() {
        try {
            String prenom = medecinPrenomField.getText().trim();
            String nom = medecinNomField.getText().trim();
            String email = medecinEmailField.getText().trim();
            String phone = medecinPhoneField.getText().trim();
            String address = medecinAddressField.getText().trim();
            int nbState = Integer.parseInt(medecinNbStateField.getText());
            String city = medecinCityField.getText().trim();
            Long nbAgreement = Long.parseLong(medecinNbAgreementField.getText().trim());

            if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty()) {
                showErrorMessage("Veuillez remplir au moins le prénom, nom et email!");
                return;
            }

            // Create client with default values for required fields
            Medecin newMedecin = new Medecin, nom, address.isEmpty() ? "Non renseigné" : address, nbState, city, phone.isEmpty() ? "0000000000" : phone,
                    email,String.valueOf(nbAgreement).isEmpty() ? 0L : nbAgreement);

            PharmacieController.addClient(newMedecin);
            filterMedecins();//loadClientsData();
            clearMedecinForm();
            showSuccessMessage("Medecin ajouté avec succès!");
            updateStatusLabel("Nouveau Medecin ajouté: Dr " + prenom + " " + nom);

        } catch (Exception e) {
            showErrorMessage("Erreur lors de l'ajout du client: " + e.getMessage());
        }
    }
    private void updateMedecin() {
        try {
            int selectedMedecin = medecinTable.getSelectedRow();
            if (selectedMedecin >= 0 || selectedMedecin > medecinTable.getRowCount()) {
                cancelButton.setVisible(true);
                validationButton.setVisible(true);
                addClientButton.setVisible(false);
                clearClientButton.setVisible(false);
                updateClientButton.setVisible(false);
                setMedecinForm(PharmacieController.getListMedecins().get(selectedMedecin));
                validationButton.addActionListener(actionEvent -> {
                    Medecin medecin = PharmacieController.getListMedecin().get(selectedMedecin);
                    PharmacieController.updateMedecin(Medecin,
                            medecinPrenomField.getText(),
                            medecinNomField.getText(),
                            medecinAddressField.getText(),
                            Integer.parseInt(medecinNbAgreementField.getText()),
                            medecinCityField.getText(),
                            medecinPhoneField.getText(),
                            medecinEmailField.getText(),
                            Long.parseLong(medecinNbAgreementField.getText()),
                    );
                    filterMedecin();//loadClientsData();
                    clearMedecinForm();
                    cancelButton.setVisible(false);
                    validationButton.setVisible(false);
                    addClientButton.setVisible(true);
                    clearClientButton.setVisible(true);
                    updateClientButton.setVisible(true);
                    showSuccessMessage("Medecin modifié avec succès!");
                    updateStatusLabel("Medecin modifié "); //TODO add lastName + FirstName
                });
                cancelButton.addActionListener(e -> {
                    clearMedecintForm();
                    cancelButton.setVisible(false);
                    validationButton.setVisible(false);
                    addClientButton.setVisible(true);
                    clearClientButton.setVisible(true);
                    updateClientButton.setVisible(true);
                });
            } else {
                showErrorMessage("Selected Medecin required");
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
        AchatDialog dialog = new AchatDialog(this,"Nouvel Achat Direct",false);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            filterAchats();
            updateStatistics();
            updateStatusLabel("Nouvel achat enregistré");
            dateFilterCombo.setSelectedItem(DateFilter.ALL_TIME.toString());
        }
    }

    private void createAchatWithOrdonnance() {
        if (PharmacieController.getListClients().isEmpty()) {
            showErrorMessage("Aucun client enregistré. Veuillez d'abord ajouter des clients.");
            return;
        }
        AchatDialog dialog = new AchatDialog(this,"Nouvel Achat Ordonnance",true);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            filterAchats();
            loadOrdoData();
            updateStatistics();
            updateStatusLabel("Nouvel achat enregistré");
            dateFilterCombo.setSelectedItem(DateFilter.ALL_TIME.toString());
        }
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
            "Détails du remboursement:\n" +
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

    // call popup details
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
            Achat achat = PharmacieController.listAchats.get(selectedRow);
            AchatDetailsDialog dialog = new AchatDetailsDialog(this, achat);
            dialog.setVisible(true);
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
    private void showOrdoDetails() {
        int selectedRow = ordoTable.getSelectedRow();
        if (selectedRow != -1) {
           // LocalDate dateAchat = LocalDate.parse((String)achatsTableModel.getValueAt(selectedRow, 0),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Ordonnance ordo = PharmacieController.getListOrdo().get(selectedRow);
            OrdoDetailsDialog dialog = new OrdoDetailsDialog(this, ordo);
            dialog.setVisible(true);
        }
    }
    private void showMutDetails() {
        int selectedRow = mutuelleTable.getSelectedRow();
        if (selectedRow != -1) {
            Mutuelle mut = PharmacieController.getListMutuelles().get(selectedRow);
            MutDetailsDialog dialog = new MutDetailsDialog(this, mut);
            dialog.setVisible(true);
        }
    }

    // loading data
    private void loadInitialData() {
        filterClients();
        loadMedicamentsData();
        filterAchats(); //TODO sort by date in JTable
        loadOrdoData(); //TODO sort by date in JTable
        loadMedecinData(); //TODO sort by Name(a-z) / by cat
        loadMutData(); //TODO sort by Name(a-z)
        updateStatistics();
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
       /* ordoTableModel.setRowCount(0);
        for (Ordonnance ordonnance : PharmacieController.getListOrdo()) {
            Object[] row = {
                    ordonnance.getDateOrdo(),
                    ordonnance.getPatient().getFirstName() + " " + ordonnance.getPatient().getLastName(),
                    "Dr "+ordonnance.getMedecin().getLastName()+" "+ordonnance.getMedecin().getFirstName(),
                    ordonnance.getNbAgreement(),
                    ordonnance.getIdMedecin()
            };
            ordoTableModel.addRow(row);
        }*/
        filterOrdoByMedecins();
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
        private JList<Medicament> medicamentsList;
        private DefaultListModel<Medicament> medicamentsListModel;
        private boolean confirmed = false;
        private JComboBox<Client> clientCombo;
        private JComboBox<Medecin> medecinsCombo;
        private boolean typeAchat;
        private JComboBox<Achat> achatsCombo;
        public JFormattedTextField dateOrdoField;

        public AchatDialog(Frame parent, String titleFrame,boolean typeAchat) {
            super(parent, titleFrame ,true);
            this.typeAchat = typeAchat;
            initializeDialog();
        }
        private void initializeDialog() {
            setSize(600, 600);
            setLocationRelativeTo(getParent());
            
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

            // Client selection
            JPanel clientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            clientPanel.add(new JLabel("Client:"));
            clientCombo = new JComboBox<>();
            clientPanel.add(clientCombo);
            for (Client client : PharmacieController.getListClients()) {
                clientCombo.addItem(client);
            }
            clientPanel.add(clientCombo);

            //Medecin selection
            JPanel medecinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            medecinPanel.setVisible(false);
            medecinPanel.add(new JLabel("Medecin:"));
            medecinsCombo = new JComboBox<>();
            for (Medecin medecin : PharmacieController.getListMedecins()) {
                medecinsCombo.addItem(medecin);
            }
            medecinPanel.add(medecinsCombo);

            //date Ordo
            JPanel dateOrdoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            dateOrdoPanel.setVisible(false);
            dateOrdoPanel.add(new JLabel("Date Ordonnance:"));
            dateOrdoField = new JFormattedTextField();
            dateOrdoField.setColumns(10);  // largeur du champ
            dateOrdoPanel.add(dateOrdoField);


            // Med selection
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
            JLabel totalLabel = new JLabel("Total: 0.00 €");
            JLabel remboursementLabel = new JLabel("Remboursement: 0.00 €");
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

            JPanel topPanel = new JPanel(new GridLayout(3, 1)); // 3 lines : client + medecin
            topPanel.add(clientPanel);
            topPanel.add(medecinPanel);
            topPanel.add(dateOrdoPanel);
            if (this.typeAchat){
                medecinPanel.setVisible(true);
                dateOrdoPanel.setVisible(true);
            }
            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(totalPanel, BorderLayout.EAST);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            setContentPane(mainPanel);
        }
        private void confirmAchat() {
            //selection client
            Client selectedClient = (Client) clientCombo.getSelectedItem();
            int[] selectedIndices = medicamentsList.getSelectedIndices();
            if (selectedClient == null || selectedIndices.length == 0) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client et au moins un médicament!");
                return;}

            try {
                Achat newAchat = new Achat(LocalDate.now(), selectedClient);
                for (int index : selectedIndices) {
                    Medicament med = medicamentsListModel.getElementAt(index);
                    newAchat.addMedAchat(med);
                }
                if (typeAchat) {
                    //selection medecin
                    Medecin selectedMedecin = (Medecin) medecinsCombo.getSelectedItem();
                   //seize date ordo
                    LocalDate seizeDateOrdo = LocalDate.parse(dateOrdoField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    if (selectedMedecin == null || seizeDateOrdo.isAfter(LocalDate.now())){
                        JOptionPane.showMessageDialog(this, "Veuillez sélectionner un medecin et date réelle d'ordonnance!");
                        return;
                    }
                Ordonnance newOrdo = new Ordonnance(seizeDateOrdo, selectedMedecin, selectedClient);
                newOrdo.getListMedOrdo().addAll(newAchat.getListMedAchat()); // get list empty and create copy of list achat
                newAchat.setOrdonnance(newOrdo); // add new ordo at purchase
                PharmacieController.addOrdonnance(newOrdo);
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

            gbc.gridx = 0; gbc.gridy = 7;
            panel.add(new JLabel("Mutuelle:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(String.valueOf(client.getMutuelle())), gbc);

            // Close button
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dispose());
            gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(closeButton, gbc);
            
            setContentPane(panel);
            this.pack();
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
            this.pack();
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
            panel.add(new JLabel(achat.getClient().toString()), gbc);

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
            gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(closeButton, gbc);

            setContentPane(panel);
            this.pack();
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
            this.pack();
        }
    }
    private static class OrdoDetailsDialog extends JDialog {
        public OrdoDetailsDialog(Frame parent, Ordonnance ordo) {
            super(parent, "Détails de l'ordonnance", true);
            setSize(400, 300);
            setLocationRelativeTo(parent);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(2, 5, 2, 5);
            gbc.anchor = GridBagConstraints.WEST;

            // Add client information
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Date de l'ordonnance:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(ordo.getDateOrdo()), gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Medecin:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(ordo.getMedecin().toString()), gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("Patient:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(ordo.getPatient().toString()), gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            panel.add(new JLabel("Médicaments:"), gbc);
            gbc.gridx = 1;
            StringBuilder sb = new StringBuilder("<html>");
            for (Medicament med :ordo.getListMedOrdo()) {
                sb.append(med.getNameMed()).append("<br>"); } //TODO add quantity
            sb.append("</html>");
            panel.add(new JLabel(sb.toString()), gbc);
//DateTimeFormatter.ofPattern("dd/MM/yyyy")
            // Close button
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dispose());
            gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(closeButton, gbc);

            setContentPane(panel);
            this.pack();
        }
    }
    private static class MutDetailsDialog extends JDialog {
        public MutDetailsDialog(Frame parent, Mutuelle mut) {
            super(parent, "Détails de la Mutuelle", true);
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
            panel.add(new JLabel(mut.getLastName()), gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Adresse:"), gbc);
            gbc.gridx = 1;
            StringBuilder sb = new StringBuilder("<html>");
            if (mut.getAddress()==null) {
                sb = new StringBuilder("Non renseigné");
            } else {
            for (Mutuelle mutuelle : PharmacieController.getListMutuelles()) {
                sb.append(mutuelle.getAddress()).append("<br>");
                sb.append(mutuelle.getNbState()).append(" ").append(mutuelle.getCity()).append("<br>"); }
                sb.append("</html>");}
            panel.add(new JLabel(sb.toString()), gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("Coord:"), gbc);
            gbc.gridx = 1;
            StringBuilder sbCoord = new StringBuilder("<html>");
            if (mut.getPhone()==null) {
                sbCoord = new StringBuilder("Non renseigné");
            } else {
                sbCoord.append(mut.getEmail()).append(" ").append(mut.getPhone()).append("<br>");
            sbCoord.append("</html>");}
            panel.add(new JLabel(sbCoord.toString()), gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            panel.add(new JLabel("Remb:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(String.valueOf(mut.getTauxRemb())), gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            panel.add(new JLabel("Dep:"), gbc);
            gbc.gridx = 1;
            StringBuilder sbMut = new StringBuilder("<html>");
            if (mut.getDep()==null) {
                sbMut = new StringBuilder("Non renseigné");
            } else {
                sbMut.append(mut.getDep()).append("<br>");
                sbMut.append("</html>");}
            panel.add(new JLabel(sbMut.toString()), gbc);

//DateTimeFormatter.ofPattern("dd/MM/yyyy")
            // Close button
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dispose());
            gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(closeButton, gbc);

            setContentPane(panel);
            this.pack();
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

        // Update the border count
        setupBorderScroll(achatsScrollPane, "("+achatsTableModel.getRowCount()+" / "+
                PharmacieController.getListAchats().size()+")");
        // update status message
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
    private void filterOrdoByMedecins() {
        Medecin selectedMedecinFilter = (Medecin) medecinsCombo.getSelectedItem();
        assert selectedMedecinFilter != null; // not be null
        ordoTableModel.setRowCount(0);
        for (Ordonnance ordoFilted : PharmacieController.getListOrdo()) {
            boolean medecinSelected = selectedMedecinFilter.equals(ordoFilted.getMedecin()); //"Dr "+medecin.getLastName()+" "+medecin.getFirstName());
            if (medecinSelected) {
                Object[] row = {
                        ordoFilted.getDateOrdo(),
                        ordoFilted.getPatient().getFirstName() + " " + ordoFilted.getPatient().getLastName(),
                        "Dr "+ordoFilted.getMedecin().getLastName()+" "+ordoFilted.getMedecin().getFirstName(),
                        ordoFilted.getNbAgreement(),
                        ordoFilted.getIdMedecin()
                };
                ordoTableModel.addRow(row);
            }
        }


        // Update the border title with filtered count
        setupBorderScroll(ordoScrollPane, "("+ordoTableModel.getRowCount()+" / "+
                PharmacieController.getListOrdo().size()+")");
        // Create status message showing both active filters
        String statusMessage = "Ordonnances filtrées: "+selectedMedecinFilter;
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