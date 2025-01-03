package org.example.fotosappsaa.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.fotosappsaa.task.TaskManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

    @FXML
    private Button btSave;
    @FXML
    private Text txPath;
    @FXML
    private Label lbStatus;
    @FXML
    private ProgressBar pbProgress;

    private BufferedImage image;

    private BufferedImage proceesedBufferedImage;

    private String savingPath = "C:/Users/javie/IdeaProjects/FotosAppsAA/FOTOS PROCESADAS";




    public ImageController() {

    }

    public void setImage(BufferedImage image) {
        this.image = image;
        Image originalimage = SwingFXUtils.toFXImage(image, null);
        ivOriginal.setImage(originalimage);
        txPath.setText(savingPath.substring(savingPath.lastIndexOf("/") + 1));
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

            TaskManager taskManager = new TaskManager(image, getSelectedFilters());
            pbProgress.progressProperty().bind(taskManager.progressProperty());
            pbProgress.setVisible(false);
            lbStatus.textProperty().bind(taskManager.messageProperty());

        taskManager.setOnRunning(event -> {
            pbProgress.setVisible(true);
        });


        taskManager.setOnSucceeded(event -> {
            BufferedImage proceesedBufferedImage = taskManager.getValue();
            Image processedImage = SwingFXUtils.toFXImage(proceesedBufferedImage, null);
            ivProcessed.setImage(processedImage);
            pbProgress.progressProperty().unbind();
            pbProgress.setProgress(0);
        });
        taskManager.setOnFailed(event -> {
            lbStatus.setText("Error al procesar la imagen.");
            pbProgress.progressProperty().unbind();
            pbProgress.setProgress(0);
        });

        new Thread(taskManager).start();

    }

    public void saveImage(ActionEvent event) {
        if (proceesedBufferedImage!=null) {
            try {
                UUID uuid = UUID.randomUUID();
                String newfilename = uuid.toString();
                File file = new File(savingPath +  newfilename + "_modificada.jpg");
                ImageIO.write(proceesedBufferedImage, "jpg", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No guardado");
            alert.setHeaderText("No se ha generado ninguna imagen nueva para guardar");
            alert.showAndWait();
        }
    }

    public void browseSavePath(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Carpeta de Guardado");
        File selectedFolder = directoryChooser.showDialog(new Stage());
        savingPath = selectedFolder.getAbsolutePath();
        txPath.setText(savingPath.substring(savingPath.lastIndexOf("\\") + 1));
    }

    public void start(ActionEvent actionEvent) throws Exception {
        if(image != null) {
            sendSelection();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ninguna imagen");
            alert.showAndWait();
        }

    }

    public void cancel(ActionEvent actionEvent) {
    }
}
