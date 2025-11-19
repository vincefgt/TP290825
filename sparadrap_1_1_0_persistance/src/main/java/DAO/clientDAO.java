package DAO;

import model.Client;
import model.Mutuelle;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class clientDAO extends DAO<Client> {

    public clientDAO() throws SQLException, IOException, ClassNotFoundException, IOException {
    }

    @Override
    public Client insert(Client entity) throws SQLException {
        int inserted = 0; // valeur de l'id par défaut
        // requête
        StringBuilder insertIntoDoctor = new StringBuilder();
        insertIntoDoctor.append("insert into person (firstname, lastname) values(?,?)");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    insertIntoDoctor.toString(),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                inserted = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting doctor " + e.getMessage());
        }

        return entity;
    }

    @Override
    public boolean update(Client entity) throws SQLException {
        /** requête
         *
         * **/
        StringBuilder updateIntoClient = new StringBuilder();
        updateIntoClient.append("CALL updatePerson(?,?,?,?,?,?,?,?);");
        //TODO >>>>>>> updateIntoClient.append("CALL updateMutuelle");
        connection.setAutoCommit(false);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
            updateIntoClient.toString());
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setLong(4, entity.getNbSS());
            preparedStatement.setString(5, entity.getPhone());
            preparedStatement.setString(6, entity.getEmail());
            preparedStatement.setString(7, entity.getDateBirth());
            preparedStatement.setInt(8, entity.getMutuelle().getId_mut());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error BDD " + e.getMessage());
            connection.rollback();
        }
        return true;
    }

    @Override
    public boolean deleteById(Integer pId) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteByObj(Client entity) throws SQLException {
        int inserted = 0; // valeur de l'id par défaut
        // requête
        StringBuilder deleteIntoDoctor = new StringBuilder();
        deleteIntoDoctor.append("delete from person where id_person=?");

        PreparedStatement preparedStatement = connection.prepareStatement(
                deleteIntoDoctor.toString(),
                PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, entity.getId());
        preparedStatement.executeUpdate();
        return true;
    }

    @Override
    public Client findById(Integer pId) throws SQLException {
        return null;
    }

    @Override
    public List<Client> findAll() throws SQLException {

        String select = "select  p.id_person, p.firstname, p.lastname, p.nSS, p.phone, p.email,\n" +
                " p.dob, street, op_city, name_city, p.dob, p.id_mutuelle, p2.lastname  as nameMut from person p\n" +
                "INNER JOIN address ON address.id_address=p.id_address\n" +
                "INNER JOIN city on city.id_city=address.id_city\n" +
                "LEFT JOIN mutuelle m ON m.id_mutuelle = p.id_mutuelle\n" +
                "LEFT JOIN person p2 ON p2.id_person = m.id_person\n" +
                "WHERE person.nSS IS NOT NULL;";

        List<Client> clients = new ArrayList<>();

        try {
            Statement statement = connection.createStatement(); // etablissement d'un statement
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {

                // utilisation du resultSet pour récuperer les valeurs de chaques colonnes ciblées par le nom
                int numId = resultSet.getInt("id_person");
                String prenom = resultSet.getString("firstname");
                String nom = resultSet.getString("lastname");
                Long nSS = resultSet.getLong("nSS");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String dob = resultSet.getString("dob");
                String street = resultSet.getString("street");
                Integer op_city = resultSet.getInt("op_city");
                String name_city = resultSet.getString("name_city");
                LocalDate dob_client = resultSet.getDate("dob").toLocalDate(); //, DateTimeFormatter.ofPattern("dd-MM/yyyy"));

                Client clt = new Client(numId, prenom, nom, street, op_city, name_city, phone, email, nSS, dob_client, null , null);
                clients.add(clt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
        // list Clients
    }

    @Override
    public void closeConnection() throws SQLException {

    }

}
