package edu.upc.project.db;

import edu.upc.project.models.ItemType;
import edu.upc.project.util.ObjectHelper;
import edu.upc.project.util.QueryHelper;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SessionImpl implements Session {
    private final Connection conn;

    public SessionImpl(Connection conn) {
        this.conn = conn;
    }

    public void save(Object entity) {
        String insertQuery = QueryHelper.createQueryINSERT(entity);
        // INSERT INTO User (ID, lastName, firstName, address, city) VALUES (0, ?, ?, ?,?)
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(insertQuery);
            pstm.setObject(1, 0);
            int i = 2;

            for (String field: ObjectHelper.getFields(entity)) {
                pstm.setObject(i++, ObjectHelper.getter(entity, field));
            }

            pstm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() throws SQLException
    {
        try {
            this.conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object get(Class theClass, String[] theColumns, Object[] values) throws SQLException {
        String selectQuery  = QueryHelper.createQuerySELECT(theClass, theColumns);
        ResultSet rs;
        PreparedStatement pstm = null;
        boolean empty = true;

        try {
            pstm = conn.prepareStatement(selectQuery);
            for (int i = 0; i < values.length; i++)
            {
                pstm.setObject(i+1, values[i]); //son los ?
            }
            rs = pstm.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            int numberOfColumns = rsmd.getColumnCount();

            Object o = theClass.newInstance();

            Object valueColumn = null;
            while (rs.next())
            {
                for (int i = 1; i <= numberOfColumns; i++)
                {
                    String columnName = rsmd.getColumnName(i);
                    Object value = rs.getObject(i);

                    //Handle ENUM conversion specifically for the 'type' column in the Item table
                    if ("type".equalsIgnoreCase(columnName) && value instanceof String) {
                        String enumValue = (String) value;
                        try {
                            // Assuming ItemType is your enum class
                            ItemType itemType = ItemType.valueOf(enumValue.toUpperCase());
                            ObjectHelper.setter(o, columnName, itemType);
                        } catch (IllegalArgumentException e) {
//                            System.err.println("Invalid enum value for column 'type': " + enumValue);
                        }
                    } else {
                        ObjectHelper.setter(o, columnName, value);
                    }
                }
            }
            return o;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void update(Object entity) throws SQLException {
        String updateQuery = QueryHelper.createQueryUPDATE(entity);
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(updateQuery);

            // Get fields and their values
            String[] fields = ObjectHelper.getFields(entity);
            int index = 1;

            // Set values for each field except ID (assuming ID is the last field)
            for (String field : fields) {
                if (!field.equalsIgnoreCase("id")) { // Assuming "id" is the primary key
                    Object value = ObjectHelper.getter(entity, field);
                    pstm.setObject(index++, value); // Set value for each field
                }
            }

            // Set the ID value for the WHERE clause
            Object idValue = ObjectHelper.getter(entity, "id");
            pstm.setObject(index, idValue); // Set ID value for WHERE clause

            // Execute the update
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (pstm != null) {
                pstm.close(); // Ensure resources are released
            }
        }
    }

    public void delete(Object object) throws SQLException {
        String tableName = object.getClass().getSimpleName().toLowerCase();
        String[] fields = ObjectHelper.getFields(object);
        String deleteQuery = "DELETE FROM " + tableName + " WHERE ";

        for (int i = 0; i < fields.length; i++) {
            deleteQuery += fields[i] + " = ?";
            if (i < fields.length - 1) {
                deleteQuery += " AND ";
            }
        }

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(deleteQuery);

            for (int i = 0; i < fields.length; i++) {
                Object value = ObjectHelper.getter(object, fields[i]);
                System.out.println("Setting parameter " + (i + 1) + ": " + value);
                pstm.setObject(i + 1, value);
            }

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }
    }

    public List<Object> getAll(Class theClass) throws SQLException {
        String selectQuery = QueryHelper.createQuerySELECTAll(theClass);
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<Object> resultList = new ArrayList<>();

        try {
//            System.out.println("Executing query: " + selectQuery);
            pstm = conn.prepareStatement(selectQuery);
            rs = pstm.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
//            System.out.println("Number of columns: " + numberOfColumns);

            // Print all column names and types
//            for (int i = 1; i <= numberOfColumns; i++) {
//                System.out.println("Column " + i + ": " + rsmd.getColumnName(i) +
//                        " (Type: " + rsmd.getColumnTypeName(i) + ")");
//            }

            while (rs.next()) {
                Object obj = theClass.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= numberOfColumns; i++) {
                    String columnName = rsmd.getColumnName(i);
                    Object value = rs.getObject(i);

                    //Handle ENUM conversion specifically for the 'type' column in the Item table
                    if ("type".equalsIgnoreCase(columnName) && value instanceof String) {
                        String enumValue = (String) value;
                        try {
                            // Assuming ItemType is your enum class
                            ItemType itemType = ItemType.valueOf(enumValue.toUpperCase());
                            ObjectHelper.setter(obj, columnName, itemType);
                        } catch (IllegalArgumentException e) {
//                            System.err.println("Invalid enum value for column 'type': " + enumValue);
                        }
                    } else {
                        ObjectHelper.setter(obj, columnName, value);
                    }
                }
//                System.out.println("Retrieved object: " + obj);
                resultList.add(obj);
            }

//            System.out.println("Retrieved " + resultList.size() + " objects");
            return resultList;

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            System.err.println("Reflective Operation Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstm != null) try { pstm.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return resultList;
    }

    public List<Object> query(String query, Class theClass, HashMap params) {
        return null;
    }
}
