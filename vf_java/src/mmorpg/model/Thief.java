package mmorpg.model;

import java.util.ArrayList;
import java.util.List;

public class Thief extends Person{
    private int poh = 0; //point of hability;

    public Thief(String name, int level, int pol, Race race, Classes Class, Group group, int poh) {
        super(name, level,pol, race,Class,group);
        this.setPoh(poh);
    }

    public int getPoh(){
        return this.poh;
    }
    public void setPoh(int poh){
        this.poh = poh;
    }


    static List<Thief> listThief = new ArrayList<>();
    public static List<Thief> getListThief() {
        return listThief;
    }

    @Override
    public String toString(){
        return super.toString()+" "+getPoh()+" / "+super.getGroup();
    }
}

