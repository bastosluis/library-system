package com.hiiragi.library.repository;

import java.util.List;

public interface CrudRepository<T> {
    T save(T entity);

    T findById(Long id);

    boolean deleteById(Long id);

    List<T> findAll();

    boolean delete(T entity);

    boolean isEmpty();
}