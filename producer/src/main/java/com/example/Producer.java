package com.example;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 *
 * Keys are used to determine the partition within a log to which a message get's appended to.
 * Splitting a log into partitions allows to scale-out the system.
 *
 */

@Component
public class Producer {

    // Spring’s KafkaTemplate is auto-configured
    private final KafkaTemplate<Integer, BankTransfer> kafkaTemplate;

    public Producer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    public void send() {
        BankTransfer bankTransfer = new BankTransfer(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), new Random().nextLong());

        System.out.println("messasge sended");
        kafkaTemplate.send("bank-transfers", bankTransfer);
    }

}
