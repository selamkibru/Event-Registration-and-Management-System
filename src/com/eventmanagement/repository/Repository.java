package com.eventmanagement.repository;

import java.util.List;

public interface Repository<T> {
    void save(T item);           // Create or Update
    List<T> findAll();           // Read all
    T findById(String id);       // Read single
    void delete(String id);      // Delete
}

