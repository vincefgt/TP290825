package exo_poo;

import static exo_poo.Livre.getNbTotal;

public class TestLivre {
    public static void mainLivre() {
        Livre l1 = new Livre(0);
        //Livre l1 = new Livre("Pikachu");
        //Livre l1 = new Livre("Java", "Pikachu", 150);
        System.out.println(l1.toString()); //display state init
        l1.setNbPages(140); //modif p
        l1.setTitle("Java");
        l1.setAuteur("Pikachu");
        Livre l2 = new Livre("Afpa", "Malcom", 50,45);
        System.out.println(l2.toString()); // display state init
        l2.setNbPages(60); //modif p
        l1.setPrice(22);
        l2.setPrice(40);
        System.out.println(l1.toString());
        System.out.println(l2.toString());
        System.out.println("Pages Total: "+getNbTotal());
       // System.out.println("Pages Total: "+NbTotal);
    }
}
