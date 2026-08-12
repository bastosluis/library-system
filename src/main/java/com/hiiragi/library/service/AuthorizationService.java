package com.hiiragi.library.service;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.exceptions.InactiveUserException;
import com.hiiragi.library.exceptions.LoanLimitExceededExcetion;
import com.hiiragi.library.exceptions.UnauthorizedException;
import com.hiiragi.library.model.User;

public class AuthorizationService {
    private User user;

    public AuthorizationService(User user){
        this.user = user;
    }

    public void requireRole(UserRole... allowedRoles){
        for (UserRole role : allowedRoles){
            if (user.getRole() == role){
                return;
            }
        }
        throw new UnauthorizedException("User "+user.getLogin()+" is unauthorized to perform this operation.");    
    }

    public void requireActive(){
        if (!user.isActive()){
            throw new InactiveUserException("User "+user.getLogin()+" is inactive.");
        }
    }

    public void requireLoanUnderLimit(){
        if (user.getMaxLoans() < user.getAmountOfLoans()){
            throw new LoanLimitExceededExcetion("User "+user.getLogin()+" has exceeded their limit of "+user.getMaxLoans()+" loans.");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
