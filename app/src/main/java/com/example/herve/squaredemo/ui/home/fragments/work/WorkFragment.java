package com.example.herve.squaredemo.ui.home.fragments.work;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.herve.squaredemo.R;
import com.example.herve.squaredemo.base.MvpBaseFragment;
import com.example.herve.squaredemo.ui.home.fragments.work.presenter.WorkContract;
import com.example.herve.squaredemo.ui.home.fragments.work.presenter.WorkPresenter;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class WorkFragment extends MvpBaseFragment<WorkContract.Presenter> implements WorkContract.PresenterView {

    private ProgressBar progress;

    public static WorkFragment newInstance() {
        WorkFragment fragment = new WorkFragment();
        return fragment;
    }

    public static WorkFragment newInstance(Bundle args) {
        WorkFragment fragment = new WorkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        mPresenter.loading();
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected void findViewById() {
        progress = (ProgressBar) rootView.findViewById(R.id.progress);

    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_work_layout;
    }

    @Override
    public void setProgressVisibility(int visibility) {
        progress.setVisibility(visibility);
    }

    @Override
    public void success() {

    }

    @Override
    public void error() {

    }

    @Override
    protected WorkContract.Presenter initPresenter() {
        return new WorkPresenter(this);
    }
}
