package com.hsn.resourceloader.Downloader;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public class DownloadItem {
    private String url,tag;

    public DownloadItem(String url, String tag) {
        this.url = url;
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public String getTag() {
        return tag;
    }

}
