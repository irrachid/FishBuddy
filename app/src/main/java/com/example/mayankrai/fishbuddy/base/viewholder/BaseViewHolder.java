package com.example.mayankrai.fishbuddy.base.viewholder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mayankrai.fishbuddy.BR;
import com.example.mayankrai.fishbuddy.FishBuddyApplication;
import com.example.mayankrai.fishbuddy.base.navigator.Navigator;
import com.example.mayankrai.fishbuddy.base.uibase.view.MvvmView;
import com.example.mayankrai.fishbuddy.base.uibase.viewmodel.ViewModel;
import com.example.mayankrai.fishbuddy.dependencyinjection.component.DaggerViewHolderComponent;
import com.example.mayankrai.fishbuddy.dependencyinjection.component.ViewHolderComponent;
import com.example.mayankrai.fishbuddy.dependencyinjection.module.ViewHolderModule;

import javax.inject.Inject;


/* Base class for ViewHolders when using a view model with data binding.
 * This class provides the binding and the view model to the subclass. The
 * view model is injected and the binding is created when the content view is bound.
 * Each subclass therefore has to call the following code in the constructor:
 *    viewHolderComponent().inject(this);
 *    bindContentView(view);
 *
 * After calling these methods, the binding and the view model is initialized.
 * saveInstanceState() and restoreInstanceState() are not called/used for ViewHolder
 * view models.
 *
 * Your subclass must implement the MvvmView implementation that you use in your
 * view model. */
public abstract class BaseViewHolder<B extends ViewDataBinding, V extends ViewModel> extends RecyclerView.ViewHolder {

    protected B binding;

    @Inject protected V viewModel;

    protected final View itemView;

    private ViewHolderComponent viewHolderComponent;

    @Inject Navigator navigator;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    protected final ViewHolderComponent viewHolderComponent() {
        if(viewHolderComponent == null) {
            viewHolderComponent = DaggerViewHolderComponent.builder()
                    .viewHolderModule(new ViewHolderModule(itemView))
                    .appComponent(FishBuddyApplication.getAppComponent())
                    .build();
        }

        return viewHolderComponent;
    }

    protected final void bindContentView(@NonNull View view) {
        if(viewModel == null) { throw new IllegalStateException("viewModel must not be null and should be injected via viewHolderComponent().inject(this)"); }
        binding = DataBindingUtil.bind(view);
        binding.setVariable(BR.viewModel, viewModel);

        viewModel.attach(this instanceof MvvmView ? (MvvmView) this : null);
        viewModel.attach();

    }

    public final V viewModel() {
        return viewModel;
    }

    public final void executePendingBindings() {
        if(binding != null) { binding.executePendingBindings(); }
    }
}
