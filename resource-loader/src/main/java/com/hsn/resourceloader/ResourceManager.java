package com.hsn.resourceloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.hsn.resourceloader.Cache.Cacheable;
import com.hsn.resourceloader.Cache.CachedBitmap;
import com.hsn.resourceloader.Cache.CachedJson;
import com.hsn.resourceloader.Cache.ResourceCache;
import com.hsn.resourceloader.Downloader.DownloadItem;
import com.hsn.resourceloader.Downloader.DownloadListener;
import com.hsn.resourceloader.Downloader.Downloader;
import com.hsn.resourceloader.Downloader.DownloaderImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public class ResourceManager implements DownloadListener {

    private static ResourceManager resourceManager;
    private static Configuration configuration;

    private Map<String, ResourceRequest> requestMap = new HashMap<>();
    private Map<String, OnResourceLoadedListener> listenerMap = new HashMap<>();
    private Downloader downloader;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private ResourceCache cache;


    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuration configuration) {
        ResourceManager.configuration = configuration;
    }

    public interface OnResourceLoadedListener {
        void onLoaded(Object o);

        void onError(Exception e);

    }

    public String addRequest(final ResourceRequest resourceRequest, final OnResourceLoadedListener loadedListener) {
        final Cacheable cacheable = cache.getResourceFromMemCache(resourceRequest.getUrl());
        if (cacheable != null) {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    loadedListener.onLoaded(cacheable.getValue());
                }
            });
            return resourceRequest.getRequestIdentifier();
        }
        String requestIdentifier = System.nanoTime() + "ç" + resourceRequest.getUrl();
        requestMap.put(requestIdentifier, resourceRequest);
        listenerMap.put(requestIdentifier, loadedListener);
        downloader.start(new DownloadItem(resourceRequest.getUrl(), requestIdentifier));
        return requestIdentifier;
    }

    public void cancelRequest(String requestIdentifier) {
        downloader.cancel(requestIdentifier);
    }

    public ResourceManager() {
        if (configuration == null)
            configuration = Configuration.getDefault();
        downloader = new DownloaderImpl(configuration.getMaxConcurrentDownloads());
        downloader.setListener(this);
        cache = new ResourceCache(configuration.getCacheSize());
    }

    @NonNull
    public static ResourceManager get() {
        if (resourceManager == null) {
            synchronized (ResourceManager.class) {
                if (resourceManager == null) {
                    resourceManager = new ResourceManager();
                }
            }
        }
        return resourceManager;
    }


    @Override
    public void onComplete(String tag, byte[] data) {
        final ResourceRequest resourceRequest = requestMap.remove(tag);
        final OnResourceLoadedListener onResourceLoadedListener = listenerMap.remove(tag);

        if (resourceRequest == null)
            return;
        if (resourceRequest.getCastClazz() == Bitmap.class) {
            Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (b != null) {
                //resourceRequest.getImageView().setImageBitmap(b);
                final CachedBitmap cachedBitmap = new CachedBitmap(tag.split("ç")[1], b);
                cache.addResourceToMemoryCache(cachedBitmap);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onResourceLoadedListener != null)
                            onResourceLoadedListener.onLoaded(cachedBitmap.getValue());
                    }
                });
            } else {
                if (onResourceLoadedListener != null) {
                    onResourceLoadedListener.onError(new Exception("Failed to decode bitmap"));
                }
            }
        }

        if (resourceRequest.getCastClazz() == JSONObject.class) {
            JSONObject jsonObject;
            try {
                JSONArray jsonArray = new JSONArray(new String(data));
                jsonObject = new JSONObject().accumulate("Data", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
                try {
                    jsonObject = new JSONObject(new String(data));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    if (onResourceLoadedListener != null)
                        onResourceLoadedListener.onError(new Exception("Invalid json string"));
                    return;
                }

            }
            final CachedJson cachedJson = new CachedJson(resourceRequest.getUrl(), jsonObject);
            cache.addResourceToMemoryCache(cachedJson);
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (onResourceLoadedListener != null)
                        onResourceLoadedListener.onLoaded(cachedJson.getValue());
                }
            });

        }
    }


    @Override
    public void onFail(String tag, String message) {

    }
}
