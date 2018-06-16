package ru.aizen.app.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.aizen.app.controller.HexEditorController;

import java.io.IOException;

public class HexEditorModalStage extends Stage {
    private static final String TITLE = "HEX Editor";

    private HexEditorController controller;

    public HexEditorModalStage() throws IOException {
        super(StageStyle.UTILITY);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hex.fxml"));
        setScene(new Scene(loader.load()));
        setTitle(TITLE);
        setAlwaysOnTop(true);
        this.controller = loader.getController();
    }

    public void showHex() {
        if (isShowing())
            return;
        show();
    }

    public HexEditorController getController() {
        return controller;
    }

}
