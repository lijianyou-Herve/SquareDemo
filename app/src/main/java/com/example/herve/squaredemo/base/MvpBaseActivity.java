package com.example.herve.squaredemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Herve on 2016/10/10.
 */

public abstract class MvpBaseActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();

        super.onCreate(savedInstanceState);
    }

    protected abstract P initPresenter();
}
