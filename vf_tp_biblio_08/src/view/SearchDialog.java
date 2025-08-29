package view;

import controler.Main;
import model.Book;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchDialog extends JDialog {
    
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    private JTable resultsTable;
    private DefaultTableModel resultsTableModel;
    private JButton searchButton;
    private JButton closeButton;
    
    public SearchDialog(Frame parent) {
        super(parent, "Search Books", true);
        initializeDialog();
        createComponents();
        layoutComponents();
        setupEventHandlers();
    }
    
    private void initializeDialog() {
        setSize(600, 400);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
    
    private void createComponents() {
        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        searchTypeCombo = new JComboBox<>(new String[]{"Title", "Author", "ISBN"});
        searchTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Create results table
        String[] columns = {"Title", "Author", "ISBN", "Stock", "Status"};
        resultsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        resultsTable = new JTable(resultsTableModel);
        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        resultsTable.setRowHeight(25);
        resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        resultsTable.getTableHeader().setBackground(new Color(52, 73, 94));
        resultsTable.getTableHeader().setForeground(Color.WHITE);
        
        searchButton = createStyledButton("Search", new Color(52, 152, 219));
        closeButton = createStyledButton("Close", new Color(149, 165, 166));
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        searchField.setPreferredSize(new Dimension(200, 30));
        searchTypeCombo.setPreferredSize(new Dimension(100, 30));
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("by"));
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchButton);
        
        // Create results panel
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94)), 
            "Search Results",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(52, 73, 94)
        ));
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(closeButton);
        
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void setupEventHandlers() {
        searchButton.addActionListener(e -> performSearch());
        closeButton.addActionListener(e -> dispose());
        
        // Allow Enter key to trigger search
        searchField.addActionListener(e -> performSearch());
    }
    
    private void performSearch() {
        String searchText = searchField.getText().trim();
        String searchType = (String) searchTypeCombo.getSelectedItem();
        
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter search text!", "Search Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Clear previous results
        resultsTableModel.setRowCount(0);
        
        // Perform search based on type
        switch (searchType) {
            case "Title":
                searchByTitle(searchText);
                break;
            case "Author":
                searchByAuthor(searchText);
                break;
            case "ISBN":
                searchByISBN(searchText);
                break;
        }
        
        if (resultsTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No books found matching your search criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void searchByTitle(String title) {
        List<Book> results = Main.SearchTitle(title);
        for (Book book : results) {
            addBookToResults(book);
        }
    }
    
    private void searchByAuthor(String authorName) {
        for (Book book : Book.getListBooks()) {
            String fullAuthorName = book.getFirstNameAuthor() + " " + book.getLastNameAuthor();
            if (fullAuthorName.toLowerCase().contains(authorName.toLowerCase())) {
                addBookToResults(book);
            }
        }
    }
    
    private void searchByISBN(String isbn) {
        try {
            long isbnLong = Long.parseLong(isbn);
            Book book = Main.SearchIsbn(isbnLong);
            if (book != null) {
                addBookToResults(book);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid ISBN number!", "Invalid ISBN", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addBookToResults(Book book) {
        String status = book.getStock() > 0 ? "Available" : "Out of Stock";
        Object[] row = {
            book.getTitle(),
            book.getFirstNameAuthor() + " " + book.getLastNameAuthor(),
            book.getIsbn(),
            book.getStock(),
            status
        };
        resultsTableModel.addRow(row);
    }
}