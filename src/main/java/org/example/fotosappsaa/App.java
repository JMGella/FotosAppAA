package org.example.fotosappsaa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.fotosappsaa.log.LogEntry;
import org.example.fotosappsaa.log.LogManager;

import java.util.ArrayList;
import java.util.List;


public class App extends Application {



    @Override
        public void start(Stage primaryStage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui_app.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("Actividad de Aprendizaje: Filtros de Fotos");
            primaryStage.setScene(scene);
            primaryStage.show();
        }





        public static void main(String[] args) {
            launch(args);
        }
    }