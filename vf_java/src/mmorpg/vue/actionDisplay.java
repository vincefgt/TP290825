package mmorpg.vue;

import mmorpg.controler.Mmorpg;
import mmorpg.model.*;

import java.security.PublicKey;
import java.util.Scanner;

import static mmorpg.model.Race.raceMap;

public class actionDisplay {

    private static String perso; // data personnage
    private static String name;
    private static String race;
    private static String classes;
    private static String p1;
    private static String p2;
    private static String p3;
    private static String groupName;

    public static void displayNewPerson() {
        InputAndDisplay.message("New Person?: [y/n]", 1); //green
        if (sc.nextLine().equals("y")) {
            displayCreatePerson();
        } else {
            displayListHealer();
            displayListThief();
            displayListWarrior();
        }
        displayCreateGroup();
    }

    //input
    static Scanner sc = new Scanner(System.in);
    public static String getName() {
        return name;
    }

    public static String getRaceInput() {
        return race;
    }

    public static String getClassInput() {
        return classes;
    }

    public static boolean controlInputUser(String name, String race, String classes) {
        boolean inputName = false, inputRace = false, inputClasse = false;

        if (name != null && !name.isBlank()) {
            inputName = true;
        }
        if (race != null && !race.isBlank() &&
                (race.equals("warrior") || race.equals("thief") || race.equals("healer"))) {
            inputRace = true;
        }
        if (classes != null && !classes.isBlank() &&
                (classes.equals("humain") || classes.equals("gnome") || classes.equals("elfe"))) {
            inputClasse = true;
        }

        return inputClasse && inputName && inputRace;
    }


    //Creation
    public static void displayCreatePerson() {
        boolean flag = false;
        //do {
        System.out.println("Seize: Name / Race" + Race.races + " / Classes" + Classes.listClasses);
        do {
        // supp space in & out
        perso = sc.nextLine().trim();
        //perso.split(" ");
        String[] caracteres = perso.split("\\s+");
        name = caracteres[0].toLowerCase();
        race = caracteres[1].toLowerCase();
        classes = caracteres[2].toLowerCase();

        flag = controlInputUser(actionDisplay.getName(),actionDisplay.getRaceInput(),actionDisplay.getClassInput());
        if (!flag) {
            System.err.println("Invalid Input: Try again");}
        } while (!flag);
        //call create person
        Mmorpg.createPerson(actionDisplay.getName(), Race.getRace(), Classes.getClasses());
    }


    public static void displayCreateGroup() {
        if (Person.getListPerson().size() >= 3) {
            InputAndDisplay.message("Create Group?: [y/n]", 1); //green
            if (sc.nextLine().equals("y")) {
                displayListPerson();
                InputAndDisplay.message("Give Group Name", 1);
                actionDisplay.groupName = sc.nextLine();
                InputAndDisplay.message("Selected 3 players", 1);
                p1 = sc.nextLine();
                InputAndDisplay.message("Selected 2 players", 1);
                p2 = sc.nextLine();
                InputAndDisplay.message("Selected 1 players", 1);
                p3 = sc.nextLine();

            /*return groupName;
            return p1;
            return p2;
            return p3;*/
            }
            //call
            Mmorpg.createGroup(groupName, p1, p2, p3);
        }
    }

    public static void displayPersonsGroup(String groupName) {
        InputAndDisplay.message("PersonInGroup " + groupName, 0); //green
        for (Group g : Group.getListPersonsGroup()) {
            InputAndDisplay.message(String.valueOf(g), 0);
        }
    }

    //list
    public static void displayListWarrior() {
        // afficher liste
        InputAndDisplay.message("ListOfWarriors:", 3);
        //System.out.println("ListOfWarriors:");
        for (Warrior w : Warrior.getListWarrior()) {
            InputAndDisplay.message(String.valueOf(w), 3);
        }
    }

    public static void displayListHealer() {
        InputAndDisplay.message("ListOfHealers:", 4);
        //System.out.println("ListOfHealers:");
        for (Healer h : Healer.getListHealer()) {
            InputAndDisplay.message(String.valueOf(h), 4);
            //System.out.println(h);
        }
    }

    public static void displayListThief() {
        InputAndDisplay.message("ListOfthiefs:", 2); //green
        //System.out.println("ListOfThiefs:");
        for (Thief t : Thief.getListThief()) {
            InputAndDisplay.message(String.valueOf(t), 2);
            //System.out.println(t);
        }
    }

    public static void displayListPerson() {
        InputAndDisplay.message("ListOfPersons:", 0); //green
        //System.out.println("ListOfThiefs:");
        for (Person p : Person.getListPerson()) {
            InputAndDisplay.message(String.valueOf(p), 0);
            //System.out.println(t);
        }
    }

    public static void displayListGroup() {
        InputAndDisplay.message("ListOfGroup:", 0); //green
        for (String g : Group.getListGroup()) {
            InputAndDisplay.message(String.valueOf(g), 0);
        }
    }


} // end
