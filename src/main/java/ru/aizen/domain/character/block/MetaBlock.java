package ru.aizen.domain.character.block;

import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Status;
import ru.aizen.domain.character.Title;
import ru.aizen.domain.dao.CharacterDao;
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
    public static final int ORDER = 2;
    private static final int NAME_LENGTH = 16;

    private String name;
    private Status status;
    private Title title;
    private CharacterClass characterClass;
    private int level;
    private int activeHand;
    private LocalDateTime time;

    private CharacterDao characterDao;

    public MetaBlock(CharacterDao characterDao) {
        super(ORDER);
        this.characterDao = characterDao;
    }

    @Override
    public MetaBlock parse(ByteReader reader) {
        this.activeHand = reader.readInt();
        this.name = reader.readString(NAME_LENGTH).trim();
        this.status = new Status(reader.readByte());
        byte tempTitleValue = reader.readByte();
        reader.skip(2);
        this.characterClass = CharacterClass.getByValue(reader.readByte());
        this.title = characterDao.getTitleByValue(characterClass, status, tempTitleValue);
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
                .put(characterDao.getTitleValue(status, title.getDifficult()))
                .putShort((short) 0)
                .put((byte) characterClass.getValue())
                .put(new byte[]{0x10, 0x1E})
                .put((byte) level)
                .putInt(0x00000000)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt((int) time.toEpochSecond(ZoneOffset.UTC))
                .putInt(0xFFFFFFFF);
        buffer.flip();
        return UByte.getUnsignedBytes(buffer.array());
    }

    private byte[] fillNameWithSpaces() {
        ByteBuffer nameBuffer = ByteBuffer.allocate(16)
                .put(name.getBytes());
        return nameBuffer.array();
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

    public void setLevel(int level) {
        this.level = level;
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
