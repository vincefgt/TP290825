package training.afpa.cda24060;

import exo_poo.*;
import exo_poo2.*;
import training.afpa.cda24060.geometry.Circle;
import training.afpa.cda24060.geometry.TestCircle;
import static exo_poo.TestLivre.mainLivre;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static <action> void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println(">>>>>>>> Hello awesome !!");

        //create instance > class main
        Main main = new Main();
        // call methode
        main.start();
    }

    private void start() {
        // via List
        //List_exo.App();
        //Exo
        //Personne personne = new Personne("Doe","1 blvd Kennedy, NY");
        Staff staff = new Staff("Doe","bvld kennedy", "Brooklyn",150);
        staff.displayStaff();
        Student student = new Student("Doe","1 blvd Kennedy, NY","MBAfinance",2025,250);
        student.displayStudent();
        // }
        City v1 = new City("Lyon", 1500000);
        City v2 = new City("Bobigny");
        Capital c1 = new Capital("Paris", 10000000, "France");
        Capital c2 = new Capital("Ouagadougou", "Burkina-Faso");
        v1.displayCity();
        v2.displayCity();
        c1.displayCapital();
        c2.displayCapital();

    }
}


