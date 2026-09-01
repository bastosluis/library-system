package com.hiiragi.library.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
    //*The id value itself will be handled by the repository classes*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id = null;
    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
