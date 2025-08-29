package mmorpg.model;

import java.util.ArrayList;
import java.util.List;

public class Warrior extends Person {
    private int pos = 0; //point of strength;

    public Warrior(String name, int level, int pol, Race race, Classes Class, Group group, int pos) {
        super(name, level,pol, race,Class,group);
        this.setPos(pos);
    }

    public int getPos(){
        return this.pos;
    }

    public void setPos(int pos){
        this.pos = pos;
    }

    // create list of
    private static ArrayList<Warrior> listWarrior = new ArrayList<>();

    public static void createListWarrior() {
        //Warrior defautl = new Warrior("default",1,100,null,null,100);
        //Warrior.getListWarrior().add(defautl);
    }


    public static List<Warrior> getListWarrior(){
        return listWarrior;
    }

    public static void addWarrior(Warrior w){
        listWarrior.add(w);
    }

    @Override
    public String toString(){
        return super.toString()+" "+ getPos()+" / "+super.getGroup();
    }

}
