package mmorpg.model;

import java.util.ArrayList;
import java.util.List;

public class Healer extends Person{
    private int pom = 0; //point of strength;

    public Healer(String name, int level, int pol, Race race, Classes Class, Group group, int pom) {
        super(name, level,pol, race,Class,group);
        this.setPom(pom);
    }

    public static List<Healer> listHealer = new ArrayList<>();

    public static List<Healer> getListHealer(){
        return listHealer;
    }

    public int getPom(){
        return this.pom;
    }

    public void setPom(int pom){
        this.pom = pom;
    }

    public static void addListHealer(Healer h){
        listHealer.add(h);
    }


    @Override
    public String toString(){
        return super.toString()+" "+ getPom()+" / "+super.getGroup();
    }
}
