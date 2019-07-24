package io.github.ms.cloudappwatch.messaging;

import io.github.ms.cloudappwatch.messaging.channel.AppEventChannel;
import io.github.ms.cloudappwatch.messaging.channel.AppFullListChannel;
import io.github.ms.cloudappwatch.messaging.channel.HeartBeatChannel;
import io.github.ms.cloudappwatch.messaging.model.AppEvent;
import io.github.ms.cloudappwatch.messaging.model.HeartBeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;

public class ConsumerService {

    private Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @StreamListener(AppEventChannel.CHANNEL)
    public void consume(AppEvent message) {
        log.info("Received AppEvent message: {}", message);
    }

    @StreamListener(AppFullListChannel.CHANNEL)
    public void consume(AppFullListChannel message) {
        log.info("Received AppFullListChannel message: {}", message);
    }

    @StreamListener(HeartBeatChannel.CHANNEL)
    public void consume(HeartBeat heartBeat) {
        log.info("Received HeartBeat message: {}");
    }

}
