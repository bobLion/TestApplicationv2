package com.example.bob.testlistener.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.bob.testlistener.R;
import com.example.bob.testlistener.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaceCheckActivity extends BaseActivity {

    @BindView(R.id.root)
    LinearLayout root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_face);
        ButterKnife.bind(this);
        initStatusBar();
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
}
