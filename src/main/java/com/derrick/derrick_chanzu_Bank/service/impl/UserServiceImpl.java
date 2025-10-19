package com.derrick.derrick_chanzu_Bank.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derrick.derrick_chanzu_Bank.dto.AccountInfo;
import com.derrick.derrick_chanzu_Bank.dto.BankResponse;
import com.derrick.derrick_chanzu_Bank.dto.CreditDebitRequest;
import com.derrick.derrick_chanzu_Bank.dto.EmailDetails;
import com.derrick.derrick_chanzu_Bank.dto.EnquiryRequest;
import com.derrick.derrick_chanzu_Bank.dto.TransferRequest;
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

                .accountName(userRequest.getFirstName() + " " + userRequest.getLastName() + " " +userRequest.getOtherName())

        



        
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
        
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

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

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        

        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " +foundUser.getOtherName() ;

    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request){
        //checking if the account exists
        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());

        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)

                    .build();
        }
        
        User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
        //update the information of that user
        //to add bigDecimal is different from integers and doubles we call add method
        

        
         userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
         userRepository.save(userToCredit);

        return BankResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
        .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
        .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " "
                                + userToCredit.getOtherName())
        .accountBalance(userToCredit.getAccountBalance())
        .accountNumber(request.getAccountNumber())

        
        .build())
        .build();


        
    }

    @Override

    public BankResponse debitAccount(CreditDebitRequest request) {
        //check if the account exists
        //check if the amount we intend to withdraw is not more than the account balance

          boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());

        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)

                    .build();
        }
        
        User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());

        //we convert bigDecimal to integers

        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();

        if (availableBalance.intValue() < debitAmount.intValue()) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
            .accountInfo(null)
            .build();
            
        } else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
            userRepository.save(userToDebit);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                            .accountBalance(userToDebit.getAccountBalance())
                    .accountNumber(request.getAccountNumber())
            
                    .build())
            .build();


             
        }
        



       
    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        /*
         * get the account to debit (check if it exists)
         * check if the amount Im debiting is not more than the account balance
         * debit the account
         * get the account to debit
         * credit the account
        */

        
        boolean isDestinationAccountExist = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());


        if (!isDestinationAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)

                    .build();

        }

        User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        if (request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)

                    .build();

        }
        
        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
        String sourceUsername = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName() + " " + sourceAccountUser.getOtherName();
        userRepository.save(sourceAccountUser);

        EmailDetails debitAlert = EmailDetails.builder()
                .subject("DEBIT ALERT!")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("The sum of " +request.getAmount() + " has been deducted from your account. \nYour current balance is: " +sourceAccountUser.getAccountBalance())
        
        
                .build();
        emailService.sendEmailAlert(debitAlert);


        User destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
       // String recipientUserName = destinationAccountUser.getFirstName() + " " +destinationAccountUser.getLastName() +  " " + destinationAccountUser.getOtherName();
        userRepository.save(destinationAccountUser); 

                EmailDetails creditAlert = EmailDetails.builder()
                .subject("CREDIT ALERT!")
                .recipient(destinationAccountUser.getEmail())
                .messageBody("The sum of " +request.getAmount() + " has been sent to your account from" +sourceUsername +" . \nYour current balance is: " +destinationAccountUser.getAccountBalance())

        
        
                .build();
        emailService.sendEmailAlert(debitAlert);

        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
        
        .build();

    }


}
