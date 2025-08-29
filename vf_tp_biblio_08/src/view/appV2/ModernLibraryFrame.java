package view.appV2;

import controler.Main;
import exception.SaisieException;
import model.Book;
import model.Subscriber;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ModernLibraryFrame extends JFrame {
    
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private StatsPanel statsPanel;
    
    // Tables
    private JTable booksTable;
    private JTable subscribersTable;
    private JTable borrowedBooksTable;
    private DefaultTableModel booksTableModel;
    private DefaultTableModel subscribersTableModel;
    private DefaultTableModel borrowedBooksTableModel;
    
    // Form fields
    private JTextField titleField, authorFirstField, authorLastField, stockField, isbnField;
    private JTextField subFirstField, subLastField, emailField;
    
    public ModernLibraryFrame() {
        initializeFrame();
        createComponents();
        layoutComponents();
        setupEventHandlers();
        loadAllData();
    }
    
    private void initializeFrame() {
        setTitle("📚 Library Management System - BookRent Pro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1200, 700));
        
        // Modern styling
        getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(52, 73, 94)));
    }
    
    private void createComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        
        // Create stats panel
        statsPanel = new StatsPanel();
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        
        createTables();
        createFormFields();
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
        borrowedBooksTableModel = new DefaultTableModel(borrowColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        borrowedBooksTable = createStyledTable(borrowedBooksTableModel);
    }
    
    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(52, 152, 219, 50));
        table.setSelectionForeground(new Color(52, 73, 94));
        
        return table;
    }
    
    private void createFormFields() {
        // Book form fields
        titleField = createStyledTextField();
        authorFirstField = createStyledTextField();
        authorLastField = createStyledTextField();
        stockField = createStyledTextField();
        isbnField = createStyledTextField();
        
        // Subscriber form fields
        subFirstField = createStyledTextField();
        subLastField = createStyledTextField();
        emailField = createStyledTextField();
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setPreferredSize(new Dimension(200, 35));
        return field;
    }
    
    private void layoutComponents() {
        // Create header
        JPanel headerPanel = createHeaderPanel();
        
        // Create tabs
        tabbedPane.addTab("📚 Books Management", createBooksTab());
        tabbedPane.addTab("👥 Subscribers", createSubscribersTab());
        tabbedPane.addTab("📖 Borrowed Books", createBorrowedTab());
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(statsPanel, BorderLayout.CENTER);
        mainPanel.add(tabbedPane, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        JLabel titleLabel = new JLabel("📚 Library Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Professional Book Rental Management - v2.0");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        // Add search button in header
        JButton globalSearchButton = createStyledButton("🔍 Advanced Search", Color.black);
        globalSearchButton.addActionListener(e -> openSearchDialog());
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(globalSearchButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createBooksTab() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(248, 249, 250));
        
        // Table with scroll
        JScrollPane scrollPane = new JScrollPane(booksTable);
        scrollPane.setBorder(createModernBorder("Books Collection"));
        scrollPane.setPreferredSize(new Dimension(0, 300));
        
        // Form panel
        JPanel formPanel = createBookForm();
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createBookForm() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(createModernBorder("Add New Book"));
        formPanel.setBackground(Color.WHITE);
        
        // Input fields panel
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(createLabel("Title:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(titleField, gbc);
        
        gbc.gridx = 2;
        fieldsPanel.add(createLabel("Author First Name:"), gbc);
        gbc.gridx = 3;
        fieldsPanel.add(authorFirstField, gbc);
        
        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        fieldsPanel.add(createLabel("Author Last Name:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(authorLastField, gbc);
        
        gbc.gridx = 2;
        fieldsPanel.add(createLabel("Stock:"), gbc);
        gbc.gridx = 3;
        stockField.setPreferredSize(new Dimension(100, 35));
        fieldsPanel.add(stockField, gbc);
        
        // Row 3
        gbc.gridx = 0; gbc.gridy = 2;
        fieldsPanel.add(createLabel("ISBN (14 digits):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        isbnField.setPreferredSize(new Dimension(300, 35));
        fieldsPanel.add(isbnField, gbc);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(new EmptyBorder(0, 15, 15, 15));
        
        JButton addButton = createStyledButton("➕ Add Book", new Color(46, 204, 113));
        JButton clearButton = createStyledButton("🗑️ Clear Form", new Color(149, 165, 166));
        
        addButton.addActionListener(e -> addNewBook());
        clearButton.addActionListener(e -> clearBookForm());
        
        buttonsPanel.add(clearButton);
        buttonsPanel.add(addButton);
        
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return formPanel;
    }
    
    private JPanel createSubscribersTab() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(248, 249, 250));
        
        // Table
        JScrollPane scrollPane = new JScrollPane(subscribersTable);
        scrollPane.setBorder(createModernBorder("Registered Subscribers"));
        scrollPane.setPreferredSize(new Dimension(0, 350));
        
        // Form
        JPanel formPanel = createSubscriberForm();
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSubscriberForm() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(createModernBorder("Register New Subscriber"));
        formPanel.setBackground(Color.WHITE);
        
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // First Name
        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(createLabel("First Name:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(subFirstField, gbc);
        
        // Last Name
        gbc.gridx = 2;
        fieldsPanel.add(createLabel("Last Name:"), gbc);
        gbc.gridx = 3;
        fieldsPanel.add(subLastField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        fieldsPanel.add(createLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        emailField.setPreferredSize(new Dimension(300, 35));
        fieldsPanel.add(emailField, gbc);
        
        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(new EmptyBorder(0, 15, 15, 15));
        
        JButton addButton = createStyledButton("➕ Add Subscriber", new Color(155, 89, 182));
        JButton clearButton = createStyledButton("🗑️ Clear Form", new Color(149, 165, 166));
        
        addButton.addActionListener(e -> addNewSubscriber());
        clearButton.addActionListener(e -> clearSubscriberForm());
        
        buttonsPanel.add(clearButton);
        buttonsPanel.add(addButton);
        
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return formPanel;
    }
    
    private JPanel createBorrowedTab() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(248, 249, 250));
        
        JScrollPane scrollPane = new JScrollPane(borrowedBooksTable);
        scrollPane.setBorder(createModernBorder("Currently Borrowed Books"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(248, 249, 250));
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        JButton returnButton = createStyledButton("📤 Return Selected", new Color(231, 76, 60));
        JButton refreshButton = createStyledButton("🔄 Refresh", new Color(52, 152, 219));
        
        returnButton.addActionListener(e -> returnSelectedBook());
        refreshButton.addActionListener(e -> loadAllData());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(returnButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(52, 73, 94));
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
                BorderFactory.createLineBorder(new Color(52, 73, 94), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ),
            title,
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 14), 
            new Color(52, 73, 94)
        );
    }
    
    private void setupEventHandlers() {
        // Double-click on books table to show details
        booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showBookDetails();
                }
            }
        });
        
        // Double-click on books table to borrow
        booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && SwingUtilities.isRightMouseButton(e)) {
                    showBorrowContextMenu(e);
                }
            }
        });
    }
    
    private void showBookDetails() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow != -1) {
            String isbn = booksTableModel.getValueAt(selectedRow, 2).toString();
            Book book = Main.SearchIsbn(Long.parseLong(isbn));
            if (book != null) {
                BookDetailsDialog dialog = new BookDetailsDialog(this, book);
                dialog.setVisible(true);
            }
        }
    }
    
    private void showBorrowContextMenu(MouseEvent e) {
        JPopupMenu contextMenu = new JPopupMenu();
        
        JMenuItem borrowItem = new JMenuItem("📖 Borrow this book");
        JMenuItem detailsItem = new JMenuItem("ℹ️ View details");
        
        borrowItem.addActionListener(event -> borrowSelectedBook());
        detailsItem.addActionListener(event -> showBookDetails());
        
        contextMenu.add(borrowItem);
        contextMenu.add(detailsItem);
        
        contextMenu.show(booksTable, e.getX(), e.getY());
    }
    
    private void addNewBook() {
        try {
            if (!validateBookForm()) return;
            
            String title = titleField.getText().trim();
            String firstName = authorFirstField.getText().trim();
            String lastName = authorLastField.getText().trim();
            int stock = Integer.parseInt(stockField.getText().trim());
            long isbn = Long.parseLong(isbnField.getText().trim());
            
            new Book(firstName, lastName, title, stock, isbn, null, null);
            
            clearBookForm();
            loadAllData();
            showSuccessMessage("📚 Book added successfully!");
            
        } catch (NumberFormatException e) {
            showErrorMessage("❌ Please enter valid numbers for Stock and ISBN!");
        } catch (SaisieException e) {
            showErrorMessage("❌ Error: " + e.getMessage());
        }
    }
    
    private void addNewSubscriber() {
        try {
            if (!validateSubscriberForm()) return;
            
            String firstName = subFirstField.getText().trim();
            String lastName = subLastField.getText().trim();
            String email = emailField.getText().trim();
            
            new Subscriber(firstName, lastName, LocalDateTime.now(), email);
            
            clearSubscriberForm();
            loadAllData();
            showSuccessMessage("👥 Subscriber registered successfully!");
            
        } catch (Exception e) {
            showErrorMessage("❌ Error: " + e.getMessage());
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
            
            Main.newBorrowingBook(isbn);
            loadAllData();
            showSuccessMessage("📖 Book borrowed successfully!");
            
        } catch (Exception e) {
            showErrorMessage("❌ Error borrowing book: " + e.getMessage());
        }
    }
    
    private void returnSelectedBook() {
        int selectedRow = borrowedBooksTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("❌ Please select a borrowed book to return!");
            return;
        }
        
        try {
            String isbnText = borrowedBooksTableModel.getValueAt(selectedRow, 2).toString();
            long isbn = Long.parseLong(isbnText);
            
            Main.returnBook(isbn);
            loadAllData();
            showSuccessMessage("📤 Book returned successfully!");
            
        } catch (Exception e) {
            showErrorMessage("❌ Error returning book: " + e.getMessage());
        }
    }
    
    private void openSearchDialog() {
        SearchDialog searchDialog = new SearchDialog(this);
        searchDialog.setVisible(true);
    }
    
    private boolean validateBookForm() {
        if (titleField.getText().trim().isEmpty() ||
            authorFirstField.getText().trim().isEmpty() ||
            authorLastField.getText().trim().isEmpty() ||
            stockField.getText().trim().isEmpty() ||
            isbnField.getText().trim().isEmpty()) {
            
            showErrorMessage("❌ Please fill all fields!");
            return false;
        }
        
        try {
            Integer.parseInt(stockField.getText().trim());
            Long.parseLong(isbnField.getText().trim());
        } catch (NumberFormatException e) {
            showErrorMessage("❌ Stock and ISBN must be valid numbers!");
            return false;
        }
        
        return true;
    }
    
    private boolean validateSubscriberForm() {
        if (subFirstField.getText().trim().isEmpty() ||
            subLastField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty()) {
            
            showErrorMessage("❌ Please fill all fields!");
            return false;
        }
        
        String email = emailField.getText().trim();
        if (!email.contains("@") || !email.contains(".")) {
            showErrorMessage("❌ Please enter a valid email address!");
            return false;
        }
        
        return true;
    }
    
    private void clearBookForm() {
        titleField.setText("");
        authorFirstField.setText("");
        authorLastField.setText("");
        stockField.setText("");
        isbnField.setText("");
    }
    
    private void clearSubscriberForm() {
        subFirstField.setText("");
        subLastField.setText("");
        emailField.setText("");
    }
    
    private void loadAllData() {
        loadBooksData();
        loadSubscribersData();
        loadBorrowedBooksData();
        statsPanel.updateStats();
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
    
    private void loadBorrowedBooksData() {
        borrowedBooksTableModel.setRowCount(0);
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
            borrowedBooksTableModel.addRow(row);
        }
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Main.init();
                new ModernLibraryFrame().setVisible(true);
            } catch (SaisieException e) {
                e.printStackTrace();
            }
        });
    }
}