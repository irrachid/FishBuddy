package com.example.mayankrai.fishbuddy.screens.homescreen.feedscreen.recyclerviewadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankrai.fishbuddy.R;
import com.example.mayankrai.fishbuddy.data.model.Feed;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private List<Feed> feedList = new ArrayList<>();

    @Inject
    public FeedAdapter() { }


    //to add the data to list
    public void add(int location, Feed object) {
        try {
            System.out.println("FeedAdapter.add object="+object.toString());
            feedList.add(location, object);
            notifyItemInserted(location);
            notifyItemRangeChanged(location, feedList.size());
        } catch (Exception e) {
            //FirebaseCrash.report(e);
        }
    }

    //to add the data to list
    public void addAll(List<Feed> object) {
        try {
            feedList.addAll(object);
            notifyDataSetChanged();
        } catch (Exception e) {
            //FirebaseCrash.report(e);
        }
    }


    //to update the data in list
    public void update(int location, Object o)
    {
        notifyItemChanged(location);
    }

    //to remove the data in list
    public void remove(int location,Object o){
        feedList.remove(location);
        notifyItemRemoved(location);
        notifyItemRangeChanged(location, feedList.size());
    }

    //to clear all the data in list
    public void clearData() {
        int size = this.feedList.size();
        this.feedList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void setCountryList(List<Feed> feedList) {
        this.feedList = feedList;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, viewGroup, false);

        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder feedViewHolder, int position) {
        feedViewHolder.viewModel().update(feedList.get(position), position == feedList.size()-1);
        feedViewHolder.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }
}
