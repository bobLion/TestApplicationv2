package com.example.bob.testlistener.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bob.testlistener.R;
import com.example.bob.testlistener.activity.FaceCheckActivity;
import com.example.bob.testlistener.activity.ZoomImagesActivity;
import com.example.bob.testlistener.adapter.FrontPageRecycleAdapter;
import com.example.bob.testlistener.application.CheckApplications;
import com.example.bob.testlistener.base.BaseFragment;
import com.example.bob.testlistener.entity.ADInfo;
import com.example.bob.testlistener.widget.CustomerGridView;
import com.example.bob.testlistener.widget.ImageCycleView;

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
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510807274495&di=4016b43354014f2fc5551fc48211c28f&imgtype=0&src=http%3A%2F%2Fa4.att.hudong.com%2F13%2F28%2F300001051406131167289986614_950.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510807465163&di=771f8fdbeefe8111f68078330901e9f3&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F111121%2F10020-11112109552840.jpg",
            "https://goss1.vcg.com/creative/vcg/800/version23/VCG41160557579.jpg",
            "http://mpic.tiankong.com/aca/722/aca7224cedb82c2d378ddbb35ad3cc1a/640.jpg"
    };

    private List<String> imgList = new ArrayList<String>();

    private OnChangePareTextViewListener onChangePareTextViewListener;

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
                if(position == 0){
                   /* Intent intent = new Intent();
                    intent.setAction("Android.intent.action.VIEW");
                    Uri uri = Uri.parse("https://item.taobao.com/item.htm?spm=a21123.10416352.saveMoney.1&id=556556823285"); // 商品地址
                    intent.setData(uri);
//                    intent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
                    intent.setClassName("com.taobao.taobao", "com.tmall.wireless.splash.TMSplashActivity");
                    startActivity(intent);*/
                   if(CheckApplications.isApplicationAvailable(mContext,"com.tmall.wireless")){
                       Toast.makeText(mContext,"即将跳转至手机天猫客户端",Toast.LENGTH_LONG).show();
                       turnToAnotherApplication("com.tmall.wireless","com.tmall.wireless.splash.TMSplashActivity","天猫");
                   }else{
                       Toast.makeText(mContext,"请先安装手机天猫客户端",Toast.LENGTH_LONG).show();
                   }
                }else if(position == 1){
                    if(CheckApplications.isApplicationAvailable(mContext,"com.jingdong.app.mall")){

                        Toast.makeText(mContext,"即将跳转至手机京东客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("com.jingdong.app.mall","com.jingdong.app.mall.main.MainActivity","京东");
                    }else{
                        Toast.makeText(mContext,"请先安装手机京东客户端",Toast.LENGTH_LONG).show();
                    }
                }else if(position == 2){
                    if(CheckApplications.isApplicationAvailable(mContext,"com.thestore.main")){
                        Toast.makeText(mContext,"即将跳转至手机一号店客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("com.thestore.main","com.thestore.main.LoadingActivity","一号店");
                    }else{

                        Toast.makeText(mContext,"请先安装手机一号店客户端",Toast.LENGTH_LONG).show();
                    }
                }else if(position == 3){
                    if(CheckApplications.isApplicationAvailable(mContext,"com.suning.mobile.ebuy")){
                        Toast.makeText(mContext,"即将跳转至苏宁易购手机客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("com.suning.mobile.ebuy","com.suning.mobile.ebuy.base.host.InitialActivity","苏宁易购");
                    }else{
                        Toast.makeText(mContext,"请先安装苏宁易购手机客户端",Toast.LENGTH_LONG).show();
                    }
                }else if(position == 4){
                    if(CheckApplications.isApplicationAvailable(mContext,"com.dangdang.buy2")){
                        Toast.makeText(mContext,"即将跳转至当当手机客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("com.dangdang.buy2","com.dangdang.buy2.StartupActivity","当当");
                    }else{
                        Toast.makeText(mContext,"请先安装当当手机客户端",Toast.LENGTH_LONG).show();
                    }
                }else if(position == 5){
                    if(CheckApplications.isApplicationAvailable(mContext,"cn.amazon.mShop.android")){
                        Toast.makeText(mContext,"即将跳转至亚马逊手机客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("cn.amazon.mShop.android","com.amazon.mShop.home.HomeActivity","亚马逊");
                    }else{
                        Toast.makeText(mContext,"请先安装亚马逊手机客户端",Toast.LENGTH_LONG).show();
                    }
                }else if(position == 6){
                    if(CheckApplications.isApplicationAvailable(mContext,"com.achievo.vipshop")){
                        Toast.makeText(mContext,"即将跳转至唯品会手机客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("com.achievo.vipshop","com.achievo.vipshop.activity.LodingActivity","唯品会");
                    }else{
                        Toast.makeText(mContext,"请先安装唯品会手机客户端",Toast.LENGTH_LONG).show();
                    }
                }else if(position == 7){
                    if(CheckApplications.isApplicationAvailable(mContext,"com.xunmeng.pinduoduo")){
                        Toast.makeText(mContext,"即将跳转至拼多多手机客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("com.xunmeng.pinduoduo","com.xunmeng.pinduoduo.ui.activity.MainFrameActivity","拼多多");
                    }else{
                        Toast.makeText(mContext,"请先安装拼多多手机客户端",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        mGrdFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    if(CheckApplications.isApplicationAvailable(mContext,"me.ele")){
                        Toast.makeText(mContext,"即将跳转至饿了么手机客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("me.ele","me.ele.Launcher","饿了么");
                    }else{
                        Toast.makeText(mContext,"请先安装饿了么手机客户端",Toast.LENGTH_LONG).show();
                    }
                }else if(position == 1){
                    if(CheckApplications.isApplicationAvailable(mContext,"com.baidu.lbs.waimai")){
                        Toast.makeText(mContext,"即将跳转至百度外卖手机客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("com.baidu.lbs.waimai","com.baidu.lbs.waimai.SplashActivity","百度外卖");
                    }else{
                        Toast.makeText(mContext,"请先安装百度外卖手机客户端",Toast.LENGTH_LONG).show();
                    }
                }else if(position == 2){
                    if(CheckApplications.isApplicationAvailable(mContext,"com.sankuai.meituan.takeoutnew")){
                        Toast.makeText(mContext,"即将跳转至美团外卖手机客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("com.sankuai.meituan.takeoutnew","com.sankuai.meituan.takeoutnew.ui.page.boot.WelcomeActivity","美团");
                    }else{
                        Toast.makeText(mContext,"请先安装美团外卖手机客户端",Toast.LENGTH_LONG).show();
                    }
                }else if(position == 3){
                    if(CheckApplications.isApplicationAvailable(mContext,"com.dianping.v1")){
                        Toast.makeText(mContext,"即将跳转至大众点评手机客户端",Toast.LENGTH_LONG).show();
                        turnToAnotherApplication("com.dianping.v1","com.dianping.main.guide.SplashScreenActivity","大众点评");
                    }else{
                        Toast.makeText(mContext,"请先安装大众点评手机客户端",Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

//        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
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

    private void turnToAnotherApplication(String packageNamw,String activityName,String appName){
        Toast.makeText(mContext,"即将跳转至" + appName + "手机客户端",Toast.LENGTH_LONG).show();
        ComponentName  comp = new ComponentName(packageNamw, activityName);
        Intent mIntent = new Intent();
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.setComponent(comp);
        startActivity(mIntent);
    }
}
