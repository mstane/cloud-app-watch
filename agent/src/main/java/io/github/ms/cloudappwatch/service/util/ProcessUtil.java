package io.github.ms.cloudappwatch.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class ProcessUtil {

    private static Logger log = LoggerFactory.getLogger(ProcessUtil.class);

    private ProcessUtil() {
    }

    public static Set<String> getAllProcesses() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(new String[]{"ps", "-eo", "command"});

        try {
            Process p = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {

                Set<String> processSet = new HashSet<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    processSet.add(line);
                }
                p.waitFor();
                return processSet;
            }

        } catch (Exception e) {
            log.warn("", e);
            throw new IllegalStateException("Reading process statuses error");
        }
    }

}
