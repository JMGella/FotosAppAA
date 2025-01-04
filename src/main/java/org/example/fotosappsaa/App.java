package org.example.fotosappsaa;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        long time;
        Stage splashStage = new Stage();
        StackPane splashPane = new StackPane();
        splashPane.getChildren().add(new Text("Cargando Actividad de Aprendizaje..."));
        Scene splashScene = new Scene(splashPane, 1200, 670);
        splashStage.setScene(splashScene);
        splashStage.show();


        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(3000);

                return null;
            }
        };

        task.setOnSucceeded(event -> {

            splashStage.close();
            showMainApp(primaryStage);

        });

        new Thread(task).start();
    }

    private void showMainApp(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui_app.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("Actividad de Aprendizaje: Filtros de Fotos");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}