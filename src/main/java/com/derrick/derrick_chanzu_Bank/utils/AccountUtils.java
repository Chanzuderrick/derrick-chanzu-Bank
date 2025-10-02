package com.derrick.derrick_chanzu_Bank.utils;

import java.time.Year;
import java.util.Random;

public class AccountUtils {
    /*
     * I want my account number to always begin with the current year
     * then any random 6 digits
     * I need to concatenate the year and the 6 random digits
     * 
     * 
    */

   public static final String ACCOUNT_EXISTS_CODE = "001";

   public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account created";

   public static final String ACCOUNT_CREATION_SUCCESS = "002";

   public static final String ACCOUNT_CREATION_MESSAGE = "Account has been successfully created!";

   public static final String ACCOUNT_NOT_EXIST_CODE = "003";

   public static final String ACCOUNT_NOT_EXIST_MESSAGE = "User with the provided Account Number does not exist";

   public static final String ACCOUNT_FOUND_CODE = "004";
   
   public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";

   public static final String ACCOUNT_CREDITED_SUCCESS = "005";

   public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User account has been credited!";







   

    public static String generateAccountNumber(){
        
    Year currentYear = Year.now();
    int min = 100000;
    int max = 999999;

    Random random = new Random();
    int RandomNumber = random.nextInt(min, max);

    String year = String.valueOf(currentYear);
    String randomNumber = String.valueOf(RandomNumber);

    StringBuilder accountNumber = new StringBuilder();

    return accountNumber.append(year).append(randomNumber).toString();

    
    }

    


}
