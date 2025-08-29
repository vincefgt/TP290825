package exo_poo;

public class Livre {
    private String title, auteur;
    private int nbPages;
    private static int nbTotal;
    private double price;
    private boolean priceFix = false;
    //public static int getNbTotal;

    public Livre(String title, String auteur, int nbPages, double price) {
        this.auteur = auteur;
        this.title = title;
        this.nbPages = nbPages;
        this.price = price;
        priceFix = true;
        nbTotal(nbPages);
    }

    public Livre(String title, String auteur, int nbPages) {
        this.auteur = auteur;
        this.title = title;
        this.nbPages = nbPages;
        nbTotal(nbPages);
    }
    public Livre(double price) {
        this.price = price;
    }
    public Livre(String auteur) {
        this.auteur = auteur;
    }

    public Livre() {
        ;
    }


    private int nbTotal(int nbPages) {
        return nbTotal = nbTotal + nbPages;
    }

    public static int getNbTotal() {
        return nbTotal;
    }

    public String getAuteur() {
        return this.auteur;
    }

    public void setTitle(String ptitle) {
        this.title = ptitle;
    }

    public void setNbPages(int pNpages) {
        // error if 0p
        if (pNpages >= 1) {
            //init nbTotal
            if (pNpages > nbTotal) {
                this.nbTotal = nbTotal + (pNpages - nbPages);
            } else {
                this.nbTotal = nbTotal - (nbPages - pNpages);
            }
            this.nbPages = pNpages; // init page livre
        } else {
            System.out.println("ERROR!");
        }
    }


    public String setAuteur(String pAuteur) {
        return this.auteur = pAuteur;
    }

    public void setPrice(int pPrice) {
        if (priceFix) {
            System.out.println("ERROR! prix: " + price);
        } else {
            this.price = pPrice;
            priceFix = true;
        }
    }

    public double getPrice() {
        return this.price;
    }


//    public static void mainLivre() {
//        Livre l1 = new Livre();
//        //Livre l1 = new Livre("Pikachu");
//        //Livre l1 = new Livre("Java", "Pikachu", 150);
//        System.out.println(l1.toString()); //display state init
//        l1.setNbPages(140); //modif p
//        l1.setTitle("Java");
//        l1.setAuteur("Pikachu");
//        Livre l2 = new Livre("Afpa", "Malcom", 50);
//        System.out.println(l2.toString()); // display state init
//        l2.setNbPages(60); //modif p
//        System.out.println(l1.toString());
//        System.out.println(l2.toString());
//        System.out.println("Pages Total: "+nbTotal);
//    }

    @Override
    public String toString() {
        if (title == null) {
            if (nbPages < 1) {
                if (auteur == null) {
                    if (price == 0.0) {
                        return "Livre:";
                    } else {
                        return "Livre: Prix: " + price;
                    }
                } else {
                    return "Auteur: " + auteur+", Prix: " + price;
                }
            } else {
                return "Auteur: " + auteur + ", Pages: " + nbPages+", Prix: " + price;
                }
            } else {
                return "Livre: " + title + ", Auteur: " + auteur + ", Pages: " + nbPages+", Prix: " + price;

        }
    }
}