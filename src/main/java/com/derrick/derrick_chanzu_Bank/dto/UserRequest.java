package com.derrick.derrick_chanzu_Bank.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //for creating objects
@AllArgsConstructor
@NoArgsConstructor


public class UserRequest {

    /*
     * basically, dto is data transfer objects
     * its not everything in the entity class that I want to expose
     * it helps me to create my objects
     * helps me collect data from the user to the database
    */

    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;
    private String stateOfOrigin;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String status;

}
