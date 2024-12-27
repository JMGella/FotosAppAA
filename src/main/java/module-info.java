module org.example.fotosappsaa {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens org.example.fotosappsaa to javafx.fxml;
    exports org.example.fotosappsaa;
    exports org.example.fotosappsaa.controller;
    opens org.example.fotosappsaa.controller to javafx.fxml;
}