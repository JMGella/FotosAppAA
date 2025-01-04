module org.example.fotosappsaa {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires javafx.swing;

    opens org.example.fotosappsaa to javafx.fxml;
    exports org.example.fotosappsaa;
    exports org.example.fotosappsaa.controller;
    opens org.example.fotosappsaa.controller to javafx.fxml;
    opens org.example.fotosappsaa.log to javafx.base;
}