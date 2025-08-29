package utils;

import javax.swing.*;
import java.util.Arrays;
import java.util.Scanner;

public class Tool {

    /*public int void scan(){
        Scanner sc = new Scanner(System.in);
        int readenter = sc.nextInt();
        System.out.println(readenter);
        return readenter;
    }*/

    //create Tab 1 dimension w/ Size to enter
    public float[] createTab(){
        int indexMax;
        System.out.println("Enter tab length:");
        Scanner sc = new Scanner(System.in);
        indexMax = sc.nextInt();
        float[] scoreboard = new float[indexMax];

        for (int i = 0; i < indexMax; i++) {
        System.out.println("Enter number("+(i+1)+"): ");
        scoreboard[i] = sc.nextInt();
        }
        System.out.println(Arrays.toString(scoreboard));
        return scoreboard;
    }

    // Sort Tab 1 dimension
    public float[] sortTab(float[] scoreboard){
        //float[] scoreboard = new float[scoreboard.length];
        //float result = 0;
        //float notes = 0;
        float key;
        int memkey; //indice of memorissation
        for (int i = 1; i < scoreboard.length ; i++) {
            key = scoreboard[i];
            memkey = i - 1; //save previous number

            while (memkey >= 0 && scoreboard[memkey] > key) {
                scoreboard[memkey+1] = scoreboard[memkey];
                memkey =  memkey-1;
            }
            scoreboard[memkey+1] = key;
        }
        System.out.println(Arrays.toString(scoreboard)); //display scoreboard
        return scoreboard;
    }

    //Search value Tab 1 dimension
    public void searchValue(float[] scoreboardSort, int searchvalue){
        int lowkey = 0;
        int highkey = scoreboardSort.length;
        int middlekey = (lowkey + highkey)/2;
        boolean found = false;

        while (!found && lowkey <= highkey) {
            if  (searchvalue < scoreboardSort[middlekey]) {
                highkey = middlekey-1;
            } else if (searchvalue > scoreboardSort[middlekey]) {
                lowkey = middlekey+1;
            } else {
                found = true;
            }
            middlekey = (lowkey + highkey)/2;
        }

        if (found) {
           // return scoreboardSort[middlekey];
        } else {
            middlekey = middlekey-1;
        }
        System.out.println("searchValue index: "+(middlekey+1));
    }

    //Create tab 2 dim factor
    public int[][] createTab2(int indexMax, int indexMax2){
        //int indexMax2 = 2;
        int N;

        // only of <exo2_1
        System.out.println("Enter tab length:");
        Scanner sc = new Scanner(System.in);
        indexMax = sc.nextInt();

        int[][] scoreboard2 = new int[indexMax][indexMax2];
        for (int i = 0; i < indexMax; i++) {
            for (int j = 0; j < indexMax2; j++) {
                if (j == 0){
                    scoreboard2[i][j] = i+1;
                } else {
                    scoreboard2[i][j] = factor(i+1);
                }
                System.out.print(scoreboard2[i][j] + " ");

            }
            System.out.println(">>> La factorielle de "+(i+1)+" est : "+factor(i+1));
            System.out.println(); // new line
        }
        // Method 2
        System.out.println(Arrays.deepToString(scoreboard2));
        return scoreboard2;

    }

    //calcul factor
    public int factor(int j) { //2 > 3
        int sumf = 1;
        int k = 1;
        while (k <= j) {
            sumf = sumf * k;
            k++;
        }
        //System.out.println("La factorielle de "+N+" est : "+sum);
        return sumf;
    }

    //dsiplay windows w msg
    public void popup(String msg,String title){
        JOptionPane.showMessageDialog(null,msg,title, JOptionPane.INFORMATION_MESSAGE);
    }

    //Find Max Value in tab
    public float maxValueTab(float[] scoreboard){
     float maxValue = 0;
        for (int i = 1; i < scoreboard.length ; i++) {
         if (scoreboard[i] > maxValue) {
             maxValue = scoreboard[i];
         }
     }
        return maxValue;
    }

    //calcul average for tab
    public float moyValueTab(float[] tab){
        float sum = 1;
        int i = 1;
        int N = tab.length;
     while (i < N) {
         sum = sum + tab[i];
         i++;
     }
        float moy = sum/i;

        return moy;
    }

    //Enter value via popup windows
    public float popupEnterValue(String msg){
        String input = JOptionPane.showInputDialog(null, msg);
        return Float.parseFloat(input);
    }

    //create Tab 1 dimension Size to enter
    public float[] createTabValueEnter(int indexMax){
        float[] scoreboard = new float[indexMax];
        for (int i = 0; i < indexMax; i++) {
          //  System.out.println("Enter number("+(i+1)+"): ");
         //   Scanner sc = new Scanner(System.in);
 //           scoreboard[i] = sc.nextInt();
            scoreboard[i] = popupEnterValue("Enter number("+(i+1)+")");
        }
        System.out.println(Arrays.toString(scoreboard));
        return scoreboard;
    }

    //Create tab 2 dim multi
    public int[][] createTab2Multi(int indexMax, int indexMax2){
        //int indexMax2 = 2;
        int N;

        int[][] scoreboard2 = new int[indexMax][indexMax2];
        for (int i = 0; i < indexMax; i++) {
            for (int j = 0; j < indexMax2; j++) {
                if (j == 0){
                    scoreboard2[i][j] = i+1;
                } else {
                    scoreboard2[i][j] = (i+1)*j;
                }
                System.out.print(scoreboard2[i][j] + " ");
            }
            System.out.println(); // new line
        }
        // Method 2
        //System.out.println(Arrays.deepToString(scoreboard2));
        return scoreboard2;
    }

    // Error msg
    public void errorpopup(String msg){
        JOptionPane.showMessageDialog(null,"Please enter a correct number"+msg,"ERROR",JOptionPane.ERROR_MESSAGE);
    }

    public boolean againpopup(String msg,String title){
        boolean choice = true;
        //while (choice) {
        int option = JOptionPane.showConfirmDialog(null,msg,title,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        // Réagir au choix
        if (option == JOptionPane.YES_OPTION) {
            choice = true;  // boucle continue
        } else {
            choice = false;
            //break;
        }
        //}
        return choice;
    }

    //Calculator
    public static float calculator(char operator,float ope1,float ope2){
        float sum =0;
        switch (operator) {
            case '+': sum = ope1 + ope2;
                break;
            case '-': sum = ope1 - ope2;
                break;
            case '/':
                if (ope1 == 0 || ope2 ==0){
                    JOptionPane.showMessageDialog(null, "Erreur : division par zéro !");
                }
                sum = ope1 / ope2;
                break;
            case '*' : sum = ope1*ope2;
                break;
            default: JOptionPane.showMessageDialog(null, "Opérateur non reconnu : "+operator);
                    break;
        }
        return sum;
    };

    //Enter value via popup windows ope
    public char popupEnterValueOpe(String msg){
        String ope = JOptionPane.showInputDialog(null, msg);
        return ope.charAt(0);
    }

    //popupSort
    public static int TypeSort(){
        String[] options = { "ascending", "descending"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Sort by ...",
                "Menu principal",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
        return choice;
    }

    //sort
    public float[] sortdescensingTab(float[] scoreboard){
        float key;
        int memkey; //indice of memorissation
        for (int i = 1; i < scoreboard.length ; i++) {
            key = scoreboard[i];
            memkey = i - 1; //save previous number

            while (memkey >= 0 && scoreboard[memkey] < key) {
                scoreboard[memkey+1] = scoreboard[memkey];
                memkey = memkey-1;
            }
            scoreboard[memkey+1] = key;
        }
        System.out.println(Arrays.toString(scoreboard)); //display scoreboard
        return scoreboard;
    }


}

