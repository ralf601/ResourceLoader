package com.hsn.resourceloader.Downloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public class DownloaderImpl implements Downloader {

    final Map<String, Download> downloadList = new ConcurrentHashMap<>();

    final Executor executor ;
    DownloadListener downloadListener;

    public DownloaderImpl(int maxConcurrentDownloads) {
        //executor = new DownloadExecutor(maxConcurrentDownloads);
        executor = Executors.newFixedThreadPool(maxConcurrentDownloads);
    }

    @Override
    public void setListener(DownloadListener listener) {
        downloadListener = listener;
    }

    @Override
    public void start(final DownloadItem item) {
        Download download = new Download(item);
        executor.execute(download);
        downloadList.put(item.getTag(), download);
    }

    @Override
    public void cancel(String tag) {
        Download download = downloadList.remove(tag);
        if (download != null)
            download.stopDownload();
    }

    void done(String tag, byte[] data) {
        if (downloadListener != null) {
            downloadListener.onComplete(tag, data);
        }
        downloadList.remove(tag);
    }

    void fail(String tag, String message) {
        if (downloadListener != null) {
            downloadListener.onFail(tag, message);
        }
        downloadList.remove(tag);
    }


    final class Download implements Runnable {
        private DownloadItem downloadItem;
        private URL url = null;
        private boolean stopDownload = false;

        public Download(DownloadItem downloadItem) {
            this.downloadItem = downloadItem;
        }

        public void stopDownload() {
            stopDownload = true;
        }

        @Override
        public void run() {
            try {
                url = new URL(downloadItem.getUrl());
                URLConnection conn = url.openConnection();
                byte[] data;
                try {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int nRead;
                    data = new byte[16384];
                    while ((nRead = conn.getInputStream().read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                        if (stopDownload) {
                            fail(downloadItem.getTag(), "foce stopped");
                            return;
                        }
                    }
                    buffer.flush();
                    data = buffer.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                    fail(downloadItem.getTag(), "failed to convert inputstream to byte array");
                    return;
                }
                if (data != null)
                    done(downloadItem.getTag(), data);
                else
                    fail(downloadItem.getTag(), "failed to convert inputstream to byte array");
            } catch (IOException e) {
                e.printStackTrace();
                fail(downloadItem.getTag(), e.getMessage());
            }
        }
    }
}
