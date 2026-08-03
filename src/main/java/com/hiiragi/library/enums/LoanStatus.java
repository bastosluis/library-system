package com.hiiragi.library.enums;

public enum LoanStatus {
    ACTIVE("Active"),
    RETURNED("Returned"),
    LATE("Late");

    private final String status;
    
    private LoanStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
