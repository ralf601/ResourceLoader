package com.hsn.resourceloadersample.screens.common.controllers;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

        new ResourceRequest()
                .load("http://pastebin.com/raw/wgkJgazE")
                .as(JSONObject.class, new ResourceRequest.OnCompelTeListener<JSONObject>() {
                    @Override
                    public void onComplete(JSONObject result) {
                        Log.i("JSON", result.toString());
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

    }
}
