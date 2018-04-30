package ru.aizen.domain.character.block;

import ru.aizen.domain.character.Status;
import ru.aizen.domain.data.ByteReader;
import ru.aizen.domain.data.UByte;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class MetaBlock extends DataBlock {
    public static final int META_BLOCK_OFFSET = 16;
    public static final int META_BLOCK_SIZE = 40;

    private static final int NAME_LENGTH = 16;

    private String name;
    private Status status;
    private byte title;
    private byte characterClass;
    private int level;
    private int activeHand;
    private LocalDateTime time;

    public MetaBlock(int order) {
        super(order);
    }

    @Override
    public MetaBlock parse(ByteReader reader) {
        this.activeHand = reader.readInt();
        this.name = reader.readString(NAME_LENGTH).trim();
        this.status = new Status(reader.readByte());
        this.title = reader.readByte();
        reader.skip(2);
        this.characterClass = reader.readByte();
        reader.skip(2);
        this.level = reader.readByte();
        reader.skip(4);
        this.time = LocalDateTime.ofEpochSecond(reader.readInt(), 0, ZoneOffset.UTC);
        reader.skip(4);
        return this;
    }

    @Override
    public List<UByte> collect() {
        ByteBuffer buffer = ByteBuffer.allocate(META_BLOCK_SIZE)
                .putInt(activeHand)
                .put(fillNameWithSpaces())
                .put(status.toByte())
                .put(title)
                .putShort((short) 0)
                .put(characterClass)
                .put((ByteBuffer) ByteBuffer.allocate(2).put((byte) 16).put((byte) 30).flip())
                .put((byte) level)
                .putInt(0)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt((int) time.toEpochSecond(ZoneOffset.UTC))
                .putInt(-1);
        buffer.flip();
        return UByte.getUnsignedBytes(buffer.array());
    }

    private byte[] fillNameWithSpaces() {
        ByteBuffer nameBuffer = ByteBuffer.allocate(16)
                .put(name.getBytes());
        return nameBuffer.array();
    }

    public void clearTime() {
        time = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public byte getTitle() {
        return title;
    }

    public void setTitle(byte title) {
        this.title = title;
    }

    public int getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(byte characterClass) {
        this.characterClass = characterClass;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getActiveHand() {
        return activeHand;
    }

    public void setActiveHand(int activeHand) {
        this.activeHand = activeHand;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime timestamp) {
        this.time = timestamp;
    }

    @Override
    public String toString() {
        return "MetaBlock{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", title=" + title +
                ", characterClass=" + characterClass +
                ", level=" + level +
                ", activeHand=" + activeHand +
                ", timestamp=" + time +
                '}';
    }


}
