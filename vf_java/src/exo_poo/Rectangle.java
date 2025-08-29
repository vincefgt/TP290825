package exo_poo;

public class Rectangle {
    //attribut
    private double length;
    private double width;
    private double surfaceResult;
    //builder
    public Rectangle (double plength,double pwidth) {
        this.length = plength;
        this.width = pwidth;
    }

    //method

    private double surface(double length, double width) {
        double s = this.getRectangleLength() * this.getRectangleWidth();
        //System.out.println(this.getRectangleLength() * this.getRectangleWidth());
        return s;
    }
    private double perimeter(double length, double width) {
        double p = (this.getRectangleLength() + this.getRectangleWidth())*2;
        //System.out.println(this.getRectangleLength() * this.getRectangleWidth());
        return p;
    }


    public double getRectangleLength() {
        return this.length;
    }

    public double getRectangleWidth() {
        return this.width;
    }

    public void getResult(){
        System.out.println("Length: " + getRectangleLength());
        System.out.println("Width: " + getRectangleWidth());
        System.out.println("Surface: "+surface(getRectangleLength(),getRectangleWidth()));
        System.out.println("Perimeter: "+perimeter(getRectangleLength(),getRectangleWidth()));
    }

}
