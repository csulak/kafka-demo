package com.example;

import lombok.Data;

@Data
public class BankTransfer {

    private String fromAccount;
    private String toAccount;
    private Long amount;

    public BankTransfer() {
    }
}
