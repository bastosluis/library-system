package com.hiiragi.library.model;
import java.util.ArrayList;

import com.hiiragi.library.enums.UserRole;

public class User {
    private Long id = null;
    private String name;
    private String email;
    private String phone;
    private boolean isActive; // Whether they can use our library or not
    private int maxLoans; // How many they can borrow at the same time
    private UserRole role; //Admin, Librarian or Member
    private ArrayList<BookCopy> borrowedCopies = null;

    public User(String name, String email, String phone, boolean isActive, int maxLoans, UserRole role){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.maxLoans = maxLoans;
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format("Name: %s%nRole: %s%nId: %d%nEmail: %s%nPhone: %s%nActive : %b%nMax Loans: %d%n", name, role.getLabel(), id, email, phone, isActive, maxLoans);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getMaxLoans() {
        return maxLoans;
    }

    public void setMaxLoans(int maxLoans) {
        this.maxLoans = maxLoans;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void addBorrowedBookCopy(BookCopy copy){
        this.borrowedCopies.add(copy);
    }
}
