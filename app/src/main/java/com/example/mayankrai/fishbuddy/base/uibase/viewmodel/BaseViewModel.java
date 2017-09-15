package com.example.mayankrai.fishbuddy.base.uibase.viewmodel;

import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.example.mayankrai.fishbuddy.base.uibase.view.MvvmView;


public class BaseViewModel<T extends MvvmView> extends BaseObservable implements ViewModel<T>
{
  private T view;
  private boolean resumed = false;
  //protected CompositeSubscription subscriptions = new CompositeSubscription();

  public T getView() {
    return view;
  }

  public boolean isResumed() {
    return resumed;
  }

  // Never override both attach() and attach(T view)
  @Override public void attach() {
    // Override if required
  }

  @CallSuper
  @Override public void attach(T view) {
    this.view = view;
  }

  @CallSuper
  @Override public void resume() {
    resumed = true;
  }

  @CallSuper
  @Override public void pause() {
    resumed = false;
  }

  @CallSuper
  @Override public void detach() {
    //subscriptions.unsubscribe();
  }

  @Override public void restoreInstanceState(Bundle savedInstanceState) {
    // Override if required
  }

  @Override public void saveInstanceState(Bundle outState) {
    // Override if required
  }
}
