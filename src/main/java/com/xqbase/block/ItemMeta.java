package com.xqbase.block;

import java.io.Serializable;

public class ItemMeta implements Serializable {

    public static final int META_SIZE = (Integer.SIZE + Integer.SIZE + Long.SIZE + Long.SIZE) / Byte.SIZE;

    private static final int LAST_ACCESS_OFFSET = 0;
    private static final int TTL_OFFSET = 8;
    private static final int KEY_SIZE_OFFSET = 16;
    private static final int VALUE_SIZE_OFFSET = 20;

    private long lastAccessTime;
    private long ttl;
    private int keySize;
    private int valueSize;
    private int offset;

    public ItemMeta(int offset) {
        this.keySize = 0;
        this.valueSize = 0;
        this.lastAccessTime = System.currentTimeMillis();
        this.ttl = 0L;
        this.offset = offset;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public int getValueSize() {
        return valueSize;
    }

    public void setValueSize(int valueSize) {
        this.valueSize = valueSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
