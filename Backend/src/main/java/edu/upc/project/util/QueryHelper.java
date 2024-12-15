package edu.upc.project.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QueryHelper {

    public static String createQueryINSERT(Object entity) {

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(entity.getClass().getSimpleName().toLowerCase()).append(" ");
        sb.append("(");

        String [] fields = ObjectHelper.getFields(entity);

        sb.append("ID");
        for (String field: fields) {
            if (!field.equals("id")) sb.append(", ").append(field);
        }
        sb.append(") VALUES (?");

        for (String field: fields) {
            if (!field.equals("id"))  sb.append(", ?");
        }
        sb.append(")");
        return sb.toString();
    }

    public static String createQuerySELECT(Class theClass, String[] theColumns) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(theClass.getSimpleName().toLowerCase());
        sb.append(" WHERE ");
        for (int i = 0; i < theColumns.length; i++) {
            sb.append(theColumns[i]).append(" = ?");
            if (i < theColumns.length - 1) {
                sb.append(" AND ");
            }
        }
        return sb.toString();
    }

    public static String createQuerySELECTobject(Object entity) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(entity.getClass().getSimpleName().toLowerCase());
        sb.append(" WHERE ID = ?");

        return sb.toString();
    }
    public static String createQuerySELECTAll(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName().toLowerCase());
        return sb.toString();
    }

    public static String createQueryUPDATE(Object entity) {
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(entity.getClass().getSimpleName().toLowerCase()).append(" SET ");

        // Get the fields of the entity
        String[] fields = ObjectHelper.getFields(entity);

        // Append each field to the SET clause, excluding the ID
        for (String field : fields) {
            if (!field.equalsIgnoreCase("id")) { // Assuming "id" is the primary key
                sb.append(field).append(" = ?, "); // Prepare for parameterized query
            }
        }

        // Remove the last comma and space
        if (fields.length > 0) {
            sb.setLength(sb.length() - 2); // Remove last comma and space
        }

        // Append the WHERE clause using ID to specify which record to update
        sb.append(" WHERE id = ?"); // Assuming ID is the primary key

        return sb.toString();
    }

    public static String createQueryCOUNT(Class<?> theClass) {
        StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ");
        sb.append(theClass.getSimpleName().toLowerCase());
        return sb.toString();
    }

    public static String createQueryDELETE(Class<?> theClass, String theColumn) {
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        sb.append(theClass.getSimpleName().toLowerCase());
        sb.append(" WHERE ").append(theColumn).append(" = ?"); // Placeholder for parameter
        return sb.toString();
    }

    public static String createQuerySELECTAllByUsername(Object entity) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(entity.getClass().getSimpleName().toLowerCase());
        sb.append(" WHERE username = ?");
        return sb.toString();
    }

}
