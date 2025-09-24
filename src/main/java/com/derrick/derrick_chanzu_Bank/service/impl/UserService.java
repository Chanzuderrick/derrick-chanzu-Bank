package com.derrick.derrick_chanzu_Bank.service.impl;

import com.derrick.derrick_chanzu_Bank.dto.BankResponse;
import com.derrick.derrick_chanzu_Bank.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest); //bank response is the return type/ data type

}
