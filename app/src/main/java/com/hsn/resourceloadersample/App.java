package com.hsn.resourceloadersample;

import android.app.Application;

import com.hsn.resourceloader.Configuration;
import com.hsn.resourceloader.ResourceManager;

/**
 * Created by hassanshakeel on 2/15/18.
 */

public class App extends Application {

    public void onCreate() {
        super.onCreate();
        setupResourceLoader();
    }

    private void setupResourceLoader(){
        Configuration resourceLoaderConfiguration = new Configuration();
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        resourceLoaderConfiguration.setCacheSize(cacheSize); //cache size in kb
        resourceLoaderConfiguration.setMaxConcurrentDownloads(10);//max concurrent downloads
        ResourceManager.setConfiguration(resourceLoaderConfiguration);
    }


}
