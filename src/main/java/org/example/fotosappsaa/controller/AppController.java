package org.example.fotosappsaa.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javafx.event.ActionEvent;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AppController {


    @FXML
    private TabPane tpImages;
    private BufferedImage image;


    public AppController() {

    }


    @FXML
    private void selectImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        String imagePath = selectedFile.getAbsolutePath();
        String filename = imagePath.substring(imagePath.lastIndexOf("\\") + 1);
        image = ImageIO.read(selectedFile);
        startimage(filename);
    }


    @FXML
    private void selectFolder(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Carpeta de Imágenes");
        File selectedFolder = directoryChooser.showDialog(new Stage());
        File[] files = selectedFolder.listFiles();
        List<BufferedImage> images = new ArrayList<>();
        List<String> filenames = new ArrayList<>();
        for (File file : files) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                images.add(bufferedImage);
                String filename = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\") + 1);
                filenames.add(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        startbatch(filenames, images);

    }


    private void startimage(String filename) {

        try {
            paintTab(filename, image, tpImages);


        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private void startbatch(List<String> filenames, List<BufferedImage> images) {

        for (BufferedImage image : images) {

            try {
                String filename = filenames.get(images.indexOf(image));
                paintTab(filename, image, tpImages);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }


    private void paintTab(String filename, BufferedImage image, TabPane tpImages) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fotosappsaa/image.fxml"));
        VBox content = loader.load();

        if (image == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ninguna imagen");
            alert.showAndWait();
            return;
        } else {
            ImageController controller = loader.getController();
            controller.setImage(image);


            Tab newTab = new Tab(filename);

            newTab.setContent(content);
            tpImages.getTabs().add(newTab);


        }


    }
}





