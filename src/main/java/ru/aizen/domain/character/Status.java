package ru.aizen.domain.character;

import ru.aizen.domain.util.BinaryUtils;

/**
 * Keep info about expansion, hardcore mode and status (dead or alive, if hardcode)
 */
public class Status {
    private boolean isExpansion;
    private boolean isHardcode;
    private boolean isDead;

    public Status(byte b) {
        readStatus(b);
    }

    public Status(String hex) {
        readStatus(Byte.parseByte(hex, 16));
    }

    private void readStatus(byte b){
        String bin = BinaryUtils.toBinaryString(b, true);
        System.out.println(bin);
        this.isExpansion = parseChar(bin.charAt(5));
        this.isHardcode = parseChar(bin.charAt(3));
        this.isDead = parseChar(bin.charAt(2));
    }

    private boolean parseChar(char ch){
        return ch == '1';
    }

    public byte toByte(){
        return 0;
    }

    public boolean isExpansion() {
        return isExpansion;
    }

    public void setExpansion(boolean expansion) {
        isExpansion = expansion;
    }

    public boolean isHardcode() {
        return isHardcode;
    }

    public void setHardcode(boolean hardcode) {
        isHardcode = hardcode;
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
                ", isHardcode=" + isHardcode +
                ", isDead=" + isDead +
                '}';
    }
}
