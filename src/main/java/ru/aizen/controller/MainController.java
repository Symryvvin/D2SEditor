package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.codec.DecoderException;
import ru.aizen.domain.Character;
import ru.aizen.domain.HeroData;
import ru.aizen.domain.util.BinHexUtils;
import ru.aizen.domain.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainController {
    @FXML private Label checkSumInput;
    @FXML private Label fileName;
    @FXML private Label checkSumOutput;
    @FXML private TextArea hexCodeOutput;
    @FXML private TextArea hexCodeInput;

    private String folder;
    private Path path;
    private Character character;

    @FXML private EditorController editorController;
    private StatsController statsController;

    public void initialize() {
        folder = "C:/Users/" + System.getProperty("user.name") + "/Saved Games/Diablo II/";
        statsController = editorController.getStatsController();
    }

    @FXML
    private void onOpenClick() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open Resource File");
            chooser.setInitialDirectory(new File(folder));
            File file = chooser.showOpenDialog(new Stage());
            if (file != null) {
                path = file.toPath();
                openFile();
                FileUtils.backUp(character.getData());
                statsController.loadCharacterStats(character.getAttributes());
            }
        } catch (IOException | DecoderException e) {
            e.printStackTrace();
        }
    }

    private void openFile() throws DecoderException {
        clearAll();
        loadCharacter();
        setInputData();
    }

    private void loadCharacter() throws DecoderException {
        character = new Character(new HeroData(path));
        character.load();
    }

    private void setInputData() {
        checkSumInput.setText("Checksum: " + character.getData().getCheckSum());
        fileName.setText("File name: " + character.getData().getFileName());
        hexCodeInput.setText(BinHexUtils.getFormattedHexString(character.getData().getData()));
    }

    private void clearAll() {
        hexCodeInput.clear();
        hexCodeOutput.clear();
        checkSumInput.setText("");
        checkSumOutput.setText("");
    }

    @FXML
    private void onSaveClick() {
        try {
            character.getData().setOutputData(BinHexUtils.getDecodeHexString(hexCodeInput.getText()));
            character.getData().calculateCheckSum();
            byte[] toSave = character.getData().getDataToSave();
            saveFile(toSave);
        } catch (IOException | DecoderException e) {
            e.printStackTrace();
        }
    }

    private void saveFile(byte[] data) throws IOException {
        Files.write(Paths.get(folder + character.getData().getFileName()), data);
        setOutputData(data);
    }

    private void setOutputData(byte[] output) {
        checkSumOutput.setText("Checksum: " + character.getData().getCheckSum());
        hexCodeOutput.setText(BinHexUtils.getFormattedHexString(output));
    }

    @FXML
    private void onRestoreClick() throws IOException, DecoderException {
        FileUtils.fromBackUp(character.getData());
        clearAll();
        openFile();
    }
}
