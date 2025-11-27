package BDD;

import logger.MySlf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionBdd {
        public static Connection connectBdd () {
            Connection connection = null;
            final String pathConfig = "config.properties";  // nom du fichier de config
            Properties props = new Properties(); // variable properties

            // chargement du fichier de config
            try (InputStream is = ConnectionBdd.class.getClassLoader().getResourceAsStream(pathConfig)) {
                props.load(is);
            } catch (IOException e1) {
                MySlf4j.getLogger().error("Error loading properties file: %s\n{}", e1.getMessage());
            }

            try {
                Class.forName(props.getProperty("jdbc.driver.class")); // chargement du driver
                // Création de la connection
                String url = props.getProperty("jdbc.url");
                String login = props.getProperty("jdbc.login");
                String password = props.getProperty("jdbc.password");
                connection = DriverManager.getConnection(url, login, password);

            } catch (ClassNotFoundException e) {
                MySlf4j.getLogger().error(e.getMessage());
                System.err.printf("Error loading JDBC Driver: %s\n", e.getMessage()); // si driver introuvale
            } catch (SQLException e) {
                System.err.printf("Error Connexion JDBC Driver: %s\n", e.getMessage()); // si connesion impossible
                MySlf4j.getLogger().error(e.getMessage());
            }
            return connection;
        }

        public static void close_BDD (Connection connection){
            if (connection != null) {
                try {
                    connection.close();
                    MySlf4j.getLogger().info("Deconnected to dababase {}", String.valueOf(connection));
                } catch (SQLException e) {
                    MySlf4j.getLogger().error("Error closing connection {}", e.getMessage());
                }

            }
        }
    }
