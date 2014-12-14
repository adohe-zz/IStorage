package com.xqbase.block;

import com.xqbase.pointer.Pointer;
import com.xqbase.stroage.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultStorageBlock implements IBlock {

    private final int index;

    private final long capacity;

    private final StorageConfig.StorageMode storageMode;

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

    /**
     * Create a new storage block
     * @param file the storage file
     * @param index the file index
     * @param capacity capacity of block
     * @param storageMode storage-mode of the block
     */
    public DefaultStorageBlock(File file, int index, int capacity, StorageConfig.StorageMode storageMode) throws IOException {
        this.index = index;
        this.capacity = capacity;
        this.storageMode = storageMode;
        switch (storageMode) {
            case File:
                this.underlyingStorage = new FileStorage(file, capacity);
                break;
            case MapFile:
                this.underlyingStorage = new MapFileStorage(file, capacity);
                break;
            case OffHeap:
                this.underlyingStorage = new OffHeapStorage(capacity);
                break;
        }
    }

    @Override
    public byte[] get(Pointer pointer) throws IOException {
        byte[] value = new byte[pointer.getValueSize()];
        this.underlyingStorage.get(pointer.getMetaOffset() + ItemMeta.META_SIZE + pointer.getKeySize(), value);
        return value;
    }

    @Override
    public Pointer store(byte[] key, byte[] value, long ttl) {
        int payLoad = key.length + value.length;
        Allocation allocation = allocate(payLoad);
        if (allocation == null)
            return null;
        return store(allocation, key, value, ttl);
    }

    private Pointer store(Allocation allocation, byte[] key, byte[] value, long ttl) {
        return null;
    }

    @Override
    public long markDirty(int dirtySize) {
        return 0;
    }

    @Override
    public long getCapacity() {
        return this.capacity;
    }

    @Override
    public long getDirty() {
        return this.dirtyStorage.get();
    }

    @Override
    public long getUsed() {
        return this.usedStorage.get();
    }

    @Override
    public double getDirtyRatio() {
        return 0;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public StorageConfig.StorageMode getStorageMode() {
        return this.storageMode;
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

    /**
     * Check the block capacity enough for this payLoad
     * @param payLoad payLoad
     * @return {@code null} if this block can't allocate
     * this payLoad
     */
    private Allocation allocate(int payLoad) {
        int offset = currentItemOffset.addAndGet(ItemMeta.META_SIZE + payLoad);

        if (capacity < offset)
            return null;
        return new Allocation(offset - ItemMeta.META_SIZE  - payLoad);
    }

    private static class Allocation {

        private int itemOffset;

        public Allocation(int itemOffset) {
            this.itemOffset = itemOffset;
        }
    }
}
