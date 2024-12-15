package edu.upc.project.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface Session<E> {
    void save(Object entity);                                           // Crud
    void close() throws SQLException;
    Object get(Class theClass, String[] theColumns, Object[] values) throws SQLException;         // cRud
    void update(Object object) throws SQLException;                                         // crUd
    void delete(Object object) throws SQLException;                                         // cruD
    List<Object> getAll(Class theClass) throws SQLException;
    List<Object> query(String query, Class theClass, HashMap params);
}
