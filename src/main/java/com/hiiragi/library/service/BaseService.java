package com.hiiragi.library.service;

import java.util.List;

import com.hiiragi.library.model.BaseEntity;
import com.hiiragi.library.repository.CrudRepository;

public abstract class BaseService<
                T extends BaseEntity,
                R extends CrudRepository<T>>{

    protected final R repository; 

    public BaseService(R repository){
        this.repository = repository;
    }

    public T add(T entity){
        return this.repository.save(entity);
    }

    public boolean removeById(Long id){
        return this.repository.deleteById(id);
    }

    public T findById(Long id){
        return this.repository.findById(id);
    }

    public List<T> findAll(){
        return this.repository.findAll();
    }
}
