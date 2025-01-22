module org.example.fotosappsaa {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.logging.log4j;
    requires slf4j.api;

    opens org.example.fotosappsaa to javafx.fxml;
    exports org.example.fotosappsaa;
    exports org.example.fotosappsaa.controller;
    opens org.example.fotosappsaa.controller to javafx.fxml;


}