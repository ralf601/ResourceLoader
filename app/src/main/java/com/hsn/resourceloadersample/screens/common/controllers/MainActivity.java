package com.hsn.resourceloadersample.screens.common.controllers;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hsn.resourceloader.ResourceRequest;
import com.hsn.resourceloadersample.R;
import com.hsn.resourceloadersample.screens.common.views.RootViewImpl;
import com.hsn.resourceloadersample.screens.common.views.ViewMvc;
import com.hsn.resourceloadersample.screens.mainwall.controllers.MainWallFragment;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements BaseFragment.AbstractFragmentCallback {

    ViewMvc viewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMvc = new RootViewImpl(this, null);

        // Set the root view of the associated MVC view as the content of this activity
        setContentView(viewMvc.getRootView());

        // Show the default fragment if the application is not restored
        if (savedInstanceState == null) {
            replaceFragment(MainWallFragment.class, false, null);
        }
    }

    @Override
    public void replaceFragment(Class<? extends Fragment> claz, boolean addToBackStack,
                                Bundle args) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();


        Fragment newFragment;

        try {
            // Create new fragment
            newFragment = claz.newInstance();
            if (args != null) newFragment.setArguments(args);
        } catch (InstantiationException e) {
            e.printStackTrace();
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        if (addToBackStack) {
            // Add this transaction to the back stack
            ft.addToBackStack(null);
        }

        // Change to a new fragment
        ft.replace(R.id.frame_contents, newFragment, claz.getClass().getSimpleName());
        ft.commit();


        testDownloader();

    }

    private void testDownloader() {

        String imageUrl = "https://stackoverflow.com";
        //String imageUrl = "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32&s=63f1d805cffccb834cf839c719d91702";
        new ResourceRequest()
                .load(imageUrl)
                .as(Bitmap.class, new ResourceRequest.OnCompeteListener<Bitmap>() {
                    @Override
                    public void onComplete(Bitmap result) {
                        Log.i("","");
                    }
                    @Override
                    public void onError(Exception e) {
                    }
                });

    }
}
