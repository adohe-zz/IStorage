package com.xqbase.block;

public class BlockItem {

    private ItemMeta meta;
    private byte[] key;
    private byte[] value;

    public BlockItem(byte[] key, byte[] value, ItemMeta meta) {
        this.key = key;
        this.value = value;
        this.meta = meta;
    }

    public BlockItem(byte[] key, byte[] value) {
        this(key, value, null);
    }

    public BlockItem(ItemMeta meta) {
        this(null, null, meta);
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public ItemMeta getMeta() {
        return meta;
    }
}
