package com.example.mayankrai.fishbuddy.data.remote;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by mayankrai on 8/28/17.
 */

public class DisposableManager {

    public DisposableManager() {
    }

    private  CompositeDisposable compositeDisposable;

    public  void add(Disposable disposable) {
        getCompositeDisposable().add(disposable);
    }

    public  void dispose() {
        getCompositeDisposable().dispose();
    }

    private  CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }
}
