package io.github.ms.cloudappwatch.messaging.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface AppEventChannel {

    String CHANNEL = "appEventChannel";

    @Input
    MessageChannel appEventChannel();

}
