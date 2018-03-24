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
    private long reallyFileSize;

    private byte[] data;
    private byte[] preData;
    private byte[] postData;
    private byte[] fileSize;

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
        this.preData = Arrays.copyOfRange(data, 0, 12);
        this.postData = Arrays.copyOfRange(data, 16, data.length);
        this.fileSize = Arrays.copyOfRange(data, 9, 10);
        this.reallyFileSize = preData.length + postData.length + 4;
    }

    public Path getInput() {
        return input;
    }

    public Path getBackUp() {
        return backUp;
    }

    public void setData(byte[] data) {
        this.data = data;
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

    public long getReallyFileSize() {
        return reallyFileSize;
    }

    public byte[] getFileSize() {
        return fileSize;
    }


}
