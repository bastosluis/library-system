package com.hiiragi.library.service;

import java.util.List;
import java.util.Optional;

import com.hiiragi.library.exceptions.NotFoundException;
import com.hiiragi.library.model.BaseEntity;
import com.hiiragi.library.repository.CrudRepository;

public abstract class BaseService<
                T extends BaseEntity,
                R extends CrudRepository<T>>{

    protected final R repository; 

    protected BaseService(R repository){
        this.repository = repository;
    }

    public void removeById(Long id){
        boolean removed = this.repository.deleteById(id);
        if (!removed) {
            throw new NotFoundException("Failed to remove entity with id: " + id);
        }
    }

    public T update(T entity, Long id){
        return this.repository.update(entity, id);
    }

    public Optional<T> findById(Long id){
        return this.repository.findById(id);
    }

    public List<T> findAll(){
        return this.repository.findAll();
    }
}
