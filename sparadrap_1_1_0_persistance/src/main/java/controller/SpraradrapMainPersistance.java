package controller;

import BDD.Singleton;
import model.Medicament;
import view.PharmacieView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class SpraradrapMainPersistance {
    public static List<Medicament> drugs;


    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        // connect classic
       // connectionBdd connectionBdd = new ConnectionBdd();
       //connectionBdd.connectBdd();

        Singleton.testConnectionWithSingleton(); // avec singleton
        /*
        Implementation.getTableData(Singleton.getInstanceDB(), "doctor");
        Implementation.getTableData(Singleton.getInstanceDB(), "categories");
        Implementation.getTableData(Singleton.getInstanceDB(), "mutuelle");
        Implementation.getTableData(Singleton.getInstanceDB(), "person");
        */
        //PharmacieView.printList(Implementation.selectFromPrescription(Singleton.getInstanceDB())); //displaing list
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

