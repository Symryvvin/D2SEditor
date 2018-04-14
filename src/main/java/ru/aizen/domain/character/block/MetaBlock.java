package ru.aizen.domain.character.block;

import ru.aizen.domain.character.Status;
import ru.aizen.domain.data.BlockSize;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MetaBlock extends DataBlock {
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
    public DataBlock parse(ByteBuffer buffer) {
        size = buffer.capacity();
        this.activeHand = buffer.getInt();
        byte[] name = new byte[NAME_LENGTH];
        buffer.get(name, 0, NAME_LENGTH);
        this.name = new String(name).trim();
        this.status = new Status(buffer.get());
        this.title = buffer.get();
        buffer.getShort();//skip 2 bytes
        this.characterClass = buffer.get();
        buffer.getShort();//skip 2 bytes
        this.level = buffer.get();
        buffer.getInt();//skip 4 bytes
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        time = LocalDateTime.ofEpochSecond(buffer.getInt(), 0, ZoneOffset.UTC);
        buffer.getInt();//skip 4 bytes
        return this;
    }

    @Override
    public ByteBuffer collect() {
        ByteBuffer buffer = ByteBuffer.allocate(BlockSize.META_BLOCK_SIZE)
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
        return buffer;
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
