package com.example.herve.squaredemo.ui.home.fragments.life.presenter;


import android.view.View;

import com.example.herve.squaredemo.base.MvpBasePresenter;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class LifePresenter extends MvpBasePresenter<LifeContract.PresenterView> implements LifeContract.Presenter {


    public LifePresenter(LifeContract.PresenterView mPresenter) {
        super(mPresenter);
    }


    @Override
    public void loading() {


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.setProgressVisibility(View.GONE);
                mPresenter.success();

            }
        }, 1000);

    }
}
