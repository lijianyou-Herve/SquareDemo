package com.example.herve.squaredemo.ui.home.fragments.life;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.herve.squaredemo.R;
import com.example.herve.squaredemo.base.MvpBaseFragment;
import com.example.herve.squaredemo.ui.home.fragments.life.adapter.LifeAdapter;
import com.example.herve.squaredemo.ui.home.fragments.life.presenter.LifeContract;
import com.example.herve.squaredemo.ui.home.fragments.life.presenter.LifePresenter;
import com.example.herve.squaredemo.widget.MaskView;

import java.util.ArrayList;

import static com.example.herve.squaredemo.R.id.maskView;
import static com.example.herve.squaredemo.R.id.mask_button;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class LifeFragment extends MvpBaseFragment<LifeContract.Presenter> implements LifeContract.PresenterView {

    private ProgressBar progress;
    private RecyclerView recycleViewLife;


    public static LifeFragment newInstance() {
        LifeFragment fragment = new LifeFragment();
        return fragment;
    }

    public static LifeFragment newInstance(Bundle args) {
        LifeFragment fragment = new LifeFragment();
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
        recycleViewLife = (RecyclerView) rootView.findViewById(R.id.recycle_view_life);

    }


    public RecyclerView getRecycleViewLife() {
        return recycleViewLife;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_life_layout;
    }

    @Override
    public void setProgressVisibility(int visibility) {
        progress.setVisibility(visibility);
    }

    @Override
    public void success() {

        LifeAdapter lifeAdapter = new LifeAdapter(mContext);

        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.drawable.create_life_love_nor);
        data.add(R.drawable.create_life_baby_nor);
        data.add(R.drawable.create_life_show_nor);
        data.add(R.drawable.create_life_birthday_nor);

        data.add(R.drawable.create_life_festival_nor);
        data.add(R.drawable.create_life_friend_nor);
        data.add(R.drawable.create_life_travel_nor);
        data.add(R.drawable.create_life_all_nor);

        lifeAdapter.setData(data);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 4);

        recycleViewLife.setLayoutManager(layoutManager);
        recycleViewLife.setAdapter(lifeAdapter);


    }

    @Override
    public void error() {

    }

    @Override
    protected LifeContract.Presenter initPresenter() {
        return new LifePresenter(this);
    }
}
