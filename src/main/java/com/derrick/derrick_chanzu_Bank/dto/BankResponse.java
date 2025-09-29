package com.derrick.derrick_chanzu_Bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BankResponse {
    /*
     * we also want to define a response
     * I want a single response for every single service
     * we will also make use of composition
     * after sending the info in the userRequest we will be expecting this response
     * 
    */

    private String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo;



}
