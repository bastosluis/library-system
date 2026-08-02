package com.hiiragi.library.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    T save(T entity);

    Optional<T> findById(Long id);

    boolean deleteById(Long id);

    List<T> findAll();

    boolean delete(T entity);

    boolean isEmpty();
}