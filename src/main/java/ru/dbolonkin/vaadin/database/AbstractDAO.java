package ru.dbolonkin.vaadin.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<K extends Number, T> {
    public abstract List<T> findAll(String table) throws IOException, SQLException, ClassNotFoundException;

    public abstract T findEntityById(String table, K id) throws SQLException, IOException, ClassNotFoundException;

    public abstract boolean delete(String table, T entity) throws SQLException, IOException, ClassNotFoundException;

    public abstract boolean create(String table, T entity) throws SQLException, IOException, ClassNotFoundException;

    public abstract T update(String table, T entity) throws IOException, SQLException, ClassNotFoundException;
}
