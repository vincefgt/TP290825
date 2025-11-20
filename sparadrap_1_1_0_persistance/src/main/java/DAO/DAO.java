package DAO;

import model.Medecin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DAO<T> {

    /**
     * instance singleton de connexion vers la base de données
     */
    protected static Connection connection;

    static {
        try {
            connection = Singleton.getInstanceDB();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected DAO() throws SQLException, IOException, ClassNotFoundException, IOException {
    }

    /**
     * Creation et persistance d'un objet T
     * @param entity
     * @return
     */
    public abstract T insert(T entity) throws SQLException;

    /**
     * Mise à jour et persistance d'un objet T
     * @param entity
     * @return 1 for success 0 for error
     */
    public abstract boolean update(T entity) throws SQLException;

    /**
     * Suppression et persistance d'un objet T
     * @param pId
     * @return 1 for success 0 for error
     */
    public abstract boolean deleteById(Integer pId) throws SQLException;

    public abstract boolean deleteByObj(T entity) throws SQLException;

    /**
     * Recherche par id
     * @param pId
     * @return l'objet T ciblé
     */
    public abstract T findById(Integer pId) throws SQLException;

    /**
     * recherche global
     * @return liste de tous les objets T
     */
    public abstract List<T> findAll() throws SQLException;

    /**
     * Methode de cloture de la connexion
     */
    public abstract void closeConnection() throws SQLException;

    public void lockTable(String ptable) throws SQLException {
        try {
            connection.setAutoCommit(false); // start transaction
            Statement statement = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("LOCK TABLES ? WRITE");
            ps.setString(1, ptable);
            ps.executeUpdate();


            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
                connection.commit(); // Release lock
                throw e;
            }
        }
    }

}
