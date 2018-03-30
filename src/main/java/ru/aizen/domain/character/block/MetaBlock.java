package ru.aizen.domain.character.block;

import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Status;
import ru.aizen.domain.character.Title;
import ru.aizen.domain.data.BlockSize;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MetaBlock implements DataBlock {
    private static final int NAME_LENGTH = 16;

    private String name;
    private Status status;
    private Title title;
    private CharacterClass characterClass;
    private byte level;
    private int activeHand;
    private LocalDateTime time;

    public MetaBlock() {}

    @Override
    public DataBlock parse(ByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.activeHand = buffer.getInt();
        byte[] name = new byte[NAME_LENGTH];
        buffer.get(name, 0, NAME_LENGTH);
        this.name = new String(name).trim();
        this.status = new Status(buffer.get());
        this.title = Title.parse(buffer.get(), status);
        buffer.getShort();//skip 2 bytes
        this.characterClass = CharacterClass.parse(buffer.get());
        buffer.getShort();//skip 2 bytes
        this.level = buffer.get();
        buffer.getInt();//skip 4 bytes
        time = LocalDateTime.ofEpochSecond(buffer.getInt(), 0, ZoneOffset.UTC);
        buffer.getInt();//skip 4 bytes
        return this;
    }

    @Override
    public ByteBuffer collect() {
        ByteBuffer buffer = ByteBuffer.allocate(BlockSize.META_BLOCK_SIZE)
                .putInt(activeHand)
                .put(name.getBytes())
                .put(status.toByte())
                .put(title.getValue())
                .putShort((short)0)
                .putInt(characterClass.getValue())
                .putShort((short)0)
                .put(level)
                .putInt(0)
                .putInt((int)time.toEpochSecond(ZoneOffset.UTC))
                .putInt(0);
        buffer.flip();
        return buffer;
    }

    public void clearTime(){
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

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public void setLevel(byte level) {
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
