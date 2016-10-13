package com.example.herve.squaredemo.ui.home.fragments.life.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.herve.squaredemo.R;

import java.util.ArrayList;


/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.LifeViewHolder> {


    private Context mContext;

    ArrayList<Integer> data = new ArrayList<>();


    public LifeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Integer> data) {
        this.data = data;
    }

    @Override
    public LifeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.template_item_layout, parent, false);
        return new LifeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(LifeViewHolder holder, int position) {
        holder.ivTemplateItem.setImageResource(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class LifeViewHolder extends RecyclerView.ViewHolder {

        ImageView ivTemplateItem;

        public LifeViewHolder(View itemView) {
            super(itemView);
            ivTemplateItem= (ImageView) itemView.findViewById(R.id.iv_template_item);
        }
    }
}
