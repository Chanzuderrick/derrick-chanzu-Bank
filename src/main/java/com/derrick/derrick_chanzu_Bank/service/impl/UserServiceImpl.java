package com.derrick.derrick_chanzu_Bank.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derrick.derrick_chanzu_Bank.dto.AccountInfo;
import com.derrick.derrick_chanzu_Bank.dto.BankResponse;
import com.derrick.derrick_chanzu_Bank.dto.EmailDetails;
import com.derrick.derrick_chanzu_Bank.dto.EnquiryRequest;
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
    .subject("WELCOME TO DERRICK CHANZU BANK, YOUR NEW ACCOUNT IS READY")
    .messageBody(
        "Dear " + savedUser.getFirstName() + 
        ", \n\n We are delighted to inform you that your account has been successfully created!\n" +
        "Here are your account details:\n\n" +
        "Account name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName() + "\n" +
        "Account number: " + savedUser.getAccountNumber() + "\n\n" +
        "We are excited to have you on board. Thank you for choosing Derrick Chanzu Bank! \n\n Best regards, \n Derrick Chanzu Bank Team, \n Customer Support: 0742804467"
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

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request){
        //check if the accont Number provided exists
        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)

                    .build();
        }
        
        User foundUser = userRepository.findByAccountName(request.getAccountNumber());

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " "
                                + foundUser.getOtherName())
                
                .build())

        
        .build();

    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {

         boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExists) {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }

        User foundUser = userRepository.findByAccountName(request.getAccountNumber());
        

        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " +foundUser.getOtherName() ;

    }


}
