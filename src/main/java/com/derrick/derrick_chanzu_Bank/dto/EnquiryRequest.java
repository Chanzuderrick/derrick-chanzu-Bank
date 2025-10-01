package com.derrick.derrick_chanzu_Bank.dto;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EnquiryRequest {
    private String accountNumber;

}
