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
        this.isHardcore = binary.getValueAt(2);
        this.isDead = binary.getValueAt(3);
        this.isExpansion = binary.getValueAt(5);
    }

    public byte toByte() {
        Binary binary = new Binary(Byte.SIZE);
        binary.setValueAt(2, isHardcore);
        binary.setValueAt(3, isDead);
        binary.setValueAt(5, isExpansion);
        binary.reverse();
        return Byte.parseByte(binary.toString(), 2);
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
