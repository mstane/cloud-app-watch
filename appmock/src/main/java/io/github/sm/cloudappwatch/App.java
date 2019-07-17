package io.github.sm.cloudappwatch;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * $ps -ef | grep dummy-app
 * $java -cp appmock-1.0-SNAPSHOT.jar io.github.sm.cloudappwatch.App
 */
public class App {

    private static String PATH_NAME = ".";
    private static String COMMAND_JAVA = "java";
    private static String COMMAND_CLASS_NAME = "io.github.sm.cloudappwatch.DummyApp";

//    private static String COMMAND_CLASS_PATH = "appmock-1.0-SNAPSHOT.jar";
    private static String COMMAND_CLASS_PATH = "./target/classes";

    private static int PROCESS_CHANGE_TIME = 10000;

    private static String PROCESS_NAME_PREFIX = "dummy-app";

    private static int MIN_NUMBER_OF_PROCESS = 3;
    private static int MAX_NUMBER_OF_PROCESS = 4;

    private static int numberOfProcesses = 0;
    private static Map<Integer, Process> processes = new HashMap<>();

    public static void main(String[] args) throws Exception {
        numberOfProcesses = getNumberOfProcesses();
        System.out.println("Hello! " + numberOfProcesses);

        createAllProcesses();

        processDynamic();

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

    }

    private static void processDynamic() throws Exception {
        int index = 0;
        while (true) {
            Thread.sleep(PROCESS_CHANGE_TIME);
            stopProcess(index);
            Thread.sleep(PROCESS_CHANGE_TIME);
            createProcess(index);

            index = getNextIndex(index);
        }
    }

    private static int getNumberOfProcesses() {
        return getRundomNumber(MIN_NUMBER_OF_PROCESS, MAX_NUMBER_OF_PROCESS + 1);
    }

    private static int getRundomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private static void createAllProcesses() throws Exception {
        for (int i = 0; i < numberOfProcesses; i++) {
            createProcess(i);
            Thread.sleep(PROCESS_CHANGE_TIME);
        }
    }

    private static void stopAllProcesses() throws Exception {
        processes.forEach((k, v) -> stopProcess(k));
    }

    private static Process createProcess(int index) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String processName = PROCESS_NAME_PREFIX + index;
        processBuilder.command(COMMAND_JAVA, COMMAND_CLASS_NAME, processName);
        processBuilder.directory(new File(PATH_NAME));
        Map<String, String> env = processBuilder.environment();
        env.put("CLASSPATH", COMMAND_CLASS_PATH);
        Process process = processBuilder.start();
        processes.put(index, process);
        System.out.println(String.format(
                "Process [%d] has been started with [%s] [%s] [%s] [%s]",
                index,
                COMMAND_JAVA,
                COMMAND_CLASS_NAME,
                COMMAND_CLASS_PATH,
                processName
        ));
        return process;
    }

    private static void stopProcess(int index) {
        Process process = processes.get(index);
        process.destroy();
        processes.remove(index);
        System.out.println("Process [" + index + "] has been destroyed");
    }


    private static int getNextIndex(int index) {
        return (index + 1) % numberOfProcesses;
    }


}
