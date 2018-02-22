package com.example.bob.testlistener.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.bob.testlistener.application.AppLifeReceiver;
import com.example.bob.testlistener.config.AppConfig;
import com.example.bob.testlistener.util.PreferenceUtil;

/**
 * Created by Bob on 2017/10/30.
 */

public class BaseActivity extends AppCompatActivity {

    private long exitTime;

    @Override
    public void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
    }

    @Override
    public void onBackPressed() {
        if (onBackQuit()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                confirmQuit();
            }
        } else {
            super.onBackPressed();
        }
    }

    protected boolean onBackQuit() {
        return false;
    }

    protected void confirmQuit() {
        quit();
    }

    protected void quit() {
        PreferenceUtil.putBoolean(this, AppConfig.APLICATION_EXIT, true);
        finish();
        Intent intent = new Intent();
        intent.setAction(AppLifeReceiver.ON_APP_QUIT);
        intent.putExtra("type", 0);
        intent.putExtra("error", "");
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }


}
