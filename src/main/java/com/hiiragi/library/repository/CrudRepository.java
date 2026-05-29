package com.hiiragi.library.repository;

import java.util.List;

public interface CrudRepository<T> {
    T save(T entity);

    T findById(Long id);

    void deleteById(Long id);

    List<T> findAll();

    void delete(T entity);
}