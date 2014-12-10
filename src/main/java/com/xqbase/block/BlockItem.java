package com.xqbase.block;

public class BlockItem {

    private ItemMeta meta;
    private byte[] key;
    private byte[] value;

    public BlockItem(byte[] key, byte[] value) {
        this.key = key;
        this.value = value;
        this.meta = null;
    }

    public BlockItem(ItemMeta meta) {
        this.key = null;
        this.value = null;
        this.meta = meta;
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
