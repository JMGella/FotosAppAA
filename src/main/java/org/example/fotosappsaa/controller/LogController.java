package org.example.fotosappsaa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fotosappsaa.log.LogEntry;
import org.example.fotosappsaa.log.LogManager;

import java.util.ArrayList;
import java.util.List;

public class LogController {

    @FXML
    private TableView<LogEntry> tvLog;
    @FXML
    private TableColumn<LogEntry, String> tcImage;
    @FXML
    private TableColumn<LogEntry, String> tcFilters;
    @FXML
    private TableColumn<LogEntry, String> tcTimestamp;

    private ObservableList<LogEntry> logEntries = FXCollections.observableArrayList();




    public LogController() {
     }

@FXML
    public void initialize(){
        LogManager logManager = LogManager.getInstance();
        logEntries.addAll(logManager.getLogEntries());
        tvLog.setItems(logEntries);
        for (LogEntry logEntry : logEntries) {
            tcImage.setCellValueFactory(new PropertyValueFactory<>("inputFilename"));
            tcFilters.setCellValueFactory(new PropertyValueFactory<>("filters"));
            tcTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        }
    }

    public void updateLog(){
       LogManager logManager = LogManager.getInstance();
       logEntries.setAll(logManager.getLogEntries());
        tvLog.refresh();
    }





}




