package com.example.mayankrai.fishbuddy.base.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankrai.fishbuddy.BR;
import com.example.mayankrai.fishbuddy.base.activity.BaseActivity;
import com.example.mayankrai.fishbuddy.base.uibase.view.MvvmView;
import com.example.mayankrai.fishbuddy.base.uibase.viewmodel.ViewModel;
import com.example.mayankrai.fishbuddy.dependencyinjection.component.DaggerFragmentComponent;
import com.example.mayankrai.fishbuddy.dependencyinjection.component.FragmentComponent;
import com.example.mayankrai.fishbuddy.dependencyinjection.module.FragmentModule;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

/* Base class for Fragments when using a view model with data binding.
 * This class provides the binding and the view model to the subclass. The
 * view model is injected and the binding is created when the content view is set.
 * Each subclass therefore has to call the following code in onCreateView():
 *    if(viewModel == null) { fragmentComponent().inject(this); }
 *    return setAndBindContentView(inflater, container, R.layout.my_fragment_layout, savedInstanceState);
 *
 * After calling these methods, the binding and the view model is initialized.
 * saveInstanceState() and restoreInstanceState() methods of the view model
 * are automatically called in the appropriate lifecycle events when above calls
 * are made.
 *
 * Your subclass must implement the MvvmView implementation that you use in your
 * view model. */
public abstract class BaseFragment<B extends ViewDataBinding, V extends ViewModel> extends Fragment {

    protected B binding;

    @Inject protected V viewModel;

    @Inject
    RefWatcher refWatcher;

    private FragmentComponent mFragmentComponent;

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(viewModel != null) { viewModel.saveInstanceState(outState); }
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        if(viewModel != null) {
            viewModel.detach();
            if(refWatcher != null) { refWatcher.watch(viewModel);}
        }
        binding = null;
        viewModel = null;
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        if(refWatcher != null) {
            refWatcher.watch(this);
            refWatcher.watch(mFragmentComponent);
        }
        mFragmentComponent = null;
    }


    protected final FragmentComponent fragmentComponent() {
        if(mFragmentComponent == null) {
            mFragmentComponent = DaggerFragmentComponent.builder()
                    .fragmentModule(new FragmentModule(this))
                    .activityComponent(((BaseActivity) getActivity()).activityComponent())
                    .build();
        }

        return mFragmentComponent;
    }

    /* Sets the content view, creates the binding and attaches the view to the view model */
    protected final View setAndBindContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, @LayoutRes int layoutResID) {
        if(viewModel == null) { throw new IllegalStateException("viewModel must already be set via injection"); }
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false);

        viewModel.attach(this instanceof MvvmView ? (MvvmView) this : null);
        viewModel.attach();

        binding.setVariable(BR.viewModel, viewModel);

        return binding.getRoot();
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

    public abstract boolean onBackPressed();
}
