package view;

import model.Book;
import model.Subscriber;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatsCardPanel extends JPanel {
    
    private JLabel totalBooksLabel;
    private JLabel availableBooksLabel;
    private JLabel borrowedBooksLabel;
    private JLabel totalSubscribersLabel;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color WARNING_COLOR = new Color(243, 156, 18);
    private final Color INFO_COLOR = new Color(155, 89, 182);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(52, 73, 94);
    
    public StatsCardPanel() {
        initializePanel();
        createStatCards();
        updateStats();
    }
    
    private void initializePanel() {
        setLayout(new GridLayout(1, 4, 20, 0));
        setBackground(new Color(248, 249, 250));
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }
    
    private void createStatCards() {
        // Create individual stat cards
        JPanel totalBooksCard = createStatCard(
            "📚", "Total Books", "0", PRIMARY_COLOR);
        JPanel availableCard = createStatCard(
            "✅", "Available", "0", SUCCESS_COLOR);
        JPanel borrowedCard = createStatCard(
            "📖", "Borrowed", "0", WARNING_COLOR);
        JPanel subscribersCard = createStatCard(
            "👥", "Subscribers", "0", INFO_COLOR);
        
        // Extract labels for updating
        totalBooksLabel = extractValueLabel(totalBooksCard);
        availableBooksLabel = extractValueLabel(availableCard);
        borrowedBooksLabel = extractValueLabel(borrowedCard);
        totalSubscribersLabel = extractValueLabel(subscribersCard);
        
        // Add cards to panel
        add(totalBooksCard);
        add(availableCard);
        add(borrowedCard);
        add(subscribersCard);
    }
    
    private JPanel createStatCard(String icon, String title, String value, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(25, 25, 25, 25)
        ));
        card.setPreferredSize(new Dimension(200, 140));
        
        // Create hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(250, 250, 250));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(accentColor, 2),
                    new EmptyBorder(24, 24, 24, 24)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(CARD_COLOR);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                    new EmptyBorder(25, 25, 25, 25)
                ));
            }
        });
        
        // Header with icon and title
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBackground(CARD_COLOR);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        
        headerPanel.add(iconLabel);
        headerPanel.add(titleLabel);
        
        // Value panel
        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        valuePanel.setBackground(CARD_COLOR);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valueLabel.setForeground(accentColor);
        
        valuePanel.add(valueLabel);
        
        // Accent line at bottom
        JPanel accentLine = new JPanel();
        accentLine.setBackground(accentColor);
        accentLine.setPreferredSize(new Dimension(0, 4));
        
        card.add(headerPanel, BorderLayout.NORTH);
        card.add(valuePanel, BorderLayout.CENTER);
        card.add(accentLine, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JLabel extractValueLabel(JPanel card) {
        // Navigate through the card structure to find the value label
        JPanel valuePanel = (JPanel) card.getComponent(1); // CENTER component
        return (JLabel) valuePanel.getComponent(0);
    }
    
    public void updateStats() {
        // Calculate statistics
        int totalBooks = Book.getListBooks().size();
        int availableBooks = 0;
        int borrowedBooks = Book.getListBooksBorrowed().size();
        int totalSubscribers = Subscriber.getListSubscriber().size();
        
        // Count available books
        for (Book book : Book.getListBooks()) {
            if (book.getStock() > 0) {
                availableBooks++;
            }
        }
        
        // Update labels
        if (totalBooksLabel != null) totalBooksLabel.setText(String.valueOf(totalBooks));
        if (availableBooksLabel != null) availableBooksLabel.setText(String.valueOf(availableBooks));
        if (borrowedBooksLabel != null) borrowedBooksLabel.setText(String.valueOf(borrowedBooks));
        if (totalSubscribersLabel != null) totalSubscribersLabel.setText(String.valueOf(totalSubscribers));
        
        // Refresh the display
        repaint();
    }
}