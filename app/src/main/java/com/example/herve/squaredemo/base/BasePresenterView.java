package com.example.herve.squaredemo.base;

/**
 * Created by Herve on 2016/10/10.
 */

public interface BasePresenterView {

    void setProgressVisibility(int visibility);

    void success();

    void error();
}
