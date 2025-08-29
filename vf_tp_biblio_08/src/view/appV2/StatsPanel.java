package view.appV2;

import model.Book;
import model.Subscriber;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatsPanel extends JPanel {
    
    private JLabel totalBooksLabel;
    private JLabel availableBooksLabel;
    private JLabel borrowedBooksLabel;
    private JLabel totalSubscribersLabel;
    private JLabel totalAuthorsLabel;
    
    public StatsPanel() {
        initializePanel();
        createComponents();
        layoutComponents();
        updateStats();
    }
    
    private void initializePanel() {
        setLayout(new GridLayout(1, 5, 10, 0));
        setBorder(new EmptyBorder(15, 20, 15, 20));
        setBackground(new Color(248, 249, 250));
    }
    
    private void createComponents() {
        totalBooksLabel = new JLabel();
        availableBooksLabel = new JLabel();
        borrowedBooksLabel = new JLabel();
        totalSubscribersLabel = new JLabel();
        totalAuthorsLabel = new JLabel();
    }
    
    private void layoutComponents() {
        add(createStatCard("Total Books", totalBooksLabel, new Color(52, 152, 219)));
        add(createStatCard("Available", availableBooksLabel, new Color(46, 204, 113)));
        add(createStatCard("Borrowed", borrowedBooksLabel, new Color(241, 196, 15)));
        add(createStatCard("Subscribers", totalSubscribersLabel, new Color(155, 89, 182)));
        add(createStatCard("Authors", totalAuthorsLabel, new Color(230, 126, 34)));
    }
    
    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(color);
        
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(new Color(52, 73, 94));
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
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
        
        // Count unique authors
        int totalAuthors = (int) Book.getListBooks().stream()
            .map(book -> book.getFirstNameAuthor() + " " + book.getLastNameAuthor())
            .distinct()
            .count();
        
        // Update labels
        totalBooksLabel.setText(String.valueOf(totalBooks));
        availableBooksLabel.setText(String.valueOf(availableBooks));
        borrowedBooksLabel.setText(String.valueOf(borrowedBooks));
        totalSubscribersLabel.setText(String.valueOf(totalSubscribers));
        totalAuthorsLabel.setText(String.valueOf(totalAuthors));
    }
}