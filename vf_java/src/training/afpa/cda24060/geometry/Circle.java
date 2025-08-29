package training.afpa.cda24060.geometry;

import java.text.DecimalFormat;

public class Circle {
    private double radius;
    private Point centerCircle;
    private static boolean touched = false;
    private double abscisse;
    private double ordonnee;

    public final double pi = 3.1416;

    //builder

    public Circle(double radius){
        this.radius = radius;
    }
    //saturation
    public Circle(double radius, Point centerCircle){
        this.radius = radius;
        this.centerCircle = centerCircle;
    }
    //saturation
    public Circle(Point centerCircle){
        this.radius = radius;
        this.centerCircle = centerCircle;
    }

    //saturation
    private Circle(double radius,double abscisse,double ordonnee){
        this.radius = radius;
        this.abscisse = abscisse; //*
        this.ordonnee = ordonnee; //*
    }

    //getter radius
    private double getRadius(){
        return this.radius;
    }

    private double getabscisse(){
        return this.abscisse;
    }

    //calcul perimeter
    private double perimeterCircle() {
        return (2 * this.getRadius()) * pi;
    }
    //calcul surface
    private double surfaceCircle() {
         return Math.pow((pi * this.getRadius()),2);

    }

    //getter result perimeter et surface
    public void getCircleResult() {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("Surface: "+df.format(surfaceCircle()));
        System.out.println("Perimeter: "+df.format(perimeterCircle()));
        System.out.println("CenterCircle: "+centerCircle.toString());
        //testAppartenance(abscisse,ordonnee,double c1x,double  c1y,getRadius());//System.out.println("test: "+(testAppartenance(double p1x, double p1y, double c1x, double c1y)));
        System.out.println();
    }

    public boolean setTouched(boolean ptouched) {
        return this.touched = ptouched;
    }

    /*
    // test point in circle
    private void testAppartenance(double p1x, double p1y, double c1x,double  c1y, double radius) {
        double d = Math.sqrt(Math.pow((p1x - c1x), 2) + Math.pow((p1y - c1y), 2));
        if (d == radius) {
            Circle.setTouched(true);
            System.out.println("Point TOUCH Circle");
        } else {
            System.out.println("Point NOT TOUCH Circle, try again!!");
        }
    }*/
}
