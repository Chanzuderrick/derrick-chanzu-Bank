package com.derrick.derrick_chanzu_Bank.service.impl;

import com.derrick.derrick_chanzu_Bank.dto.BankResponse;
import com.derrick.derrick_chanzu_Bank.dto.CreditDebitRequest;
import com.derrick.derrick_chanzu_Bank.dto.EnquiryRequest;
import com.derrick.derrick_chanzu_Bank.dto.TransferRequest;
import com.derrick.derrick_chanzu_Bank.dto.UserRequest;


public interface UserService {
    //we create a bank account for the user and use userRequest in the parameters. the data type is userRequest
    //we send an object of user request 
    BankResponse createAccount(UserRequest userRequest); //bank response is the return type/ data type
    BankResponse balanceEnquiry(EnquiryRequest request);

    String nameEnquiry(EnquiryRequest request);

    BankResponse creditAccount(CreditDebitRequest request);

    BankResponse debitAccount(CreditDebitRequest request);

    BankResponse transfer(TransferRequest request);

}
