package org.example.fotosappsaa.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.fotosappsaa.log.LogEntry;
import org.example.fotosappsaa.log.LogManager;

import java.util.ArrayList;
import java.util.List;

public class LogController {

    @FXML
    private TableView<LogEntry> tvLog;
    @FXML
    private TableColumn tcImage;
    @FXML
    private TableColumn tcFilters;
    @FXML
    private TableColumn tcTimestamp;

    private List<LogEntry> logEntries = new ArrayList<>();



    public LogController() {
     }

@FXML
    public void initialize(){
        LogManager logManager = LogManager.getInstance();
        logEntries = logManager.getLogEntries();
        for (LogEntry logEntry : logEntries) {
            System.out.print(logEntry.getInputFilename());  //aqui funciona en consola
            System.out.print(logEntry.getFilters());
            System.out.print(logEntry.getTimestamp());
        }

    }

    public void updateLog(){
        tvLog.getItems().clear();
        for (LogEntry logEntry : logEntries) {
            tvLog.getItems().add(logEntry);
        }
    }





}




