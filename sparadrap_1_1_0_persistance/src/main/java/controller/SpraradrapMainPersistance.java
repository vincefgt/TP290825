package controller;

import BDD.Singleton;

import java.io.IOException;
import java.sql.SQLException;


public class SpraradrapMainPersistance {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        // connect classic
       //onnectionBdd connectionBdd = new ConnectionBdd();
       //connectionBdd.connectBdd();
        Singleton.testConnectionWithSingleton(); // avec singleton
        SparadrapMainApp.main(args); // loading App

    }

    /**
        List<Abonne> listAbonnes = null;
        try {
            listAbonnes = AbonneImplementation.selectFromAbonne_WtithSingleton();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Abonne abonne : listAbonnes) {
            System.out.println(abonne);
        }

        ListAbonnes frame = new ListAbonnes(listAbonnes);
        frame.setVisible(true);

        System.out.println("---------------------");**/

    }

