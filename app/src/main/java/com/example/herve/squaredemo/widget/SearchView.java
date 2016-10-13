package com.example.herve.squaredemo.widget;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.herve.squaredemo.R;
import com.example.herve.squaredemo.widget.base.BaseWidget;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class SearchView extends FrameLayout implements BaseWidget {

    protected Context mContext;


    private ImageView ivSearch;
    private TextInputEditText inputEdit;


    public SearchView(Context context) {
        super(context);
        initView(context, this);

    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, this);

    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, this);

    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, this);
    }


    private void initView(Context context, BaseWidget baseWidget) {
        this.mContext = context;

        baseWidget.initLayout();

        baseWidget.findViewById();

        baseWidget.initUi();

        baseWidget.initListener();

    }


    @Override
    public void initLayout() {
        LayoutInflater.from(mContext).inflate(R.layout.search_view_layout, this,true);
    }


    @Override
    public void findViewById() {


        ivSearch = (ImageView) findViewById(R.id.iv_search);
        inputEdit = (TextInputEditText) findViewById(R.id.input_edit);


    }

    @Override
    public void initUi() {


    }


    @Override
    public void initListener() {

    }
}
