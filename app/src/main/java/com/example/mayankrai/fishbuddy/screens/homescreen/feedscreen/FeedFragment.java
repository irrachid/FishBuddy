package com.example.mayankrai.fishbuddy.screens.homescreen.feedscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankrai.fishbuddy.R;
import com.example.mayankrai.fishbuddy.base.fragment.BaseFragment;
import com.example.mayankrai.fishbuddy.databinding.FeedFragmentBinding;
import com.example.mayankrai.fishbuddy.transition.ScaleDownImageTransition;
import com.example.mayankrai.fishbuddy.utility.maps.MapBitmapCache;

public class FeedFragment extends BaseFragment<FeedFragmentBinding,FeedViewModel> implements FeedView {

    @SuppressLint("NewApi")
    public static Fragment newInstance(final Context ctx) {
        FeedFragment fragment = new FeedFragment();
        ScaleDownImageTransition transition = new ScaleDownImageTransition(ctx, MapBitmapCache.instance().getBitmap());
        transition.addTarget(ctx.getString(R.string.mapPlaceholderTransition));
        transition.setDuration(600);
        fragment.setEnterTransition(transition);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setAndBindContentView(inflater, container, savedInstanceState, R.layout.fragment_recyclerview);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.relaodData();
            }
        });

        if(savedInstanceState == null) {
            binding.swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    onRefresh(true);
                }
            });
        }

        viewModel.relaodData();
    }


    @Override
    public void onRefresh(boolean isRefreshed) {
        binding.swipeRefreshLayout.setRefreshing(isRefreshed);
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }
}
