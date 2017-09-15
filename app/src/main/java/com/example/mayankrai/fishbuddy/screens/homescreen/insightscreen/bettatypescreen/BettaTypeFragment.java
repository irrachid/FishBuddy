package com.example.mayankrai.fishbuddy.screens.homescreen.insightscreen.bettatypescreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankrai.fishbuddy.R;
import com.example.mayankrai.fishbuddy.base.fragment.BaseFragment;
import com.example.mayankrai.fishbuddy.databinding.BettaTypeFragmentBinding;

public class BettaTypeFragment extends BaseFragment<BettaTypeFragmentBinding,BettaTypeViewModel> implements BettaTypeView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setAndBindContentView(inflater, container, savedInstanceState, R.layout.fragment_betta_type);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
