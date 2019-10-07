package ru.dbolonkin.vaadin.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<K extends Number, T> {
    public abstract List<T> findAll() throws IOException, SQLException;

    public abstract T findEntityById(K id) throws SQLException, IOException;

    public abstract boolean delete(K id);

    public abstract boolean delete(T entity) throws SQLException, IOException;

    public abstract boolean create(T entity) throws SQLException, IOException;

    public abstract T update(T entity) throws IOException, SQLException;
}
