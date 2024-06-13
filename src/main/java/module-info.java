module crackengine.jcrackengine {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.swing;

    opens crackengine.jcrackengine to javafx.fxml;
    exports crackengine.jcrackengine;
}