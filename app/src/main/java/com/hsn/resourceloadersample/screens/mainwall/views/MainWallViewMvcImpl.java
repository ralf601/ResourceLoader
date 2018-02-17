package com.hsn.resourceloadersample.screens.mainwall.views;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.hsn.resourceloadersample.R;
import com.hsn.resourceloadersample.model.UserContent;
import com.hsn.resourceloadersample.screens.mainwall.adapters.PinAdapter;

import java.util.List;

/**
 * This MVC view contains a list view and intercepts click events
 */
public class MainWallViewMvcImpl implements MainWallView, PinAdapter.OnItemClickListener {


    private View mRootView;
    private MainWallViewInteractor interactor;
    private RecyclerView recyclerView;
    private PinAdapter pinAdapter;
    private SwipeRefreshLayout refreshLayout;


    public MainWallViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.view_main_wall, container, false);
        initView(mRootView);
        setupRecyclerView();
        initListeners();
    }

    @Override
    public void setInteractor(MainWallViewInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void stopRefreshIndicator() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }


    @Override
    public void bindUserContent(List<UserContent> userContents) {
        pinAdapter.update(userContents);
    }

    @Override
    public void onClick(UserContent userContent) {
        if (interactor != null)
            interactor.onItemClicked(userContent);
    }


    private void setupRecyclerView() {
        pinAdapter = new PinAdapter();
        pinAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getRootView().getContext()));

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setAdapter(pinAdapter);


    }


    @Override
    public void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
    }

    @Override
    public void initListeners() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                interactor.fetchData();
            }
        });
    }
}
