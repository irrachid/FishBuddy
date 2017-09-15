package com.example.mayankrai.fishbuddy.base.navigator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


public class ActivityNavigator implements Navigator {

    protected final FragmentActivity activity;

    public ActivityNavigator(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void finishActivity() {
        activity.finish();
    }

    @Override
    public final void startActivity(@NonNull Intent intent) {
        activity.startActivity(intent);
    }

    @Override
    public final void startActivity(@NonNull String action) {
        activity.startActivity(new Intent(action));
    }

    @Override
    public final void startActivity(@NonNull String action, @NonNull Uri uri) {
        activity.startActivity(new Intent(action, uri));
    }

    @Override
    public final void replaceFragment(@IdRes int containerId, Fragment fragment, Bundle args) {
        replaceFragmentInternal(activity.getSupportFragmentManager(), containerId, fragment, null, args, false, null);
    }

    @Override
    public final void replaceFragment(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag, Bundle args) {
        replaceFragmentInternal(activity.getSupportFragmentManager(), containerId, fragment, fragmentTag, args, false, null);
    }

    @Override
    public final void replaceFragmentAndAddToBackStack(@IdRes int containerId, Fragment fragment, Bundle args, String backstackTag) {
        replaceFragmentInternal(activity.getSupportFragmentManager(), containerId, fragment, null, args, true, backstackTag);
    }

    @Override
    public final void replaceFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag, Bundle args, String backstackTag) {
        replaceFragmentInternal(activity.getSupportFragmentManager(), containerId, fragment, fragmentTag, args, true, backstackTag);
    }

    protected final void replaceFragmentInternal(FragmentManager fm, @IdRes int containerId, Fragment fragment, String fragmentTag, Bundle args, boolean addToBackstack,  String backstackTag) {
        if(args != null) { fragment.setArguments(args);}
        FragmentTransaction ft = fm.beginTransaction().replace(containerId, fragment, fragmentTag);
        if(addToBackstack) {
            ft.addToBackStack(backstackTag).commit();
            fm.executePendingTransactions();
        } else {
            ft.commitNow();
        }
    }
}
