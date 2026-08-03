package com.hiiragi.library.model;
import java.util.ArrayList;
import java.util.List;

import com.hiiragi.library.enums.UserRole;

public class User extends BaseEntity{
    private String name;
    private String email;
    private String phone;
    private boolean isActive; // Whether they can use our library or not
    private int maxLoans; // How many they can borrow at the same time
    private UserRole role; //Admin, Librarian or Member
    private ArrayList<BookCopy> borrowedCopies;
    private String login;
    private String password;

    
    public User(String name, String email, String phone, boolean isActive, int maxLoans, UserRole role, ArrayList<BookCopy> borrowedCopies, String login, String password){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.maxLoans = maxLoans;
        this.role = role;
        this.borrowedCopies = borrowedCopies;
        this.login = login;
        this.password = password;
    }
    
    public User(String name, String email, String phone, boolean isActive, int maxLoans, UserRole role, String login, String password){
        this(name, email, phone, isActive, maxLoans, role, new ArrayList<>(), login, password);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return String.format("Name: %s%nRole: %s%nId: %d%nEmail: %s%nPhone: %s%nActive : %b%nMax Loans: %d%n", name, role.getLabel(), id, email, phone, isActive, maxLoans);
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

    public void borrow(BookCopy copy){
        this.borrowedCopies.add(copy);
    }

    public List<BookCopy> getBorrowedCopies(){
        return this.borrowedCopies;
    }
}
