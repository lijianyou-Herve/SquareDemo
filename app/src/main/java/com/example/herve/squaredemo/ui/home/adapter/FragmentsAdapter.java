package com.example.herve.squaredemo.ui.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.herve.squaredemo.ui.home.fragments.life.LifeFragment;
import com.example.herve.squaredemo.ui.home.fragments.work.WorkFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class FragmentsAdapter extends FragmentPagerAdapter {


    List<String> data = new ArrayList<>();


    LifeFragment lifeFragment;
    WorkFragment workFragment;

    public FragmentsAdapter(FragmentManager fm) {
        super(fm);

    }

    public void setData(List<String> data) {
        this.data = data;
        lifeFragment=LifeFragment.newInstance();
        workFragment=WorkFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0){
            return lifeFragment;

        }else {
            return workFragment;

        }

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position);
    }
}
