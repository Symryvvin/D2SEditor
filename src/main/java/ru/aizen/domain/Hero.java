package ru.aizen.domain;

import java.util.Arrays;

public class Hero {
    private String fileName;

    private byte[] data;
    private byte[] preData;
    private byte[] postData;

    private int checkSum;

    public Hero(byte[] data, String fileName) {
        this.data = data;
        this.fileName = fileName;
    }

    public void setOutputData(byte[] data){
        int offset = 12;
        int checkSumSize = 4;
        this.preData = Arrays.copyOfRange(data, 0, offset);
        this.postData = Arrays.copyOfRange(data, offset + checkSumSize, data.length);
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
