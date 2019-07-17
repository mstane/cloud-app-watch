package io.github.ms.cloudappwatch.service;

import io.github.ms.cloudappwatch.domain.enumeration.AppStatus;
import io.github.ms.cloudappwatch.service.util.ProcessUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AppStatuses {

    private Map<String, AppStatus> statuses = new ConcurrentHashMap<>();

    public void setAppStatus(String app, AppStatus newStatus) {
        AppStatus currentStatus = statuses.get(app);
        if (currentStatus != newStatus) {
            //TODO: Send on event publisher
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
                return AppStatus.UP;
                //TODO: Send on event publisher
            } else if (!allProcesses.contains(k) && v == AppStatus.UP){
                return AppStatus.DOWN;
                //TODO: Send on event publisher
            }
            return v;
        });
    }

    @Scheduled(fixedRateString = "${caw.agent.scheduled-update-statuses.fixed-rate}")
    public void updateStatuses() {
        Set<String> allProcesses = ProcessUtil.getAllProcesses();
        updateStatuses(allProcesses);
    }


}
