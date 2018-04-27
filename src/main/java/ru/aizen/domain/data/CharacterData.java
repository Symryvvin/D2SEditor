package ru.aizen.domain.data;

import ru.aizen.domain.UByte;
import ru.aizen.domain.character.block.DataBlock;
import ru.aizen.domain.exception.ValidatorException;
import ru.aizen.domain.util.Validator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterData {
    private Path input;
    private Path backupFolder;
    private Path lastBackup;
    private byte[] bytes;
    private DataReader reader;
    private DataWriter writer;

    /**
     * Constructor. In constructor set fields of path to original and backup folder
     * @param filePath path to open save file and get data
     */
    public CharacterData(Path filePath) throws IOException {
        this.input = filePath;
        String folder = filePath.toString().replace(".d2s", "");
        this.backupFolder = Paths.get(folder);
        if (Files.notExists(backupFolder)) {
            Files.createDirectory(Paths.get(folder));
        }
    }

    /**
     * Read data to bytes field and creating DataReader/DataWriter
     * @throws IOException
     */
    public void read() throws IOException, ValidatorException {
        this.bytes = Files.readAllBytes(input);
        Validator.validateFormat(Arrays.copyOfRange(bytes, 0, 4));
        this.reader = new DataReader(bytes);
        this.writer = new DataWriter(input);
    }

    public void write(List<DataBlock> blocks) throws IOException {
        List<UByte> result = blocks.stream()
                .flatMap(b -> b.collect().stream())
                .collect(Collectors.toList());
        bytes = getDataToSave(result);
        reader = new DataReader(bytes);
        writer.write(bytes);
    }

    /**
     * Add to data actual file size value and new checksum
     * @param bytes data represented as list of Ubytes
     * @return data represented as array of bytes
     */
    private byte[] getDataToSave(List<UByte> bytes) {
        bytes = replaceFileSize(bytes, bytes.size());
        int checksum = checksum(bytes);
        bytes = replaceChecksum(bytes, checksum);
        return UByte.toArray(bytes);
    }

    /**
     * Replace bytes in header (8, 9, 10 and 11 bytes in order) to byte array of new actual file size
     * @param data list of Ubytes
     * @param fileSize actual size
     * @return changed list of UBytes
     */
    private List<UByte> replaceFileSize(List<UByte> data, int fileSize) {
        byte[] size = ByteBuffer.allocate(4)
                .putInt(Integer.reverseBytes(fileSize))
                .array();
        data.set(8, new UByte(size[0]));
        data.set(9, new UByte(size[1]));
        data.set(10, new UByte(size[2]));
        data.set(11, new UByte(size[3]));
        return data;
    }

    /**
     * Replace bytes in header (12, 13, 14 and 15 bytes in order) to byte array of new checksum
     * @param data list of Ubytes
     * @param checksum calculated checksum
     * @return changed list of UBytes
     */
    private List<UByte> replaceChecksum(List<UByte> data, int checksum) {
        byte[] sum = ByteBuffer.allocate(4)
                .putInt(Integer.reverseBytes(checksum))
                .array();
        data.set(12, new UByte(sum[0]));
        data.set(13, new UByte(sum[1]));
        data.set(14, new UByte(sum[2]));
        data.set(15, new UByte(sum[3]));
        return data;
    }

    public DataBlock createStubBlock(int order, int start, int size) {
        return reader.createStubBlock(order, start, size);
    }

    /**
     * Algorithm to get checksum of new changed data
     * @param bytes list of UBytes (changed data)
     * @return new actual checksum
     */
    private int checksum(List<UByte> bytes) {
        return bytes.stream()
                .map(UByte::get)
                .reduce(0, (first, second) -> (first << 1) + second + ((first < 0) ? 1 : 0));
    }

    public Path getInput() {
        return input;
    }

    public Path getBackupFolder() {
        return backupFolder;
    }

    public Path getLastBackup() {
        return lastBackup;
    }

    public void setLastBackup(Path lastBackup) {
        this.lastBackup = lastBackup;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public DataReader getReader() {
        return reader;
    }


}
