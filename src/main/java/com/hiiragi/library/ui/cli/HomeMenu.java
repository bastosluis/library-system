package com.hiiragi.library.ui.cli;

import com.hiiragi.library.model.User;

public class HomeMenu implements Menu{

    private User user;
    
    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    public void setUser(User user) {
        this.user = user;
    }

}
