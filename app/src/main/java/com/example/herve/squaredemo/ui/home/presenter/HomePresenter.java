package com.example.herve.squaredemo.ui.home.presenter;

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
public class HomePresenter extends MvpBasePresenter<HomeContract.PresenterView> implements HomeContract.Presenter {


    public HomePresenter(HomeContract.PresenterView mPresenter) {
        super(mPresenter);
    }


    @Override
    public void loadData() {
        mPresenter.setProgressVisibity(View.VISIBLE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.setProgressVisibity(View.GONE);

                mPresenter.success();

            }
        }, 1);
    }

    @Override
    public void refreshData() {

    }
}
