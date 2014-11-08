package com.xqbase.pointer;

import com.xqbase.block.IBlock;

public class Pointer {

    private final IBlock block;
    private final int metaOffset;
    private final int keySize;
    private final int valueSize;
    private final long ttl;

    public Pointer(IBlock block, int metaOffset, int keySize, int valueSize, long ttl) {
        this.block = block;
        this.metaOffset = metaOffset;
        this.keySize = keySize;
        this.valueSize = valueSize;
        this.ttl = ttl;
    }

    public IBlock getBlock() {
        return block;
    }

    public int getMetaOffset() {
        return metaOffset;
    }

    public int getKeySize() {
        return keySize;
    }

    public int getValueSize() {
        return valueSize;
    }

    public long getTtl() {
        return ttl;
    }
}
