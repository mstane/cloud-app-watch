package io.github.ms.cloudappwatch.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class AppMock {

    private static int PROCESS_CHANGE_TIME = 10000;

    private static String PROCESS_NAME_PREFIX = "dummy-app";

    private static int MIN_NUMBER_OF_PROCESS = 3;
    private static int MAX_NUMBER_OF_PROCESS = 4;

    private int numberOfProcesses = 0;
    private Map<Integer, Process> processes = new HashMap<>();

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


}
