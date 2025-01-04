package org.example.fotosappsaa.log;

import java.util.List;

public class LogEntry {
    private String inputFilename;
    private String filters;

    private String timestamp;



    public LogEntry(String inputFilename, List<String> filters, String timestamp) {
        this.inputFilename = inputFilename;
        for (String filter : filters) {
            this.filters += filter + ", ";
        }

        this.timestamp = timestamp;
    }

    public String getInputFilename() {
        return inputFilename;
    }

    public String getFilters() {
        return filters;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

