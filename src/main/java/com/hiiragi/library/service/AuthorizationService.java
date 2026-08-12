package com.hiiragi.library.service;

import com.hiiragi.library.application.Session;
import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.exceptions.EmptySessionException;
import com.hiiragi.library.exceptions.InactiveUserException;
import com.hiiragi.library.exceptions.LoanLimitExceededExcetion;
import com.hiiragi.library.exceptions.UnauthorizedException;
import com.hiiragi.library.model.User;
public class AuthorizationService {
    private Session session;

    public AuthorizationService(Session session){
        this.session = session;
    }

    public void requireRole(UserRole... allowedRoles){
        User user = session.getCurrentUser().orElseThrow(() -> new EmptySessionException());
        for (UserRole role : allowedRoles){
            if (user.getRole() == role){
                return;
            }
        }
        throw new UnauthorizedException("User "+user.getLogin()+" is unauthorized to perform this operation.");    
    }

    public void requireActive(){
        User user = session.getCurrentUser().orElseThrow(() -> new EmptySessionException());
        if (!user.isActive()){
            throw new InactiveUserException("User "+user.getLogin()+" is inactive.");
        }
    }

    public void requireLoanUnderLimit(){
        User user = session.getCurrentUser().orElseThrow(() -> new EmptySessionException());
        if (user.getMaxLoans() <= user.getAmountOfLoans()){
            throw new LoanLimitExceededExcetion("User "+user.getLogin()+" has exceeded their limit of "+user.getMaxLoans()+" loans.");
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
