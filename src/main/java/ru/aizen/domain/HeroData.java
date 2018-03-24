package ru.aizen.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class HeroData {
    private Path input;
    private Path backUp;
    private String fileName;

    private byte[] data;
    private byte[] preData;
    private byte[] postData;

    private int checkSum;

    public HeroData(Path filePath) {
        try {
            this.input = filePath;
            this.backUp = Paths.get(filePath.toString() + ".bak");
            this.data = Files.readAllBytes(filePath);
            this.fileName = filePath.getFileName().toString();
            splitData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setOutputData(byte[] data){
        splitData(data);
    }

    private void splitData(byte[] data){
        int offset = 12;
        int checkSumSize = 4;
        this.preData = Arrays.copyOfRange(data, 0, offset);
        this.postData = Arrays.copyOfRange(data, offset + checkSumSize, data.length);
    }

    public Path getInput() {
        return input;
    }

    public Path getBackUp() {
        return backUp;
    }

    public byte[] getData() {
        return data;
    }

    public byte[] getPreData() {
        return preData;
    }

    public byte[] getPostData() {
        return postData;
    }

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public String getFileName() {
        return fileName;
    }
}
