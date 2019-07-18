package io.github.ms.cloudappwatch.demo;

public class DummyApp {

    private static long WAIT_TIME = 10000;

    public static void main(String[] args) {
        try {
            String processStamp = " ";
            for (String arg : args) {
                processStamp += arg + " ";
            }
            System.out.println("Started process: " + processStamp);
            while (true) {
                Thread.sleep(WAIT_TIME);
                System.out.println("Running process: " + processStamp);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
