package io.github.ms.cloudappwatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Cloudappwatch.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private boolean demo = true;

    public boolean isDemo() {
        return demo;
    }

    public void setDemo(boolean demo) {
        this.demo = demo;
    }

}
