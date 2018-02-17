package com.hsn.resourceloader.Downloader;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public interface DownloadListener {

    void onComplete(String tag , byte [] data);

    void onFail(String tag ,String message);
}
