package com.hiiragi.library.model;

public abstract class BaseEntity {
    //*The id value itself will be handled by the repository classes*/
    protected Long id = null;
    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
