package com.hsn.resourceloader.Downloader;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public interface Downloader {

    void start(DownloadItem item);

    void setListener(DownloadListener listener);

    void cancel(String tag);

}
