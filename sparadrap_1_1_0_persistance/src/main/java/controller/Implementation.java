package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

public class Implementation {

    public List<Map<String, Object>> getTableData(Connection connection, String tableName) {
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
}
