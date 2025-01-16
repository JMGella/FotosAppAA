package org.example.fotosappsaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class AppController {


    @FXML
    private TabPane tpImages;
    private BufferedImage image;

    private boolean logIsOpen;
    private LogController logController;

    @FXML
    private Text txThreadNumber;

    @FXML
    private Slider slider;
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>());




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
        if (selectedFile == null) {
            return;
        }
        String imagePath = selectedFile.getAbsolutePath();
        String filename = imagePath.substring(imagePath.lastIndexOf("\\") + 1);
        image = ImageIO.read(selectedFile);
        startimage(filename);
    }


    @FXML
    private void selectFolder(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Carpeta de Imágenes");
        File defaultDirectory = new File("C:/Users/javie/IdeaProjects/FotosAppsAA/CARPETA DE FOTOS");
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedFolder = directoryChooser.showDialog(new Stage());
        if (selectedFolder == null) {
            return;
        }
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
            controller.setExecutor(executor);


            Tab newTab = new Tab(filename);
            newTab.setContent(content);
            tpImages.getTabs().add(newTab);
            tpImages.getSelectionModel().select(newTab);
            controller.setFilename(filename);

        }
    }

    public void openLog() throws IOException {

        if (!logIsOpen) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fotosappsaa/log.fxml"));
            Tab logTab = new Tab("Log");
            VBox content = loader.load();
            logController = loader.getController();
            logTab.setContent(content);
            tpImages.getTabs().add(logTab);
            tpImages.getSelectionModel().select(logTab);
            logIsOpen = true;
            logTab.setOnClosed(event -> logIsOpen = false);

        } else {

            for (Tab tab : tpImages.getTabs()) {
                if (tab.getText().equals("Log")) {
                    tpImages.getSelectionModel().select(tab);
                }
                logController.updateLog();
            }
        }
    }


 public void setThreadNumber(int threadNumber){
     if (threadNumber > executor.getMaximumPoolSize()) {
         executor.setMaximumPoolSize(threadNumber);
     }
     executor.setCorePoolSize(threadNumber);
     if (threadNumber < executor.getMaximumPoolSize()) {
         executor.setMaximumPoolSize(threadNumber);
     }

     executor.prestartAllCoreThreads();

    }


    public ExecutorService getExecutor() {
        return executor;
    }
    @FXML
    public void sendThreadNumber(ActionEvent actionEvent) {

        int threadNumber = (int) slider.getValue();
        setThreadNumber(threadNumber);
        txThreadNumber.setText(String.valueOf(threadNumber));


    }

 }
















