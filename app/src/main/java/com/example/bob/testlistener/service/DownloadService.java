package com.example.bob.testlistener.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.bob.testlistener.R;
import com.example.bob.testlistener.enums.DownloadEnum;

import java.io.File;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.example.bob.testlistener.enums.DownloadEnum.DOWNLOADING;
import static com.example.bob.testlistener.enums.DownloadEnum.FAIL;
import static com.example.bob.testlistener.enums.DownloadEnum.PAUSE;
import static com.example.bob.testlistener.enums.DownloadEnum.SUCCESS;

/**
 * Created by Bob on 2018/2/28.
 */

public class DownloadService extends Service {
    /****
     * 发送广播的请求码
     */
    private final int REQUEST_CODE_BROADCAST = 0X0001;
    /****
     * 发送广播的action
     */
    private final String BROADCAST_ACTION_CLICK = "servicetask";
    /**
     * 通知
     */
    private Notification notification;
    /**
     * 通知的Id
     */
    private final int NOTIFICATION_ID = 1;
    /**
     * 通知管理器
     */
    private NotificationManager notificationManager;
    /**
     * 通知栏的远程View
     */
    private RemoteViews mRemoteViews;
    /**
     * 下载是否可取消
     */
    private Callback.Cancelable cancelable;
    /**
     * 自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
     */
    private String filePath = Environment.getExternalStorageDirectory()+ File.separator+"test/";
    private File file;

    private String downloadUrl;

    /**
     * 当前在状态 默认正在下载中
     */
    private DownloadEnum status = DOWNLOADING;
    private MyBroadcastReceiver myBroadcastReceiver;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerBroadCast();
        if(null != intent){
            downloadUrl = intent.getStringExtra("downloadUrl");
            if(null != downloadUrl){
                download(downloadUrl);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 注册按钮点击广播*
     */
    private void registerBroadCast() {
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION_CLICK);
        registerReceiver(myBroadcastReceiver, filter);
    }
    /**
     * 更新通知界面的按钮的广播
     */
    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(BROADCAST_ACTION_CLICK)) {
                return;
            }
//            Logger.d("status=" + status);
            switch (status) {
                case DOWNLOADING:
                    /**当在下载中点击暂停按钮**/
                    cancelable.cancel();
                    mRemoteViews.setTextViewText(R.id.bt, "下载");
                    mRemoteViews.setTextViewText(R.id.tv_message, "暂停中...");
                    status = PAUSE;
                    notificationManager.notify(NOTIFICATION_ID, notification);
                    break;
                case SUCCESS:
                    /**当下载完成点击完成按钮时关闭通知栏**/
                    notificationManager.cancel(NOTIFICATION_ID);
                    unregisterReceiver(myBroadcastReceiver);
                    break;
                case FAIL:
                case PAUSE:
                    /**当在暂停时点击下载按钮**/
                    download(downloadUrl);
                    mRemoteViews.setTextViewText(R.id.bt, "暂停");
                    mRemoteViews.setTextViewText(R.id.tv_message, "下载中...");
                    status = DOWNLOADING;
                    notificationManager.notify(NOTIFICATION_ID, notification);
                    break;
            }
        }
    }

    /**
     * 显示一个下载带进度条的通知
     *
     * @param context 上下文
     */
    public void showNotificationProgress(Context context) {
        /**进度条通知构建**/
        NotificationCompat.Builder builderProgress = new NotificationCompat.Builder(context);
        /**设置为一个正在进行的通知**/
        builderProgress.setOngoing(true);
        /**设置小图标**/
        builderProgress.setSmallIcon(R.mipmap.ic_launcher);

        /**新建通知自定义布局**/
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.notification);
        /**进度条ProgressBar**/
        mRemoteViews.setProgressBar(R.id.pb, 100, 0, false);
        /**提示信息的TextView**/
        mRemoteViews.setTextViewText(R.id.tv_message, "下载中...");
        /**操作按钮的Button**/
        mRemoteViews.setTextViewText(R.id.bt, "暂停");
        /**设置左侧小图标*/
        mRemoteViews.setImageViewResource(R.id.iv, R.mipmap.ic_launcher);
        /**设置通过广播形式的PendingIntent**/
        Intent intent = new Intent(BROADCAST_ACTION_CLICK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_BROADCAST, intent, 0);
        mRemoteViews.setOnClickPendingIntent(R.id.bt, pendingIntent);
        /**设置自定义布局**/
        builderProgress.setContent(mRemoteViews);
        /**设置滚动提示**/
        builderProgress.setTicker("开始下载...");
        notification = builderProgress.build();
        /**设置不可手动清除**/
        notification.flags = Notification.FLAG_NO_CLEAR;
        /**获取通知管理器**/
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        /**发送一个通知**/
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * 在通知栏显示文件名
     *
     * @param url 下载地址
     */
    private void showFileName(String url) {
        mRemoteViews.setTextViewText(R.id.tv_name, url.substring(url.lastIndexOf("/") + 1));
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * 下载更改进度
     *
     * @param total   总大小
     * @param current 当前已下载大小
     */
    private void updateNotification(long total, long current) {
        mRemoteViews.setTextViewText(R.id.tv_size, formatSize(current) + "/" + formatSize(total));
        int result = Math.round((float) current / (float) total * 100);
        mRemoteViews.setTextViewText(R.id.tv_progress, result + "%");
        mRemoteViews.setProgressBar(R.id.pb, 100, result, false);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * 下载失败
     */
    private void downloadFail() {
        status = FAIL;
        if (!cancelable.isCancelled()) {
            cancelable.cancel();
        }
        mRemoteViews.setTextViewText(R.id.bt, "重试");
        mRemoteViews.setTextViewText(R.id.tv_message, "下载失败");
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * 下载成功
     */
    private void downloadSuccess() {
        status = SUCCESS;
        mRemoteViews.setTextViewText(R.id.bt, "完成");
        mRemoteViews.setTextViewText(R.id.tv_message, "下载完成");
        notificationManager.notify(NOTIFICATION_ID, notification);
       /* Intent intent = new Intent(Intent.ACTION_VIEW);
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
        String path = filePath + fileName;
        intent.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");
        startActivity(intent);*/
    }

    /**
     * 格式化文件大小
     *
     * @param size
     * @return
     */
    private String formatSize(long size) {
        String format;
        if (size >= 1024 * 1024) {
            format = byteToMB(size) + "M";
        } else if (size >= 1024) {
            format = byteToKB(size) + "k";
        } else {
            format = size + "b";
        }
        return format;
    }

    /**
     * byte转换为MB
     *
     * @param bt 大小
     * @return MB
     */
    private float byteToMB(long bt) {
        int mb = 1024 * 1024;
        float f = (float) bt / (float) mb;
        float temp = (float) Math.round(f * 100.0F);
        return temp / 100.0F;
    }

    /**
     * byte转换为KB
     *
     * @param bt 大小
     * @return K
     */
    private int byteToKB(long bt) {
        return Math.round((bt / 1024));
    }

    /**
     * 销毁时取消下载，并取消注册广播，防止内存溢出
     */
    @Override
    public void onDestroy() {
        if (cancelable != null && !cancelable.isCancelled()) {
            cancelable.cancel();
        }
        if (myBroadcastReceiver != null) {
            unregisterReceiver(myBroadcastReceiver);
        }
        super.onDestroy();
    }

    /**
     * 下载文件
     */
    private void download(String downloadUrl) {
        RequestParams requestParams = new RequestParams(downloadUrl);
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
        file = new File(filePath, fileName);
        showNotificationProgress(DownloadService.this);
        showFileName(fileName);
        requestParams.setSaveFilePath(file.getPath());
        /**自动为文件命名**/
        requestParams.setAutoRename(true);
        /**自动为文件断点续传**/
        requestParams.setAutoResume(true);

        cancelable = x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
//                Logger.d("下载完成");
//                Logger.d("result=" + result.getPath());
                downloadSuccess();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Logger.d("下载异常");
                downloadFail();
            }

            @Override
            public void onCancelled(CancelledException cex) {
//                Logger.d("下载已取消");
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
//                Logger.d("total=" + total + "--" + "current=" + current);
                updateNotification(total, current);
            }
        });
    }



}
