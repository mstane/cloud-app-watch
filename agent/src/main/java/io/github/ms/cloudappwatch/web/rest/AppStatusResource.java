package io.github.ms.cloudappwatch.web.rest;

import io.github.ms.cloudappwatch.domain.enumeration.AppStatus;
import io.github.ms.cloudappwatch.service.AppStatuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/app-statuses")
public class AppStatusResource {

    @Autowired
    private AppStatuses appStatuses;


    @GetMapping
    public Map<String, AppStatus> getAppStatuses() {
        return appStatuses.getStatuses();
    }

    @PostMapping
    public void setAppStatuses(List<String> apps) {
        appStatuses.reloadAppList(apps);
    }

}
