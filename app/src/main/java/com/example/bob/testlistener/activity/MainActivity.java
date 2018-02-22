package com.example.bob.testlistener.activity;

import android.app.ActivityManager;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bob.testlistener.R;
import com.example.bob.testlistener.adapter.AppModuleAdapter;
import com.example.bob.testlistener.base.BaseActivity;
import com.example.bob.testlistener.entity.TabEntity;
import com.example.bob.testlistener.fragment.FrontPageFragment;
import com.example.bob.testlistener.fragment.LearningFragment;
import com.example.bob.testlistener.fragment.MineFragment;
import com.example.bob.testlistener.fragment.SettingFragment;
import com.example.bob.testlistener.fragment.ShoppingCartFragment;
import com.example.bob.testlistener.service.ChildService;
import com.example.bob.testlistener.service.MainService;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.squareup.haha.perflib.Main;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.buttom_tab)
    CommonTabLayout mCommTab;
    @BindView(R.id.view_pager_main)
    ViewPager mViewPageFront;
    @BindView(R.id.root)
    LinearLayout root;

    private AppModuleAdapter appModuleAdapter;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private FrontPageFragment frontPageFragment;
    private MineFragment mineFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private SettingFragment settingFragment;
    private LearningFragment learningFragment;
    private android.support.v4.app.Fragment mCurrentFragment;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        initView();
        initStatusBar();
        initService();
    }

    private void initService(){
//        Intent mainServiceIntent = new Intent(MainActivity.this, MainService.class);
//        startService(mainServiceIntent);
//
//        Intent childServiceIntent = new Intent(MainActivity.this, ChildService.class);
//        startService(childServiceIntent);
    }

    private void initView(){
        appModuleAdapter = new AppModuleAdapter(getSupportFragmentManager());
        if(null == frontPageFragment){
            frontPageFragment = new FrontPageFragment();
            appModuleAdapter.APP_MODULE.add(frontPageFragment);
        }

        if(null == shoppingCartFragment){
            shoppingCartFragment = new ShoppingCartFragment();
            appModuleAdapter.APP_MODULE.add(shoppingCartFragment);
        }

        if(null == mineFragment){
            mineFragment = new MineFragment();
            appModuleAdapter.APP_MODULE.add(mineFragment);
        }


        if(null == learningFragment){
            learningFragment = new LearningFragment();
            appModuleAdapter.APP_MODULE.add(learningFragment);
        }

        if(null == settingFragment){
            settingFragment = new SettingFragment();
            appModuleAdapter.APP_MODULE.add(settingFragment);
        }


        mTabEntities.add(new TabEntity("首页", R.mipmap.icon_main_home, R.mipmap.icon_main_home_unselect));
        mTabEntities.add(new TabEntity("运动", R.mipmap.sport_selected, R.mipmap.sport_normal));
        mTabEntities.add(new TabEntity("购物", R.mipmap.car, R.mipmap.car_unselected));
        mTabEntities.add(new TabEntity("学习", R.mipmap.learn_selected, R.mipmap.learn_normal));
        mTabEntities.add(new TabEntity("美食", R.mipmap.food_selected, R.mipmap.food));
        mCommTab.setTabData(mTabEntities);

        mViewPageFront.setAdapter(appModuleAdapter);
        mViewPageFront.setOffscreenPageLimit(5);

        mCommTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPageFront.setCurrentItem(position);
                mCurrentFragment = appModuleAdapter.getItem(position);
                if(position == 2){
                    mCommTab.setVisibility(View.GONE);
                }else{
                    mCommTab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPageFront.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommTab.setCurrentTab(position);
                mCurrentFragment = appModuleAdapter.getItem(position);
                if(position == 2){
                    mCommTab.setVisibility(View.GONE);
                }else{
                    mCommTab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if(null != frontPageFragment){
            frontPageFragment.setOnChangePareTextViewListener(new FrontPageFragment.OnChangePareTextViewListener() {
                @Override
                public void changeClick() {
                    if(null != mineFragment){
                        mineFragment.setText("我的页面");

                    }
                    if(null != shoppingCartFragment){
                        mViewPageFront.setCurrentItem(1);
                        shoppingCartFragment.setShopCart("增加了一件商品");
                    }
                }
            });
        }
    }

    private void initStatusBar() {
        //状态栏沉浸
        int statusBarHeight = 60;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        root.setPadding(0, statusBarHeight, 0, 0);
    }

    public static boolean isServiceWorked(Context pContext, String serviceName){
        ActivityManager activityManager = (ActivityManager) pContext.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningServiceInfos = (ArrayList<ActivityManager.RunningServiceInfo>) activityManager.getRunningServices(Integer.MAX_VALUE);
        for(int i = 0;i<runningServiceInfos.size();i++){
            if (runningServiceInfos.get(i).service.getClassName().toCharArray().equals(serviceName)){
                return true;
            }
        }
        return false;
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


//        return super.onKeyDown(keyCode, event);

        if(mCurrentFragment instanceof MineFragment) {
            ((MineFragment) mCurrentFragment).onKeyDown(keyCode, event);
            return true;
        }else{
//            System.exit(0);
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //与上次点击返回键时刻作差
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    //大于2000ms则认为是误操作，使用Toast进行提示
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    //并记录下本次点击“返回键”的时刻，以便下次进行判断
                    mExitTime = System.currentTimeMillis();
                } else {
                    //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                    System.exit(0);
                }
                return true;
            }
        }
        return false;
    }



}
