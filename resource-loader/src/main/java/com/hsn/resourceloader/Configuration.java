package com.hsn.resourceloader;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public class Configuration {
    private int cacheSize = DEFAULT_CACHE_SIZE;
    private int maxConcurrentDownloads = DEFAULT_CONCURRENT_DOWNLOADS;

    private static final int DEFAULT_CACHE_SIZE = 5 * 1024;
    private static final int DEFAULT_CONCURRENT_DOWNLOADS = 10;


    public static Configuration getDefault() {
        return new Configuration(DEFAULT_CACHE_SIZE, DEFAULT_CONCURRENT_DOWNLOADS);
    }

    public Configuration(int cacheSize, int maxConcurrentDownloads) {
        this.cacheSize = cacheSize;
        this.maxConcurrentDownloads = maxConcurrentDownloads;
    }

    public Configuration() {
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public int getMaxConcurrentDownloads() {
        return maxConcurrentDownloads;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public void setMaxConcurrentDownloads(int maxConcurrentDownloads) {
        this.maxConcurrentDownloads = maxConcurrentDownloads;
    }
}
