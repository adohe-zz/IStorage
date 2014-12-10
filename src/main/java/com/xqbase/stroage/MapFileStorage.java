package com.xqbase.stroage;

import com.sun.tools.javac.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Memory map file storage
 */
public class MapFileStorage implements IStorage {

    private FileChannel fileChannel;
    private RandomAccessFile rf;
    private ThreadLocalByteBuffer threadLocalByteBuffer;

    public MapFileStorage(String dir, int index, int capacity) throws IOException {
        File directory = new File(dir);
        if (!directory.exists()) {
            Assert.check(directory.mkdirs());
        }

        String fullFileName = dir + index + "-" + System.currentTimeMillis() + DATA_FILE_SUFFIX;
        rf = new RandomAccessFile(fullFileName, "rw");
        fileChannel = rf.getChannel();
        rf.setLength(capacity);
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.PRIVATE, 0, capacity);
        threadLocalByteBuffer = new ThreadLocalByteBuffer(mappedByteBuffer);
    }

    public MapFileStorage(File file, int capacity) throws IOException {
        rf = new RandomAccessFile(file, "rw");
        fileChannel = rf.getChannel();
        rf.setLength(capacity);
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.PRIVATE, 0, capacity);
        threadLocalByteBuffer = new ThreadLocalByteBuffer(mappedByteBuffer);
    }

    private ByteBuffer getLocalByteBuffer(int position) {
        ByteBuffer byteBuffer = this.threadLocalByteBuffer.getByteBuffer();
        byteBuffer.position(position);
        return byteBuffer;
    }

    @Override
    public void get(int position, byte[] destination) throws IOException {
        ByteBuffer byteBuffer = getLocalByteBuffer(position);
        byteBuffer.get(destination);
    }

    @Override
    public void put(int position, byte[] source) throws IOException {
        ByteBuffer byteBuffer = getLocalByteBuffer(position);
        byteBuffer.put(source);
    }

    @Override
    public void free() {
        MappedByteBuffer mappedByteBuffer = (MappedByteBuffer) threadLocalByteBuffer.getByteBuffer();
        mappedByteBuffer.clear();

        try {
            fileChannel.truncate(0);
        } catch (IOException e) {/**/}
    }

    @Override
    public void close() throws IOException {
        if (fileChannel != null) {
            fileChannel.close();
        }
        if (rf != null) {
            rf.close();
        }

        // GC
        threadLocalByteBuffer.set(null);
        threadLocalByteBuffer = null;
    }

    private static class ThreadLocalByteBuffer extends ThreadLocal<ByteBuffer> {

        private ByteBuffer _src;

        public ThreadLocalByteBuffer(ByteBuffer src) {
            _src = src;
        }

        public ByteBuffer getByteBuffer() {
            return _src;
        }

        /**
         * ThreadLocal will always get the copy of data
         * @return the copy of source ByteBuffer
         */
        @Override
        protected ByteBuffer initialValue() {
            return _src.duplicate();
        }
    }
}
