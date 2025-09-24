package com.derrick.derrick_chanzu_Bank.utils;

import java.time.Year;
import java.util.Random;

public class AccountUtils {
    /*
     * I want my account number to always begin with the current year
     * then any random 6 digits
     * I need to concantinate the year and the 6 random digits
     * 
     * 
    */

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
