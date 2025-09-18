package model;

import controller.PharmacieController;
import controller.Regex;

import java.time.LocalDate;

/**
 * Represents a pharmaceutical medication in the Sparadrap pharmacy system.
 * 
 * <p>This class manages medication information including name, category, pricing,
 * stock levels, and market introduction date. It provides essential functionality
 * for inventory management and sales processing.</p>
 * 
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Medication categorization using {@link catMed} enum</li>
 *   <li>Stock level management with validation</li>
 *   <li>Price formatting to 2 decimal places</li>
 *   <li>Market date tracking</li>
 *   <li>Automatic registration in global medication list</li>
 * </ul>
 * 
 * <h2>Stock Management:</h2>
 * <p>The medication class provides robust stock management:</p>
 * <ul>
 *   <li><strong>Stock Validation</strong>: Prevents negative stock levels</li>
 *   <li><strong>Quantity Reduction</strong>: Safe stock reduction during sales</li>
 *   <li><strong>Availability Check</strong>: Validates stock before purchase</li>
 * </ul>
 * 
 * <h2>Validation Rules:</h2>
 * <ul>
 *   <li>Name cannot be null or empty</li>
 *   <li>Category must be a valid {@link catMed} value</li>
 *   <li>Price must be non-negative</li>
 *   <li>Stock must be non-negative integer</li>
 *   <li>Market date must be valid</li>
 * </ul>
 * 
 * <h2>Categories:</h2>
 * <p>Medications are classified using the {@link catMed} enumeration which includes:</p>
 * <ul>
 *   <li>ANTALGIQUE - Pain relievers</li>
 *   <li>ANTIBIOTIQUE - Antibiotics</li>
 *   <li>VITAMINE - Vitamins and supplements</li>
 *   <li>And many more medical categories</li>
 * </ul>
 * 
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Create a new medication
 * Medicament doliprane = new Medicament(
 *     "Doliprane 1000mg",
 *     catMed.ANTALGIQUE,
 *     5.20,
 *     "2023-01-01",
 *     50
 * );
 * 
 * // Reduce stock during sale
 * boolean success = doliprane.reduireQuantite(2);
 * if (success) {
 *     System.out.println("Stock reduced successfully");
 * } else {
 *     System.out.println("Insufficient stock");
 * }
 * }</pre>
 * 
 * @author Sparadrap Development Team
 * @version 1.0
 * @since 2025-01-15
 * @see catMed
 * @see Ordonnance
 * @see Achat
 */
public class Medicament {
    private String nameMed;
    private catMed cat;
    private double price;
    private LocalDate datOnMarket;
    private int stock;

    // Constructeur
    public Medicament(String nameMed, catMed cat, double price, String datOnMarket, int stock) {
        this.setNameMed(nameMed);
        this.setCat(cat);
        this.setPrice(price);
        this.setDatOnMarket(datOnMarket);
        this.setStock(stock);
        PharmacieController.getListMed().add(this);
    }

    // Getters et Setters
    public String getNameMed() {
        return this.nameMed;
    }
    public void setNameMed(String nameMed) {
        if (nameMed == null || nameMed.trim().isEmpty()) {
            throw new IllegalArgumentException("Name required");
        }
        this.nameMed = nameMed.trim();
    }

    public catMed getCat() {
        return this.cat;
    }
    public void setCat(catMed cat) {
        if (Regex.testNotEmpty(String.valueOf(cat))) {
            throw new IllegalArgumentException("Category required");
        }
        this.cat = cat;
    }

    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        Regex.setParamRegex("^\\d+(\\.\\d+)?$");
        if (price < 0|| Regex.testDigitDec(price)) {
            throw new IllegalArgumentException("price > 0 required");
        }
        this.price = PharmacieController.formatTwoDec(price);
    }

    public LocalDate getDatOnMarket() {
        return this.datOnMarket;
    }
    public void setDatOnMarket(String datOnMarket) {
        this.datOnMarket = LocalDate.parse(datOnMarket);
    }

    public int getStock() {
        return this.stock;
    }
    public void setStock(int stock) {
        Regex.setParamRegex("^\\d+$");
        if (stock < 0||Regex.testDigitLong(stock)) {
            throw new IllegalArgumentException("quantity > 0 required");
        }
        this.stock = stock;
    }

    // Méthode pour réduire la quantité (lors d'une vente)
    public boolean reduireQuantite(int quantiteVendue) {
        if (quantiteVendue < 0) {
            throw new IllegalArgumentException("La quantité vendue ne peut pas être négative");
        }
        if (this.stock >= quantiteVendue) {
            this.stock -= quantiteVendue;
            return true;
        }
        return false; // Stock insuffisant
    }


    @Override
    public String toString() {
        return getNameMed()+" ("+getCat()+") - Prix: "+getPrice()+"€ - Stock: "+getStock();
    }
}
