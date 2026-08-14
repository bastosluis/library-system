package com.hiiragi.library.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Author extends BaseEntity{
    private String name;
    private String nationality;

    @JsonCreator
    public Author(
        @JsonProperty("name") String name,
        @JsonProperty("nationality") String nationality) 
        {
        this.name = name;
        this.nationality = nationality;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
