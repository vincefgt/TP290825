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

public class mutDAO extends DAO<Mutuelle> {

    public mutDAO() throws SQLException, IOException, ClassNotFoundException, IOException {
    }

    @Override
    public Mutuelle insert(Mutuelle entity) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Mutuelle entity) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(Integer pId) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteByObj(Mutuelle entity) throws SQLException {
        return false;
    }

    @Override
    public Mutuelle findById(Integer pId) throws SQLException {
/*
        StringBuilder findByIdMut = new StringBuilder();
        findByIdMut.append("SELECT * FROM mutuelle\n"+
                            "INNER JOIN person ON person.mutuelle.id=mutuelle.id\n"+
                            "WHERE id = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(findByIdMut.toString());
        preparedStatement.setInt(1, pId);
        resulSet rs = preparedStatement.executeQuery();

        if (rs.next())

            mutuelle.setId(rs.getInt("id"));
            mutuelle.setName(rs.getString("name"));

            Mutuelle newMutuelle = new Mutuelle(i);
            // ajoutez ici les setters nécessaires
        }
        */
        return null;
    }

    @Override
    public List<Mutuelle> findAll() throws SQLException {
        String select = "select  m.id_mutuelle , p.lastname, street, op_city, name_city, p.phone, p.email, m.tauxRemb from mutuelle m\n" +
                "inner join person p on p.id_person=m.id_person\n" +
                "INNER JOIN address ON address.id_address=p.id_address\n" +
                "INNER JOIN city on city.id_city=address.id_city\n" +
                "where m.id_person IS NOT NULL\n" +
                "ORDER BY p.lastname ASC;";

        List<Mutuelle> muts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement(); // etablissement d'un statement
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {

                // utilisation du resultSet pour récuperer les valeurs de chaques colonnes ciblées par le nom
                String nom = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String street = resultSet.getString("street");
                Integer op_city = resultSet.getInt("op_city");
                String name_city = resultSet.getString("name_city");
                Integer id_mutuelle = resultSet.getInt("id_mutuelle");
                Double tauxRemb = resultSet.getDouble("tauxRemb");

                Mutuelle mut = new Mutuelle(id_mutuelle, nom,"Mutuelle", street,email, op_city, name_city, phone,tauxRemb);
                muts.add(mut);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return muts;
    }

    @Override
    public void closeConnection() throws SQLException {
    }
}
