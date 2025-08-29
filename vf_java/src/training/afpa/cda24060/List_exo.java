package training.afpa.cda24060;

import exo_poo.CarExecution;
import exo_poo.TestCompte;
import exo_poo.TestRectangle;
import training.afpa.cda24060.geometry.TestCircle;
import utils.Tool;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static exo_poo.TestLivre.mainLivre;

public class List_exo {
    int i;
    float[] scoreboard = new float[3];
    float result = 0;
    float notes = 0;
    float key;
    int memkey; //indice of memorissation

    public static void App() {

        Tool tool = new Tool();
        List_exo exoAlgojava = new List_exo();

        ArrayList<String> numberExo = new ArrayList<>();
        numberExo.add("1 - TestCompte");
        numberExo.add("2 - ExecutionCar");
        numberExo.add("3 - TestRectangle");
        numberExo.add("4 - TestCircle");
        numberExo.add("5 - Livre");
        numberExo.add("6 - exo1_6");
//        numberExo.add("exo2_1");
//        numberExo.add("exo2_2");
//        numberExo.add("exo2_3");
//        numberExo.add("exo2_4");
//        numberExo.add("exo2_5");
//        numberExo.add("exo2_6");
//        numberExo.add("exo1_1");
//        numberExo.add("exo1_2");
//        numberExo.add("exo1_3");
//        numberExo.add("exo1_4");
//        numberExo.add("exo1_5");
//        numberExo.add("exo1_6");
//        numberExo.add("exo2_1");
//        numberExo.add("exo2_2");
//        numberExo.add("exo2_3");
//        numberExo.add("exo2_4");
//        numberExo.add("exo2_5");
//        numberExo.add("exo2_6");

        //tiny list
        StringBuilder list = new StringBuilder();
        for (String n : numberExo) {
            list.append(n).append("\n");
        }
        String exoValue = JOptionPane.showInputDialog(null,list.toString()+"Select the exo to show");

        switch(exoValue){
            case "1":  TestCompte.testCompte(100, 50, 61);
                break;
            case "2":  CarExecution.execution("bmw", 100);
                break;
            case "3":  TestRectangle.testRectangle(40,30);
                break;
            case "4":  TestCircle.testCircle(5,3,4,7,7); //5,3,4,7,7
                break;
            case "5":  mainLivre();
                break;
            case "6": exoAlgojava.exo1_6();
                break;
            case "7": exoAlgojava.exo2_1();
                break;
            case "8": exoAlgojava.exo2_2();
                break;
            case "9": exoAlgojava.exo2_3();
                break;
            case "10":exoAlgojava.exo2_4();
                break;
            case "11":exoAlgojava.exo2_5();
                break;
            case "12":exoAlgojava.exo2_6();
                break;
            default :
                break;
        }

    }



    // average notes
    public void exo1_1() { //4.1

        for (i = 0; i < scoreboard.length; i++) {
            System.out.println("Please enter the note(" + (i + 1) + ") ?");
            Scanner scanner = new Scanner(System.in);
            notes = scanner.nextFloat();
            result = result + notes;
            //save note in scoreboard
            scoreboard[i] = notes;
        }
        System.out.println("Average = " + result / scoreboard.length); // display average
        System.out.println(Arrays.toString(scoreboard)); //display scoreboard

    }

    public void exo1_2() { //2 > 3
        int sum = 1;
        int i = 1;
        int N;

        System.out.println("Number:?");
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

//            while (i <= N) {
//                sum = sum * i;
//                i = i + 1;
//            }

//            for (int j = 0; j <= N; j++) {
//                sum = sum * j;
//            }

        do {
            sum = sum * i;
            i++;
        } while (i <= N);

        System.out.println("La factorielle de " + N + " est : " + sum);
    }

    public void exo1_3() {
        double a;
        double b;
        double c;
        double discri;
        double x1 = 0;
        double sol2 = 0;

        System.out.println("Enter a:");
        Scanner sc = new Scanner(System.in);
        a = sc.nextDouble();
        System.out.println("Enter b:");
        b = sc.nextDouble();
        System.out.println("Enter c:");
        c = sc.nextDouble();
        discri = (b * b) - 4 * (a * c);
        System.out.println("Le disicriminant est : " + discri);

        if (discri == 0) {
            x1 = (-b) / (2 * a);
        } else if (discri > 0) {
            x1 = (-b + Math.sqrt(discri)) / (2 * a);
            sol2 = (-b - Math.sqrt(discri)) / (2 * a);
        }
        System.out.println("x1 = " + x1 + " et x2 = " + sol2);
    }

    public void exo1_4() {
        int x;
        int y;
        int result;
        int indicep = 1;
        System.out.println("Enter x:");
        Scanner sc = new Scanner(System.in);
        x = sc.nextInt();
        System.out.println("Enter y:");
        y = sc.nextInt();
        result = x;
        do {
            result = result * x;
            indicep = indicep + 1;
        } while (indicep == y);
        System.out.println(x + "^" + y + " = " + result);
    }

    public void exo1_5() {
        Tool tool = new Tool();
        float[] scoreboardSort = tool.sortTab(scoreboard);
        System.out.println("Enter search value:");
        Scanner sc = new Scanner(System.in);
        int searchvalue = sc.nextInt();
        tool.searchValue(scoreboardSort, searchvalue);
        float[] scoreboard = tool.createTab();
    }

    public void exo1_6() {
        int indexMax = 10;
        Tool tool = new Tool();
        tool.createTab2(indexMax, 2);

    }

    public void exo2_1() {
        Tool tool = new Tool();
        // Creation Tab
        float[] tab = tool.createTab();
        tool.popup(Arrays.toString(tab), "ARRAY EXO2_1");
        // Max value
        String maxValue = Float.toString(tool.maxValueTab(tab));
        tool.popup("La valeur max est " + maxValue, "ARRAY EXO2_1");
    }

    public void exo2_2() {
        Tool tool = new Tool();
        // Creation Tab
        float[] tab = tool.createTab();
        tool.popup(Arrays.toString(tab), "ARRAY EXO2_2");
        // Calcul Moy
        String moyValue = Float.toString(tool.moyValueTab(tab));
        tool.popup("La moyenne est : " + moyValue, "MOY EXO2_2");
    }

    // display windows may value
    public void exo2_3() {
        Tool tool = new Tool();
        int value = (int) (tool.popupEnterValue("Enter your number of array"));
        //display tab of value
        String valueString = Arrays.toString(tool.createTabValueEnter(value));
        tool.popup(valueString, "tab of Value");
        String moyValue = Float.toString(tool.moyValueTab(scoreboard));
        tool.popup("La moyenne des " + value + " entier(s) est " + moyValue, "MOY EXO2_3");
    }

    public void exo2_4() {
        Tool tool = new Tool();
        int[][] tab = tool.createTab2Multi(10, 10);
        //loop calcul
        boolean choiceUser = true;
        while (choiceUser != false) {
            //calcul
            float x = tool.popupEnterValue("1er operance");

            // init error windows x
            while (x % 1 != 0) {
                tool.errorpopup("!");
                x = tool.popupEnterValue("1er operance");
                ;
            }

            float y = tool.popupEnterValue("2er operance");
            while (y % 1 != 0) {
                tool.errorpopup("!");
                y = tool.popupEnterValue("2er operance");
            }

            int produitValue = (int) x * (int) y;
            tool.popup("Le resultat de " + x + "x" + y + "=" + produitValue, "Result");
            choiceUser = tool.againpopup("Souhaitez-vous encore un calcul ?", "Multiplication");
        }
    }

    public void exo2_5() {
        Tool tool = new Tool();
        char ope = tool.popupEnterValueOpe("Choississez un operateur -,+,*,/");
        float ope1 = tool.popupEnterValue("Choississez un reel");
        float ope2 = tool.popupEnterValue("Choississez un reel");
        while (ope == '/') {
            if (ope1 == 0) {
                tool.errorpopup("division par 0 impossible ");
                ope1 = tool.popupEnterValue("Choississez un reel");
            } else if (ope2 == 0) {
                tool.errorpopup("division par 0 impossible ");
                ope2 = tool.popupEnterValue("Choississez un reel");
            } else {
                float result = tool.calculator(ope, ope1, ope2);
                tool.popup("Le resultat de " + ope1 + ope + ope2 + " est " + result, "Result");
                break;
            }
        }
    }

    //tab Sorting
    public void exo2_6() {
        Tool tool = new Tool();
        int value = (int) (tool.popupEnterValue("Enter your number of array"));
        //display tab of value
        float[] scoreboard = tool.createTabValueEnter(value);
        String valueString = Arrays.toString(scoreboard);
        tool.popup(valueString, "tab of Value");
        int choice = tool.TypeSort();
        switch (choice) {
            case 0:
                float[] SortTab = tool.sortTab(scoreboard);
                tool.popup(Arrays.toString(SortTab), "tab of Sort Values");
                break;
            case 1:
                SortTab = tool.sortdescensingTab(scoreboard);
                tool.popup(Arrays.toString(SortTab), "tab of Sort Values");
                break;
            default:
                break;
        }

    }
}
