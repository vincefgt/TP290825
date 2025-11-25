package DAO;

import model.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class purchaseDAO extends DAO<Achat> {

    public purchaseDAO() throws SQLException, IOException, ClassNotFoundException, IOException {
    }

    @Override
    public Achat insert(Achat entity) throws SQLException {
        int inserted = 0; // valeur de l'id par défaut
        StringBuilder insertIntoTempMed = new StringBuilder();
        insertIntoTempMed.append("CALL temp_listdrug(?)");
        try {
            CallableStatement stmt = connection.prepareCall(insertIntoTempMed.toString());
            for (Medicament med : entity.getListMedAchat()) {
                stmt.setInt(1, med.getId());
                //TODO quantity in cart
                stmt.execute();
            }
            StringBuilder insertIntoDirect = new StringBuilder();
            insertIntoDirect.append("CALL insertAllIntoDirect");
        try {
            CallableStatement stmt2 = connection.prepareCall(insertIntoDirect.toString());
            stmt2.execute();
        } catch (SQLException e) {
            System.out.println("Error inserting direct "+e.getMessage());
            throw new RuntimeException(e);
        }
        return entity;
        } finally {
        }
    }

    @Override
    public boolean update(Achat entity) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(Integer pId) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteByObj(Achat entity) throws SQLException {
        return false;
    }

    @Override
    public Achat findById(Integer pId) throws SQLException {

        return null;
    }

    @Override
    public List<Achat> findAll() throws SQLException {

        StringBuilder findIntoPurchases = new StringBuilder();
        findIntoPurchases.append("select * from purchase;");

        List<Achat> purchases = new ArrayList<>();

        try {
            Statement statement = connection.createStatement(); // etablissement d'un statement
            ResultSet resultSet = statement.executeQuery(findIntoPurchases.toString());
            while (resultSet.next()) {

                // utilisation du resultSet pour récuperer les valeurs de chaques colonnes ciblées par le nom
                Integer id_person = resultSet.getInt("id_person");
                Integer id_prescription = resultSet.getInt("id_prescription");
                LocalDate datePurchase = resultSet.getDate("date_purchase").toLocalDate();
                float totalPrescription = resultSet.getFloat("calc_total");
                float rembPurchase = resultSet.getFloat("calc_remb");

                ordoDAO ordoDAO = new ordoDAO();
                clientDAO clientDAO =  new clientDAO();
                Achat purchase;
                if (id_prescription != 0) {
                    purchase = new Achat(LocalDate.now(), clientDAO.findById(id_person), ordoDAO.findById(id_prescription), totalPrescription, rembPurchase);
                } else {
                    purchase = new Achat(LocalDate.now(),totalPrescription, rembPurchase);
                };
                purchases.add(purchase);
                }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.err.println("Erreur import purchaseDAO: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return purchases;
    }

        @Override
    public void closeConnection() throws SQLException {

    }
}
