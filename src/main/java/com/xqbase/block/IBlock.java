package com.xqbase.block;

import com.xqbase.pointer.Pointer;
import com.xqbase.stroage.StorageConfig;

import java.io.Closeable;
import java.io.IOException;

public interface IBlock extends Comparable<IBlock>, Closeable {

    /**
     * Gets item located at specific position and update the access time
     * @param pointer the retrieve pointer
     * @return binary content of item
     */
    byte[] get(Pointer pointer) throws IOException;

    /**
     * Stores the item
     * @param key item key
     * @param value item value
     * @param ttl time-to-live for item
     */
    Pointer store(byte[] key, byte[] value, long ttl);

    /**
     * Marks storage with {@code dirtySize} as dirty
     * @param dirtySize dirty size
     * @return total dirty size
     */
    long markDirty(int dirtySize);

    /**
     * Gets capacity of the block
     * @return capacity of the block
     */
    long getCapacity();

    /**
     * Gets total dirty size
     * @return total dirty storage size
     */
    long getDirty();

    /**
     * Gets total used storage size
     * @return total used storage size
     */
    long getUsed();

    /**
     * Calculates current dirty storage ratio
     * @return dirty ratio
     */
    double getDirtyRatio();

    /**
     * Gets the block index
     * @return block index
     */
    int getIndex();

    /**
     * Gets storage mode of this block
     * @return storage block
     */
    StorageConfig.StorageMode getStorageMode();

    /**
     * Frees the block storage
     */
    void free();
}
