package com.xqbase.stroage;

import com.sun.tools.javac.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Disk file storage
 */
public class FileStorage implements IStorage {

    private final FileChannel fileChannel;
    private final RandomAccessFile rf;

    public FileStorage(String dir, int index, int capacity) throws IOException {
        File file = new File(dir);
        if (!file.exists()) {
            Assert.check(file.mkdirs());
        }

        String fullFileName = dir + index + "-" + System.currentTimeMillis() + DATA_FILE_SUFFIX;
        rf = new RandomAccessFile(fullFileName, "rw");
        rf.setLength(capacity);
        fileChannel = rf.getChannel();
    }

    public FileStorage(File file, int capacity) throws IOException {
        rf = new RandomAccessFile(file, "rw");
        rf.setLength(capacity);
        fileChannel = rf.getChannel();
    }

    @Override
    public void get(int position, byte[] destination) throws IOException {
        fileChannel.read(ByteBuffer.wrap(destination), position);
    }

    @Override
    public void put(int position, byte[] source) throws IOException {
        ByteBuffer bf = ByteBuffer.wrap(source);

        while (bf.hasRemaining()) {
            int len = fileChannel.write(bf, position);
            position += len;
        }
    }

    @Override
    public void free() {
        try {
            fileChannel.truncate(0);
        } catch (IOException e){/**/}
    }

    @Override
    public void close() throws IOException {
        if (fileChannel != null) {
            fileChannel.close();
        }
        if (rf != null) {
            rf.close();
        }
    }
}
