package com.xqbase.block;

import java.io.Serializable;

public class ItemMeta implements Serializable {

    public static final int META_SIZE = (Integer.SIZE + Integer.SIZE + Long.SIZE + Long.SIZE) / Byte.SIZE;

    private static final int LAST_ACCESS_OFFSET = 0;
    private static final int TTL_OFFSET = 8;
    private static final int KEY_SIZE_OFFSET = 16;
    private static final int VALUE_SIZE_OFFSET = 20;

    private long lastAccessTime;
    private long ttl;
    private int keySize;
    private int valueSize;
    private int offset;


}
