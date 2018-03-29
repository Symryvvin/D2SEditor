package ru.aizen.domain.character;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Meta {
    private static final int NAME_LENGTH = 16;

    private String name;
    private Status status;
    private Title title;
    private CharacterClass characterClass;
    private int level;
    private int activeHand;
    private LocalDateTime time;

    public Meta(byte[] meta) {
        ByteBuffer buffer = ByteBuffer.wrap(meta);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.activeHand = buffer.getInt();
        byte[] name = new byte[NAME_LENGTH];
        buffer.get(name, 0, NAME_LENGTH);
        this.name = new String(name).trim();
        this.status = new Status(buffer.get());
        this.title = Title.parse(buffer.get(), status);
        buffer.getShort();//skip 2 byte
        this.characterClass = CharacterClass.parse(buffer.get());
        buffer.getShort();//skip 2 byte
        this.level = buffer.get();
        buffer.getInt();
        time = LocalDateTime.ofEpochSecond(buffer.getInt(), 0, ZoneOffset.UTC);
        buffer.getInt();
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

    public int getLevel() {
        return level;
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
        this.time = time;
    }

    @Override
    public String toString() {
        return "Meta{" +
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
