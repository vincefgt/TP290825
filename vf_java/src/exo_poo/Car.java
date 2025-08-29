package exo_poo;

public class Car {
    //decla
    private String brand;
    private int price;

    public Car(String brand, int price) {
    this.brand = brand;
    this.price = price;
    }

    private String displayCar(String brand, int price){
        StringBuffer c = new StringBuffer();
        c.append("Brand: ").append(brand).append(" | Price: ").append(price)
        .append("K");
        return c.toString();
    }

    public static void getDisplayCar(String brand, int price){
        Car c = new Car(brand,price);
        System.out.println(c.displayCar(brand,price));
    }



}
