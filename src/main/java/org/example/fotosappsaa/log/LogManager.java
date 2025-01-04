package org.example.fotosappsaa.log;

import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private List<LogEntry> logEntries = new ArrayList<>();
    private static LogManager instance;

    public LogManager() {
    }

    public static LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
        }
        return instance;
    }

    public void addLogEntry(LogEntry logEntry) {
        logEntries.add(logEntry);
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }
}
