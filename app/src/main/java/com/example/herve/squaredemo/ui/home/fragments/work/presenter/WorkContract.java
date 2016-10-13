package com.example.herve.squaredemo.ui.home.fragments.work.presenter;

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
public interface WorkContract {


    interface Presenter extends BasePresenter {

        void loading();
    }

    interface PresenterView extends BasePresenterView {

        void setProgressVisibility(int visibility);

    }
}
