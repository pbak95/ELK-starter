import utils.PropertiesUtility;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Runner {

    private static String KIBANA_BAT_PATH = PropertiesUtility.getInstance().getProperty("kibana.bat.path");

    private static String ELASTICSEARCH_BAT_PATH = PropertiesUtility.getInstance().getProperty("elasticsearch.bat.path");


    private static String LOGSTASH_HOME_PATH = PropertiesUtility.getInstance().getProperty("logstash.home.path");

    private static String LOGSTASH_CONFIG_FILE = PropertiesUtility.getInstance().getProperty("logstash.config.file.name");

    private static int PERIOD = PropertiesUtility.getInstance().getIntegerProperty("logstash.data.synchronization.period.in.minutes");

    private static void startProcess(String command, String processName) {
        try {
            Runtime.getRuntime().exec("cmd /c start " + command);

        } catch (IOException e) {
            System.out.println("Unable to start: " + processName);
        }
    }

    private static void startProcessAndQuit(String command, String processName) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("cmd /c start " + command);
            //TODO close cmd window due to it blocks next synchronizatiion
            //process.waitFor(20, TimeUnit.SECONDS);
            //process.destroy();
            //process.waitFor(); // wait for the process to terminate
        } catch (Exception e) {
            System.out.println("Unable to start: " + processName);
        }


    }

    /**
     * 1. BEFORE RUNNING, UPDATE CONFIG.PROPERTIES FILE
     * 2. LOGSTASH CONFIG FILE SHOULD BE PLACED IN PROJECT ROOT
     */


    public static void main(String[] args) {
        startProcess(ELASTICSEARCH_BAT_PATH + "elasticsearch.bat", "ELASTICSEARCH");
        startProcess(KIBANA_BAT_PATH + "kibana.bat", "KIBANA");
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> startProcessAndQuit(LOGSTASH_HOME_PATH + "bin\\logstash.bat -f " +  LOGSTASH_CONFIG_FILE,
                "LOGSTASH"),0, PERIOD, TimeUnit.MINUTES);

    }
}
