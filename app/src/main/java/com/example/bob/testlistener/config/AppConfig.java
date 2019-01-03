package com.example.bob.testlistener.config;

import android.os.Environment;

public class AppConfig {

    /**
     * @Fields IS_RELEASE : Debug时可以为 false,发行版本带签名时设置为 true;
     */
    public static boolean IS_RELEASE = true;

    public static final int RESULT_OK = -1;
    /**
     * @Fields ENABLE_LOG : 是否允许输出log
     */
    public static boolean ENABLE_LOG = false;
    /**
     * @Field testMode : 运行模式 测试与正式区分
     */
    public static boolean TEST_MODE = false;

    public static final String CRASH_LOG_PATH = "sdcard/crash/";

    /**
     * @Field APLICATION_EXIT :应用是否退出
     */
    public static final String APLICATION_EXIT = "aplication_exit";

    /**
     * 是否保持屏幕长亮
     * **/
    public static boolean KEEP_SCREEN_LIGHT = true;
    /***
     * 单次最多发送图片数
     */
    public static final int MAX_IMAGE_SIZE = 8;
    /***
     * 相册中图片对象集合
     */
    public static final String EXTRA_IMAGE_LIST = "image_list";
    /***
     * 当前选择的照片位置
     */
    public static final String EXTRA_CURRENT_IMG_POSITION = "current_img_position";
    /***
     * 可添加的图片数量
     */
    public static final String EXTRA_CAN_ADD_IMAGE_SIZE = "can_add_image_size";
    /**
     * 相册名称
     */
    public static final String EXTRA_BUCKET_NAME = "buck_name";
    /**
     * @Fields ROOT_PATH : 设备存储根路径
     */
    public static final String ROOT_PATH = Environment
            .getExternalStorageDirectory().getPath();

    /**
     * @Field LOGIN_USER_NAME :最近一次登陆人密码
     */
    public static final String LOGIN_USER_PASSWORD = "login_user_password";

    /**
     * @Field LOGIN_USER_NAME :最近一次登陆人姓名
     */
    public static final String LOGIN_USER_NAME = "name";
    /**
     * @Field LOGIN_USER_ORG :最近一次登陆人组织
     */
    public static final String LOGIN_USER_ORG = "org";
    /**
     * @Field LOGIN_USER_USERID :最近一次登陆人userCode
     */
    public static final String LOGIN_USER_CODE = "userCode";

    public static final String DBNAME = "greendao.db";

    public static final String TIAN_MAO_APP_PACKAGE_NAME = "com.tmall.wireless";
    public static final String JING_DONG_APP_PACKAGE_NAME = "com.jingdong.app.mall";
    public static final String YI_HAO_DIAN_APP_PACKAGE_NAME = "com.thestore.main";
    public static final String SUNING_APP_PACKAGE_NAME = "com.suning.mobile.ebuy";
    public static final String DANGDANG_MAO_APP_PACKAGE_NAME = "com.dangdang.buy2";
    public static final String AMAZON_APP_PACKAGE_NAME = "cn.amazon.mShop.android";
    public static final String WEI_PIN_HUI_MAO_APP_PACKAGE_NAME = "com.achievo.vipshop";
    public static final String PIN_DUO_DUO_APP_PACKAGE_NAME = "com.xunmeng.pinduodu";
    public static final String ELEME_APP_PACKAGE_NAME = "me.ele";
    public static final String BAI_DU_WAI_MAI_APP_PACKAGE_NAME = "com.baidu.lbs.waimai";
    public static final String MEI_TUAN_WAI_MAI_APP_PACKAGE_NAME = "com.sankuai.meituan.takeoutnew";
    public static final String DA_ZHONG_DIAN_PING_APP_PACKAGE_NAME = "com.dianping.v1";

    public static final String JING_DONG_APP_LAUNCH_ACTIVITY_NAME = "com.jingdong.app.mall.main.MainActivity";
    public static final String YI_HAO_DIAN_APP_LAUNCH_ACTIVITY_NAME = "com.thestore.main.LoadingActivit";
    public static final String SUNING_APP_LAUNCH_ACTIVITY_NAME = "com.suning.mobile.ebuy.base.host.InitialActivity";
    public static final String DANGDANG_MAO_APP_LAUNCH_ACTIVITY_NAME = "com.dangdang.buy2.StartupActivity";
    public static final String AMAZON_APP_LAUNCH_ACTIVITY_NAME = "com.amazon.mShop.home.HomeActivity";
    public static final String WEI_PIN_HUI_MAO_APP_LAUNCH_ACTIVITY_NAME = "com.achievo.vipshop.activity.LodingActivity";
    public static final String PIN_DUO_DUO_APP_LAUNCH_ACTIVITY_NAME = "com.xunmeng.pinduoduo.ui.activity.MainFrameActivity";
    public static final String ELEME_APP_LAUNCH_ACTIVITY_NAME = "me.ele.Launcher";
    public static final String BAI_DU_WAI_MAI_APP_LAUNCH_ACTIVITY_NAME = "com.baidu.lbs.waimai.SplashActivity";
    public static final String MEI_TUAN_WAI_MAI_APP_LAUNCH_ACTIVITY_NAME = "com.sankuai.meituan.takeoutnew.ui.page.boot.WelcomeActivity";
    public static final String DA_ZHONG_DIAN_PING_APP_LAUNCH_ACTIVITY_NAME = "com.dianping.main.guide.SplashScreenActivity";
    public static final String TIAN_MAO_APP_LAUNCH_ACTIVITY_NAME = "com.tmall.wireless.splash.TMSplashActivity";

    public static final String TIAN_MAO_APP_URL = "http://gdown.baidu.com/data/wisegame/0f697805a2f8f6cc/tianmao_1809.apk";
    public static final String JING_DONG_APP_URL = "http://storage.360buyimg.com/jdmobile/JDMALL-PC2.apk";
    public static final String YI_HAO_DIAN_APP_URL = "http://storage.360buyimg.com/build-cms/V6.0.2T5_8149237.apk";
    public static final String SU_NING_APP_URL = "http://gdown.baidu.com/data/wisegame/d3795abd31d9f68e/suningyigou_236.apk";
    public static final String DANG_DANG_APP_URL = "http://gdown.baidu.com/data/wisegame/0f697805a2f8f6cc/tianmao_1809.apk";
    public static final String AMAZON_APP_URL = "http://gdown.baidu.com/data/wisegame/9493e6adc45432b0/yamaxungouwu_1241143010.apk";
    public static final String WEI_PIN_HUI_APP_URL = "http://gdown.baidu.com/data/wisegame/d3bc93c9cb5e91ee/weipinhui_923.apk";
    public static final String PIN_DUO_DUO_APP_URL = "http://dl001.liqucn.com/upload/2018/277/o/com.xunmeng.pinduoduo_4.14.2_liqucn.com.apk";
    public static final String ELEME_APP_URL = "http://gdown.baidu.com/data/wisegame/0f697805a2f8f6cc/tianmao_1809.apk";
    public static final String BAI_DU_WAI_MAI_APP_URL = "http://gdown.baidu.com/data/wisegame/ede2d909ff0ad519/baiduwaimaimeishidingcan_136.apk";
    public static final String MEI_TUAN_WAI_MAI_APP_URL = "http://gdown.baidu.com/data/wisegame/b6d195395dae83d0/meituanwaimai_60710.apk";
    public static final String DA_ZHONG_DIAN_PING_APP_URL = "http://gdown.baidu.com/data/wisegame/b0b529398f814c6c/dazhongdianping_100211.apk";

}
