package ru.aizen.domain;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.math.NumberUtils;
import ru.aizen.domain.util.BinHexUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CharacterData {
    private Path input;
    private Path backUp;

    private byte[] bytes;
    private byte[] preData;
    private byte[] postData;

    private int checkSum;
    private short sizeInBytes;

    private HeroDataReader reader;

    public CharacterData(Path filePath) {
        this.input = filePath;
        this.backUp = Paths.get(filePath.toString() + ".bak");
    }

    public void read() throws IOException {
        System.out.println(input);
        this.bytes = Files.readAllBytes(input);
        this.reader = new HeroDataReader(bytes);
        splitData(bytes);
        calculateCheckSum();
    }

    public void setOutputData(byte[] data) {
        splitData(data);
    }

    private void splitData(byte[] data) {
        this.preData = Arrays.copyOfRange(data, 0, 12);
        this.postData = Arrays.copyOfRange(data, 16, data.length);
        this.sizeInBytes = (short) (preData.length + postData.length + 4);
    }

    public byte[] getAttributesBlock() throws DecoderException {
        return reader.getAttributesBlock();
    }

    public void calculateCheckSum() throws IOException {
        byte[] zero = new byte[4];
        Arrays.fill(zero, NumberUtils.BYTE_ZERO);
        replaceFileSize();
        checkSum = checksum(BinHexUtils.getUnsignedByteList(concatAllBytes(zero)));
    }

    public byte[] getDataToSave() throws IOException {
        Integer reversed = Integer.reverseBytes(checkSum);
        return concatAllBytes(ByteBuffer.allocate(4).putInt(reversed).array());
    }

    private byte[] concatAllBytes(byte[] checksum) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(preData);
        outputStream.write(checksum);
        outputStream.write(postData);
        return outputStream.toByteArray();
    }

    public byte[] calculateFileSize() {
        return ByteBuffer.allocate(2)
                .putShort(Short.reverseBytes(sizeInBytes))
                .array();
    }

    private void replaceFileSize() {
        byte[] size = ByteBuffer.allocate(2).putShort(Short.reverseBytes(sizeInBytes)).array();
        preData[8] = size[0];
        preData[9] = size[1];
    }

    private int checksum(List<Integer> byteList) {
        return byteList.stream()
                .reduce(0, (first, second) -> (first << 1) + second + ((first < 0) ? 1 : 0));
    }

    public Path getInput() {
        return input;
    }

    public Path getBackUp() {
        return backUp;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getCheckSum() {
        return checkSum;
    }
}
