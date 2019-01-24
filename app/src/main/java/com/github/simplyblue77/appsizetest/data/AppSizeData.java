package com.github.simplyblue77.appsizetest.data;

public class AppSizeData {
    private long cacheSize;
    private long dataSize;
    private long codeSize;

    public AppSizeData(long cacheSize, long dataSize, long codeSize) {
        this.cacheSize = cacheSize;
        this.dataSize = dataSize;
        this.codeSize = codeSize;
    }

    public long getCacheSize() {
        return cacheSize;
    }

    public long getCodeSize() {
        return codeSize;
    }

    public long getDataSize() {
        return dataSize;
    }

    public long getTotalSize() {
        return cacheSize + dataSize + codeSize;
    }
}
