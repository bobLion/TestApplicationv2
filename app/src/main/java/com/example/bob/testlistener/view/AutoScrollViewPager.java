package com.example.bob.testlistener.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.bob.testlistener.R;

/**
 * Created by Administrator on 2018/2/22.
 */

public class AutoScrollViewPager extends RelativeLayout {

    private AutoViewPager autoViewPager;
    private Context mContext;
    private LinearLayout linearLayout;


    public AutoScrollViewPager(Context context) {
        super(context);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Context context){
        mContext = context;
        autoViewPager = new AutoViewPager(context);
        linearLayout = new LinearLayout(mContext);
        addView(autoViewPager);
    }

    public void setAdapter(BaseViewPageAdapter baseViewPageAdapter){
        if(null != baseViewPageAdapter){
            baseViewPageAdapter.init(autoViewPager,baseViewPageAdapter);
        }
    }

    public AutoViewPager getAutoViewPager(){
        return autoViewPager;
    }

    public void initPointView(int size){
        linearLayout = new LinearLayout(mContext);
        for(int i = 0;i<size;i++){
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            params.leftMargin = 8;
            params.gravity = Gravity.CENTER;
            imageView.setLayoutParams(params);
            if(i == 0){
                imageView.setBackgroundResource(R.drawable.point_checked);
            }else{
                imageView.setBackgroundResource(R.drawable.point_normal);
            }

            linearLayout.addView(imageView);
        }

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(CENTER_HORIZONTAL);
        layoutParams.setMargins(0,10,0,10);
        linearLayout.setLayoutParams(layoutParams);
        addView(linearLayout);
    }

    public void updatePointView(int position){
        int size = linearLayout.getChildCount();
        for(int i = 0; i< size;i++){
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if(i == position){
                imageView.setBackgroundResource(R.drawable.point_checked);
            }else{
                imageView.setBackgroundResource(R.drawable.point_normal);
            }
        }
    }

    public void onDestroy(){
        if(autoViewPager != null){
            autoViewPager.onDestroy();
        }
    }
}
