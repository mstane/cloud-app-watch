package io.github.ms.cloudappwatch.web.rest;

import io.github.ms.cloudappwatch.service.CloudappwatchKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cloudappwatch-kafka")
public class CloudappwatchKafkaResource {

    private final Logger log = LoggerFactory.getLogger(CloudappwatchKafkaResource.class);

    private CloudappwatchKafkaProducer kafkaProducer;

    public CloudappwatchKafkaResource(CloudappwatchKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
