package com.hsn.resourceloadersample;

import android.graphics.Bitmap;

import com.hsn.resourceloader.Downloader.DownloadItem;
import com.hsn.resourceloader.Downloader.DownloadListener;
import com.hsn.resourceloader.Downloader.Downloader;
import com.hsn.resourceloader.Downloader.DownloaderImpl;
import com.hsn.resourceloader.ResourceRequest;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DownloadTest {
    volatile boolean downloadTestCompleted = false;

    @Test
    public void testDownloader() {

        Downloader downloader = new DownloaderImpl(1);
        downloader.setListener(new DownloadListener() {
            @Override
            public void onComplete(String tag, byte[] data) {
                assertTrue(true);
                downloadTestCompleted=true;
            }
            @Override
            public void onFail(String tag, String message) {
                assertTrue(false);
                downloadTestCompleted=true;
            }
        });
        downloader.start(new DownloadItem("http://pastebin.com/raw/wgkJgazE","7"));

        while (!downloadTestCompleted){}
    }
}