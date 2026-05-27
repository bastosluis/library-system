package com.hiiragi.library.enums;

public enum BookStatus {
    AVAILABLE("Available"),
    BORROWED("Borrowed"),
    RESERVED("Reserved"),
    LOST("Lost"),
    DAMAGED("Damaged");

    private final String status;

    private BookStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
