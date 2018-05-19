package ru.aizen.domain.character;

import ru.aizen.domain.data.binary.Binary;

/**
 * Keep info about expansion, hardcore mode and status (dead or alive, if hardcode)
 */
public class Status {
    private boolean isExpansion;
    private boolean isHardcore;
    private boolean isDead;

    public Status() {
    }

    public Status(byte b) {
        readStatus(b);
    }

    public Status(String hex) {
        readStatus(Byte.parseByte(hex, 16));
    }

    private void readStatus(byte b) {
        Binary binary = new Binary(new byte[]{b});
        this.isHardcore = binary.get(2);
        this.isDead = binary.get(3);
        this.isExpansion = binary.get(5);
    }

    public byte toByte() {
        Binary binary = new Binary(Byte.SIZE);
        binary.set(2, isHardcore);
        binary.set(3, isDead);
        binary.set(5, isExpansion);
        return binary.getByte(0);
    }

    public boolean isExpansion() {
        return isExpansion;
    }

    public void setExpansion(boolean expansion) {
        isExpansion = expansion;
    }

    public boolean isHardcore() {
        return isHardcore;
    }

    public void setHardcore(boolean hardcore) {
        isHardcore = hardcore;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public String toString() {
        return "Status{" +
                "isExpansion=" + isExpansion +
                ", isHardcore=" + isHardcore +
                ", isDead=" + isDead +
                '}';
    }
}
