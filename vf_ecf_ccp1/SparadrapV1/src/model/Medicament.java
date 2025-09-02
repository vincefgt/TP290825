package model;

import controler.PharmacieController;
import controler.Regex;

import java.time.LocalDate;

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
        return nameMed;
    }
    public void setNameMed(String nameMed) {
        if (nameMed == null || nameMed.trim().isEmpty()) {
            throw new IllegalArgumentException("Name required");
        }
        this.nameMed = nameMed.trim();
    }

    public catMed getCat() {
        return cat;
    }
    public void setCat(catMed cat) {
        if (Regex.testNotEmpty(String.valueOf(cat))) {
            throw new IllegalArgumentException("Category required");
        }
        this.cat = cat;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        Regex.setParamRegex("^\\d+(\\.\\d+)?$");
        if (price < 0|| Regex.testDigitDec(price)) {
            throw new IllegalArgumentException("price > 0 required");
        }
        this.price = price;
    }

    public LocalDate getDatOnMarket() {
        return datOnMarket;
    }
    public void setDatOnMarket(String datOnMarket) {
        this.datOnMarket = LocalDate.parse(datOnMarket);
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        Regex.setParamRegex("^\\d+$");
        if (stock < 0||Regex.testDigit(stock)) {
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
        return nameMed + " (" + cat + ") - Prix: " + price + "€ - Stock: " + stock;
    }
}
