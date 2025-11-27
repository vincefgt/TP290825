package DAO;

import logger.MySlf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.mysql.cj.conf.PropertyKey.profileSQL;

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
           // ?logger=com.mysql.cj.log.StandardLogger&profileSQL=true;
           // System.out.println("Connected to database : " + connection);
        }
    }

    public static Connection getInstanceDB() throws SQLException, IOException, ClassNotFoundException {
        try {
            if (getConnection() == null || getConnection().isClosed()) {
                new Singleton();
            }
        } finally {
        }
        return connection;
    }

    public static void closeInstanceDB() throws SQLException {
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                getConnection().close();
                MySlf4j.getLogger().info("Connection closed."+getConnection());
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
            MySlf4j.getLogger().info("Connected to database: " + connection);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            MySlf4j.getLogger().error("Connection failed "+e.getMessage());
        }

    }

}
