package com.example;

import lombok.Data;

@Data
public class BankTransfer {

    private final String fromAccount;
    private final String toAccount;
    private final Long amount;

}
