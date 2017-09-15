package com.example.mayankrai.fishbuddy.base.uibase.viewmodel;

import android.os.Bundle;

import com.example.mayankrai.fishbuddy.base.uibase.view.MvvmView;

public interface ViewModel<T extends MvvmView> {

  void attach();

  void attach(T view);

  void resume();

  void pause();

  void detach();

  void restoreInstanceState(Bundle savedInstanceState);

  void saveInstanceState(Bundle outState);
}
