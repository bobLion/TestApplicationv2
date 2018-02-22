package com.example.bob.testlistener.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bob.testlistener.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/22.
 */

public abstract class BaseViewPageAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private List<T> datas = new ArrayList<>();
    private Context mContext;
    private AutoViewPager mView;

    private OnAutoViewPagerItemClickListener onAutoViewPagerItemClickListener;

    public BaseViewPageAdapter (Context context,List<T> datas){
        this.mContext = context;
        this.datas = datas;
    }

    public BaseViewPageAdapter(Context context,List<T> datas,OnAutoViewPagerItemClickListener listener){
        this.mContext = context;
        this.datas = datas;
        this.onAutoViewPagerItemClickListener = listener;
    }

    public void init(AutoViewPager viewPager,BaseViewPageAdapter adapter){
        mView = viewPager;
        mView.setAdapter(adapter);
        mView.addOnPageChangeListener(this);
        if(datas == null || datas.size() == 0){
            return;
        }
        int position = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2)% getCount();
        mView.setCurrentItem(position);
        mView.start();
        mView.updatePointView(getRealCount());
    }

    public void setListener (OnAutoViewPagerItemClickListener listener){
        this.onAutoViewPagerItemClickListener = listener;
    }


    public void add(T t){
        datas.add(t);
        notifyDataSetChanged();
        mView.updatePointView(getRealCount());
    }

    @Override
    public int getCount() {
        return datas == null ?0:Integer.MAX_VALUE;
    }

    public int getRealCount(){
        return datas == null ? 0:datas.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.imageview,container,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAutoViewPagerItemClickListener != null) {
                    onAutoViewPagerItemClickListener.onItemClick(position % getRealCount(),datas.get(position % getRealCount()));
                }
            }
        });

        loadImage(view,position,datas.get(position % getRealCount()));
        container.addView(view);
        return view;
    }

    public abstract void loadImage(ImageView imageView,int position,T t);

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mView.onPageSelected(position % getRealCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private interface OnAutoViewPagerItemClickListener<T>{
        void onItemClick(int position,T t);
    }
}
