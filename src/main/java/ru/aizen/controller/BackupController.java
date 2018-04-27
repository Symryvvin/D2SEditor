package ru.aizen.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.data.Backup;

import java.io.IOException;
import java.nio.file.Path;

public class BackupController {
    @FXML private ListView<Path> list;
    private Backup backup;
    private Character character;

    public void setData(Character character) {
        this.character = character;
        backup = new Backup(character.getName(), character.getCharacterData().getBackupFolder());
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
        }
    }

    @FXML
    private void onRestoreClick() {
        if (!list.getSelectionModel().isEmpty()) {
            character.getCharacterData().setLastBackup(list.getSelectionModel().getSelectedItem());
        }
    }
}
