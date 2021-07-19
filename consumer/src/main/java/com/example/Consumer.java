package com.example;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(topics = "bank-transfers")
    public void handle(@Payload BankTransfer bankTransfer) {
        System.out.println(bankTransfer);
    }

}
