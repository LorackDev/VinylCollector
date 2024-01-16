package com.example.vinylcollector;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author Lorenz
 * Includes functions that let you load, edit and delete data from the database.
 */
public class VinylDataExchange {

    private static final String jdbcUrl = "jdbc:sqlite:src\\main\\resources\\com\\example\\vinylcollector\\VinylCollectorDB.db";

    public static void writeIntoDataBase(String[] values) {
            addToDataBase(values);
    }

    public static void writeIntoDataBase(String[] values, String idToCheck) {
        if (!idExists(idToCheck))
            addToDataBase(values);
        if (idExists(idToCheck))
            updateInDatabase(values, idToCheck);
    }

    private static void addToDataBase(String[] values) {
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, "Vinyl", null);
            ArrayList<String> columnNamesList = new ArrayList<>();

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                columnNamesList.add(columnName);
            }

            columnNamesList.remove(0);


            StringBuilder sqlBuilder = new StringBuilder("INSERT INTO Vinyl (");
            for (String columnName : columnNamesList) {
                sqlBuilder.append(columnName).append(", ");
            }
            sqlBuilder.setLength(sqlBuilder.length() - 2);
            sqlBuilder.append(") VALUES (");
            for (int i = 0; i < values.length; i++) {
                sqlBuilder.append("?, ");
            }
            sqlBuilder.setLength(sqlBuilder.length() - 2);
            sqlBuilder.append(")");


            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString())) {

                int parameterIndex = 1;
                for (String columnName : columnNamesList) {
                    if (!columnName.equals("2022")) {
                        preparedStatement.setString(parameterIndex, values[parameterIndex - 1]);
                    } else {
                        preparedStatement.setInt(parameterIndex, Integer.parseInt(values[parameterIndex - 1]));
                    }
                    parameterIndex++;
                }

                preparedStatement.executeUpdate();
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateInDatabase(String[] values, String id) {
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, "Vinyl", null);
            ArrayList<String> columnNamesList = new ArrayList<>();

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                columnNamesList.add(columnName);
            }

            columnNamesList.remove(0);

            StringBuilder updateSql = new StringBuilder("UPDATE Vinyl SET ");
            for (String columnName : columnNamesList) {
                updateSql.append(columnName).append(" = ?, ");
            }
            updateSql.setLength(updateSql.length() - 2);
            updateSql.append(" WHERE Id = ?");

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql.toString())) {
                int parameterIndex = 1;
                for (String columnName : columnNamesList) {
                    if (!columnName.equals("2022")) {
                        preparedStatement.setString(parameterIndex, values[parameterIndex - 1]);
                    } else {
                        preparedStatement.setInt(parameterIndex, Integer.parseInt(values[parameterIndex - 1]));
                    }
                    parameterIndex++;
                }

                preparedStatement.setString(parameterIndex, id);

                preparedStatement.executeUpdate();
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromDatabase(String id) {
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);

            String deleteSql = "DELETE FROM Vinyl WHERE Id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {

                preparedStatement.setString(1, id);

                preparedStatement.executeUpdate();
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static String[] getRowById(String id) {
        String[] values = null;

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);

            String sql = "SELECT * FROM Vinyl WHERE Id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        values = new String[columnCount];

                        for (int i = 1; i <= columnCount; i++) {
                            values[i - 1] = resultSet.getString(i);
                        }
                    }
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return values;
    }
    public static String[][] getFullDataTable() {
        String[][] vinylArray = null;

        try {

            Connection connection = DriverManager.getConnection(jdbcUrl);
            String sql = "SELECT * FROM Vinyl";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                ArrayList<String[]> rows = new ArrayList<>();
                while (resultSet.next()) {
                    String[] rowData = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = resultSet.getString(i + 1);
                    }
                    rows.add(rowData);
                }
                vinylArray = new String[rows.size()][columnCount];
                vinylArray = rows.toArray(vinylArray);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vinylArray;
    }

    private static boolean idExists(String idToCheck) {
        try {

            Connection connection = DriverManager.getConnection(jdbcUrl);

            String sql = "SELECT COUNT(*) FROM Vinyl WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, idToCheck);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}