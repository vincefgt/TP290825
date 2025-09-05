package view;

import controler.PharmacieController;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Interface moderne alternative pour Sparadrap
 * Design épuré et fonctionnalités avancées
 */
public class ModernSparadrapInterface extends JFrame {
    
    private PharmacieController controller;
    private JTabbedPane mainTabbedPane;
    
    // Tables et modèles
    private JTable clientsTable, medicamentsTable, achatsTable;
    private DefaultTableModel clientsModel, medicamentsModel, achatsModel;
    private TableRowSorter<DefaultTableModel> clientsSorter, medicamentsSorter, achatsSorter;
    
    // Panneaux de statistiques
    private JLabel statsClients, statsMedicaments, statsAchats, statsRevenue;
    
    public ModernSparadrapInterface() {
        this.controller = new PharmacieController();
        initializeInterface();
        createComponents();
        setupLayout();
        setupEventHandlers();
        loadAllData();
        startRealTimeUpdates();
    }
    
    private void initializeInterface() {
        setTitle("🏥 SPARADRAP - Gestion Pharmaceutique Moderne");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1200, 700));
        
        // Configuration moderne
        setBackground(Color.WHITE);
        getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(52, 73, 94)));
    }
    
    private void createComponents() {
        // Créer le panneau principal avec design moderne
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        
        // Header avec gradient
        JPanel headerPanel = createModernHeader();
        
        // Dashboard avec statistiques
        JPanel dashboardPanel = createDashboard();
        
        // Onglets principaux
        mainTabbedPane = createMainTabs();
        
        // Status bar moderne
        JPanel statusBar = createModernStatusBar();
        
        // Assembly
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(dashboardPanel, BorderLayout.CENTER);
        mainPanel.add(mainTabbedPane, BorderLayout.SOUTH);
        mainPanel.add(statusBar, BorderLayout.PAGE_END);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createModernHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(52, 73, 94));
        header.setBorder(new EmptyBorder(25, 30, 25, 30));
        
        // Titre principal avec icône
        JLabel mainTitle = new JLabel("🏥 PHARMACIE SPARADRAP");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        mainTitle.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Système de Gestion Pharmaceutique - Interface Moderne");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(189, 195, 199));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(mainTitle, BorderLayout.CENTER);
        titlePanel.add(subtitle, BorderLayout.SOUTH);
        
        // Boutons d'action rapide
        JPanel quickActions = new JPanel(new FlowLayout());
        quickActions.setOpaque(false);
        
        JButton quickAddClient = createHeaderButton("👥 Client", new Color(52, 152, 219));
        JButton quickAddMed = createHeaderButton("💊 Médicament", new Color(46, 204, 113));
        JButton quickSale = createHeaderButton("🛒 Vente", new Color(241, 196, 15));
        
        quickActions.add(quickAddClient);
        quickActions.add(quickAddMed);
        quickActions.add(quickSale);
        
        header.add(titlePanel, BorderLayout.WEST);
        header.add(quickActions, BorderLayout.EAST);
        
        return header;
    }
    
    private JButton createHeaderButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Effet hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private JPanel createDashboard() {
        JPanel dashboard = new JPanel(new GridLayout(1, 4, 15, 0));
        dashboard.setBorder(new EmptyBorder(20, 30, 20, 30));
        dashboard.setBackground(new Color(248, 249, 250));
        
        // Cartes de statistiques
        dashboard.add(createStatsCard("👥 Clients", "0", new Color(52, 152, 219), statsClients = new JLabel("0")));
        dashboard.add(createStatsCard("💊 Médicaments", "0", new Color(46, 204, 113), statsMedicaments = new JLabel("0")));
        dashboard.add(createStatsCard("🛒 Achats", "0", new Color(241, 196, 15), statsAchats = new JLabel("0")));
        dashboard.add(createStatsCard("💰 CA Total", "0.00 €", new Color(155, 89, 182), statsRevenue = new JLabel("0.00 €")));
        
        return dashboard;
    }
    
    private JPanel createStatsCard(String title, String value, Color color, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(color);
        
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(new Color(52, 73, 94));
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        valueLabel.setText(value);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        // Effet hover sur la carte
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
            }
        });
        
        return card;
    }
    
    private JTabbedPane createMainTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabs.setBackground(Color.WHITE);
        tabs.setPreferredSize(new Dimension(0, 400));
        
        // Onglets avec icônes
        tabs.addTab("👥 Clients", createClientsTab());
        tabs.addTab("💊 Médicaments", createMedicamentsTab());
        tabs.addTab("🛒 Achats & Ventes", createAchatsTab());
        tabs.addTab("📊 Rapports", createReportsTab());
        
        return tabs;
    }
    
    private JPanel createClientsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);
        
        // Table des clients avec recherche
        String[] columns = {"Prénom", "Nom", "Email", "Téléphone", "Mutuelle", "Médecin"};
        clientsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        clientsTable = new JTable(clientsModel);
        styleModernTable(clientsTable);
        
        clientsSorter = new TableRowSorter<>(clientsModel);
        clientsTable.setRowSorter(clientsSorter);
        
        JScrollPane scrollPane = new JScrollPane(clientsTable);
        scrollPane.setBorder(createModernBorder("Liste des Clients"));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createMedicamentsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);
        
        String[] columns = {"Nom", "Catégorie", "Prix", "Stock", "Statut"};
        medicamentsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        medicamentsTable = new JTable(medicamentsModel);
        styleModernTable(medicamentsTable);
        
        medicamentsSorter = new TableRowSorter<>(medicamentsModel);
        medicamentsTable.setRowSorter(medicamentsSorter);
        
        JScrollPane scrollPane = new JScrollPane(medicamentsTable);
        scrollPane.setBorder(createModernBorder("Inventaire des Médicaments"));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createAchatsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);
        
        String[] columns = {"Date", "Client", "Type", "Montant", "Remboursement", "Bénéfice"};
        achatsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        achatsTable = new JTable(achatsModel);
        styleModernTable(achatsTable);
        
        achatsSorter = new TableRowSorter<>(achatsModel);
        achatsTable.setRowSorter(achatsSorter);
        
        JScrollPane scrollPane = new JScrollPane(achatsTable);
        scrollPane.setBorder(createModernBorder("Historique des Achats"));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createReportsTab() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        // Graphiques et rapports (simulés avec des panneaux colorés)
        panel.add(createReportCard("📈 Ventes par Mois", "Graphique des ventes", new Color(52, 152, 219)));
        panel.add(createReportCard("🏆 Top Médicaments", "Médicaments les plus vendus", new Color(46, 204, 113)));
        panel.add(createReportCard("👥 Clients Actifs", "Analyse de la clientèle", new Color(241, 196, 15)));
        panel.add(createReportCard("💰 Rentabilité", "Analyse financière", new Color(155, 89, 182)));
        
        return panel;
    }
    
    private JPanel createReportCard(String title, String description, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(color);
        
        JLabel descLabel = new JLabel(description, JLabel.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(127, 140, 141));
        
        JButton viewButton = new JButton("Voir le rapport");
        viewButton.setBackground(color);
        viewButton.setForeground(Color.WHITE);
        viewButton.setBorderPainted(false);
        viewButton.setFocusPainted(false);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);
        card.add(viewButton, BorderLayout.SOUTH);
        
        return card;
    }
    
    private void styleModernTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(52, 152, 219, 30));
        table.setSelectionForeground(new Color(52, 73, 94));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
    }
    
    private javax.swing.border.Border createModernBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 73, 94), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ),
            title,
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(52, 73, 94)
        );
    }
    
    private JPanel createModernStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(52, 73, 94));
        statusBar.setBorder(new EmptyBorder(8, 20, 8, 20));
        
        JLabel statusLabel = new JLabel("🟢 Système opérationnel");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.WHITE);
        
        JLabel timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(new Color(189, 195, 199));
        
        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(timeLabel, BorderLayout.EAST);
        
        // Mettre à jour l'heure
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeLabel.setText(LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                });
            }
        }, 0, 1000);
        
        return statusBar;
    }
    
    private void setupLayout() {
        // La disposition est principalement gérée par le fichier .form
        // Ici on peut ajouter des ajustements spécifiques
    }
    
    private void setupEventHandlers() {
        // Les gestionnaires d'événements seront ajoutés ici
        // pour les interactions utilisateur
    }
    
    private void loadAllData() {
        loadClientsData();
        loadMedicamentsData();
        loadAchatsData();
        updateStatistics();
    }
    
    private void loadClientsData() {
        clientsModel.setRowCount(0);
        for (Client client : PharmacieController.getListClients()) {
            Object[] row = {
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhone(),
                client.getMutuelle() != null ? client.getMutuelle().getLastName() : "Aucune",
                client.getMedecinTraitant() != null ? client.getMedecinTraitant().getLastName() : "Aucun"
            };
            clientsModel.addRow(row);
        }
    }
    
    private void loadMedicamentsData() {
        medicamentsModel.setRowCount(0);
        for (Medicament med : PharmacieController.getListMed()) {
            String statut = med.getStock() > 0 ? "✅ Disponible" : "❌ Rupture";
            Object[] row = {
                med.getNameMed(),
                med.getCat().toString(),
                String.format("%.2f €", med.getPrice()),
                med.getStock(),
                statut
            };
            medicamentsModel.addRow(row);
        }
    }
    
    private void loadAchatsData() {
        achatsModel.setRowCount(0);
        for (Achat achat : PharmacieController.getListAchats()) {
            double benefice = achat.getTotal() - achat.getRemb();
            Object[] row = {
                achat.getDateAchat(),
                achat.getClient().getFirstName() + " " + achat.getClient().getLastName(),
                achat.IsAchatDirect() ? "🛒 Direct" : "📋 Ordonnance",
                String.format("%.2f €", achat.getTotal()),
                String.format("%.2f €", achat.getRemb()),
                String.format("%.2f €", benefice)
            };
            achatsModel.addRow(row);
        }
    }
    
    private void updateStatistics() {
        statsClients.setText(String.valueOf(PharmacieController.getListClients().size()));
        statsMedicaments.setText(String.valueOf(PharmacieController.getListMed().size()));
        statsAchats.setText(String.valueOf(PharmacieController.getListAchats().size()));
        
        double totalRevenue = PharmacieController.getListAchats().stream()
                .mapToDouble(Achat::getTotal)
                .sum();
        statsRevenue.setText(String.format("%.2f €", totalRevenue));
    }
    
    private void startRealTimeUpdates() {
        // Mettre à jour les données toutes les 30 secondes
        Timer updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> updateStatistics());
            }
        }, 30000, 30000);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Utiliser le Look and Feel du système
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
                
                // Lancer l'interface moderne
                new ModernSparadrapInterface().setVisible(true);
                
            } catch (Exception e) {
                e.printStackTrace();
                // Fallback vers l'interface standard
                new SparadrapMainInterface().setVisible(true);
            }
        });
    }
}