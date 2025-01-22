package org.example.fotosappsaa.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LogController {

    @FXML
    private TextArea txArea;





    public LogController() {
     }

@FXML
    public void initialize(){
        updateLog();

    }

    public void updateLog() {
        txArea.clear();
        File logsFile = new File("./filters.log");
        try (BufferedReader reader = new BufferedReader(new FileReader(logsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                txArea.setText(txArea.getText() + line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }










