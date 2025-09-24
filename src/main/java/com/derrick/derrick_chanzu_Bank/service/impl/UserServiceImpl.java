package com.derrick.derrick_chanzu_Bank.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derrick.derrick_chanzu_Bank.dto.BankResponse;
import com.derrick.derrick_chanzu_Bank.dto.UserRequest;
import com.derrick.derrick_chanzu_Bank.entity.User;
import com.derrick.derrick_chanzu_Bank.repository.UserRepository;
import com.derrick.derrick_chanzu_Bank.utils.AccountUtils;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    // ILL now have access to methods in the user repository
 

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        /*
         * creating an account - saving a new user into the db
         * we have to instantiate
         * we then check if the user actually exists
         * 
        */

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            

        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
        
                .lastName(userRequest.getLastName())
        
                .otherName(userRequest.getOtherName())
        
                .gender(userRequest.getGender())
        
                .address(userRequest.getAddress())
        
                .stateOfOrigin(userRequest.getStateOfOrigin())
        
                .accountNumber(AccountUtils.generateAccountNumber())

                .accountBalance(BigDecimal.ZERO)

                .email(userRequest.getEmail())

                .phoneNumber(userRequest.getPhoneNumber())

                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())

                .status("ACTIVE")
        



        
        .build();

        //BigDecimal.valueOf(100);

        
    
        
    }


}
