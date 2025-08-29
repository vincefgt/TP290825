package exo_poo2;

public class City {
    private String name;
    protected int nbHab;

    public City (String name) {
        this.name = name;
        this.nbHab = -1;
    }

    public City (String name, int nbHab) {
        this.name = name;
        if (nbHab<0){
            System.out.println("nb of Citizen should be a positive number");
            this.nbHab = -1;
        } else {
            this.nbHab = nbHab;
        }
    }

    public String getName() {
        return name;
    }
    public void setNbHab(int pnbHab) {
        if  (pnbHab<0){
            System.out.println("nb of citizen should be a positive number & impossibility to change");
        } else {
            this.nbHab = pnbHab;
        }
    }
    public int getNbHab() {
        return nbHab;
    }

    public void displayCity(){
        if (getNbHab() == -1){
            System.out.println("Citizen: "+getName()+" unknown");
        } else {
            System.out.println("Citizen: "+getName()+" "+getNbHab());
        }
    }

}
