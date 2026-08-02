package com.hiiragi.library.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hiiragi.library.model.BaseEntity;

public abstract class InMemoryRepository<T extends BaseEntity>
        implements CrudRepository<T> {

    protected final List<T> entities = new ArrayList<>();

    protected Long nextId = 1L;

    @Override
    public T save(T entity) {
        entity.setId(nextId++);
        entities.add(entity);
        return entity;
    }
    
    @Override
    public boolean delete(T entity) {
        return entities.remove(entity);
    }

    @Override
    public Optional<T> findById(Long id) {
        for (T entity : entities) {
            if (entity.getId().equals(id)) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id){
        return findById(id)
                .map(this::delete)
                .orElse(false);
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(entities);
    }

    @Override
    public boolean isEmpty() {
        return entities.isEmpty();
    }
}