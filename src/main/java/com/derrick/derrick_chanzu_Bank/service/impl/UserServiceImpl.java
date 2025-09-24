package com.derrick.derrick_chanzu_Bank.service.impl;

import com.derrick.derrick_chanzu_Bank.dto.BankResponse;
import com.derrick.derrick_chanzu_Bank.dto.UserRequest;
import com.derrick.derrick_chanzu_Bank.entity.User;

public class UserServiceImpl implements UserService {
 

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        /*
         * creating an account - saving a new user into the db
         * we have to instantiate
         * 
        */

        User newUser = User.builder()

        
        
        .build();
    
        return null;
    }


}
