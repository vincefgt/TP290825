package controller;

import DAO.Singleton;
import model.Medicament;

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
        /**
        Implementation.getTableData(Singleton.getInstanceDB(), "doctor");
        Implementation.getTableData(Singleton.getInstanceDB(), "categories");
        Implementation.getTableData(Singleton.getInstanceDB(), "mutuelle");
        Implementation.getTableData(Singleton.getInstanceDB(), "person");
        **/
        //PharmacieView.printList(Implementation.selectFromPrescription(Singleton.getInstanceDB())); //displaing list
        SparadrapMainApp.main(args); // loading App
    }
}

