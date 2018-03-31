package ru.aizen.domain.data;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.math.NumberUtils;
import ru.aizen.domain.character.block.DataBlock;
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

    private short sizeInBytes;

    private DataReader reader;

    public CharacterData(Path filePath) {
        this.input = filePath;
        this.backUp = Paths.get(filePath.toString() + ".bak");
    }

    public void read() throws IOException {
        this.bytes = Files.readAllBytes(input);
        System.out.println(Hex.encodeHexString(bytes));
        this.reader = new DataReader(bytes);
    }

    public DataBlock createStubBlock(int order, int start, int size) {
        return reader.createStubBlock(order, start, size);
    }

    public void setOutputData(byte[] data) {
        splitData(data);
    }

    private void splitData(byte[] data) {
        this.preData = Arrays.copyOfRange(data, 0, 12);
        this.postData = Arrays.copyOfRange(data, 16, data.length);
        this.sizeInBytes = (short) (preData.length + postData.length + 4);
    }

    public byte[] getDataToSave() throws IOException {
        byte[] zero = new byte[4];
        Arrays.fill(zero, NumberUtils.BYTE_ZERO);
        replaceFileSize();
        int checksum = checksum(BinHexUtils.getUnsignedByteList(concatAllBytes(zero)));
        Integer reversed = Integer.reverseBytes(checksum);
        return concatAllBytes(ByteBuffer.allocate(4).putInt(reversed).array());
    }

    private byte[] concatAllBytes(byte[] checksum) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(preData);
        outputStream.write(checksum);
        outputStream.write(postData);
        return outputStream.toByteArray();
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

    public DataReader getReader() {
        return reader;
    }
}
