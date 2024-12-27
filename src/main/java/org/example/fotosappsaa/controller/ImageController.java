package org.example.fotosappsaa.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import org.example.fotosappsaa.task.TaskManager;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class ImageController {

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
    private ImageView ivOriginal;
    @FXML
    private ImageView ivProcessed;

    private BufferedImage image;

    public ImageController() {

    }

    public void setImage(BufferedImage image) {
        this.image = image;
        Image originalimage = SwingFXUtils.toFXImage(image, null);
        ivOriginal.setImage(originalimage);
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

    private void sendSelection() throws Exception {
        if (image != null) {

            TaskManager taskManager = new TaskManager(image, getSelectedFilters());
            BufferedImage proceesedBufferedImage = taskManager.call();
            Image processedImage = SwingFXUtils.toFXImage(proceesedBufferedImage, null);
            ivProcessed.setImage(processedImage);


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ninguna imagen");
            alert.showAndWait();
        }

    }


    public void start(ActionEvent actionEvent) throws Exception {
        sendSelection();
    }

    public void cancel(ActionEvent actionEvent) {
    }
}
