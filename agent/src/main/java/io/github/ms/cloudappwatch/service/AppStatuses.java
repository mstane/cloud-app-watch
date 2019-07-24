package io.github.ms.cloudappwatch.service;

import io.github.ms.cloudappwatch.domain.enumeration.AppStatus;
import io.github.ms.cloudappwatch.messaging.channel.AppEventChannel;
import io.github.ms.cloudappwatch.messaging.channel.HeartBeatChannel;
import io.github.ms.cloudappwatch.messaging.model.AppEvent;
import io.github.ms.cloudappwatch.messaging.model.HeartBeat;
import io.github.ms.cloudappwatch.service.util.ProcessUtil;
import io.github.ms.cloudappwatch.service.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AppStatuses {

    @Autowired
    private AppEventChannel appEventChannel;

    @Autowired
    private HeartBeatChannel heartBeatChannel;

    private Map<String, AppStatus> statuses = new ConcurrentHashMap<>();

    public void setAppStatus(String app, AppStatus newStatus) {
        AppStatus currentStatus = statuses.get(app);
        if (currentStatus != newStatus) {
            sendEvent(app, currentStatus, newStatus);
        }
    }

    public Map<String, AppStatus> getStatuses() {
        return Collections.unmodifiableMap(statuses);
    }

    /**
     * Reload apps from received list
     *
     * @param appList
     */
    public void reloadAppList(List<String> appList) {
        List<String> removeList = new ArrayList<>(statuses.keySet());
        for (String app : appList) {
            if (statuses.containsKey(app)) {
                removeList.remove(app);
            } else {
                statuses.put(app, null);
            }
        }
        if (!CollectionUtils.isEmpty(removeList)) {
            for (String app : removeList) {
                statuses.remove(app);
            }
        }
    }

    private void updateStatuses(Set<String> allProcesses) {
        statuses.replaceAll((k, v) -> {
            if (allProcesses.contains(k) && v != AppStatus.UP) {
                sendEvent(k, v, AppStatus.UP);
                return AppStatus.UP;
            } else if (!allProcesses.contains(k) && v == AppStatus.UP) {
                sendEvent(k, v, AppStatus.DOWN);
                return AppStatus.DOWN;
            }
            return v;
        });
    }

    @Scheduled(fixedRateString = "${application.agent.update-statuses-fixed-rate}")
    public void updateStatuses() {
        Set<String> allProcesses = ProcessUtil.getAllProcesses();
        updateStatuses(allProcesses);
    }

    private void sendEvent(String app, AppStatus oldStatus, AppStatus newStatus) {
        appEventChannel.appEventChannel().send(
            MessageBuilder.withPayload(
                new AppEvent(Util.hostname, ZonedDateTime.now(), app, oldStatus, newStatus)).build()
        );
    }

    @Scheduled(fixedRateString = "${application.agent.heartbeat-fixed-rate}")
    private void sendHeartBeat() {
        heartBeatChannel.heartBeatChannel().send(
            MessageBuilder.withPayload(new HeartBeat(Util.hostname, ZonedDateTime.now())).build());
    }



}
