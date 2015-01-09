package com.xqbase.stroage;

import java.io.Closeable;
import java.io.IOException;

/**
 * Underlying Storage Interface for real read/write
 *
 * @author Tony He
 */
public interface IStorage extends Closeable {

    String DATA_FILE_SUFFIX = ".data";

    /**
     * Get bytes from the specified pos
     * @param position the position
     * @param destination the destination byte array
     * @throws IOException throw {@code IOException} when get fails
     */
    void get(int position, byte[] destination) throws IOException;

    /**
     * Put bytes into the storage with specified beginning pos
     * @param position the position
     * @param source the bytes source
     * @throws IOException throw {@code IOException} when put fails
     */
    void put(int position, byte[] source) throws IOException;

    /**
     * Just free(recycle) the storage
     */
    void free();
}
