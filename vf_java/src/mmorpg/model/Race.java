package mmorpg.model;

import com.sun.jdi.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Race {
    private String type;
    public static Map<String, Race> raceMap = new HashMap<>();
    private static String race; // from user input

    public Race(String type) {
        this.setRace(type);
        Race.raceMap.put(type, this);  //new Race(type));
        //races.add(Race.raceMap.get(type));
        Race.races.add(this);         //new Race(type));
    }

    public static Race getRace() {
        return raceMap.get(race);
    }

    public void setRace(String type) {
        this.type = type;
    }

    public static List<Race> races = new ArrayList<>();


    @Override
    public String toString() {
        return this.type;
    }
}


