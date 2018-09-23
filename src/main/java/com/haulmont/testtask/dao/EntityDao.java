package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Entity;

import java.util.List;


public interface EntityDao<T extends Entity> {
    List<T> getAll();

    T get(long id);

    void update(T entity);

    void add(T entity);

    void remove(T entity);

    List<T> find(T entity);

    void rollback();

    void closeSession();
}
