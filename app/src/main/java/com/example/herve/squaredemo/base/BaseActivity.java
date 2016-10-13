package com.example.herve.squaredemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Herve on 2016/10/10.
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected Context mContext;

    protected abstract int initLayoutId();

    protected abstract void findViewById();

    protected abstract void initUi();

    protected abstract void initData();

    protected abstract void initListener();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initLayoutId());
        findViewById();
        initUi();
        initData();
        initListener();

    }


}
