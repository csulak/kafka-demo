package com.example;

import com.example.util.LZ4CompressorUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    LZ4CompressorUtil lz4CompressorUtil;

    public Consumer(LZ4CompressorUtil lz4CompressorUtil) {
        this.lz4CompressorUtil = lz4CompressorUtil;
    }

    @KafkaListener(topics = "byte-array")
    public void handleByteArray(ConsumerRecord<String, Object> record) {

        // *** LOGIC TO GET THE HEADERS RECEIVED ***
        MessageHeaderAccessor accessor = new MessageHeaderAccessor();
        for (Header h : record.headers()) {
            accessor.setHeader(h.key(), new String(h.value(), StandardCharsets.UTF_8));
            LOGGER.info("Key : {} and Value : {}",h.key(),h.value());
        }
        accessor.setHeader("topic", record.topic());
        // *** END LOGIC TO GET THE HEADERS RECEIVED ***


        // Logic to get the byte[] value from record
        String[] byteValues = record.value().toString().substring(1, record.value().toString().length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i=0, len=bytes.length; i<len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }

        String payload = lz4CompressorUtil.deCompress(bytes);
        LOGGER.info("Received messages payload --> {}", payload);

        System.out.println(payload);
    }

}
