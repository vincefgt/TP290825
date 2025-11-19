package DAO;

import model.Mutuelle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class mutDAO extends DAO<Mutuelle> {

    protected mutDAO() throws SQLException, IOException, ClassNotFoundException, IOException {
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

        StringBuilder findByIdMut = new StringBuilder();
        findByIdMut.append("SELECT * FROM mutuelle\n"+
                            "INNER JOIN person ON person.mutuelle.id=mutuelle.id\n"+
                            "WHERE id = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(findByIdMut.toString());
        preparedStatement.setInt(1, pId);
        resulSet rs = preparedStatement.executeQuery();

        Mutuelle newMutuelle = null;
        if (rs.next())

            mutuelle.setId(rs.getInt("id"));
            mutuelle.setName(rs.getString("name"));

            Mutuelle newMutuelle = new Mutuelle(i);
            // ajoutez ici les setters nécessaires
        }

        return newMutuelle;
    }

    @Override
    public List<Mutuelle> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void closeConnection() throws SQLException {

    }
}
