package com.example.bob.testlistener.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.bob.testlistener.activity.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/11/23.
 */

public class MainService extends Service {

    public final static String TAG = "com.example.bob";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "MainService_onStartCommand");

        mThread.start();
        return START_STICKY ;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Timer mTimer = new Timer();
            TimerTask mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Log.e(TAG, "MainService_Running");
                    boolean b = MainActivity.isServiceWorked(MainService.this, "com.example.bob.testlistener.service.ChildService");
                    if(!b){
                        Intent serviceIntent = new Intent(MainService.this,ChildService.class);
                        startService(serviceIntent);

                    }
                }
            };
            mTimer.schedule(mTimerTask,0,1000);
        }
    });


    @Override
    public void onDestroy() {
        Log.e(TAG, "MainService_onDestroy");
    }
}
