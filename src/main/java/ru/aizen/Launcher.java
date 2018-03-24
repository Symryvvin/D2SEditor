package ru.aizen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        stage.setScene(new Scene(root, 800, 800));
        stage.setTitle("ShadowMaster");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.show();
    }
}
