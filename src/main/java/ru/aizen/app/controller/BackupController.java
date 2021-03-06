package ru.aizen.app.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import ru.aizen.domain.data.Backup;

import java.io.IOException;
import java.nio.file.Path;

public class BackupController {

    @FXML private ListView<Path> list;
    private Backup backup;

    public void createBackup(Path path) throws IOException {
        backup = new Backup(path);
        backup.createBackup();
        initializeList();
    }

    private void initializeList() {
        list.setItems(new ObservableListWrapper<>(backup.getPaths()));
    }

    @FXML
    private void onDeleteClick() throws IOException {
        if (!list.getSelectionModel().isEmpty()) {
            Path item = list.getSelectionModel().getSelectedItem();
            backup.delete(item);
            initializeList();
            list.getSelectionModel().selectFirst();
        }
    }

    public void onListClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            if (!list.getSelectionModel().isEmpty()) {
                backup.selectBackup(list.getSelectionModel().getSelectedItem());
            }
        }
    }

    public void revert() throws IOException {
        backup.revertLast();
    }

    public Path renameFiles(String newName) throws IOException {
        return backup.renameAll(newName);
    }
}
