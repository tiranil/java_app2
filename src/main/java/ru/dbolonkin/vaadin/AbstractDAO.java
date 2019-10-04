package ru.dbolonkin.vaadin;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<K extends Number, T> {
    public abstract List<T> findAll();

    public abstract T findEntityById(K id);

    public abstract boolean delete(K id);

    public abstract boolean delete(T entity);

    public abstract boolean create(T entity) throws SQLException;

    public abstract T update(T entity);
}
