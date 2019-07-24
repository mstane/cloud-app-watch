package io.github.ms.cloudappwatch.messaging.model;

import io.github.ms.cloudappwatch.domain.enumeration.AppStatus;

import java.time.ZonedDateTime;
import java.util.Objects;

public class AppEvent {

    private String serverName;

    private ZonedDateTime sendingTime;

    private String commandLine;

    private AppStatus previousStatus;

    private AppStatus currentStatus;

    public AppEvent(String serverName, ZonedDateTime sendingTime, String commandLine, AppStatus previousStatus, AppStatus currentStatus) {
        this.serverName = serverName;
        this.sendingTime = sendingTime;
        this.commandLine = commandLine;
        this.previousStatus = previousStatus;
        this.currentStatus = currentStatus;
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

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public AppStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(AppStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    public AppStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(AppStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppEvent that = (AppEvent) o;
        return Objects.equals(serverName, that.serverName) &&
            Objects.equals(sendingTime, that.sendingTime) &&
            Objects.equals(commandLine, that.commandLine) &&
            previousStatus == that.previousStatus &&
            currentStatus == that.currentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverName, sendingTime, commandLine, previousStatus, currentStatus);
    }
}
