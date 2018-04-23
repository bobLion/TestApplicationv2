package com.example.bob.testlistener.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bob.testlistener.R;
import com.example.bob.testlistener.base.BaseFragment;
import com.example.bob.testlistener.config.AppConfig;
import com.example.bob.testlistener.widget.FlikerProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bob on 2017/10/31.
 */

public class LearningFragment extends BaseFragment {

    @BindView(R.id.tv_my_shopcart)
    TextView mTvMyShopCart;
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.bpb_download_progress)
    FlikerProgressBar progressBar;


    private long fileSize;
    private long downloaddFileSize;
    private String fileEx, fileNa,fileName;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(!Thread.currentThread().isInterrupted()){
                switch (msg.what){
                    case 0:
                        progressBar.setProgress(0);
                        break;
                    case 1:
//                        int result = downloaddFileSize * 100 /fileSize;
                        long result = (Long) msg.obj;
                        progressBar.setProgress(result);
//                        progressBar.setProgress(downloaddFileSize);
                        break;
                    case 2:
                        Toast.makeText(mContext,"文件下载完成！",Toast.LENGTH_LONG).show();
                        progressBar.finishLoad();
                        FileInputStream fis= null;
                        try {
                            fis = new FileInputStream(Environment.getExternalStorageDirectory()+ File.separator+"/test/"+fileName);
                            installAPK(Environment.getExternalStorageDirectory()+ File.separator+"/test/"+fileName);
                        }catch (FileNotFoundException ex){
                            ex.printStackTrace();
                        }
                        break;
                    case -1:
                        String error = msg.getData().getString("error");
                        Toast.makeText(mContext,error,Toast.LENGTH_LONG).show();
                        break;
                    default:
                            break;
                }
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_learn,container,false);
        mContext = getActivity().getApplicationContext();
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setShopCart(String str){
        mTvMyShopCart.setText(str);
    }


    @OnClick (R.id.btn_download)
     void setmBtnDownload(View view){
        new Thread(){
            @Override
            public void run() {
                try{
                    String path =  Environment.getExternalStorageDirectory() + File.separator + "/test/";
                    fileName = AppConfig.TIAN_MAO_APP_URL.substring(AppConfig.TIAN_MAO_APP_URL.lastIndexOf("/")+1);
                    URL pUrl = new URL(AppConfig.TIAN_MAO_APP_URL);
                    URLConnection urlConnection = pUrl.openConnection();
                    urlConnection.connect();
                    InputStream is = urlConnection.getInputStream();
                    fileSize = urlConnection.getContentLength();
                    if(null == is){
                        Toast.makeText(mContext,"获取文件流失败",Toast.LENGTH_LONG).show();
                    }
                    if(fileSize <= 0){
                        Toast.makeText(mContext,"获取文件大小失败",Toast.LENGTH_LONG).show();
                    }
                    File file = new File(path);
                    File file1 = new File(path + fileName);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    if(!file1.exists()){
                        file1.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(path + fileName);
                    byte[] buffer = new byte[1024];
                    downloaddFileSize  = 0;
                    Message msg = mHandler.obtainMessage();
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    do{
                        int number = is.read(buffer);
                        if(number == -1){
                            break;
                        }
                        fos.write(buffer,0,number);
                        downloaddFileSize += number;
                        Message msg1 = mHandler.obtainMessage();
                        msg1.what = 1;
                        long result = downloaddFileSize * 100 /fileSize;
                        msg1.obj = downloaddFileSize * 100 / fileSize;
                        mHandler.sendMessage(msg1);

                    }while (true);
                    Message msg2 = mHandler.obtainMessage();
                    msg2.what = 2;
                    mHandler.sendMessage(msg2);
                    try {
                        is.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }.start();

    }

    private void installAPK(String apkPath ) {
//        if (Build.VERSION.SDK_INT < 23) {
           /* Intent intents = new Intent();
            intents.setAction("android.intent.action.VIEW");
            intents.addCategory("android.intent.category.DEFAULT");
            intents.setType("application/vnd.android.package-archive");
            intents.setData(apk);
            intents.setDataAndType(apk, "application/vnd.android.package-archive");
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intents);*/
//        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)),"application/vnd.android.package-archive");
        startActivity(intent);

    }

}
