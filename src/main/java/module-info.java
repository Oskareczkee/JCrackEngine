module crackengine.jcrackengine {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires annotations;
    requires javafx.media;

    opens crackengine.jcrackengine to javafx.fxml;
    exports crackengine.jcrackengine;
    exports crackengine.jcrackengine.drawing;
    opens crackengine.jcrackengine.drawing to javafx.fxml;
    exports crackengine.jcrackengine.core;
    opens crackengine.jcrackengine.core to javafx.fxml;
    exports crackengine.jcrackengine.drawing.sprite;
    opens crackengine.jcrackengine.drawing.sprite to javafx.fxml;
    exports crackengine.jcrackengine.drawing.map;
    opens crackengine.jcrackengine.drawing.map to javafx.fxml;
    exports crackengine.jcrackengine.drawing.collision;
    opens crackengine.jcrackengine.drawing.collision to javafx.fxml;
    exports crackengine.jcrackengine.drawing.interfaces;
    opens crackengine.jcrackengine.drawing.interfaces to javafx.fxml;
    exports crackengine.jcrackengine.math;
    opens crackengine.jcrackengine.math to javafx.fxml;
    exports crackengine.jcrackengine.drawing.util;
    opens crackengine.jcrackengine.drawing.util to javafx.fxml;
}