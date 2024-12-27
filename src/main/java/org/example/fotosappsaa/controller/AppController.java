package org.example.fotosappsaa.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javafx.event.ActionEvent;
import org.example.fotosappsaa.task.TaskManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AppController {


    @FXML
    private CheckBox cbGrey;
    @FXML
    private CheckBox cbBright;
    @FXML
    private CheckBox cbInverted;
    @FXML
    private Button btProcess;
    @FXML
    private Button btCancel;
    @FXML
    private Button btSelectImage;
    @FXML
    private Button btBatch;
    @FXML
    private Button btNewPath;
    @FXML
    private TextField tfImagePath; //cambiar a savingpath
    @FXML
    private TabPane tpImages;

    private String savePath;

    private String imagePath;
    private BufferedImage image;



    public AppController() {

    }


    @FXML
    public void browsePath(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Carpeta");

        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            savePath = selectedDirectory.getAbsolutePath();
            tfImagePath.setText(savePath);
        }
    }




    @FXML private File selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        imagePath = selectedFile.getAbsolutePath();
        return selectedFile;
    }



    @FXML private File selectFolder(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Carpeta de Imágenes");
        File selectedFolder = directoryChooser.showDialog(new Stage());
        return selectedFolder;
    }

    private List<String> getSelectedFilters() {
        List<String> selectedOptions = new ArrayList<>();

        if (cbGrey.isSelected()) {
            selectedOptions.add("GREYSCALE");
        }
        if (cbBright.isSelected()) {
            selectedOptions.add("BRIGHT");
        }
        if (cbInverted.isSelected()) {
            selectedOptions.add("INVERTED");
        }

        return selectedOptions;
    }

    private void sendSelection() {
        TaskManager taskManager = new TaskManager(image, getSelectedFilters());
    }

    //falta transformar el archivo en imagen

    @FXML private void start() {
        try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fotosappsaa/image.fxml"));
             VBox content = loader.load();

            String filename = imagePath.substring(imagePath.lastIndexOf("\\") + 1);
            Tab newTab = new Tab(filename);

            newTab.setContent(content);
            tpImages.getTabs().add(newTab);

            sendSelection();


         } catch (IOException e) {
             e.printStackTrace();

         }


    }




    @FXML private void cancel() {  }






    }






