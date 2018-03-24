package ru.aizen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.aizen.domain.Hero;
import ru.aizen.domain.util.BinHexUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainController {
    @FXML private TextArea hexCodeOutput;
    @FXML private TextArea hexCodeInput;

    private String folder;
    private Hero hero;

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
                openFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openFile(File file) throws IOException {
        byte[] data = Files.readAllBytes(file.toPath());
        hero = new Hero(data, file.getName());
        hexCodeInput.setText(BinHexUtils.getFormattedHexString(data));
    }

    @FXML
    private void onSaveClick() {
        try {
            hero.setOutputData(BinHexUtils.getDecodeHexString(hexCodeInput.getText()));
            int cs = BinHexUtils.calculateCheckSum(hero);
            byte[] toSave = getResultBytes(cs);
            Files.write(Paths.get(folder + hero.getFileName()), toSave);
            hexCodeOutput.setText(BinHexUtils.getFormattedHexString(toSave));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getResultBytes(int checkSum) throws IOException {
        String hex = Integer.toHexString(checkSum);
        if (hex.length() < 8)
            hex = hex + "0";
        System.out.println(hex);
        byte[] array = DatatypeConverter.parseHexBinary(hex);
        byte[] reverse = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            reverse[i] = array[array.length - 1 - i];
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(hero.getPreData());
        outputStream.write(reverse);
        outputStream.write(hero.getPostData());
        return outputStream.toByteArray();
    }
}
