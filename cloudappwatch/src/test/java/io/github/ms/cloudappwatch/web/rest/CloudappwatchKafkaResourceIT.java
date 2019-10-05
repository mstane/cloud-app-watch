package io.github.ms.cloudappwatch.web.rest;

import io.github.ms.cloudappwatch.CloudappwatchApp;
import io.github.ms.cloudappwatch.service.CloudappwatchKafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EmbeddedKafka
@SpringBootTest(classes = CloudappwatchApp.class)
public class CloudappwatchKafkaResourceIT {

    @Autowired
    private CloudappwatchKafkaProducer kafkaProducer;

    private MockMvc restMockMvc;

    @BeforeEach
    public void setup() {
        CloudappwatchKafkaResource kafkaResource = new CloudappwatchKafkaResource(kafkaProducer);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(kafkaResource)
            .build();
    }

    @Test
    public void sendMessageToKafkaTopic() throws Exception {
        restMockMvc.perform(post("/api/cloudappwatch-kafka/publish?message=yolo"))
            .andExpect(status().isOk());
    }
}
