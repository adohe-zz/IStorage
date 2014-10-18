package com.xqbase.stroage;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/**
 * Off heap storage
 */
public class OffHeapStorage implements IStorage {

    protected ByteBuffer byteBuffer;

    private static final Unsafe UNSAFE = getUnSafe();
    private static final long BYTE_ARRAY_OFFSET = (long) UNSAFE.arrayBaseOffset(byte[].class);
    private final long address;

    public OffHeapStorage(int capacity) {
        this.address = UNSAFE.allocateMemory(capacity);
    }

    public OffHeapStorage(int capacity, ByteBuffer byteBuffer) {
        this.byteBuffer = ByteBuffer.allocateDirect(capacity);
        try {
            Method method = byteBuffer.getClass().getMethod("address");
            method.setAccessible(true);
            this.address = (Long) method.invoke(byteBuffer);
        } catch (Exception e) {
            throw new RuntimeException("Unable to allocate memory" + e);
        }
    }

    /**
     * Get the UnSafe through reflection
     * @return UnSafe instance
     */
    private static Unsafe getUnSafe() {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            return (Unsafe) unsafeField.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void get(int position, byte[] destination) throws IOException {
        assert position >= 0 : position;
        this.get(address + position, destination, BYTE_ARRAY_OFFSET, destination.length);
    }

    /**
     * Copy bytes from the local buffer to the destination byte array
     * @param base the base address
     * @param destination the destination byte array
     * @param offset the offset
     * @param length the number of copy bytes
     */
    private void get(long base, byte[] destination, long offset, int length) {
        UNSAFE.copyMemory(null, base, destination, offset, length);
    }

    @Override
    public void put(int position, byte[] source) throws IOException {
        assert position >= 0 : position;
        this.put(source, BYTE_ARRAY_OFFSET, address + position, source.length);
    }

    /**
     * Put bytes from source array into the local buffer
     * @param source the source byte array
     * @param srcOffset the source array offset
     * @param base the base address
     * @param length the number of copy bytes
     */
    private void put(byte[] source, long srcOffset, long base, int length) {
        UNSAFE.copyMemory(source, srcOffset, null, base, length);
    }

    @Override
    public void free() {

    }

    @Override
    public void close() throws IOException {
        UNSAFE.freeMemory(this.address);
    }
}
