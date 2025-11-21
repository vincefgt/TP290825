package DAO;

import controller.SparadrapMainApp;
import model.Client;
import model.Medecin;
import model.Mutuelle;
import model.Ordonnance;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ordoDAO extends DAO<Ordonnance> {
    public ordoDAO() throws SQLException, IOException, ClassNotFoundException, IOException {
    }

    @Override
    public Ordonnance insert(Ordonnance entity) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Ordonnance entity) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(Integer pId) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteByObj(Ordonnance entity) throws SQLException {
        return false;
    }

    @Override
    public Ordonnance findById(Integer pId) throws SQLException {
        return null;
    }

    @Override
    public List<Ordonnance> findAll() throws SQLException {

        StringBuilder findIntoOrdo = new StringBuilder();
        findIntoOrdo.append("select * from prescription;");

        List<Ordonnance> ordos = new ArrayList<>();

        try {
            Statement statement = connection.createStatement(); // etablissement d'un statement
            ResultSet resultSet = statement.executeQuery(findIntoOrdo.toString());
            while (resultSet.next()) {

                // utilisation du resultSet pour récuperer les valeurs de chaques colonnes ciblées par le nom
                int numId = resultSet.getInt("id_person");
                Integer id_doctor = resultSet.getInt("id_doctor");
                Integer id_prescription = resultSet.getInt("id_prescription");
                LocalDate datePrescription = resultSet.getDate("date_prescription").toLocalDate();

                clientDAO clientDAO = new clientDAO();
                medecinDAO medecinDAO = new medecinDAO();
                Ordonnance ordo = new Ordonnance(id_prescription,datePrescription,medecinDAO.findById(id_doctor),clientDAO.findById(numId));
                ordos.add(ordo);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
           // System.err.println("Erreur import All Ordo: " + e.getMessage());
             throw new RuntimeException(e);
        }
        return ordos;
    }

    @Override
    public void closeConnection() throws SQLException {

    }
}
