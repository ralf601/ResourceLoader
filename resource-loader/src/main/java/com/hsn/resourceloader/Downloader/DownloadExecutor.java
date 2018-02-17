package com.hsn.resourceloader.Downloader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public class DownloadExecutor extends ThreadPoolExecutor {

    public DownloadExecutor(int maximumPoolSize) {
        super(maximumPoolSize, maximumPoolSize,
                60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    }


}
