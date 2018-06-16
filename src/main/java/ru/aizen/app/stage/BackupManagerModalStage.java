package ru.aizen.app.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.aizen.app.controller.BackupController;

import java.io.IOException;

public class BackupManagerModalStage extends Stage {
    private static final String TITLE = "Backup Manager";

    private BackupController controller;

    public BackupManagerModalStage() throws IOException {
        super(StageStyle.UTILITY);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/backup.fxml"));
        setScene(new Scene(loader.load()));
        setTitle(TITLE);
        setAlwaysOnTop(true);
        this.controller = loader.getController();
    }

    public void showBackup() {
        if (isShowing())
            return;
        show();
    }

    public BackupController getController() {
        return controller;
    }
}
