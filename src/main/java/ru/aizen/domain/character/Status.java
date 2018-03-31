package ru.aizen.domain.character;

import ru.aizen.domain.util.BinaryUtils;

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
        String bin = BinaryUtils.toBinaryString(b, true);
        this.isExpansion = parseChar(bin.charAt(5));
        this.isHardcore = parseChar(bin.charAt(2));
        this.isDead = parseChar(bin.charAt(3));
    }

    private boolean parseChar(char ch) {
        return ch == '1';
    }

    public byte toByte() {
        return (byte) Integer.parseInt(getBinaryString(), 2);
    }

    private String getBinaryString() {
        return "00" +
                (isExpansion ? "1" : "0") +
                "0" +
                (isDead ? "1" : "0") +
                (isHardcore ? "1" : "0") +
                "00";
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
