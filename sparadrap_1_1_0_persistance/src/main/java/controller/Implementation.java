package controller;

import jdk.jfr.Category;
import model.*;
import view.SparadrapMainInterface;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import static controller.PharmacieController.getListMed;
import static controller.SpraradrapMainPersistance.drugs;

public class Implementation {
    //public static Categories Cat;

    public static List<Map<String, Object>> getTableData(Connection connection, String tableName) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        String query = "SELECT * FROM " + tableName; // Query to get data

        try {
            Statement statement = connection.createStatement(); // establishment d'un statement
            ResultSet resultSet = statement.executeQuery(query);

            // Récupération des métadonnées pour connaître les colonnes
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap<>();

                // Pour chaque colonne, on ajoute la valeur dans la Map
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    row.put(columnName, value);
                }

                dataList.add(row);
            }

            resultSet.close();
            statement.close();

            System.out.println("✓ " + dataList.size() + " lignes récupérées de la table '" + tableName + "' (" + columnCount + " colonnes)");

            return dataList;

        } catch(
                SQLException e)

        {
            throw new RuntimeException(e);
        }
    }

    public static  List<Medicament> selectFromDrugs(Connection connection) {

    String select = "select id_drugs,name_drugs,name_cat,price_drugs,date_market,stock from drugs\n" +
            "INNER JOIN categories CA ON ca.id_cat=drugs.id_cat;";

    List<Medicament> drugs = new ArrayList<>();

    try {
        Statement statement = connection.createStatement(); // etablissement d'un statement
        ResultSet resultSet = statement.executeQuery(select);
        while (resultSet.next()) {

            // utilisation du resultSet pour récuperer les valeurs de chaques colonnes ciblées par le nom
            int numab = resultSet.getInt("id_drugs");
            String nom = resultSet.getString("name_drugs");
            String cat = resultSet.getString("name_cat");
            double price = resultSet.getDouble("price_drugs");
            String date = resultSet.getString("date_market");
            int stock = resultSet.getInt("stock");
            Medicament drug = new Medicament(numab, nom, cat, price, date, stock);
            drugs.add(drug);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return drugs;
    } // list Drugs
    public static  List<Mutuelle> selectFromMutuelle(Connection connection) {

        String select = "select mutuelle.id_mutuelle, lastname, tauxRemb, phone, email, street, op_city, name_city from mutuelle\n" +
                "INNER JOIN person ON person.id_person=mutuelle.id_person\n"+
                "INNER JOIN address ON address.id_address=person.id_address\n" +
                "INNER JOIN city on city.id_city=address.id_city;";

        List<Mutuelle> muts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement(); // etablissement d'un statement
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {

                // utilisation du resultSet pour récuperer les valeurs de chaques colonnes ciblées par le nom
                int numab = resultSet.getInt("mutuelle.id_mutuelle");
                String nom = resultSet.getString("lastname");
                double tauxRemb = resultSet.getDouble("tauxRemb");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String street = resultSet.getString("street");
                int op_city = resultSet.getInt("op_city");
                String name_city = resultSet.getString("name_city");

                Mutuelle mut = new Mutuelle(numab, nom, "Mutuelle",street,email,op_city,name_city,phone, tauxRemb);
                muts.add(mut);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return muts;
    } //list Mutuelles
    public static  List<Client> selectFromClients(Connection connection) {

        String select = "select id_person, firstname, lastname, nSS, phone, email, dob, street, op_city, name_city ,dob from person\n" +
                "INNER JOIN address ON address.id_address=person.id_address\n" +
                "INNER JOIN city on city.id_city=address.id_city\n" +
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

                Client clt = new Client(numId, prenom, nom, street, op_city, name_city, phone, email, nSS, dob_client, null, null );
                clients.add(clt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    } // list Clients
    public static  List<Medecin> selectFromDoctors(Connection connection) {

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
    } //list doctors
    /*public static  List<Ordonnance> selectFromPrescription(Connection connection) {

        //String select = "select p.id_prescription, date_prescription, person.firstname, person.lastname, id_doctor from prescription p\n" +
        String select = "select * from prescription p\n" +
                "INNER JOIN `contains` ON `contains`.id_prescription=p.id_prescription\n"+
                "INNER JOIN person ON person.id_person=p.id_person";

        List<Ordonnance> preps = new ArrayList<>();

        try {
            Statement statement = connection.createStatement(); // etablissement d'un statement
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {

                // utilisation du resultSet pour récuperer les valeurs de chaques colonnes ciblées par le nom
                int numid = resultSet.getInt("p.id_prescription");
                LocalDate date_prep = resultSet.getDate("p.date_prescription").toLocalDate();
                int id_person = resultSet.getInt("p.id_person");
                int id_doctor = resultSet.getInt("p.id_doctor");

                //selectFromByIdPerson(connection,id_person);
                //selectFromByIdDoctor(connection,id_doctor);
                //Client patient = new Client(id_person, prenom, nom, street, op_city, name_city, phone, email, nSS, dob, null, null);
                Ordonnance ordo = new Ordonnance (numid, date_prep, selectFromByIdDoctor(connection,id_doctor), selectFromByIdPerson(connection,id_person)); //street, email,op_city, name_city,phone, nb_agreement, numab);
                //Medecin medecin = new Medecin (prenom, nom, email, name_city,phone, nb_agreement, numab);
                preps.add(ordo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preps;
    } //list ordo */

    /*public static  List<?> selectFromCategories(Connection connection) {

        String select = "select name_cat from categories";

        List<?> categories = new ArrayList<>();

        try {
            Statement statement = connection.createStatement(); // etablissement d'un statement
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {

                // utilisation du resultSet pour récuperer les valeurs de chaques colonnes ciblées par le nom
                String cat = resultSet.getString("name_cat");

                Cat drug = new Medicament(numab, nom, cat, price, date, stock);
                categories.add(cat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return drugs;
    }*/

    public static Client selectFromByIdPerson(Connection connection, int id_Client) {
        String selectById = "select * from person p\n"+
                            "INNER JOIN address ON address.id_address=person.id_address\n" +
                            "INNER JOIN city on city.id_city=address.id_city\n" +
                            "where p.id_person = ?";
        Client clt = new Client();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectById); // preparation de la requête
            preparedStatement.setInt(1, id_Client); // injection des parametres de la requête
            ResultSet resultSet = preparedStatement.executeQuery(); // execution de la requête

            while (resultSet.next()) {
                clt.setEmail(resultSet.getString("email"));
                clt.setLastName(resultSet.getString("lastname"));
                clt.setFirstName(resultSet.getString("firstname"));
                clt.setPhone(resultSet.getString("phone"));
                clt.setAddress(resultSet.getString("street"));
                clt.setCity(resultSet.getString("name_city"));
                clt.setDateBirth(resultSet.getDate("dob").toLocalDate());
            }

        } catch (SQLException e) {
            System.err.println("Error getting Client by id " + id_Client);
        }
        return clt;
    }
    public static Medecin selectFromByIdDoctor(Connection connection, int id_doctor) {
        String selectById = "select * from doctor \n" +
                "INNER JOIN person ON person.id_person=doctor.id_person\n" +
                "INNER JOIN address ON address.id_address=person.id_address\n" +
                "INNER JOIN city on city.id_city=address.id_city;" +
                "where doctor.id_doctor = ?";

        Medecin doc = new Medecin();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectById); // preparation de la requête
            preparedStatement.setInt(1, id_doctor); // injection des parametres de la requête
            ResultSet resultSet = preparedStatement.executeQuery(); // execution de la requête

            while (resultSet.next()) {
                doc.setEmail(resultSet.getString("email"));
                doc.setLastName(resultSet.getString("lastname"));
                doc.setFirstName(resultSet.getString("firstname"));
                doc.setPhone(resultSet.getString("phone"));
                doc.setAddress(resultSet.getString("street"));
                doc.setCity(resultSet.getString("name_city"));
                doc.setNbAgreement(resultSet.getInt("nb_agreement"));
            }

        } catch (SQLException e) {
            System.err.println("Error getting doctor by id " + id_doctor);
        }
        return doc;
    }

    public static int insertIntoDoctor(Connection connection, Medecin newDoctor) {
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

        return inserted;
    }
    public static boolean updateIntoDoctor(Connection connection, Medecin Doctor, String firstname, String lastname) {
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
                    preparedStatement.setString(1, firstname);
                    preparedStatement.setString(2, lastname);
                    preparedStatement.setInt(3, Doctor.getIdMedecin());
                    preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                selectFromDoctors(connection);
            }

        } catch (SQLException e) {
            System.err.println("Error inserting doctor " + e.getMessage());
        }
        return true;
    }
    public static boolean deteleIntoDoctor(Connection connection, Medecin Doctor) {
        int inserted = 0; // valeur de l'id par défaut
        // requête
        StringBuilder deleteIntoDoctor = new StringBuilder();
        //deleteIntoDoctor.append("delete from doctor where id_doctor = ?");
        deleteIntoDoctor.append("delete from person where id_person=(select id_person from doctor where id_doctor = ?)");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    deleteIntoDoctor.toString(),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, Doctor.getIdMedecin());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting doctor " + e.getMessage());
        }
        return true;
    }


}