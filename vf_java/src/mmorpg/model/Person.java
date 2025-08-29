package mmorpg.model;

import java.util.ArrayList;

public class Person {
    private String name =null;
    private int level=0;
    private int pol=0; //point of live
    private Race race;
    private Classes classes;
    private Group group;


    public Person(String name, int level, int pol, Race race, Classes Class,Group G) {
        this.setName(name);
        this.setLevel(level);
        this.setPol(pol);
        this.setRace(race);
        this.setClasses(classes);
        this.setGroup(G);
    }

    public void setGroup(Group g){
        this.group = g;
    }

    public Group getGroup(){
        return this.group;
    }

    static ArrayList<Person> listPerson = new ArrayList<>();

    public static ArrayList<Person> getListPerson(){
        return listPerson;
    }

    public String getName() {return name;}
    public void setName(String name) {

        this.name = name;}
    public int getLevel() {return level;}
    public void setLevel(int level) {this.level = level;}
    public int getPol() {return pol;}
    public void setPol(int pol) {this.pol = pol;}
    public Race getRace() {return race;}
    public void setRace(Race race) {this.race = race;}
    public Classes getClasses() {return classes;}
    public void setClasses(Classes classes) {this.classes = classes;}



    @Override
    public String toString() {
        return this.getName() + " " + this.getLevel() + " " + this.getPol()+" "+
                this.getRace()+" "+this.getClasses();//+" "+getGroup();
    }
}
