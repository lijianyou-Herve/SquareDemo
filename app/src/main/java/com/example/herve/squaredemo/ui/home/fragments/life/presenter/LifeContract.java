package com.example.herve.squaredemo.ui.home.fragments.life.presenter;

import com.example.herve.squaredemo.base.BasePresenter;
import com.example.herve.squaredemo.base.BasePresenterView;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public interface LifeContract {


    interface Presenter extends BasePresenter {

        void loading();
    }

    interface PresenterView extends BasePresenterView {


    }
}
