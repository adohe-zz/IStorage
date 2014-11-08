package com.xqbase.manager;

import com.xqbase.block.DefaultStorageBlock;
import com.xqbase.block.IBlock;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.xqbase.stroage.StorageConfig.StorageMode;

public class StorageManager {

    private static final long DEFAULT_BLOCK_CAPACITY = 128 * 1024 * 1024L;

    private static final int DEFAULT_INITIAL_NUMBER_OF_BLOCKS = 8;

    private final AtomicInteger blockCount = new AtomicInteger(0);

    private final Lock activeBlockChangeLock = new ReentrantLock();

    private volatile IBlock activeBlock;

    private final Queue<IBlock> usedBlocks = new ConcurrentLinkedDeque<IBlock>();

    private final Queue<IBlock> freeBlocks = new PriorityBlockingQueue<IBlock>();

    private final StorageMode storageMode;

    private final String dir;

    private final int capacityPerBlock;

    private final double dirtyRatioThreshold;

    private int allowedMaxOffHeapBlockCount;

    public StorageManager(String dir, int capacityPerBlock, int initNumberOfBlocks, StorageMode storageMode, double dirtyRatioThreshold,
                long maxOffHeapMemorySize) throws IOException {
        this.dir = dir;
        this.capacityPerBlock = capacityPerBlock;
        this.dirtyRatioThreshold = dirtyRatioThreshold;
        this.storageMode = storageMode;
        if (this.storageMode != StorageMode.File) {
            this.allowedMaxOffHeapBlockCount = (int) (maxOffHeapMemorySize / capacityPerBlock);
        } else {
            this.allowedMaxOffHeapBlockCount = 0;
        }

        initBlocks(dir, initNumberOfBlocks);
    }

    private void initBlocks(String dir, int initNumberOfBlocks) throws IOException {
        for (int i = 0; i < initNumberOfBlocks; i++) {
            freeBlocks.add(createBlock(i));
            blockCount.incrementAndGet();
        }

        activeBlock = freeBlocks.poll();
        if (activeBlock == null) {
            activeBlock = new DefaultStorageBlock(dir, blockCount.incrementAndGet(), capacityPerBlock, storageMode);
        }
    }

    private IBlock createBlock(int index) throws IOException {
        if (storageMode != StorageMode.File) {
            if (allowedMaxOffHeapBlockCount < 0) {
                return new DefaultStorageBlock(dir, index, capacityPerBlock, StorageMode.File);
            }

            allowedMaxOffHeapBlockCount --;
        }

        return new DefaultStorageBlock(dir, index, capacityPerBlock, storageMode);
    }
}
