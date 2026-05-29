package com.hiiragi.library.repository;

import java.util.ArrayList;
import java.util.List;

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
    public void delete(T entity) {
        entities.remove(entity);
    }

    @Override
    public T findById(Long id) {
        for (T entity : entities) {
            if (entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public void deleteById(Long id){
        delete(findById(id));
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(entities);
    }
}