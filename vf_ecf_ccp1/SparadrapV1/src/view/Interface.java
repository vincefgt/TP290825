package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interface principale de SparadrapV1
 * Compatible avec le plugin UI Designer d'IntelliJ IDEA
 *
 * Cette classe utilise des composants Swing standard et suit les bonnes pratiques
 * pour être facilement modifiable via l'UI Designer
 */
public class Interface extends JFrame {

    // Panneaux principaux
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private JPanel statusPanel;

    // Composants d'interface
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JTabbedPane tabbedPane;
    private JTree navigationTree;
    private JTable dataTable;
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JLabel statusLabel;

    // Boutons principaux
    private JButton btnNew;
    private JButton btnOpen;
    private JButton btnSave;
    private JButton btnExport;
    private JButton btnRefresh;

    public Interface() {
        initComponents();
        setupLayout();
        setupEventHandlers();
    }

    /**
     * Initialise tous les composants de l'interface
     * Méthode facilement modifiable via UI Designer
     */
    private void initComponents() {
        setTitle("SparadrapV1 - Gestionnaire de Données");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Création des panneaux principaux
        mainPanel = new JPanel(new BorderLayout());
//        this.setContentPane(mainPanel);
        headerPanel = new JPanel(new BorderLayout());
        contentPanel = new JPanel(new BorderLayout());
        sidebarPanel = new JPanel(new BorderLayout());
        statusPanel = new JPanel(new BorderLayout());

        // Configuration du menu
        setupMenuBar();

        // Configuration de la toolbar
        setupToolBar();

        // Configuration des composants
        setupNavigationTree();
        setupDataTable();
        setupTabbedPane();
        setupStatusPanel();
//        this.pack();
//        mainPanel.setVisible(true);

    }

    /**
     * Configure la barre de menu
     */
    private void setupMenuBar() {
        menuBar = new JMenuBar();

        // Menu Fichier
        JMenu fileMenu = new JMenu("Fichier");
        fileMenu.add(createMenuItem("Nouveau", "ctrl N", this::onNew));
        fileMenu.add(createMenuItem("Ouvrir", "ctrl O", this::onOpen));
        fileMenu.add(createMenuItem("Enregistrer", "ctrl S", this::onSave));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Exporter", "ctrl E", this::onExport));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Quitter", "ctrl Q", this::onExit));

        // Menu Edition
        JMenu editMenu = new JMenu("Edition");
        editMenu.add(createMenuItem("Copier", "ctrl C", this::onCopy));
        editMenu.add(createMenuItem("Coller", "ctrl V", this::onPaste));
        editMenu.add(createMenuItem("Supprimer", "DELETE", this::onDelete));

        // Menu Affichage
        JMenu viewMenu = new JMenu("Affichage");
        viewMenu.add(createMenuItem("Actualiser", "F5", this::onRefresh));
        viewMenu.add(createMenuItem("Logs", "ctrl L", this::onShowLogs));

        // Menu Aide
        JMenu helpMenu = new JMenu("Aide");
        helpMenu.add(createMenuItem("À propos", "", this::onAbout));

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Configure la barre d'outils
     */
    private void setupToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        btnNew = new JButton("Nouveau");
        btnNew.setIcon(createIcon("new"));
        btnNew.addActionListener(this::onNew);

        btnOpen = new JButton("Ouvrir");
        btnOpen.setIcon(createIcon("open"));
        btnOpen.addActionListener(this::onOpen);

        btnSave = new JButton("Enregistrer");
        btnSave.setIcon(createIcon("save"));
        btnSave.addActionListener(this::onSave);

        btnExport = new JButton("Exporter");
        btnExport.setIcon(createIcon("export"));
        btnExport.addActionListener(this::onExport);

        btnRefresh = new JButton("Actualiser");
        btnRefresh.setIcon(createIcon("refresh"));
        btnRefresh.addActionListener(this::onRefresh);

        toolBar.add(btnNew);
        toolBar.add(btnOpen);
        toolBar.add(btnSave);
        toolBar.addSeparator();
        toolBar.add(btnExport);
        toolBar.addSeparator();
        toolBar.add(btnRefresh);

        headerPanel.add(toolBar, BorderLayout.NORTH);
    }

    /**
     * Configure l'arbre de navigation
     */
    private void setupNavigationTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("SparadrapV1");
        DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode("Données");
        DefaultMutableTreeNode configNode = new DefaultMutableTreeNode("Configuration");
        DefaultMutableTreeNode reportsNode = new DefaultMutableTreeNode("Rapports");

        root.add(dataNode);
        root.add(configNode);
        root.add(reportsNode);

        // Ajout de sous-éléments
        dataNode.add(new DefaultMutableTreeNode("Tables"));
        dataNode.add(new DefaultMutableTreeNode("Vues"));
        dataNode.add(new DefaultMutableTreeNode("Requêtes"));

        configNode.add(new DefaultMutableTreeNode("Base de données"));
        configNode.add(new DefaultMutableTreeNode("Utilisateurs"));
        configNode.add(new DefaultMutableTreeNode("Paramètres"));

        navigationTree = new JTree(root);
        navigationTree.setRootVisible(true);
        navigationTree.expandRow(0);

        JScrollPane treeScrollPane = new JScrollPane(navigationTree);
        treeScrollPane.setPreferredSize(new Dimension(250, 0));
        treeScrollPane.setBorder(new TitledBorder("Navigation"));

        sidebarPanel.add(treeScrollPane, BorderLayout.CENTER);
    }

    /**
     * Configure la table de données principale
     */
    private void setupDataTable() {
        String[] columnNames = {"ID", "Nom", "Type", "Statut", "Date création", "Actions"};
        Object[][] data = {
                {1, "Élément 1", "Type A", "Actif", "2025-01-15", "Modifier"},
                {2, "Élément 2", "Type B", "Inactif", "2025-01-14", "Modifier"},
                {3, "Élément 3", "Type A", "Actif", "2025-01-13", "Modifier"}
        };

        dataTable = new JTable(data, columnNames);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.setRowHeight(25);

        JScrollPane tableScrollPane = new JScrollPane(dataTable);
        tableScrollPane.setBorder(new TitledBorder("Données principales"));

        contentPanel.add(tableScrollPane, BorderLayout.CENTER);
    }

    /**
     * Configure les onglets principaux
     */
    private void setupTabbedPane() {
        tabbedPane = new JTabbedPane();

        // Onglet Données
        JPanel dataPanel = new JPanel(new BorderLayout());
        dataPanel.add(contentPanel, BorderLayout.CENTER);

        // Onglet Logs
        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        logArea.append("SparadrapV1 - Démarrage du système...\n");
        logArea.append("Interface initialisée avec succès.\n");

        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setBorder(new TitledBorder("Journal d'événements"));

        // Onglet Configuration
        JPanel configPanel = createConfigPanel();

        tabbedPane.addTab("Données", dataPanel);
        tabbedPane.addTab("Logs", logScrollPane);
        tabbedPane.addTab("Configuration", configPanel);

        //contentPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Crée le panneau de configuration
     */
    private JPanel createConfigPanel() {
        JPanel configPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        configPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Configuration de la base de données
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("Serveur de base de données:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dbServerField = new JTextField("localhost:3306");
        configPanel.add(dbServerField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        configPanel.add(new JLabel("Base de données:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField dbNameField = new JTextField("sparadrap_db");
        configPanel.add(dbNameField, gbc);

        // Boutons de test
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; gbc.insets = new Insets(20, 0, 0, 0);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton testButton = new JButton("Tester la connexion");
        JButton saveConfigButton = new JButton("Sauvegarder");

        buttonPanel.add(testButton);
        buttonPanel.add(saveConfigButton);
        configPanel.add(buttonPanel, gbc);

        return configPanel;
    }

    /**
     * Configure le panneau de statut
     */
    private void setupStatusPanel() {
        statusLabel = new JLabel("Prêt");
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("Idle");
        progressBar.setPreferredSize(new Dimension(200, 20));

        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(progressBar, BorderLayout.EAST);
        statusPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    /**
     * Configure la disposition générale
     */
    private void setupLayout() {
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
    }

    /**
     * Configure les gestionnaires d'événements
     */
    private void setupEventHandlers() {
        // Sélection dans l'arbre de navigation
        navigationTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    navigationTree.getLastSelectedPathComponent();
            if (node != null) {
                updateStatusLabel("Sélectionné: " + node.toString());
            }
        });

        // Sélection dans la table
        dataTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = dataTable.getSelectedRow();
                if (selectedRow >= 0) {
                    updateStatusLabel("Ligne sélectionnée: " + (selectedRow + 1));
                }
            }
        });
    }

    // Méthodes utilitaires
    private JMenuItem createMenuItem(String text, String accelerator, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        if (!accelerator.isEmpty()) {
            item.setAccelerator(KeyStroke.getKeyStroke(accelerator));
        }
        item.addActionListener(listener);
        return item;
    }
    private Icon createIcon(String name) {
        // Placeholder pour les icônes - à remplacer par de vraies icônes
        return UIManager.getIcon("FileView.computerIcon");
    }
    private void updateStatusLabel(String text) {
        statusLabel.setText(text);
        addToLog(text);
    }
    private void addToLog(String message) {
        logArea.append(java.time.LocalDateTime.now().toString() + " - " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    // Gestionnaires d'événements
    private void onNew(ActionEvent e) {
        updateStatusLabel("Création d'un nouvel élément...");
        // TODO: Implémenter la logique
    }
    private void onOpen(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            updateStatusLabel("Ouverture du fichier: " + fileChooser.getSelectedFile().getName());
        }
    }
    private void onSave(ActionEvent e) {
        updateStatusLabel("Sauvegarde en cours...");
        // Simulation d'une tâche longue
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                progressBar.setIndeterminate(true);
                Thread.sleep(2000); // Simulation
                return null;
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                updateStatusLabel("Sauvegarde terminée");
            }
        };
        worker.execute();
    }
    private void onExport(ActionEvent e) {
        updateStatusLabel("Export des données...");
        // TODO: Implémenter la logique d'export
    }
    private void onRefresh(ActionEvent e) {
        updateStatusLabel("Actualisation des données...");
        // TODO: Implémenter l'actualisation
    }
    private void onCopy(ActionEvent e) {
        updateStatusLabel("Copie effectuée");
    }
    private void onPaste(ActionEvent e) {
        updateStatusLabel("Collage effectué");
    }
    private void onDelete(ActionEvent e) {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow >= 0) {
            int result = JOptionPane.showConfirmDialog(this,
                    "Êtes-vous sûr de vouloir supprimer cet élément ?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                updateStatusLabel("Élément supprimé");
            }
        }
    }
    private void onShowLogs(ActionEvent e) {
        tabbedPane.setSelectedIndex(1);
        updateStatusLabel("Affichage des logs");
    }
    private void onAbout(ActionEvent e) {
        JOptionPane.showMessageDialog(this,
                "SparadrapV1\n\nGestionnaire de données avancé\nVersion 1.0\n\nConçu pour être modifiable via UI Designer",
                "À propos",
                JOptionPane.INFORMATION_MESSAGE);
    }
    private void onExit(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment quitter l'application ?",
                "Confirmation de sortie",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // Point d'entrée principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new Interface().setVisible(true);
        });
    }
}