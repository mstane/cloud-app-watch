package io.github.ms.cloudappwatch.messaging.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class HeartBeat {

    private String serverName;

    private ZonedDateTime sendingTime;

    public HeartBeat(String serverName, ZonedDateTime sendingTime) {
        this.serverName = serverName;
        this.sendingTime = sendingTime;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public ZonedDateTime getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(ZonedDateTime sendingTime) {
        this.sendingTime = sendingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartBeat heartBeat = (HeartBeat) o;
        return Objects.equals(serverName, heartBeat.serverName) &&
            Objects.equals(sendingTime, heartBeat.sendingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverName, sendingTime);
    }
}
