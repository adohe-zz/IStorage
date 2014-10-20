package com.xqbase.block;

import java.util.concurrent.atomic.AtomicInteger;

public class BlockHead {

    public static final int HEAD_SIZE = (Byte.SIZE + Integer.SIZE) / Byte.SIZE;
    public static final int FLAG_OFFSET = 0;
    public static final int INDEX_OFFSET = 1;

    private byte blockFlag;

    private final AtomicInteger blockIndex = new AtomicInteger(0);

    public BlockHead() {

    }

    public BlockHead(byte blockFlag, int blockIndex) {
        this.blockFlag = blockFlag;
        this.blockIndex.set(blockIndex);
    }


    public byte getBlockFlag() {
        return blockFlag;
    }

    public void setBlockFlag(byte blockFlag) {
        this.blockFlag = blockFlag;
    }

    public int getBlockIndex() {
        return this.blockIndex.get();
    }

    public void setBlockIndex(int blockIndex) {
        this.blockIndex.set(blockIndex);
    }
}
