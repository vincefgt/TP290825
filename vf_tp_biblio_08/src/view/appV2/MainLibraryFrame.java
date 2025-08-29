package view.appV2;

import controler.Main;
import exception.SaisieException;
import model.Book;
import model.Subscriber;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainLibraryFrame extends JFrame {
    
    // UI Components
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JTable booksTable;
    private JTable subscribersTable;
    private JTable borrowedBooksTable;
    private DefaultTableModel booksTableModel;
    private DefaultTableModel subscribersTableModel;
    private DefaultTableModel borrowedBooksTableModel;
    
    // Book form components
    private JTextField titleField;
    private JTextField authorFirstNameField;
    private JTextField authorLastNameField;
    private JTextField stockField;
    private JTextField isbnField;
    
    // Subscriber form components
    private JTextField subFirstNameField;
    private JTextField subLastNameField;
    private JTextField emailField;
    
    // Search components
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    
    public MainLibraryFrame() {
        initializeFrame();
        createComponents();
        layoutComponents();
        setupEventHandlers();
        loadData();
    }
    
    private void initializeFrame() {
        setTitle("Library Management System - BookRent v2.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
        
        // Set modern look and feel
       /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    
    private void createComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        
        // Create tabbed pane with modern styling
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        
        // Create tables
        createBooksTable();
        createSubscribersTable();
        createBorrowedBooksTable();
        
        // Create form components
        createFormComponents();
    }
    
    private void createBooksTable() {
        String[] columns = {"Title", "Author", "ISBN", "Stock", "Available"};
        booksTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        booksTable = new JTable(booksTableModel);
        booksTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        booksTable.setRowHeight(25);
        booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        booksTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        booksTable.getTableHeader().setBackground(new Color(52, 73, 94));
        booksTable.getTableHeader().setForeground(Color.WHITE);
        booksTable.setGridColor(new Color(220, 220, 220));
    }
    
    private void createSubscribersTable() {
        String[] columns = {"First Name", "Last Name", "Email", "Registration Date"};
        subscribersTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        subscribersTable = new JTable(subscribersTableModel);
        subscribersTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subscribersTable.setRowHeight(25);
        subscribersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subscribersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        subscribersTable.getTableHeader().setBackground(new Color(52, 73, 94));
        subscribersTable.getTableHeader().setForeground(Color.WHITE);
        subscribersTable.setGridColor(new Color(220, 220, 220));
    }
    
    private void createBorrowedBooksTable() {
        String[] columns = {"Title", "Author", "ISBN", "Borrow Date", "Return Date", "Status"};
        borrowedBooksTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        borrowedBooksTable = new JTable(borrowedBooksTableModel);
        borrowedBooksTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        borrowedBooksTable.setRowHeight(25);
        borrowedBooksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        borrowedBooksTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        borrowedBooksTable.getTableHeader().setBackground(new Color(52, 73, 94));
        borrowedBooksTable.getTableHeader().setForeground(Color.WHITE);
        borrowedBooksTable.setGridColor(new Color(220, 220, 220));
    }
    
    private void createFormComponents() {
        // Book form fields
        titleField = createStyledTextField();
        authorFirstNameField = createStyledTextField();
        authorLastNameField = createStyledTextField();
        stockField = createStyledTextField();
        isbnField = createStyledTextField();
        
        // Subscriber form fields
        subFirstNameField = createStyledTextField();
        subLastNameField = createStyledTextField();
        emailField = createStyledTextField();
        
        // Search components
        searchField = createStyledTextField();
        searchTypeCombo = new JComboBox<>(new String[]{"Title", "Author", "ISBN"});
        searchTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private void layoutComponents() {
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        
        // Create Books tab
        JPanel booksPanel = createBooksPanel();
        tabbedPane.addTab("📚 Books", booksPanel);
        
        // Create Subscribers tab
        JPanel subscribersPanel = createSubscribersPanel();
        tabbedPane.addTab("👥 Subscribers", subscribersPanel);
        
        // Create Borrowed Books tab
        JPanel borrowedPanel = createBorrowedBooksPanel();
        tabbedPane.addTab("📖 Borrowed Books", borrowedPanel);
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("BookRent v2.0 - Professional Edition");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(248, 249, 250));
        
        // Create search panel
        JPanel searchPanel = createSearchPanel();
        
        // Create table panel
        JScrollPane tableScrollPane = new JScrollPane(booksTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94)), 
            "Books Collection",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(52, 73, 94)
        ));
        
        // Create form panel
        JPanel formPanel = createBookFormPanel();
        
        // Create button panel
        JPanel buttonPanel = createBookButtonPanel();
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        searchField.setPreferredSize(new Dimension(200, 30));
        searchTypeCombo.setPreferredSize(new Dimension(100, 30));
        
        JButton searchButton = createStyledButton("Search", new Color(52, 152, 219));
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("by"));
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchButton);
        
        return searchPanel;
    }
    
    private JPanel createBookFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94)), 
            "Add New Book",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(52, 73, 94)
        ));
        formPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(titleField, gbc);
        
        // Author First Name
        gbc.gridx = 2; gbc.gridy = 0;
        formPanel.add(new JLabel("Author First Name:"), gbc);
        gbc.gridx = 3;
        authorFirstNameField.setPreferredSize(new Dimension(150, 30));
        formPanel.add(authorFirstNameField, gbc);
        
        // Author Last Name
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Author Last Name:"), gbc);
        gbc.gridx = 1;
        authorLastNameField.setPreferredSize(new Dimension(150, 30));
        formPanel.add(authorLastNameField, gbc);
        
        // Stock
        gbc.gridx = 2; gbc.gridy = 1;
        formPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 3;
        stockField.setPreferredSize(new Dimension(100, 30));
        formPanel.add(stockField, gbc);
        
        // ISBN
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        isbnField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(isbnField, gbc);
        
        return formPanel;
    }
    
    private JPanel createBookButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        JButton addButton = createStyledButton("Add Book", new Color(46, 204, 113));
        JButton borrowButton = createStyledButton("Borrow Selected", new Color(241, 196, 15));
        JButton returnButton = createStyledButton("Return Book", new Color(231, 76, 60));
        JButton refreshButton = createStyledButton("Refresh", new Color(155, 89, 182));
        
        buttonPanel.add(addButton);
        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(refreshButton);
        
        return buttonPanel;
    }
    
    private JPanel createSubscribersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(248, 249, 250));
        
        // Create table panel
        JScrollPane tableScrollPane = new JScrollPane(subscribersTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94)), 
            "Registered Subscribers",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(52, 73, 94)
        ));
        
        // Create form panel
        JPanel formPanel = createSubscriberFormPanel();
        
        // Create button panel
        JPanel buttonPanel = createSubscriberButtonPanel();
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSubscriberFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94)), 
            "Add New Subscriber",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(52, 73, 94)
        ));
        formPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // First Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        subFirstNameField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(subFirstNameField, gbc);
        
        // Last Name
        gbc.gridx = 2; gbc.gridy = 0;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 3;
        subLastNameField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(subLastNameField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        emailField.setPreferredSize(new Dimension(300, 30));
        formPanel.add(emailField, gbc);
        
        return formPanel;
    }
    
    private JPanel createSubscriberButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        JButton addButton = createStyledButton("Add Subscriber", new Color(46, 204, 113));
        JButton refreshButton = createStyledButton("Refresh", new Color(155, 89, 182));
        
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        
        return buttonPanel;
    }
    
    private JPanel createBorrowedBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(248, 249, 250));
        
        // Create table panel
        JScrollPane tableScrollPane = new JScrollPane(borrowedBooksTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94)), 
            "Currently Borrowed Books",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(52, 73, 94)
        ));
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        JButton returnButton = createStyledButton("Return Selected Book", new Color(231, 76, 60));
        JButton refreshButton = createStyledButton("Refresh", new Color(155, 89, 182));
        
        buttonPanel.add(returnButton);
        buttonPanel.add(refreshButton);
        
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }
    
    private void setupEventHandlers() {
        // Add Book button
        JButton addBookButton = findButtonByText("Add Book");
        if (addBookButton != null) {
            addBookButton.addActionListener(e -> addNewBook());
        }
        
        // Add Subscriber button
        JButton addSubButton = findButtonByText("Add Subscriber");
        if (addSubButton != null) {
            addSubButton.addActionListener(e -> addNewSubscriber());
        }
        
        // Borrow Book button
        JButton borrowButton = findButtonByText("Borrow Selected");
        if (borrowButton != null) {
            borrowButton.addActionListener(e -> borrowSelectedBook());
        }
        
        // Return Book button
        JButton returnButton = findButtonByText("Return Selected Book");
        if (returnButton != null) {
            returnButton.addActionListener(e -> returnSelectedBook());
        }
        
        // Refresh buttons
        setupRefreshButtons();
    }
    
    private JButton findButtonByText(String text) {
        return findButtonInContainer(mainPanel, text);
    }
    
    private JButton findButtonInContainer(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals(text)) {
                return (JButton) component;
            } else if (component instanceof Container) {
                JButton found = findButtonInContainer((Container) component, text);
                if (found != null) return found;
            }
        }
        return null;
    }
    
    private void setupRefreshButtons() {
        // Find all refresh buttons and add listeners
        addRefreshListeners(mainPanel);
    }
    
    private void addRefreshListeners(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals("Refresh")) {
                ((JButton) component).addActionListener(e -> loadData());
            } else if (component instanceof Container) {
                addRefreshListeners((Container) component);
            }
        }
    }
    
    private void addNewBook() {
        try {
            String title = titleField.getText().trim();
            String firstName = authorFirstNameField.getText().trim();
            String lastName = authorLastNameField.getText().trim();
            String stockText = stockField.getText().trim();
            String isbnText = isbnField.getText().trim();
            
            if (title.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || 
                stockText.isEmpty() || isbnText.isEmpty()) {
                showErrorMessage("Please fill all fields!");
                return;
            }
            
            int stock = Integer.parseInt(stockText);
            long isbn = Long.parseLong(isbnText);
            
            Book newBook = new Book(firstName, lastName, title, stock, isbn, null, null);
            
            clearBookForm();
            loadBooksData();
            showSuccessMessage("Book added successfully!");
            
        } catch (NumberFormatException e) {
            showErrorMessage("Please enter valid numbers for Stock and ISBN!");
        } catch (SaisieException e) {
            showErrorMessage("Error: " + e.getMessage());
        }
    }
    
    private void addNewSubscriber() {
        try {
            String firstName = subFirstNameField.getText().trim();
            String lastName = subLastNameField.getText().trim();
            String email = emailField.getText().trim();
            
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                showErrorMessage("Please fill all fields!");
                return;
            }
            
            Subscriber newSubscriber = new Subscriber(firstName, lastName, LocalDateTime.now(), email);
            
            clearSubscriberForm();
            loadSubscribersData();
            showSuccessMessage("Subscriber added successfully!");
            
        } catch (Exception e) {
            showErrorMessage("Error: " + e.getMessage());
        }
    }
    
    private void borrowSelectedBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("Please select a book to borrow!");
            return;
        }
        
        try {
            String isbnText = booksTableModel.getValueAt(selectedRow, 2).toString();
            long isbn = Long.parseLong(isbnText);
            
            Main.newBorrowingBook(isbn);
            loadData();
            showSuccessMessage("Book borrowed successfully!");
            
        } catch (Exception e) {
            showErrorMessage("Error borrowing book: " + e.getMessage());
        }
    }
    
    private void returnSelectedBook() {
        int selectedRow = borrowedBooksTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("Please select a borrowed book to return!");
            return;
        }
        
        try {
            String isbnText = borrowedBooksTableModel.getValueAt(selectedRow, 2).toString();
            long isbn = Long.parseLong(isbnText);
            
            Main.returnBook(isbn);
            loadData();
            showSuccessMessage("Book returned successfully!");
            
        } catch (Exception e) {
            showErrorMessage("Error returning book: " + e.getMessage());
        }
    }
    
    private void loadData() {
        loadBooksData();
        loadSubscribersData();
        loadBorrowedBooksData();
    }
    
    private void loadBooksData() {
        booksTableModel.setRowCount(0);
        for (Book book : Book.getListBooks()) {
            Object[] row = {
                book.getTitle(),
                book.getFirstNameAuthor() + " " + book.getLastNameAuthor(),
                book.getIsbn(),
                book.getStock(),
                book.getStock() > 0 ? "Yes" : "No"
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
            String status = "Active";
            if (book.getReturnDate() != null && book.getReturnDate().isBefore(java.time.LocalDate.now())) {
                status = "Overdue";
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
    
    private void clearBookForm() {
        titleField.setText("");
        authorFirstNameField.setText("");
        authorLastNameField.setText("");
        stockField.setText("");
        isbnField.setText("");
    }
    
    private void clearSubscriberForm() {
        subFirstNameField.setText("");
        subLastNameField.setText("");
        emailField.setText("");
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
                Main.init(); // Initialize data
                new MainLibraryFrame().setVisible(true);
            } catch (SaisieException e) {
                e.printStackTrace();
            }
        });
    }
}