package exo_poo2;

public class Capital extends City{
    private String country;

    public Capital(String name, String country) {
        super(name);
        this.setCountry(country);
    }

    public Capital(String name, int nbHab, String country) {
        super(name, nbHab);
        this.setCountry(country);
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public void displayCapital(){
        if (getNbHab() == -1){
            System.out.println("City: "+getName()+" number of citizen: UNKNOWN Capital de "+getCountry());
        } else {
            System.out.println("City: "+getName()+" number of citizen: "+getNbHab()+
                    " Capital de "+getCountry());
        }


    }
}
