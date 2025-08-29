package training.afpa.cda24060.geometry;

public class Point {
    private double abscisse;
    private double ordonnee;

    public Point (double abscisse, double ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }



    @Override
    public String toString() {
        return "(" + abscisse + ", " + ordonnee + ")";
    }

}
