package DAO;

import model.Medecin;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class medecinDAO extends DAO<Medecin> {

    public medecinDAO() throws SQLException, IOException, ClassNotFoundException, IOException {
    }

    @Override
    public Medecin insert(Medecin newDoctor) throws SQLException {
        int inserted = 0; // valeur de l'id par défaut
        // requête
        StringBuilder insertIntoDoctor = new StringBuilder();
        insertIntoDoctor.append("insert into person (firstname, lastname) values(?,?)");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    insertIntoDoctor.toString(),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newDoctor.getFirstName());
            preparedStatement.setString(2, newDoctor.getLastName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                inserted = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting doctor " + e.getMessage());
        }

        return newDoctor;
    }

    @Override
    public boolean update(Medecin Doctor) throws SQLException {
        int inserted = 0; // valeur de l'id par défaut
        // requête
        StringBuilder updateIntoDoctor = new StringBuilder();
        updateIntoDoctor.append("update person\n" +
                "SET firstname = ?, lastname = ?\n" +
                "where id_person=(select id_person from doctor where id_doctor = ?)");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    updateIntoDoctor.toString(),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, Doctor.getFirstName());
            preparedStatement.setString(2, Doctor.getLastName());
            preparedStatement.setInt(1, Doctor.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting doctor " + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean deleteById(Integer pId) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteByObj(Medecin Doctor) throws SQLException {
        int inserted = 0; // valeur de l'id par défaut
        // requête
        StringBuilder deleteIntoDoctor = new StringBuilder();
        deleteIntoDoctor.append("delete from person where id_person=(select id_person from doctor where id_doctor = ?)");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    deleteIntoDoctor.toString(),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, Doctor.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting doctor " + e.getMessage());
        }
        return true;
    }

    @Override
    public Medecin findById(Integer pId) throws SQLException {
        return null;
    }

    @Override
    public List<Medecin> findAll() throws SQLException {
        String select = "select  doctor.id_doctor, firstname, lastname, nb_agreement, phone, email, street, op_city, name_city from doctor\n" +
                "INNER JOIN person ON person.id_person=doctor.id_person\n" +
                "INNER JOIN address ON address.id_address=person.id_address\n" +
                "INNER JOIN city on city.id_city=address.id_city;";

        List<Medecin> doctors = new ArrayList<>();

        try {
            Statement statement = connection.createStatement(); // etablissement d'un statement
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                // utilisation du resultSet pour récuperer les valeurs de chaques colonnes ciblées par le nom
                int numab = resultSet.getInt("doctor.id_doctor");
                Long nb_agreement = resultSet.getLong("nb_agreement");
                String prenom = resultSet.getString("firstname");
                String nom = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String street = resultSet.getString("street");
                Integer op_city = resultSet.getInt("op_city");
                String name_city = resultSet.getString("name_city");

                Medecin medecin = new Medecin (prenom, nom,street, email,op_city, name_city,phone, nb_agreement, numab);
                //Medecin medecin = new Medecin (prenom, nom, email, name_city,phone, nb_agreement, numab);
                doctors.add(medecin);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctors;
    }

    @Override
    public void closeConnection() throws SQLException {

    }
}
