package com.example.mayankrai.fishbuddy.screens.homescreen.feedscreen;


import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mayankrai.fishbuddy.base.uibase.viewmodel.BaseViewModel;
import com.example.mayankrai.fishbuddy.data.model.Feed;
import com.example.mayankrai.fishbuddy.data.model.FeedModel;
import com.example.mayankrai.fishbuddy.data.remote.DisposableManager;
import com.example.mayankrai.fishbuddy.data.remote.FBuddyRetrofitApi;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerFragment;
import com.example.mayankrai.fishbuddy.screens.homescreen.feedscreen.recyclerviewadapter.FeedAdapter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by mayankrai on 8/26/17.
 */
@PerFragment
public class FeedViewModel extends BaseViewModel<FeedView> implements IFeedViewModel {

    private final FBuddyRetrofitApi fBuddyRetrofitApi;
    public FeedAdapter adapter;
    public LinearLayoutManager linearLayoutManager;

    private DisposableManager disposableManager;

    @Inject
    public FeedViewModel(FeedAdapter adapter, LinearLayoutManager linearLayoutManager, FBuddyRetrofitApi fBuddyRetrofitApi, DisposableManager disposableManager) {
        this.adapter = adapter;
        this.linearLayoutManager = linearLayoutManager;
        this.fBuddyRetrofitApi = fBuddyRetrofitApi;
        this.disposableManager = disposableManager;
    }

    @Override
    public void relaodData() {
        Disposable disposable = fBuddyRetrofitApi.getAllFeeds()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FeedModel>() {
                    @Override
                    public void accept(FeedModel feedModel) throws Exception {
                        System.out.println("FeedViewModel.accept feedModels="+feedModel.getFeedList().size());

                        //adapter.addAll(feedModel.getFeedList());
                        //adapter.setCountryList(feedModel.getFeedList());
                        //adapter.notifyDataSetChanged();

                        for(Feed feed : feedModel.getFeedList())
                        {
                            System.out.println("FeedViewModel.accept feed="+feed);
                            adapter.add(adapter.getItemCount(),feed);
                        }
                        getView().onRefresh(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable, "Could not load feeds");
                        getView().onRefresh(false);
                    }
                });

        disposableManager.add(disposable);

    }

    @BindingAdapter({"bind:setLayoutManager"})
    public static void bindLayoutManager(final RecyclerView view, LinearLayoutManager linearLayoutManager) {
        view.setLayoutManager(linearLayoutManager);

    }

    @BindingAdapter({"bind:setAdapter"})
    public static void bindSetAdapter(final RecyclerView view, FeedAdapter adapter) {
        view.setAdapter(adapter);

    }

    @Override
    public void detach() {
        super.detach();
        disposableManager.dispose();
    }
}
