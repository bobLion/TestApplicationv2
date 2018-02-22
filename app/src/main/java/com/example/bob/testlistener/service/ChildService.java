package com.example.bob.testlistener.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.bob.testlistener.activity.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.bob.testlistener.service.MainService.TAG;

/**
 * Created by Administrator on 2017/11/23.
 */

public class ChildService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "ChildService_onStartCommand");
        mThread.start();
        return START_REDELIVER_INTENT;
    }

    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Timer mTimer = new Timer();
            TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {
                    Log.e(TAG, "ChileService_Running");
                    boolean b = MainActivity.isServiceWorked(ChildService.this,"com.example.bob.testlistener.service.MainService");
                    if(!b){
                        Intent intent = new Intent(ChildService.this,MainService.class);
                        startService(intent);
                    }
                }
            };
            mTimer.schedule(timerTask,0,1000);
        }
    });
}
