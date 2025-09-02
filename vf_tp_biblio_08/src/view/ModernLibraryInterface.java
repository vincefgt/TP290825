package view;

import controler.LibraryController;
import controler.Main;
import exception.SaisieException;
import model.Book;
import model.Subscriber;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ModernLibraryInterface extends JFrame {
    
    // Core components
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout contentLayout;
    private LibraryController controller;
    
    // Navigation buttons
    private JButton dashboardBtn;
    private JButton booksBtn;
    private JButton subscribersBtn;
    private JButton borrowedBtn;
    private JButton searchBtn;
    
    // Dashboard components
    private JPanel statsPanel;
    private JLabel totalBooksLabel;
    private JLabel availableBooksLabel;
    private JLabel borrowedBooksLabel;
    private JLabel totalSubscribersLabel;
    
    // Tables
    private JTable booksTable;
    private JTable subscribersTable;
    private JTable borrowedTable;
    private DefaultTableModel booksTableModel;
    private DefaultTableModel subscribersTableModel;
    private DefaultTableModel borrowedTableModel;
    
    // Form fields
    private JTextField titleField, authorFirstField, authorLastField, stockField, isbnField;
    private JTextField subFirstField, subLastField, emailField;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color WARNING_COLOR = new Color(243, 156, 18);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color LIGHT_GRAY = new Color(248, 249, 250);
    private final Color SIDEBAR_COLOR = new Color(44, 62, 80);
    private final Color CARD_COLOR = Color.WHITE;
    
    public ModernLibraryInterface() {
        this.controller = new LibraryController(this);
        initializeFrame();
        createComponents();
        layoutComponents();
        setupEventHandlers();
        loadAllData();
        showDashboard();
    }
    
    private void initializeFrame() {
        setTitle("📚 Library Management System - Professional Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1200, 700));
        
        // Modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_GRAY);
        
        createSidebar();
        createContentArea();
        createTables();
        createFormFields();
        createStatsPanel();
    }
    
    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(SIDEBAR_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(280, 0));
        sidebarPanel.setBorder(new EmptyBorder(25, 0, 25, 0));
        
        // Logo section
        JPanel logoPanel = createLogoSection();
        sidebarPanel.add(logoPanel);
        sidebarPanel.add(Box.createVerticalStrut(40));
        
        // Navigation buttons
        dashboardBtn = createNavButton("📊 Dashboard", "dashboard", true);
        booksBtn = createNavButton("📚 Books Management", "books", false);
        subscribersBtn = createNavButton("👥 Subscribers", "subscribers", false);
        borrowedBtn = createNavButton("📖 Borrowed Books", "borrowed", false);
        searchBtn = createNavButton("🔍 Advanced Search", "search", false);
        
        sidebarPanel.add(dashboardBtn);
        sidebarPanel.add(Box.createVerticalStrut(8));
        sidebarPanel.add(booksBtn);
        sidebarPanel.add(Box.createVerticalStrut(8));
        sidebarPanel.add(subscribersBtn);
        sidebarPanel.add(Box.createVerticalStrut(8));
        sidebarPanel.add(borrowedBtn);
        sidebarPanel.add(Box.createVerticalStrut(8));
        sidebarPanel.add(searchBtn);
        
        sidebarPanel.add(Box.createVerticalGlue());
        
        // Footer
        JLabel footerLabel = new JLabel("<html><center>BookRent Pro<br/>v2.0</center></html>", JLabel.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(149, 165, 166));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(footerLabel);
    }
    
    private JPanel createLogoSection() {
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(SIDEBAR_COLOR);
        logoPanel.setBorder(new EmptyBorder(0, 25, 0, 25));
        
        JLabel titleLabel = new JLabel("📚 BookRent", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Library Management", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        logoPanel.add(titleLabel);
        logoPanel.add(Box.createVerticalStrut(8));
        logoPanel.add(subtitleLabel);
        
        return logoPanel;
    }
    
    private JButton createNavButton(String text, String actionCommand, boolean selected) {
        JButton button = new JButton(text);
        button.setActionCommand(actionCommand);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(250, 50));
        button.setMaximumSize(new Dimension(250, 50));
        button.setBorder(new EmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (selected) {
            button.setBackground(PRIMARY_COLOR);
            button.setContentAreaFilled(true);
        }
        
        // Hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!selected) {
                    button.setBackground(new Color(52, 73, 94));
                    button.setContentAreaFilled(true);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected) {
                    button.setContentAreaFilled(false);
                }
            }
        });
        
        return button;
    }
    
    private void createContentArea() {
        contentPanel = new JPanel();
        contentLayout = new CardLayout();
        contentPanel.setLayout(contentLayout);
        contentPanel.setBackground(LIGHT_GRAY);
    }
    
    private void createTables() {
        // Books table
        String[] bookColumns = {"Title", "Author", "ISBN", "Stock", "Status"};
        booksTableModel = new DefaultTableModel(bookColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        booksTable = createStyledTable(booksTableModel);
        
        // Subscribers table
        String[] subColumns = {"First Name", "Last Name", "Email", "Registration Date"};
        subscribersTableModel = new DefaultTableModel(subColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        subscribersTable = createStyledTable(subscribersTableModel);
        
        // Borrowed books table
        String[] borrowColumns = {"Title", "Author", "ISBN", "Borrow Date", "Return Date", "Status"};
        borrowedTableModel = new DefaultTableModel(borrowColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        borrowedTable = createStyledTable(borrowedTableModel);
    }
    
    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(35);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 40));
        table.setSelectionForeground(SECONDARY_COLOR);
        table.setIntercellSpacing(new Dimension(0, 1));
        
        // Style header
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(SECONDARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        // Add sorting
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        return table;
    }
    
    private void createFormFields() {
        // Book form fields
        titleField = createStyledTextField("Enter book title...");
        authorFirstField = createStyledTextField("Author first name...");
        authorLastField = createStyledTextField("Author last name...");
        stockField = createStyledTextField("Stock quantity...");
        isbnField = createStyledTextField("ISBN (14 digits)...");
        
        // Subscriber form fields
        subFirstField = createStyledTextField("First name...");
        subLastField = createStyledTextField("Last name...");
        emailField = createStyledTextField("Email address...");
        
        // Search components
        searchField = createStyledTextField("Search books...");
        searchTypeCombo = new JComboBox<>(new String[]{"Title", "Author", "ISBN"});
        styleComboBox(searchTypeCombo);
    }
    
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        field.setPreferredSize(new Dimension(200, 40));
        field.setBackground(Color.WHITE);
        
        // Add placeholder effect
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
        
        return field;
    }
    
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboBox.setPreferredSize(new Dimension(120, 40));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
    }
    
    private void createStatsPanel() {
        statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(LIGHT_GRAY);
        statsPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        // Create stat cards
        JPanel totalBooksCard = createStatCard("📚 Total Books", "0", PRIMARY_COLOR);
        JPanel availableCard = createStatCard("✅ Available", "0", SUCCESS_COLOR);
        JPanel borrowedCard = createStatCard("📖 Borrowed", "0", WARNING_COLOR);
        JPanel subscribersCard = createStatCard("👥 Subscribers", "0", new Color(155, 89, 182));
        
        // Get labels for updating
        totalBooksLabel = (JLabel) ((JPanel) totalBooksCard.getComponent(1)).getComponent(0);
        availableBooksLabel = (JLabel) ((JPanel) availableCard.getComponent(1)).getComponent(0);
        borrowedBooksLabel = (JLabel) ((JPanel) borrowedCard.getComponent(1)).getComponent(0);
        totalSubscribersLabel = (JLabel) ((JPanel) subscribersCard.getComponent(1)).getComponent(0);
        
        statsPanel.add(totalBooksCard);
        statsPanel.add(availableCard);
        statsPanel.add(borrowedCard);
        statsPanel.add(subscribersCard);
    }
    
    private JPanel createStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(200, 120));
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(SECONDARY_COLOR);
        
        // Value
        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        valuePanel.setBackground(CARD_COLOR);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(accentColor);
        
        valuePanel.add(valueLabel);
        
        // Accent line
        JPanel accentLine = new JPanel();
        accentLine.setBackground(accentColor);
        accentLine.setPreferredSize(new Dimension(0, 3));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valuePanel, BorderLayout.CENTER);
        card.add(accentLine, BorderLayout.SOUTH);
        
        return card;
    }
    
    private void layoutComponents() {
        // Create content panels
        JPanel dashboardPanel = createDashboardPanel();
        JPanel booksPanel = createBooksPanel();
        JPanel subscribersPanel = createSubscribersPanel();
        JPanel borrowedPanel = createBorrowedPanel();
        JPanel searchPanel = createSearchPanel();
        
        // Add panels to content area
        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(booksPanel, "books");
        contentPanel.add(subscribersPanel, "subscribers");
        contentPanel.add(borrowedPanel, "borrowed");
        contentPanel.add(searchPanel, "search");
        
        // Layout main components
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(LIGHT_GRAY);
        headerPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        JLabel titleLabel = new JLabel("📊 Dashboard Overview");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(SECONDARY_COLOR);
        
        JLabel subtitleLabel = new JLabel("Library statistics and quick actions");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(LIGHT_GRAY);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        
        // Quick actions
        JPanel quickActionsPanel = createQuickActionsPanel();
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        panel.add(quickActionsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createQuickActionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JButton addBookBtn = createActionButton("➕ Add Book", SUCCESS_COLOR);
        JButton addSubBtn = createActionButton("👤 Add Subscriber", new Color(155, 89, 182));
        JButton viewBorrowedBtn = createActionButton("📖 View Borrowed", WARNING_COLOR);
        JButton searchBtn = createActionButton("🔍 Search Books", PRIMARY_COLOR);
        
        addBookBtn.addActionListener(e -> showBooks());
        addSubBtn.addActionListener(e -> showSubscribers());
        viewBorrowedBtn.addActionListener(e -> showBorrowed());
        searchBtn.addActionListener(e -> showSearch());
        
        panel.add(addBookBtn);
        panel.add(addSubBtn);
        panel.add(viewBorrowedBtn);
        panel.add(searchBtn);
        
        return panel;
    }
    
    private JButton createActionButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(160, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }
    
    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        JLabel headerLabel = new JLabel("📚 Books Management");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(SECONDARY_COLOR);
        headerLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Table panel
        JScrollPane tableScrollPane = new JScrollPane(booksTable);
        tableScrollPane.setBorder(createModernBorder("Books Collection"));
        tableScrollPane.setPreferredSize(new Dimension(0, 350));
        
        // Form panel
        JPanel formPanel = createBookFormPanel();
        
        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createBookFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(createModernBorder("Add New Book"));
        formPanel.setBackground(CARD_COLOR);
        
        // Fields panel
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(CARD_COLOR);
        fieldsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(createFieldLabel("Title:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(titleField, gbc);
        
        gbc.gridx = 2;
        fieldsPanel.add(createFieldLabel("Author First Name:"), gbc);
        gbc.gridx = 3;
        fieldsPanel.add(authorFirstField, gbc);
        
        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        fieldsPanel.add(createFieldLabel("Author Last Name:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(authorLastField, gbc);
        
        gbc.gridx = 2;
        fieldsPanel.add(createFieldLabel("Stock:"), gbc);
        gbc.gridx = 3;
        stockField.setPreferredSize(new Dimension(120, 40));
        fieldsPanel.add(stockField, gbc);
        
        // Row 3
        gbc.gridx = 0; gbc.gridy = 2;
        fieldsPanel.add(createFieldLabel("ISBN:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        isbnField.setPreferredSize(new Dimension(320, 40));
        fieldsPanel.add(isbnField, gbc);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(CARD_COLOR);
        buttonsPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        JButton addButton = createStyledButton("➕ Add Book", SUCCESS_COLOR);
        JButton clearButton = createStyledButton("🗑️ Clear", new Color(149, 165, 166));
        JButton borrowButton = createStyledButton("📖 Borrow Selected", WARNING_COLOR);
        
        buttonsPanel.add(clearButton);
        buttonsPanel.add(borrowButton);
        buttonsPanel.add(addButton);
        
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return formPanel;
    }
    
    private JPanel createSubscribersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        JLabel headerLabel = new JLabel("👥 Subscribers Management");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(SECONDARY_COLOR);
        headerLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Table
        JScrollPane tableScrollPane = new JScrollPane(subscribersTable);
        tableScrollPane.setBorder(createModernBorder("Registered Subscribers"));
        tableScrollPane.setPreferredSize(new Dimension(0, 400));
        
        // Form
        JPanel formPanel = createSubscriberFormPanel();
        
        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSubscriberFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(createModernBorder("Register New Subscriber"));
        formPanel.setBackground(CARD_COLOR);
        
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(CARD_COLOR);
        fieldsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // First Name
        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(createFieldLabel("First Name:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(subFirstField, gbc);
        
        // Last Name
        gbc.gridx = 2;
        fieldsPanel.add(createFieldLabel("Last Name:"), gbc);
        gbc.gridx = 3;
        fieldsPanel.add(subLastField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        fieldsPanel.add(createFieldLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        emailField.setPreferredSize(new Dimension(320, 40));
        fieldsPanel.add(emailField, gbc);
        
        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(CARD_COLOR);
        buttonsPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        JButton addButton = createStyledButton("➕ Add Subscriber", new Color(155, 89, 182));
        JButton clearButton = createStyledButton("🗑️ Clear", new Color(149, 165, 166));
        
        buttonsPanel.add(clearButton);
        buttonsPanel.add(addButton);
        
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return formPanel;
    }
    
    private JPanel createBorrowedPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        JLabel headerLabel = new JLabel("📖 Borrowed Books Management");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(SECONDARY_COLOR);
        headerLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Table
        JScrollPane tableScrollPane = new JScrollPane(borrowedTable);
        tableScrollPane.setBorder(createModernBorder("Currently Borrowed Books"));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(LIGHT_GRAY);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JButton returnButton = createStyledButton("📤 Return Selected", DANGER_COLOR);
        JButton refreshButton = createStyledButton("🔄 Refresh", PRIMARY_COLOR);
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(returnButton);
        
        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        JLabel headerLabel = new JLabel("🔍 Advanced Book Search");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(SECONDARY_COLOR);
        headerLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Search form
        JPanel searchFormPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchFormPanel.setBackground(CARD_COLOR);
        searchFormPanel.setBorder(BorderFactory.createCompoundBorder(
            createModernBorder("Search Criteria"),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        searchField.setPreferredSize(new Dimension(300, 40));
        searchTypeCombo.setPreferredSize(new Dimension(120, 40));
        
        JButton searchButton = createStyledButton("🔍 Search", PRIMARY_COLOR);
        
        searchFormPanel.add(new JLabel("Search by:"));
        searchFormPanel.add(searchTypeCombo);
        searchFormPanel.add(searchField);
        searchFormPanel.add(searchButton);
        
        // Results table (initially empty)
        JTable searchResultsTable = createStyledTable(new DefaultTableModel(
            new String[]{"Title", "Author", "ISBN", "Stock", "Status"}, 0));
        JScrollPane resultsScrollPane = new JScrollPane(searchResultsTable);
        resultsScrollPane.setBorder(createModernBorder("Search Results"));
        
        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(searchFormPanel, BorderLayout.CENTER);
        panel.add(resultsScrollPane, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(SECONDARY_COLOR);
        return label;
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(160, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }
    
    private javax.swing.border.Border createModernBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ),
            title,
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            SECONDARY_COLOR
        );
    }
    
    private void setupEventHandlers() {
        // Navigation buttons
        dashboardBtn.addActionListener(e -> showDashboard());
        booksBtn.addActionListener(e -> showBooks());
        subscribersBtn.addActionListener(e -> showSubscribers());
        borrowedBtn.addActionListener(e -> showBorrowed());
        searchBtn.addActionListener(e -> showSearch());
        
        // Form buttons - find and attach listeners
        setupFormButtonListeners();
        
        // Table double-click handlers
        booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showBookDetails();
                }
            }
        });
    }
    
    private void setupFormButtonListeners() {
        // This method will find buttons by their text and attach appropriate listeners
        SwingUtilities.invokeLater(() -> {
            attachButtonListener("➕ Add Book", e -> addNewBook());
            attachButtonListener("🗑️ Clear", e -> clearCurrentForm());
            attachButtonListener("📖 Borrow Selected", e -> borrowSelectedBook());
            attachButtonListener("➕ Add Subscriber", e -> addNewSubscriber());
            attachButtonListener("📤 Return Selected", e -> returnSelectedBook());
            attachButtonListener("🔄 Refresh", e -> loadAllData());
            attachButtonListener("🔍 Search", e -> performSearch());
        });
    }
    
    private void attachButtonListener(String buttonText, java.awt.event.ActionListener listener) {
        findButtonByText(mainPanel, buttonText, listener);
    }
    
    private void findButtonByText(Container container, String text, java.awt.event.ActionListener listener) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals(text)) {
                ((JButton) component).addActionListener(listener);
                return;
            } else if (component instanceof Container) {
                findButtonByText((Container) component, text, listener);
            }
        }
    }
    
    // Navigation methods
    private void showDashboard() {
        updateNavSelection(dashboardBtn);
        contentLayout.show(contentPanel, "dashboard");
        updateStats();
    }
    
    private void showBooks() {
        updateNavSelection(booksBtn);
        contentLayout.show(contentPanel, "books");
        loadBooksData();
    }
    
    private void showSubscribers() {
        updateNavSelection(subscribersBtn);
        contentLayout.show(contentPanel, "subscribers");
        loadSubscribersData();
    }
    
    private void showBorrowed() {
        updateNavSelection(borrowedBtn);
        contentLayout.show(contentPanel, "borrowed");
        loadBorrowedData();
    }
    
    private void showSearch() {
        updateNavSelection(searchBtn);
        contentLayout.show(contentPanel, "search");
    }
    
    private void updateNavSelection(JButton selectedButton) {
        // Reset all buttons
        JButton[] navButtons = {dashboardBtn, booksBtn, subscribersBtn, borrowedBtn, searchBtn};
        for (JButton btn : navButtons) {
            btn.setBackground(SIDEBAR_COLOR);
            btn.setContentAreaFilled(false);
        }
        
        // Highlight selected button
        selectedButton.setBackground(PRIMARY_COLOR);
        selectedButton.setContentAreaFilled(true);
    }
    
    // Action methods
    private void addNewBook() {
        try {
            if (!validateBookForm()) return;
            
            String title = getFieldText(titleField);
            String firstName = getFieldText(authorFirstField);
            String lastName = getFieldText(authorLastField);
            int stock = Integer.parseInt(getFieldText(stockField));
            long isbn = Long.parseLong(getFieldText(isbnField));
            
            controller.addBook(title, firstName, lastName, stock, isbn);
            clearBookForm();
            loadAllData();
            showSuccessMessage("📚 Book added successfully!");
            
        } catch (NumberFormatException e) {
            showErrorMessage("❌ Please enter valid numbers for Stock and ISBN!");
        } catch (SaisieException e) {
            showErrorMessage("❌ " + e.getMessage());
        }
    }
    
    private void addNewSubscriber() {
        try {
            if (!validateSubscriberForm()) return;
            
            String firstName = getFieldText(subFirstField);
            String lastName = getFieldText(subLastField);
            String email = getFieldText(emailField);
            
            controller.addSubscriber(firstName, lastName, email);
            clearSubscriberForm();
            loadAllData();
            showSuccessMessage("👥 Subscriber registered successfully!");
            
        } catch (SaisieException e) {
            showErrorMessage("❌ " + e.getMessage());
        }
    }
    
    private void borrowSelectedBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("❌ Please select a book to borrow!");
            return;
        }
        
        try {
            String isbnText = booksTableModel.getValueAt(selectedRow, 2).toString();
            long isbn = Long.parseLong(isbnText);
            
            controller.borrowBook(isbn);
            loadAllData();
            showSuccessMessage("📖 Book borrowed successfully!");
            
        } catch (Exception e) {
            showErrorMessage("❌ Error borrowing book: " + e.getMessage());
        }
    }
    
    private void returnSelectedBook() {
        int selectedRow = borrowedTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("❌ Please select a borrowed book to return!");
            return;
        }
        
        try {
            String isbnText = borrowedTableModel.getValueAt(selectedRow, 2).toString();
            long isbn = Long.parseLong(isbnText);
            
            controller.returnBook(isbn);
            loadAllData();
            showSuccessMessage("📤 Book returned successfully!");
            
        } catch (Exception e) {
            showErrorMessage("❌ Error returning book: " + e.getMessage());
        }
    }
    
    private void performSearch() {
        String searchText = getFieldText(searchField);
        String searchType = (String) searchTypeCombo.getSelectedItem();
        
        if (searchText.isEmpty()) {
            showErrorMessage("❌ Please enter search text!");
            return;
        }
        
        // Implement search logic here
        showSuccessMessage("🔍 Search functionality will be implemented!");
    }
    
    private void showBookDetails() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow != -1) {
            String title = booksTableModel.getValueAt(selectedRow, 0).toString();
            String author = booksTableModel.getValueAt(selectedRow, 1).toString();
            String isbn = booksTableModel.getValueAt(selectedRow, 2).toString();
            String stock = booksTableModel.getValueAt(selectedRow, 3).toString();
            
            String details = String.format(
                "<html><body style='padding: 10px;'>" +
                "<h2>📚 Book Details</h2>" +
                "<p><b>Title:</b> %s</p>" +
                "<p><b>Author:</b> %s</p>" +
                "<p><b>ISBN:</b> %s</p>" +
                "<p><b>Stock:</b> %s copies</p>" +
                "</body></html>",
                title, author, isbn, stock
            );
            
            JOptionPane.showMessageDialog(this, details, "Book Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Validation methods
    private boolean validateBookForm() {
        if (getFieldText(titleField).isEmpty() ||
            getFieldText(authorFirstField).isEmpty() ||
            getFieldText(authorLastField).isEmpty() ||
            getFieldText(stockField).isEmpty() ||
            getFieldText(isbnField).isEmpty()) {
            
            showErrorMessage("❌ Please fill all fields!");
            return false;
        }
        
        try {
            Integer.parseInt(getFieldText(stockField));
            Long.parseLong(getFieldText(isbnField));
        } catch (NumberFormatException e) {
            showErrorMessage("❌ Stock and ISBN must be valid numbers!");
            return false;
        }
        
        return true;
    }
    
    private boolean validateSubscriberForm() {
        if (getFieldText(subFirstField).isEmpty() ||
            getFieldText(subLastField).isEmpty() ||
            getFieldText(emailField).isEmpty()) {
            
            showErrorMessage("❌ Please fill all fields!");
            return false;
        }
        
        String email = getFieldText(emailField);
        if (!email.contains("@") || !email.contains(".")) {
            showErrorMessage("❌ Please enter a valid email address!");
            return false;
        }
        
        return true;
    }
    
    // Utility methods
    private String getFieldText(JTextField field) {
        String text = field.getText().trim();
        // Check if it's placeholder text
        if (text.endsWith("...") || field.getForeground().equals(Color.GRAY)) {
            return "";
        }
        return text;
    }
    
    private void clearCurrentForm() {
        String currentCard = getCurrentCardName();
        switch (currentCard) {
            case "books":
                clearBookForm();
                break;
            case "subscribers":
                clearSubscriberForm();
                break;
        }
    }
    
    private String getCurrentCardName() {
        // Simple way to track current card - could be improved
        if (booksBtn.getBackground().equals(PRIMARY_COLOR)) return "books";
        if (subscribersBtn.getBackground().equals(PRIMARY_COLOR)) return "subscribers";
        return "dashboard";
    }
    
    private void clearBookForm() {
        resetTextField(titleField, "Enter book title...");
        resetTextField(authorFirstField, "Author first name...");
        resetTextField(authorLastField, "Author last name...");
        resetTextField(stockField, "Stock quantity...");
        resetTextField(isbnField, "ISBN (14 digits)...");
    }
    
    private void clearSubscriberForm() {
        resetTextField(subFirstField, "First name...");
        resetTextField(subLastField, "Last name...");
        resetTextField(emailField, "Email address...");
    }
    
    private void resetTextField(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
    }
    
    // Data loading methods
    private void loadAllData() {
        loadBooksData();
        loadSubscribersData();
        loadBorrowedData();
        updateStats();
    }
    
    private void loadBooksData() {
        booksTableModel.setRowCount(0);
        for (Book book : Book.getListBooks()) {
            String status = book.getStock() > 0 ? "✅ Available" : "❌ Out of Stock";
            Object[] row = {
                book.getTitle(),
                book.getFirstNameAuthor() + " " + book.getLastNameAuthor(),
                book.getIsbn(),
                book.getStock(),
                status
            };
            booksTableModel.addRow(row);
        }
    }
    
    private void loadSubscribersData() {
        subscribersTableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (Subscriber subscriber : Subscriber.getListSubscriber()) {
            Object[] row = {
                subscriber.getName(),
                subscriber.getSurname(),
                subscriber.getEmail(),
                subscriber.getCreationDate().format(formatter)
            };
            subscribersTableModel.addRow(row);
        }
    }
    
    private void loadBorrowedData() {
        borrowedTableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Book book : Book.getListBooksBorrowed()) {
            String status = "🟢 Active";
            if (book.getReturnDate() != null && book.getReturnDate().isBefore(java.time.LocalDate.now())) {
                status = "🔴 Overdue";
            }
            
            Object[] row = {
                book.getTitle(),
                book.getFirstNameAuthor() + " " + book.getLastNameAuthor(),
                book.getIsbn(),
                book.getBorrowDateIn() != null ? book.getBorrowDateIn().format(formatter) : "N/A",
                book.getReturnDate() != null ? book.getReturnDate().format(formatter) : "N/A",
                status
            };
            borrowedTableModel.addRow(row);
        }
    }
    
    private void updateStats() {
        int totalBooks = controller.getTotalBooks();
        int availableBooks = controller.getAvailableBooks();
        int borrowedBooks = controller.getBorrowedBooksCount();
        int totalSubscribers = controller.getTotalSubscribers();
        
        totalBooksLabel.setText(String.valueOf(totalBooks));
        availableBooksLabel.setText(String.valueOf(availableBooks));
        borrowedBooksLabel.setText(String.valueOf(borrowedBooks));
        totalSubscribersLabel.setText(String.valueOf(totalSubscribers));
    }
    
    // Message methods
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Main.init();
                new ModernLibraryInterface().setVisible(true);
            } catch (SaisieException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error initializing application: " + e.getMessage(), 
                    "Initialization Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}