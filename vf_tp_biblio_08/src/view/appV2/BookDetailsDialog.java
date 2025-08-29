package view.appV2;

import model.Book;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BookDetailsDialog extends JDialog {
    
    private Book book;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel isbnLabel;
    private JLabel stockLabel;
    private JLabel statusLabel;
    private JLabel borrowDateLabel;
    private JLabel returnDateLabel;
    
    public BookDetailsDialog(Frame parent, Book book) {
        super(parent, "Book Details", true);
        this.book = book;
        initializeDialog();
        createComponents();
        layoutComponents();
        setupEventHandlers();
        loadBookData();
    }
    
    private void initializeDialog() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }
    
    private void createComponents() {
        titleLabel = new JLabel();
        authorLabel = new JLabel();
        isbnLabel = new JLabel();
        stockLabel = new JLabel();
        statusLabel = new JLabel();
        borrowDateLabel = new JLabel();
        returnDateLabel = new JLabel();
        
        // Style labels
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font valueFont = new Font("Segoe UI", Font.BOLD, 14);
        
        titleLabel.setFont(valueFont);
        authorLabel.setFont(labelFont);
        isbnLabel.setFont(labelFont);
        stockLabel.setFont(labelFont);
        statusLabel.setFont(valueFont);
        borrowDateLabel.setFont(labelFont);
        returnDateLabel.setFont(labelFont);
    }
    
    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.black);
        
        // Create info panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        infoPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(titleLabel, gbc);
        
        // Author
        gbc.gridx = 0; gbc.gridy = 1;
        infoPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(authorLabel, gbc);
        
        // ISBN
        gbc.gridx = 0; gbc.gridy = 2;
        infoPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(isbnLabel, gbc);
        
        // Stock
        gbc.gridx = 0; gbc.gridy = 3;
        infoPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(stockLabel, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 4;
        infoPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(statusLabel, gbc);
        
        // Borrow Date
        gbc.gridx = 0; gbc.gridy = 5;
        infoPanel.add(new JLabel("Borrow Date:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(borrowDateLabel, gbc);
        
        // Return Date
        gbc.gridx = 0; gbc.gridy = 6;
        infoPanel.add(new JLabel("Return Date:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(returnDateLabel, gbc);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        closeButton.setBackground(new Color(52, 73, 94));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setPreferredSize(new Dimension(100, 30));
        
        buttonPanel.add(closeButton);
        
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void setupEventHandlers() {
        JButton closeButton = findButtonInContainer(getContentPane(), "Close");
        if (closeButton != null) {
            closeButton.addActionListener(e -> dispose());
        }
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
    
    private void loadBookData() {
        if (book != null) {
            titleLabel.setText(book.getTitle());
            authorLabel.setText(book.getFirstNameAuthor() + " " + book.getLastNameAuthor());
            isbnLabel.setText(String.valueOf(book.getIsbn()));
            stockLabel.setText(String.valueOf(book.getStock()));
            
            // Set status with color
            if (book.getStock() > 0) {
                statusLabel.setText("Available");
                statusLabel.setForeground(new Color(46, 204, 113));
            } else {
                statusLabel.setText("Out of Stock");
                statusLabel.setForeground(new Color(231, 76, 60));
            }
            
            // Borrow and return dates
            if (book.getBorrowDateIn() != null) {
                borrowDateLabel.setText(book.getBorrowDateIn().toString());
            } else {
                borrowDateLabel.setText("Not borrowed");
            }
            
            if (book.getReturnDate() != null) {
                returnDateLabel.setText(book.getReturnDate().toString());
            } else {
                returnDateLabel.setText("N/A");
            }
        }
    }
}