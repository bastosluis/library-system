package com.hiiragi.library.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hiiragi.library.exceptions.NotFoundException;
import com.hiiragi.library.model.BaseEntity;

public abstract class InMemoryRepository<T extends BaseEntity>
        implements CrudRepository<T> {

    protected final List<T> entities = new ArrayList<>();

    protected Long nextId = 1L;

    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(nextId++);
            entities.add(entity);
        } else {
            int index = entities.indexOf(entity);
            entities.set(index, entity);
        }

        return entity;
    }
    
    @Override
    public T update(T entity, Long id) throws NotFoundException{
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId().equals(id)) {
                entities.set(i, entity);
                return entity;
            }
        }

        throw new NotFoundException("id: "+id);
    }
    
    @Override
    public boolean delete(T entity) {
        return entities.remove(entity);
    }

    @Override
    public Optional<T> findById(Long id) {
                return entities.stream()
                        .filter((entity) -> entity.getId().equals(id))
                        .findAny();
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