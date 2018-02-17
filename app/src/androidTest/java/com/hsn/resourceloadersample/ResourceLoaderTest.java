package com.hsn.resourceloadersample;

import android.graphics.Bitmap;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.hsn.resourceloader.ResourceRequest;
import com.hsn.resourceloadersample.screens.common.controllers.MainActivity;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by hassanshakeel on 2/17/18.
 */

@RunWith(AndroidJUnit4.class)
public class ResourceLoaderTest {
    volatile boolean validJsonCompleted = false;
    volatile boolean inValidJsonCompleted  = false;
    volatile boolean inValidBitmapCompleted = false;
    volatile boolean validBitmapCompleted = false;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void loadValidJsonTest() throws Exception {
        validJsonCompleted = true;
        new ResourceRequest()
                .load("http://pastebin.com/raw/wgkJgazE")
                .as(JSONObject.class, new ResourceRequest.OnCompeteListener<JSONObject>() {
                    @Override
                    public void onComplete(JSONObject result) {
                        assertTrue(true);
                        validJsonCompleted=true;
                    }
                    @Override
                    public void onError(Exception e) {
                        assertFalse(true);
                        validJsonCompleted=true;
                    }
                });

        while (!validJsonCompleted){}//wait for completation
    }

    @Test
    public void loadInValidJsonTest() throws Exception {
        new ResourceRequest()
                .load("https://stackoverflow.com")
                .as(JSONObject.class, new ResourceRequest.OnCompeteListener<JSONObject>() {
                    @Override
                    public void onComplete(JSONObject result) {
                        assertFalse(true);
                        inValidJsonCompleted=true;

                    }
                    @Override
                    public void onError(Exception e) {
                        assertTrue(true);
                        inValidJsonCompleted=true;
                    }
                });
        while (!inValidJsonCompleted){}//wait for completation

    }

    @Test
    public void loadValidBitmap() throws Exception {
        String imageUrl = "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32&s=63f1d805cffccb834cf839c719d91702";
        new ResourceRequest()
                .load(imageUrl)
                .as(Bitmap.class, new ResourceRequest.OnCompeteListener<Bitmap>() {
                    @Override
                    public void onComplete(Bitmap result) {
                        assertTrue(true);
                        validBitmapCompleted = true;
                    }
                    @Override
                    public void onError(Exception e) {
                        assertFalse(true);
                        validBitmapCompleted = true;
                    }
                });
        while (!validBitmapCompleted){}//wait for completation

    }

    @Test
    public void loadInValidBitmap() throws Exception {
        String imageUrl = "https://stackoverflow.com";
        //String imageUrl = "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32&s=63f1d805cffccb834cf839c719d91702";
        new ResourceRequest()
                .load(imageUrl)
                .as(Bitmap.class, new ResourceRequest.OnCompeteListener<Bitmap>() {
                    @Override
                    public void onComplete(Bitmap result) {
                        assertFalse(true);
                        inValidBitmapCompleted = true;
                    }
                    @Override
                    public void onError(Exception e) {
                        assertTrue(true);
                        inValidBitmapCompleted = true;
                    }
                });

        while (!inValidBitmapCompleted){}//wait for completation
    }
}
