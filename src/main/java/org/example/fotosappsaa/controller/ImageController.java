package org.example.fotosappsaa.controller;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.fotosappsaa.task.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private String savingPath = "C:/Users/javie/IdeaProjects/FotosAppsAA/FOTOS PROCESADAS/";



    private ServiceManager serviceManager;

    private String filename="";

    private boolean isCancelled = false;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(ImageController.class);




    public ImageController() {

    }

    public void setImage(BufferedImage image) {
        if (isCancelled) {
            isCancelled = false;
            return;
        }
        this.image = image;
        Image originalimage;
        if (proceesedBufferedImage != null) {
             originalimage = SwingFXUtils.toFXImage(proceesedBufferedImage, null);
        } else {
            originalimage = SwingFXUtils.toFXImage(image, null);
        }

        ivOriginal.setImage(originalimage);
        pbProgress.setOpacity(0);
        Path path = Paths.get(savingPath);
        String folderName = path.getFileName().toString();
        txPath.setText(folderName);

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

            setImage(image);
            serviceManager = new ServiceManager(image, getSelectedFilters());
            pbProgress.setOpacity(100);
            pbProgress.progressProperty().bind(serviceManager.progressProperty());
            lbStatus.textProperty().bind(serviceManager.messageProperty());


        serviceManager.setOnSucceeded(event -> {
            proceesedBufferedImage = serviceManager.getValue();
            Image processedImage = SwingFXUtils.toFXImage(proceesedBufferedImage, null);
            ivProcessed.setImage(processedImage);
            pbProgress.progressProperty().unbind();
            pbProgress.setOpacity(0);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Procesado");
            alert.setHeaderText("Imagen " + filename + " procesada con éxito");
            alert.showAndWait();
            String filters = "";
           for (String filter : getSelectedFilters()){
               filters = filters + filter + " ";
           }
           logger.info("Imagen " + filename + " procesada con éxito. Filtros aplicados: " + filters);
        });

        serviceManager.setOnCancelled(event -> {
            lbStatus.textProperty().unbind();
            lbStatus.setText("Proceso cancelado");
            pbProgress.progressProperty().unbind();
            pbProgress.setProgress(0);
            logger.warn("Proceso de la imagen " + filename + " cancelado.");

        }
        );

        serviceManager.setOnFailed(event -> {
            lbStatus.setText("Error al procesar la imagen.");
            pbProgress.progressProperty().unbind();
            pbProgress.setProgress(0);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al procesar la imagen.");
            alert.showAndWait();
            logger.error("Error al procesar la imagen " + filename + ".");
        });

        serviceManager.setOnScheduled(event -> {
            lbStatus.textProperty().unbind();
            lbStatus.setText("Imagen en cola...");

        });

        serviceManager.setOnRunning(event -> {
            lbStatus.textProperty().bind(serviceManager.messageProperty());
        });

        if(getSelectedFilters().isEmpty()) {
            pbProgress.setOpacity(0);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ningún filtro");
            alert.showAndWait();

        } else{
            serviceManager.start();
        }


    }

    public void saveImage(ActionEvent event) {
        if (proceesedBufferedImage!=null) {
            try {
                UUID uuid = UUID.randomUUID();
                String newfilename = uuid.toString();
                File file = new File(savingPath +  newfilename + "_modificada.jpg");
                ImageIO.write(proceesedBufferedImage, "jpg", file);
                logger.info("Imagen " + filename + " guardada con éxito en " + savingPath + newfilename + "_modificada.jpg");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al guardar la imagen");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No guardado");
            alert.setHeaderText("No se ha generado ninguna imagen nueva para guardar");
            alert.showAndWait();;
        }
    }

    public void browseSavePath(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Carpeta de Guardado");
        File selectedFolder = directoryChooser.showDialog(new Stage());
        if (selectedFolder == null) {
            return;
        }
        savingPath = selectedFolder.getAbsolutePath();
        txPath.setText(savingPath.substring(savingPath.lastIndexOf("\\") + 1));
        logger.info("Carpeta de guardado seleccionada: " + savingPath);
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


    public void cancelTask(ActionEvent actionEvent) {
        if(serviceManager != null) {
            serviceManager.cancel();
            lbStatus.textProperty().unbind();
            lbStatus.setText("Proceso cancelado");
            pbProgress.progressProperty().unbind();
            pbProgress.setProgress(0);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cancelado");
            alert.setHeaderText("Se ha cancelado el proceso.");
            alert.showAndWait();
            isCancelled = true;
        }
    }


    public void setFilename(String filename) {
        this.filename = filename;
    }




}
