package com.hiiragi.library.enums;

public enum UserRole {
    ADMIN("Admin"),
    LIBRARIAN("Librarian"),
    MEMBER("Member");

    private final String label;
    
    UserRole(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
