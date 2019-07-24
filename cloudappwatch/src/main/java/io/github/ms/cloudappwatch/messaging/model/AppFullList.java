package io.github.ms.cloudappwatch.messaging.model;

import io.github.ms.cloudappwatch.domain.enumeration.AppStatus;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

public class AppFullList {

    private String serverName;

    private ZonedDateTime sendingTime;

    private Map<String, AppStatus> processes;

    public AppFullList(String serverName, ZonedDateTime sendingTime, Map<String, AppStatus> processes) {
        this.serverName = serverName;
        this.sendingTime = sendingTime;
        this.processes = processes;
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

    public Map<String, AppStatus> getProcesses() {
        return processes;
    }

    public void setProcesses(Map<String, AppStatus> processes) {
        this.processes = processes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppFullList that = (AppFullList) o;
        return Objects.equals(serverName, that.serverName) &&
            Objects.equals(sendingTime, that.sendingTime) &&
            Objects.equals(processes, that.processes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverName, sendingTime, processes);
    }
}
