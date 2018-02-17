package com.hsn.resourceloadersample.screens.common.controllers;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;


/**
 * This abstract class contains convenience logic which is common to all fragments in the app.
 */
public abstract class BaseFragment extends Fragment {


    private AbstractFragmentCallback mCallback;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (AbstractFragmentCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + AbstractFragmentCallback.class.getCanonicalName());
        }

    }


    public void replaceFragment(Class<? extends Fragment> claz, boolean addToBackStack,
                                Bundle args) {
        mCallback.replaceFragment(claz, addToBackStack, args);
    }



    public interface AbstractFragmentCallback {

        void replaceFragment(Class<? extends Fragment> claz, boolean addToBackStack,
                             Bundle args);

    }

    protected void toastShort(String text) {
        Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
    }

    protected void toastLong(String text) {
        Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_LONG);
    }


}
