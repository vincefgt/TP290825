package training.afpa.cda24060.geometry;


public class TestCircle {
    public static void testCircle(double radius, double abscisse, double ordonnee, double abscissept, double ordonneept) {
        Point centerCircle = new Point(abscisse, ordonnee);
        Point p1 = new Point(abscissept, ordonneept);
        double p1x = abscissept;
        double p1y = ordonneept;
        //Circle c1 = new Circle(radius,p1x,p1y);
        Circle c1 = new Circle(radius, centerCircle);
        double c1x = abscisse;
        double c1y = ordonnee;
        //Circle c1 = new Circle(radius,new Point(abscisse,ordonnee));
        c1.getCircleResult();
    //}


        // test point in circle
        //private void testAppartenance(double p1x, double p1y, double c1x,double  c1y, double radius) {
            double d = Math.sqrt(Math.pow((p1x-c1x),2)+Math.pow((p1y-c1y),2));
            if (d == radius){
                c1.setTouched(true);
                System.out.println("Point TOUCH Circle");
            } else {
                System.out.println("Point NOT TOUCH Circle, try again!!");
            }
    }
}


