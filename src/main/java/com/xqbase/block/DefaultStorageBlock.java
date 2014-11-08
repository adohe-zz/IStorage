package com.xqbase.block;

import com.xqbase.stroage.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultStorageBlock implements IBlock {

    private final int index;

    private final long capacity;

    private StorageConfig.StorageMode storageMode;

    private final AtomicInteger usedStorage = new AtomicInteger(0);

    private final AtomicInteger dirtyStorage = new AtomicInteger(0);

    private final AtomicInteger currentItemOffset = new AtomicInteger(0);

    private IStorage underlyingStorage;

    /**
     * Create a new storage block
     * @param dir the directory
     * @param index the file index
     * @param capacity capacity of block
     * @param storageMode storage-mode of the block
     */
    public DefaultStorageBlock(String dir, int index, int capacity, StorageConfig.StorageMode storageMode) throws IOException {
        this.index = index;
        this.capacity = capacity;
        this.storageMode = storageMode;
        switch (storageMode) {
            case File:
                this.underlyingStorage = new FileStorage(dir, index, capacity);
                break;
            case MapFile:
                this.underlyingStorage = new MapFileStorage(dir, index, capacity);
                break;
            case OffHeap:
                this.underlyingStorage = new OffHeapStorage(capacity);
                break;
        }
    }

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

    private static class Allocation {

        private int itemOffset;

        public Allocation(int itemOffset) {
            this.itemOffset = itemOffset;
        }
    }

    @Override
    public int compareTo(IBlock o) {
        return 0;
    }
}
