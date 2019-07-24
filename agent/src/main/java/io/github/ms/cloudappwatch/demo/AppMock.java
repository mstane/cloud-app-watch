package io.github.ms.cloudappwatch.demo;

import io.github.ms.cloudappwatch.domain.enumeration.AppStatus;
import io.github.ms.cloudappwatch.messaging.channel.AppFullListChannel;
import io.github.ms.cloudappwatch.messaging.model.AppFullList;
import io.github.ms.cloudappwatch.service.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AppMock {

    private static int PROCESS_CHANGE_TIME = 10000;

    private static String PROCESS_NAME_PREFIX = "dummy-app";

    private static int MIN_NUMBER_OF_PROCESS = 3;
    private static int MAX_NUMBER_OF_PROCESS = 4;

    private int numberOfProcesses = 0;
    private Map<Integer, Process> processes = new HashMap<>();
    private List<String> processCommandList = new ArrayList<>();

    @Autowired
    private AppFullListChannel appFullListChannel;

    @Async
    public void run()  {
        try {
            numberOfProcesses = getNumberOfProcesses();
            System.out.println("Hello! " + numberOfProcesses);

            createAllProcesses();

            Thread closeChildrenThread = new Thread(() -> {
                try {
                    processes.forEach((k, v) -> {
                        System.out.println("Shutdown hook, destroying process " + PROCESS_NAME_PREFIX + k);
                        v.destroy();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Runtime.getRuntime().addShutdownHook(closeChildrenThread);

            processDynamic();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

    }

    private void processDynamic() throws Exception {
        int index = 0;
        while (true) {
            Thread.sleep(PROCESS_CHANGE_TIME);
            stopProcess(index);
            Thread.sleep(PROCESS_CHANGE_TIME);
            createProcess(index);

            index = getNextIndex(index);
        }
    }

    private int getNumberOfProcesses() {
        return getRundomNumber(MIN_NUMBER_OF_PROCESS, MAX_NUMBER_OF_PROCESS + 1);
    }

    private int getRundomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private void createAllProcesses() throws Exception {
        for (int i = 0; i < numberOfProcesses; i++) {
            createProcess(i);
            Thread.sleep(PROCESS_CHANGE_TIME);
        }
        sendProcessList();
    }

    private void stopAllProcesses() throws Exception {
        processes.forEach((k, v) -> stopProcess(k));
    }


    private Process createProcess(int index) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String processName = PROCESS_NAME_PREFIX + index;
        String[] command = new String[] {"bash", "-c", "exec -a " + processName + " sleep 1000000"};
        processBuilder.command(command);
        Process process = processBuilder.start();
        processes.put(index, process);
        processCommandList.add(processName + " 1000000");
        System.out.println(String.format(
            "Process [%d] has been started with [%s]",
            index,
            Arrays.toString(command)
        ));
        return process;
    }

    private void stopProcess(int index) {
        Process process = processes.get(index);
        process.destroy();
        processes.remove(index);
        System.out.println("Process [" + index + "] has been destroyed");
    }


    private int getNextIndex(int index) {
        return (index + 1) % numberOfProcesses;
    }

    private void printOut(Process process) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendProcessList() {
        Map<String, AppStatus> processList = processCommandList.stream().collect(Collectors.toMap(Function.identity(), s -> AppStatus.UP));

        appFullListChannel.appFullListChannel().send(
            MessageBuilder.withPayload(new AppFullList(Util.hostname, ZonedDateTime.now(), processList)).build()
        );

    }


}
