package com.example.bob.testlistener.fragment;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bob.testlistener.R;
import com.example.bob.testlistener.activity.ZoomImagesActivity;
import com.example.bob.testlistener.adapter.FrontPageRecycleAdapter;
import com.example.bob.testlistener.application.CheckApplications;
import com.example.bob.testlistener.base.BaseFragment;
import com.example.bob.testlistener.config.AppConfig;
import com.example.bob.testlistener.dialog.CommonDialogFragment;
import com.example.bob.testlistener.dialog.DialogFragmentHelper;
import com.example.bob.testlistener.dialog.IDialogResultListener;
import com.example.bob.testlistener.entity.ADInfo;
import com.example.bob.testlistener.service.DownloadService;
import com.example.bob.testlistener.widget.CustomerGridView;
import com.example.bob.testlistener.widget.ImageCycleView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2017/10/31.
 */

public class FrontPageFragment extends BaseFragment{

    @BindView(R.id.btn_front_page_button)
    Button mBtnClick;
    @BindView(R.id.ad_view)
    ImageCycleView mAdView;
    @BindView(R.id.grd_main)
    CustomerGridView mGrdMain;
    @BindView(R.id.grd_food)
    CustomerGridView mGrdFood;
    @BindView(R.id.recycle_main)
    RecyclerView mRecycleMain;
//    @BindView(R.id.ad_view2)
//    ImageCycleView mAdView2;

    private SimpleAdapter sim_adapter;
    private SimpleAdapter sim_food_adapter;

    private List<Map<String, Object>> data_list;
    private List<Map<String, Object>> food_list;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private FrontPageRecycleAdapter frontPageRecycleAdapter;
    private int fileSize;
    private int downloaddFileSize;
    private String fileEx, fileNa,fileName;
    NotificationManager notificationManager;
    private Notification.Builder builder;

    private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();

    private int[] icon = { R.mipmap.tmall, R.mipmap.jingdong,
            R.mipmap.yihaodian, R.mipmap.suning,
            R.mipmap.dangdang ,R.mipmap.amazon,
            R.mipmap.weipinhui,R.mipmap.pinduoduo};
    private String[] iconName = { "天猫", "京东", "一号店", "苏宁", "当当","亚马逊","唯品会","拼多多"};

    private int[] food_icon = {R.mipmap.eleme,R.mipmap.baiduwaimai ,
            R.mipmap.meituan,R.mipmap.dazhong};
    private String[] icon_food_name = {"饿了么","百度外卖","美团","大众点评"};


    private String[] imageUrls = {
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg",
            "http://pic2.ooopic.com/11/35/98/12bOOOPIC8f.jpg",
            "http://down1.sucaitianxia.com/psd02/psd158/psds28266.jpg"};

    private String[] imgs = {"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510807274510&di=20b5a9068273ce18055564f83a7e8f45&imgtype=0&src=http%3A%2F%2Fa3.topitme.com%2F0%2F55%2F1b%2F11216512689c41b550o.jpg" ,
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510807274509&di=cf5914bea84b10c5400e3d5699b2a0a1&imgtype=0&src=http%3A%2F%2Fupload.ct.youth.cn%2F2014%2F1228%2F1419724430495.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510807274507&di=cc13992b42b5828094a7a89c64a3b027&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20161031%2F8a924de6915d467286c7e2a3432a2529_th.jpeg",
            "https://goss1.vcg.com/creative/vcg/800/version23/VCG41160557579.jpg",
            "http://a2.qpic.cn/psb?/V12OUOeY0UH2KX/b8W.PQe9Wr*iwlnCGKRDMyUZ8BEOBAKn5tIHVeUHwMg!/b/dGEBAAAAAAAA&bo=CAewBAgHsAQRBzA!&rf=viewer_4",
           "http://a3.qpic.cn/psb?/V12OUOeY0UH2KX/h8iA96hL.0BcBYo814gxbPVfiJS36qUFEnVGNBa6RQE!/b/dHIAAAAAAAAA&bo=CAewBAgHsAQRBzA!&rf=viewer_4",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510807465163&di=771f8fdbeefe8111f68078330901e9f3&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F111121%2F10020-11112109552840.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510807274495&di=4016b43354014f2fc5551fc48211c28f&imgtype=0&src=http%3A%2F%2Fa4.att.hudong.com%2F13%2F28%2F300001051406131167289986614_950.jpg",
            "http://mpic.tiankong.com/aca/722/aca7224cedb82c2d378ddbb35ad3cc1a/640.jpg"
    };

    private List<String> imgList = new ArrayList<String>();

    private OnChangePareTextViewListener onChangePareTextViewListener;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(!Thread.currentThread().isInterrupted()){
                notificationManager = (NotificationManager) mContext.getSystemService(Activity.NOTIFICATION_SERVICE);
                builder = new Notification.Builder(mContext);

                switch (msg.what){
                    case 0:
//                        progressBar.setProgress(0);
                        builder.setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("正在下载")
                                .setContentInfo("下载中...");
                        break;
                    case 1:
//                        int result = downloaddFileSize * 100 /fileSize;
                        int result = (Integer) msg.obj;
                        builder.setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("正在下载")
                                .setContentInfo("下载中...");
                        builder.setProgress(100,result,false);
                        notificationManager.notify(0x3,builder.build());
//                        progressBar.setProgress(result);
//                        progressBar.setProgress(downloaddFileSize);
                        break;
                    case 2:
                        Toast.makeText(mContext,"文件下载完成！",Toast.LENGTH_LONG).show();
//                        progressBar.finishLoad();
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
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_front_page,container,false);
        mContext = getActivity().getApplicationContext();
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*mBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onChangePareTextViewListener){
                    onChangePareTextViewListener.changeClick();
                }
            }
        });*/

        initView();
    }

    private void initView() {
        for(int i=0;i < imageUrls.length; i ++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("top-->" + i);
            infos.add(info);
        }

        for(int i = 0;i < imgs.length;i++){
            imgList.add(i,imgs[i]);
        }

        mAdView.setImageResources(infos, mAdCycleViewListener);

        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        food_list = new ArrayList<Map<String,Object>>();
        //获取数据
        getData(icon,data_list,iconName);
        getData(food_icon,food_list,icon_food_name);

        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.item_grd_main, from, to);
        sim_food_adapter = new SimpleAdapter(getContext(),food_list,R.layout.item_grd_main,from,to);

        //配置适配器
        mGrdMain.setAdapter(sim_adapter);
        mGrdFood.setAdapter(sim_food_adapter);

        mGrdMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        turnToAnotherApplication(AppConfig.TIAN_MAO_APP_PACKAGE_NAME,
                                AppConfig.TIAN_MAO_APP_LAUNCH_ACTIVITY_NAME,
                                "天猫",
                                AppConfig.TIAN_MAO_APP_URL);
                        break;
                    case 1:
                        turnToAnotherApplication(AppConfig.JING_DONG_APP_PACKAGE_NAME,
                                AppConfig.JING_DONG_APP_LAUNCH_ACTIVITY_NAME,
                                "京东",
                                AppConfig.JING_DONG_APP_URL);
                        break;
                    case 2:
                        turnToAnotherApplication(AppConfig.YI_HAO_DIAN_APP_PACKAGE_NAME,
                                AppConfig.YI_HAO_DIAN_APP_LAUNCH_ACTIVITY_NAME,
                                "一号店",
                                AppConfig.YI_HAO_DIAN_APP_URL);
                        break;
                    case 3:
                        turnToAnotherApplication(AppConfig.SUNING_APP_PACKAGE_NAME,
                                AppConfig.SUNING_APP_LAUNCH_ACTIVITY_NAME,
                                "苏宁易购",
                                AppConfig.SU_NING_APP_URL);
                        break;
                    case 4:
                        turnToAnotherApplication(AppConfig.DANGDANG_MAO_APP_PACKAGE_NAME,
                                AppConfig.DANGDANG_MAO_APP_LAUNCH_ACTIVITY_NAME,
                                "当当",
                                AppConfig.DANG_DANG_APP_URL);
                        break;
                    case 5:
                        turnToAnotherApplication(AppConfig.AMAZON_APP_PACKAGE_NAME,
                                AppConfig.AMAZON_APP_LAUNCH_ACTIVITY_NAME,
                                "亚马逊",
                                AppConfig.AMAZON_APP_URL);
                        break;
                    case 6:
                        turnToAnotherApplication(AppConfig.WEI_PIN_HUI_MAO_APP_PACKAGE_NAME,
                                AppConfig.WEI_PIN_HUI_MAO_APP_LAUNCH_ACTIVITY_NAME,
                                "唯品会",
                                AppConfig.WEI_PIN_HUI_APP_URL);
                        break;
                    case 7:
                        turnToAnotherApplication(AppConfig.PIN_DUO_DUO_APP_PACKAGE_NAME,
                                AppConfig.PIN_DUO_DUO_APP_LAUNCH_ACTIVITY_NAME,
                                "拼多多",
                                AppConfig.PIN_DUO_DUO_APP_URL);
                        break;
                    default:
                        break;
                }
            }
        });

        mGrdFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        turnToAnotherApplication(AppConfig.ELEME_APP_PACKAGE_NAME,
                                AppConfig.ELEME_APP_LAUNCH_ACTIVITY_NAME,
                                "饿了么",
                                AppConfig.ELEME_APP_URL);
                        break;
                    case 1:
                        turnToAnotherApplication(AppConfig.BAI_DU_WAI_MAI_APP_PACKAGE_NAME,
                                AppConfig.BAI_DU_WAI_MAI_APP_LAUNCH_ACTIVITY_NAME,
                                "百度外卖",
                                AppConfig.BAI_DU_WAI_MAI_APP_URL);
                        break;
                    case 2:
                        turnToAnotherApplication(AppConfig.MEI_TUAN_WAI_MAI_APP_PACKAGE_NAME,
                                AppConfig.MEI_TUAN_WAI_MAI_APP_LAUNCH_ACTIVITY_NAME,
                                "美团外卖",
                                AppConfig.MEI_TUAN_WAI_MAI_APP_URL);
                        break;
                    case 3:
                        turnToAnotherApplication(AppConfig.DANGDANG_MAO_APP_PACKAGE_NAME,
                                AppConfig.DANGDANG_MAO_APP_LAUNCH_ACTIVITY_NAME,
                                "大众点评",
                                AppConfig.DANG_DANG_APP_URL);
                        break;
                    default:
                        break;
                }
            }
        });

//        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        SpacesItemDecoration decoration=new SpacesItemDecoration(5);
        mRecycleMain.addItemDecoration(decoration);
//        mRecycleMain.setLayoutManager(gridLayoutManager);
        mRecycleMain.setLayoutManager(staggeredGridLayoutManager);
//        mRecycleMain.setHasFixedSize(true);
        frontPageRecycleAdapter = new FrontPageRecycleAdapter(mContext,imgList);
        mRecycleMain.setAdapter(frontPageRecycleAdapter);
        frontPageRecycleAdapter.setOnItemClickListener(new FrontPageRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ZoomImagesActivity.class);
                intent.putExtra("img_url",imgList.get(position));
                getActivity().startActivity(intent);
            }
        });

    }

    public static interface OnChangePareTextViewListener{
        public void changeClick();
    }

    public void setOnChangePareTextViewListener(OnChangePareTextViewListener onChangePareTextViewListener){
        this.onChangePareTextViewListener = onChangePareTextViewListener;
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            Toast.makeText(getActivity(), "content->"+info.getContent(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(getActivity()).load(imageURL).into(imageView);
        }
    };



    @Override
    public void onResume() {
        super.onResume();
        mAdView.startImageCycle();
    };

    @Override
    public void onPause() {
        super.onPause();
        mAdView.pushImageCycle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdView.pushImageCycle();
    }

    public List<Map<String, Object>> getData(int[] data,List<Map<String,Object>> dataList,String[] dataName){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<data.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", data[i]);
            map.put("text", dataName[i]);
            dataList.add(map);
        }

        return data_list;
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space=space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }

    private void turnToAnotherApplication(String packageName,String activityName,String appName,String fileUrl){
        if(CheckApplications.isApplicationAvailable(mContext,packageName)){
            Toast.makeText(mContext,"即将跳转至" + appName + "手机客户端",Toast.LENGTH_LONG).show();
            ComponentName  comp = new ComponentName(packageName, activityName);
            Intent mIntent = new Intent();
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mIntent.setComponent(comp);
            startActivity(mIntent);
        }else{
            DialogFragmentHelper.showConfirmDialog(getFragmentManager(), "本地未安装"+appName+"，确定安装"+appName+"手机客户端么？", new IDialogResultListener<Integer>() {
                @Override
                public void onDataResult(Integer result) {
                    downloadFile(fileUrl);
                }
            }, true, new CommonDialogFragment.OnDialogCancelListener() {
                @Override
                public void onCancel() {
//                    ToastUtils.show(mContext,"You Click Cancel");
                }
            });
        }

    }

    private void downloadFile(String fileUrl){
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.putExtra("downloadUrl",fileUrl);
        mContext.startService(intent);
        /*new Thread(){
            @Override
            public void run() {
                try{
                    String path =  Environment.getExternalStorageDirectory() + File.separator + "/test/";
                    fileName = AppConfig.TIAN_MAO_APP_URL.substring(AppConfig.TIAN_MAO_APP_URL.lastIndexOf("/")+1);
                    URL pUrl = new URL(AppConfig.TIAN_MAO_APP_URL);
                    URLConnection urlConnection = pUrl.openConnection();
                    urlConnection.connect();
                    InputStream is = urlConnection.getInputStream();
                    fileSize = urlConnection.getContentLength()/1024;
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
                        int result = (downloaddFileSize/1024) * 100 /fileSize;
                        msg1.obj = (downloaddFileSize/1024) * 100 / fileSize;
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
        }.start();*/
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
