package com.example.bob.testlistener.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/2/22.
 */

public class AutoViewPager extends ViewPager {

    private String TAG = "AutoViewPager";
    private Timer mTimer;
    private AutoTask mAutoTask;
    private int currentItem;
    private AutoHandler mAutoHandle = new AutoHandler();

    public void init(AutoViewPager autoViewPager,BaseViewPageAdapter baseViewPageAdapter){
        baseViewPageAdapter.init(autoViewPager,baseViewPageAdapter);
    }

    public void start(){
        onStop();
        if(null == mTimer){
            mTimer = new Timer();
        }
        mTimer.schedule(new AutoTask(),3000,3000);
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentItem = getCurrentItem();
            if(currentItem == getAdapter().getCount() - 1){
                currentItem = 0;
            }else{
                currentItem++;
            }
            setCurrentItem(currentItem);
        }
    };

    public void updatePointView(int size ){
        if(getParent() instanceof AutoScrollViewPager){
            AutoScrollViewPager pager = (AutoScrollViewPager) getParent();
            pager.initPointView(size);
        }else{
            Log.e(TAG,"parent view is not AutoScrollViewPager ");
        }
    }

    public void onPageSelected(int position){
        AutoScrollViewPager pager = (AutoScrollViewPager)getParent();
        pager.updatePointView(position);
    }


    private final static class AutoHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    private class AutoTask extends TimerTask{

        @Override
        public void run() {
            mAutoHandle.post(runnable);
        }
    }
    public AutoViewPager(Context context) {
        super(context);
    }

    public void onStop(){
        if(null != mTimer){
            mTimer.cancel();
            mTimer = null;
        }
    }

    public void onDestroy(){
        onStop();
    }

    public void onResume(){
        start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                onStop();
                break;
            case MotionEvent.ACTION_UP:
                onResume();
                break;
        }
        return super.onTouchEvent(ev);
    }
}
