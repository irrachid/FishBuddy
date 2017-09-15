package com.example.mayankrai.fishbuddy.dependencyinjection.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;

import com.example.mayankrai.fishbuddy.data.remote.DisposableManager;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.ChildFragmentManager;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.HorizontalLinearLayoutManager;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private final Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    @ChildFragmentManager
    FragmentManager provideChildFragmentManager() { return mFragment.getChildFragmentManager(); }


    @Provides
    @PerFragment
    LinearLayoutManager provideLinearLayoutManager() { return new LinearLayoutManager(mFragment.getContext()); }

    @Provides
    @PerFragment
    @HorizontalLinearLayoutManager
    LinearLayoutManager provideHorizontalLinearLayoutManager() { return new LinearLayoutManager(mFragment.getContext(), LinearLayoutManager.HORIZONTAL, false); }


    @Provides
    @PerFragment
    DisposableManager provideDisposableManager() { return new DisposableManager(); }

}
