package ru.aizen.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Launcher extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = SpringApplication.run(Launcher.class);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = load();
        stage.setScene(new Scene(root));
        stage.setTitle("ShadowMaster");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.show();
    }

    private Parent load() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setLocation(getClass().getResource("/fxml/main.fxml"));
        loader.setClassLoader(getClass().getClassLoader());
        return loader.load();
    }

    @Override
    public void stop() {
        context.close();
    }
}
