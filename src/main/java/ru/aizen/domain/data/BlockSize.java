package ru.aizen.domain.data;

public class BlockSize {
    public static final int HEADER_BLOCK_OFFSET = 0;
    public static final int HEADER_BLOCK_SIZE = 16;
    public static final int META_BLOCK_OFFSET = HEADER_BLOCK_SIZE;
    public static final int META_BLOCK_SIZE = 40;
    public static final String ATTRIBUTES_BLOCK_START = "6766";
    public static final String SKILLS_BLOCK_START = "6966";
}
