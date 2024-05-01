package com.ais.recruit.aisr.dao;

import java.util.List;

public interface DAO<T> {

    // retrieves a single object by its ID from the data source.
    T getById(String id);

    //  retrieves all objects from the data source.
    List<T> getAll();

    //adds a new object to the data source or updates an existing one.
    void addOrUpdate(T object);

    // return id based on name
    int getIdFor(T object);

    // removes an object from the data source.
    void remove(T object);
    boolean save();
    boolean load();
}
