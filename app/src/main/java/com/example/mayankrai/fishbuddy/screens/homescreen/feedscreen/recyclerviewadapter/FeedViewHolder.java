package com.example.mayankrai.fishbuddy.screens.homescreen.feedscreen.recyclerviewadapter;

import android.view.View;

import com.example.mayankrai.fishbuddy.base.viewholder.BaseViewHolder;
import com.example.mayankrai.fishbuddy.databinding.FeedAdapterBinding;

public class FeedViewHolder extends BaseViewHolder<FeedAdapterBinding,FeedAdapterViewModel> implements FeedAdapterMvvmView {

    public FeedViewHolder(View v) {
        super(v);

        viewHolderComponent().inject(this);
        bindContentView(v);
    }
}
