package com.example.mayankrai.fishbuddy.base.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.example.mayankrai.fishbuddy.BR;
import com.example.mayankrai.fishbuddy.FishBuddyApplication;
import com.example.mayankrai.fishbuddy.base.navigator.Navigator;
import com.example.mayankrai.fishbuddy.base.uibase.view.MvvmView;
import com.example.mayankrai.fishbuddy.base.uibase.viewmodel.ViewModel;
import com.example.mayankrai.fishbuddy.dependencyinjection.component.ActivityComponent;
import com.example.mayankrai.fishbuddy.dependencyinjection.component.DaggerActivityComponent;
import com.example.mayankrai.fishbuddy.dependencyinjection.module.ActivityModule;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import io.realm.Realm;
/* Base class for Activities when using a view model with data binding.
 * This class provides the binding and the view model to the subclass. The
 * view model is injected and the binding is created when the content view is set.
 * Each subclass therefore has to call the following code in onCreate():
 *    activityComponent().inject(this);
 *    setAndBindContentView(R.layout.my_activity_layout, savedInstanceState);
 *
 * After calling these methods, the binding and the view model is initialized.
 * saveInstanceState() and restoreInstanceState() methods of the view model
 * are automatically called in the appropriate lifecycle events when above calls
 * are made.
 *
 * Your subclass must implement the MvvmView implementation that you use in your
 * view model. */
public abstract class BaseActivity<B extends ViewDataBinding, V extends ViewModel> extends AppCompatActivity {


    // Inject a Realm instance into every Activity, since the instance
    // is cached and reused for a thread (avoids create/destroy overhead)
    @Inject protected Realm realm;

    protected B binding;

    @Inject protected V viewModel;

    @Inject RefWatcher refWatcher;

    @Inject Navigator navigator;

    private ActivityComponent mActivityComponent;

    @Override
    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(viewModel != null) { viewModel.saveInstanceState(outState); }
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        if(refWatcher != null) {
            refWatcher.watch(mActivityComponent);
            if(viewModel != null) { refWatcher.watch(viewModel); }
        }
        if(viewModel != null) { viewModel.detach(); }
        binding = null;
        viewModel = null;
        mActivityComponent = null;
        if(realm != null) { realm.close(); }
    }

    public final ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .appComponent(FishBuddyApplication.getAppComponent())
                    .build();
        }
        return mActivityComponent;

    }

    /* Sets the content view, creates the binding and attaches the view to the view model */
    protected final void setAndBindContentView(@Nullable Bundle savedInstanceState, @LayoutRes int layoutResID) {
        if(viewModel == null) { throw new IllegalStateException("viewModel must already be set via injection"); }
        binding = DataBindingUtil.setContentView(this, layoutResID);

        viewModel.attach(this instanceof MvvmView ? (MvvmView) this : null);
        viewModel.attach();

        binding.setVariable(BR.viewModel, viewModel);

    }

    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    public int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }

    public String string(@StringRes int resId) {
        return getResources().getString(resId);
    }
}
