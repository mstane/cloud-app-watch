package io.github.ms.cloudappwatch.messaging.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface HeartBeatChannel {

    String CHANNEL = "heartBeatChannel";

    @Input
    MessageChannel heartBeatChannel();

}
