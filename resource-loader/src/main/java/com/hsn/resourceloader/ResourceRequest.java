package com.hsn.resourceloader;

/**
 * Created by hassanshakeel on 2/16/18.
 */

public class ResourceRequest {


    public interface OnCompeteListener<T> {
        void onComplete(T result);
        void onError(Exception e);
    }

    private String url;
    private String requestIdentifier;
    private Class castClazz;

    public ResourceRequest load(String url) {
        this.url = url;
        return this;
    }

    public <T> void as(final Class<T> clazz, final OnCompeteListener<T> listener) {
        this.castClazz = clazz;
        requestIdentifier = ResourceManager.get().addRequest(this, new ResourceManager.OnResourceLoadedListener() {
            @Override
            public void onLoaded(Object o) {
                listener.onComplete(clazz.cast(o));
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        });
    }

    public void cancel(){
        if(requestIdentifier!=null){
            ResourceManager.get().cancelRequest(requestIdentifier);
        }
    }

    public String getRequestIdentifier() {
        return requestIdentifier;
    }

    public String getUrl() {
        return url;
    }

    public Class getCastClazz() {
        return castClazz;
    }

}
