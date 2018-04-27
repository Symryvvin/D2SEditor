package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import ru.aizen.domain.data.Backup;

import java.nio.file.Path;

public class BackupController {
    @FXML private ListView list;
    @FXML private Button restore;
    @FXML private Button delete;
    private Backup backup;

    public void setData(String name, Path folder) {
        backup = new Backup(name, folder);
    }

    private void initializeList() {

    }
}
