package io.github.ms.cloudappwatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Agent.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private boolean demo = true;

    private final Agent agent = new Agent();

    public boolean isDemo() {
        return demo;
    }

    public void setDemo(boolean demo) {
        this.demo = demo;
    }

    public Agent getAgent() {
        return agent;
    }

    public static class Agent {
        private Long updateStatusesFixedRate = 60000L;

        public Long getUpdateStatusesFixedRate() {
            return updateStatusesFixedRate;
        }

        public void setUpdateStatusesFixedRate(Long updateStatusesFixedRate) {
            this.updateStatusesFixedRate = updateStatusesFixedRate;
        }
    }

}
