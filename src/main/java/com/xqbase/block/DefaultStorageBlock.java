package com.xqbase.block;

import com.xqbase.stroage.StorageConfig;

import java.io.IOException;

public class DefaultStorageBlock implements IBlock {

    @Override
    public byte[] get(int position) {
        return new byte[0];
    }

    @Override
    public void store(byte[] key, byte[] value, long ttl) {

    }

    @Override
    public long markDirty(int dirtySize) {
        return 0;
    }

    @Override
    public long getCapacity() {
        return 0;
    }

    @Override
    public long getDirty() {
        return 0;
    }

    @Override
    public long getUsed() {
        return 0;
    }

    @Override
    public double getDirtyRatio() {
        return 0;
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public StorageConfig.StorageMode getStorageMode() {
        return null;
    }

    @Override
    public void free() {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public int compareTo(IBlock o) {
        return 0;
    }
}
