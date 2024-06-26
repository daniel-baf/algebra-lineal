package Model.Utils;

import java.util.ArrayList;

public class CustomLogger {

    private static CustomLogger instance;
    private final ArrayList<String> logs;

    // Private constructor to prevent instantiation from outside
    private CustomLogger() {
        this.logs = new ArrayList<>();
    }

    // Static method to get the singleton instance
    public static CustomLogger getInstance() {
        if (instance == null) {
            instance = new CustomLogger();
        }
        return instance;
    }

    // Method to add a log to the singleton instance
    public void addLog(String log, boolean verbose) {
        if (verbose) {
            logs.add(log);
        }
    }

    /**
     * Add a title to logs
     *
     * @param title title log
     */
    public void addTitleLog(String title, boolean verbose) {
        String builder = "----------------------------------------------------------------------" +
                String.format("\t %1$s\t", title) +
                "----------------------------------------------------------------------";
        this.addLog(builder, verbose);
    }

    /**
     * Add a jumpline to diference result
     * @param jumplines n jumplines
     */
    public void addSectionEnd(int jumplines, boolean verbose) {
        this.addLog("\n".repeat(Math.max(0, jumplines)), verbose);
    }

    // Method to get all logs as a string from the singleton instance
    public String getAllLogsAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String log : logs) {
            stringBuilder.append(log).append("\n");
        }
        return stringBuilder.toString();
    }

    // Method to delete all logs from the singleton instance
    public void deleteLogs() {
        logs.clear();
    }

    public void printLogs() {
        this.logs.forEach(System.out::println);
    }
}
