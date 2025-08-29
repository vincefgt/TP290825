package mmorpg.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupName;
    private String p1;
    private String p2;
    private String p3;
    private LocalDate date;
    //static ArrayList<Group> groupGroup = new ArrayList<>();

    public Group(String groupName, String p1, String p2, String p3) {
        this.setGroupName(groupName);
        //list of Person in Group
        this.setP1(p1);
        this.setP2(p2);
        this.setP3(p3);
        Group.listPersonsGroup.add(this);
        Group.listGroup.add(this.getGroupName());
        this.setDateCreateGroup(LocalDate.now());
    }

    private void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupName() {
        return groupName;
    }

    public String getP1() {
        return p1;
    }
    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }
    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return p3;
    }
    public void setP3(String p3) {
        this.p3 = p3;
    }

    //list person in group
    static List<Group> listPersonsGroup = new ArrayList<>();

    public static List<Group> getListPersonsGroup() {
        return listPersonsGroup;
    }

    public void setDateCreateGroup(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDateCreateGroup() {
        return this.date;
    }

    //list of groups
    static List<String> listGroup = new ArrayList<>();
    public static void setListGroup(List<String> listGroup) {
        Group.listGroup = listGroup;
    }
    public static List<String> getListGroup() {
        return listGroup;
    }

    @Override
    public String toString() {
       // return "Group: "+getGroupName()+" "+getP1()+" "+getP2()+" "+getP3();
        return getP1()+" "+getP2()+" "+getP3()+" "+getDateCreateGroup();
}
}
