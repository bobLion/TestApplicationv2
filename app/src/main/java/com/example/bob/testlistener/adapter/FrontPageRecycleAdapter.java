package com.example.bob.testlistener.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bob.testlistener.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */

public class FrontPageRecycleAdapter extends RecyclerView.Adapter<FrontPageRecycleAdapter.FrontPageViewHolder>implements View.OnClickListener{

    private Context mContext;
//    private String[] imgPathList;
    private List<String> imgPathList;
    private OnItemClickListener mOnItemClickListener = null;

    public FrontPageRecycleAdapter(Context context , List<String> imgPathList){
        this.mContext = context;
        this.imgPathList = imgPathList;
    }

    @Override
    public FrontPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_front_page_recycle_view,parent,false);
        FrontPageViewHolder holder = new FrontPageViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(FrontPageViewHolder holder, int position) {
        Glide.with(mContext).load(imgPathList.get(position)).into(holder.mImg);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return imgPathList.size();
    }

    @Override
    public void onClick(View v) {
        if(null!= mOnItemClickListener){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    class FrontPageViewHolder extends RecyclerView.ViewHolder{
        ImageView mImg;

        public FrontPageViewHolder(View view ){
            super(view);
            mImg = (ImageView)view.findViewById(R.id.img_item_recycle_view);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


}
