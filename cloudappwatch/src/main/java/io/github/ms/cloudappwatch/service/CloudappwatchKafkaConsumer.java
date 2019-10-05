package io.github.ms.cloudappwatch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CloudappwatchKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(CloudappwatchKafkaConsumer.class);
    private static final String TOPIC = "topic_cloudappwatch";

    @KafkaListener(topics = "topic_cloudappwatch", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
