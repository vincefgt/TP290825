package DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Singleton {
    private static final Properties props = new Properties();
    private static Connection connection;
    private final String PATHCONF = "config.properties";

    private Singleton() throws SQLException, IOException, ClassNotFoundException {
        try(InputStream is = Singleton.class.getClassLoader().getResourceAsStream(PATHCONF)) {
            props.load(is); // chargement de properties
            Class.forName(props.getProperty("jdbc.driver.class")); // chargement du driver

            // Création de la connection
            String  url = props.getProperty("jdbc.url");
            String  login = props.getProperty("jdbc.login");
            String  password = props.getProperty("jdbc.password");

            connection = DriverManager.getConnection(url, login, password); // création de la connexion
           // System.out.println("Connected to database : " + connection);
        }
    }

    public static Connection getInstanceDB() throws SQLException, IOException, ClassNotFoundException {

        try {
            if (getConnection() == null || getConnection().isClosed()) {
                new Singleton();
                System.out.println("Connected to database : " + getConnection());
            } /*else {
                 System.out.println("Connection already existing");
            }*/
        } finally {

        }
        return connection;
    }

    public static void closeInstanceDB() throws SQLException {
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                getConnection().close();
            }
        } finally {

        }
    }

    private static Connection getConnection() {
        return connection;
    }

    public static void testConnectionWithSingleton() {
        Connection connection = null;
        try {
            connection = Singleton.getInstanceDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(connection);
        System.out.println("---------------------");

    }

}
