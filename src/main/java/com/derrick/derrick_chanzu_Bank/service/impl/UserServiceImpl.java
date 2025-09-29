package com.derrick.derrick_chanzu_Bank.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derrick.derrick_chanzu_Bank.dto.AccountInfo;
import com.derrick.derrick_chanzu_Bank.dto.BankResponse;
import com.derrick.derrick_chanzu_Bank.dto.EmailDetails;
import com.derrick.derrick_chanzu_Bank.dto.UserRequest;
import com.derrick.derrick_chanzu_Bank.entity.User;
import com.derrick.derrick_chanzu_Bank.repository.UserRepository;
import com.derrick.derrick_chanzu_Bank.utils.AccountUtils;

import lombok.Builder;

@Service
@Builder


public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;


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
            return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
            .accountInfo(null)

            .build();

           
            

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

        //we then save the user to the database

        User savedUser = userRepository.save(newUser);
        
        //send email alerts
       

        EmailDetails emailDetails = EmailDetails.builder()
    .recipient(savedUser.getEmail())
    .subject("ACCOUNT CREATION")
    .messageBody(
        "Congratulations, " + savedUser.getFirstName() + 
        " your account has been successfully created!\n" +
        "Your account details are:\n" +
        "Account name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName() + "\n" +
        "Account number: " + savedUser.getAccountNumber() + "\n\n" +
        "Thank you for banking with us!"
    )
    .build();


        emailService.sendEmailAlert(emailDetails);

        return BankResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
        .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)

        .accountInfo(AccountInfo.builder()
        .accountBalance(savedUser.getAccountBalance())
        .accountNumber(savedUser.getAccountNumber())
        .accountName(savedUser.getFirstName() + " " +savedUser.getLastName() + " " +savedUser.getOtherName())
        



        .build()
        
        )

        
        .build();

        
    
        
    }


}
