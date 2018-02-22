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

}
