package com.hsn.resourceloadersample.screens.mainwall.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsn.resourceloadersample.R;
import com.hsn.resourceloadersample.model.UserContent;
import com.hsn.resourceloadersample.network.WebApiError;
import com.hsn.resourceloadersample.network.WebApiManager;
import com.hsn.resourceloadersample.network.WebApiResponseListener;
import com.hsn.resourceloadersample.screens.common.controllers.BaseFragment;
import com.hsn.resourceloadersample.screens.mainwall.views.MainWallView;
import com.hsn.resourceloadersample.screens.mainwall.views.MainWallViewMvcImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A Fragment containing  pins
 */
public class MainWallFragment extends BaseFragment implements
        WebApiResponseListener<List<UserContent>>,
        MainWallView.MainWallViewInteractor {


    private MainWallView mainWallView;
    private WebApiManager webApiManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainWallView = new MainWallViewMvcImpl(inflater, container);
        mainWallView.setInteractor(this);
        return mainWallView.getRootView();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webApiManager = WebApiManager.getInstance(getActivity().getApplicationContext());
        webApiManager.fetchDummyUserContent(this);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDataFetched(List<UserContent> response) {
        //sort by date
        Collections.sort(response, new Comparator() {
            @Override
            public int compare(Object userContent1, Object userContent2) {
                return ((UserContent)userContent2).getCreatedAt()
                        .compareTo(((UserContent)userContent1).getCreatedAt());
            }
        });
        mainWallView.bindUserContent(response);
        mainWallView.stopRefreshIndicator();
    }

    @Override
    public void onError(WebApiError error, String message) {
        mainWallView.stopRefreshIndicator();
        switch (error) {
            case Exception:
            case NoNetworkAvailable:
                toastShort(message);
                break;
            case TokenExpired:
                toastShort(getString(R.string.token_expired));
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClicked(UserContent userContent) {

    }

    @Override
    public void fetchData() {
        webApiManager.fetchDummyUserContent(this);
    }


//    @Override
//    public void onSmsMessageClicked(long id) {
//        // Create a bundle that will pass the ID of the clicked SMS message to the new fragment
//        Bundle args = new Bundle(1);
//        args.putLong(SmsDetailsFragment.ARG_SMS_MESSAGE_ID, id);
//
//        // Replace this fragment with a new one and pass args to it
//        replaceFragment(SmsDetailsFragment.class, true, args);
//    }

//    @Override
//    public void onSmsMessagesFetched(List<SmsMessage> smsMessages) {
//        mViewMVC.bindSmsMessages(smsMessages);
//    }
}
