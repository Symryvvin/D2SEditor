package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private HeroData heroData;

    public void initialize() {
        folder = "C:/Users/" + System.getProperty("user.name") + "/Saved Games/Diablo II/";
    }

    @FXML
    private void onOpenClick() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open Resource File");
            chooser.setInitialDirectory(new File(folder));
            File file = chooser.showOpenDialog(new Stage());
            if (file != null) {
                openFile(file.toPath());
                FileUtils.backUp(heroData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openFile(Path path) {
        clearAll();
        heroData = new HeroData(path);
        heroData.setCheckSum(BinHexUtils.calculateCheckSum(heroData));
        checkSumInput.setText("Checksum: " + heroData.getCheckSum());
        fileName.setText("File name: " + heroData.getFileName());
        hexCodeInput.setText(BinHexUtils.getFormattedHexString(heroData.getData()));
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
            heroData.setOutputData(BinHexUtils.getDecodeHexString(hexCodeInput.getText()));
            heroData.setCheckSum(BinHexUtils.calculateCheckSum(heroData));
            checkSumOutput.setText("Checksum: " + heroData.getCheckSum());
            byte[] toSave = BinHexUtils.getResultBytes(heroData);
            Files.write(Paths.get(folder + heroData.getFileName()), toSave);
            hexCodeOutput.setText(BinHexUtils.getFormattedHexString(toSave));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRestoreClick() throws IOException {
        FileUtils.fromBackUp(heroData);
        openFile(heroData.getInput());
        checkSumOutput.setText("Checksum: " + heroData.getCheckSum());
        hexCodeOutput.clear();
    }
}
