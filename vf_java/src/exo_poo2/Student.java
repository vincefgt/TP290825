package exo_poo2;

public class Student extends Personne {
    private int year;
    private double fee;
    private String program;

    public Student(String name, String address, String program,
                   int year, double fee) {
        super(name,address);
        this.fee = fee;
        this.program = program;
        this.year = year;
    }

    public double getFee() {
        return fee; }
    public void setFee(double fee) {
        this.fee = fee; }
    public String getProgram() {
        return program; }
    public void setProgram(String program) {
        this.program = program; }
    public int getYear() {
        return year; }
    public void setYear(int year) {
        this.year = year; }

    public void displayStudent(){
        System.out.println(this);
    }


    @Override
    public String toString() {
        return "Student "+super.toString()+"; "+program+"; "+year+"; "+fee;
    }
}
