package exo_poo2;

public class Personne {
    private String name;
    private String address;

    public Personne(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Personne{"+ getName() + "; "+ getAddress()+"}";
    }
}
