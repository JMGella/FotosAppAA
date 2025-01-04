package org.example.fotosappsaa.log;

import java.util.List;

public class LogEntry {
    private String inputFilename;
    private String filters;
    private String outputFilename;
    private String timestamp;


    public LogEntry(String inputFilename, List<String> filters, String outputFilename, String timestamp) {
        this.inputFilename = inputFilename;
        for (String filter : filters) {
            this.filters += filter + ", ";
        }
        this.outputFilename = outputFilename;
        this.timestamp = timestamp;
    }

    public String getInputFilename() {
        return inputFilename;
    }

    public String getFilters() {
        return filters;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

