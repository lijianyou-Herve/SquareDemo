package com.example.herve.squaredemo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.herve.squaredemo.R;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public abstract class BaseFragment<F extends BaseFragment> extends Fragment {


    protected View rootView;

    protected Activity mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        rootView = inflater.inflate(initLayoutId(), null);

        findViewById();

        initUi();

        initData();

        initListener();

        return rootView;
    }

    protected abstract void initListener();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
    }

    protected abstract void initData();

    protected abstract void initUi();

    protected abstract void findViewById();

    protected abstract int initLayoutId();
}
