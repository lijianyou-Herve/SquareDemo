package com.example.herve.squaredemo.ui.home;

import android.os.PersistableBundle;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.herve.squaredemo.R;
import com.example.herve.squaredemo.base.MvpBaseActivity;
import com.example.herve.squaredemo.ui.home.adapter.FragmentsAdapter;
import com.example.herve.squaredemo.ui.home.fragments.life.LifeFragment;
import com.example.herve.squaredemo.ui.home.presenter.HomeContract;
import com.example.herve.squaredemo.ui.home.presenter.HomePresenter;
import com.example.herve.squaredemo.widget.MaskView;
import com.example.herve.squaredemo.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends MvpBaseActivity<HomeContract.Presenter> implements HomeContract.PresenterView {
    private static final String TAG = "HomeActivity";
    private RelativeLayout activityMain;
    private SearchView serachView;
    private TabLayout tabLayout;
    private ViewPager vpHome;

    private MaskView maskView;
    private Button mask_button;


    private FragmentsAdapter fragmentsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }


    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViewById() {
        activityMain = (RelativeLayout) findViewById(R.id.activity_main);
        serachView = (SearchView) findViewById(R.id.serach_view);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        vpHome = (ViewPager) findViewById(R.id.vp_home);
        maskView = (MaskView) findViewById(R.id.maskView);
        mask_button = (Button) findViewById(R.id.mask_button);

    }

    @Override
    protected void initUi() {


    }

    @Override
    protected void initData() {
        mPresenter.loadData();

    }

    private boolean change = false;

    @Override
    protected void initListener() {
        mask_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LifeFragment fragment = (LifeFragment) fragmentsAdapter.getItem(0);
                Log.i(TAG, "onClick:RecycleView=" + fragment.getRecycleViewLife().getHeight());
                Log.i(TAG, "onClick:tabLayout= " + tabLayout.getHeight());
                int bottom = tabLayout.getHeight() + fragment.getRecycleViewLife().getHeight();

                int tipRes = R.drawable.create_tip;
                if (change) {
                    maskView.setRoundRect(maskView.getWidth(), serachView.getHeight(), 10, 10, 10);

                } else {

                    maskView.setRoundRect(maskView.getWidth(), bottom, (float) serachView.getHeight(), 20f, 20f);
                }

//                maskView.setFillView(mask_button);
                change = !change;
//                maskView.setTipBitmapRes(tipRes);
                maskView.showMask();
            }
        });
    }

    @Override
    public void setProgressVisibity(int visibity) {

    }

    @Override
    public void setProgressVisibility(int visibility) {

    }

    @Override
    public void success() {

        fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager());

        List<String> data = new ArrayList<>();
        data.add("生活");
        data.add("工作");
        fragmentsAdapter.setData(data);

        vpHome.setAdapter(fragmentsAdapter);

        tabLayout.setupWithViewPager(vpHome);


    }

    @Override
    public void error() {

    }

    @Override
    protected HomeContract.Presenter initPresenter() {
        return new HomePresenter(this);
    }
}
