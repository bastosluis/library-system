package com.hiiragi.library.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Category extends BaseEntity{
    private String name;
    private String description;

    @JsonCreator
    public Category(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
