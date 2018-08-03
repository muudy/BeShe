package com.xinyu.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    protected View mSetContextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //mSetContextView =inflater.inflate(getContentViewId(),container,false);
        mSetContextView= inflater.inflate(setContentLayoutViewLayoutId(), container, false);
        ButterKnife.bind(this.mSetContextView);
        return mSetContextView;
    }

    protected abstract int setContentLayoutViewLayoutId();
}

