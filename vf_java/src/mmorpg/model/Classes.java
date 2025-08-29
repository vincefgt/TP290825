package mmorpg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Classes {
    private String name;
    private String weapon;
    private String armor;
    public static Map<String, Classes> classes = new HashMap<>();
    private static String classGet;

    public static List<Classes> listClasses = new ArrayList<>();

    public Classes(String name) {
        this.setName(name);
        this.weapon = weapon();
        this.armor = armor();
        Classes.classes.put(name, this);
        listClasses.add(this);
    }

    public Classes(String name, String weapon, String armor) {
        this.setName(name);
        this.setWeapon(weapon);
        this.setArmor(armor);
    }

    private String weapon() {
        if (this.getName().equals("thief")) {
            return this.weapon = "dagger";
        } else if (this.getName().equals("warrior")) {
            return this.weapon = "sword";
        } else if (this.getName().equals("healer")) {
            return this.weapon = "stick";
        }
        return this.weapon;
    }

    private String armor() {
        if (this.getName().equals("thief")) {
            return this.armor = "leather";
        } else if (this.getName().equals("warrior")) {
            return this.armor = "steel";
        } else if (this.getName().equals("healer")) {
            return this.armor = "fabric";
        }
        return this.armor;
    }

    public static Classes getClasses() {
        return classes.get(classGet);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeapon(String weapon){
        this.weapon = weapon();
    }

    public void setArmor(String armor){
        this.armor = armor;
    }

    public String getArmor() {
        return this.armor;
    }

    public String getWeapon() {
        return this.weapon;
    }

    @Override
    public String toString() {
        return this.getName();//+" "+getWeapon()+" "+getArmor();
    }
}

