package io.github.ms.cloudappwatch.messaging.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AppEventChannel {

    String CHANNEL = "appEventChannel";

    @Output
    MessageChannel appEventChannel();

}
