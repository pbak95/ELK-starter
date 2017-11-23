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


    private static void startProcess(String command, String processName) {
        try {
            Runtime.getRuntime().exec("cmd /c start " + command);

        } catch (IOException e) {
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
        startProcess(LOGSTASH_HOME_PATH + "bin\\logstash.bat -f " +  LOGSTASH_CONFIG_FILE,
                "LOGSTASH");
    }
}
