package base;

import android.app.Application;

import http.HttpUtils;
import http.OKHttpEngine;

/**
 * @package base
 * @fileName BaseApplication
 * @Author Bob on 2018/4/13 8:55.
 * @Describe TODO
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化http引擎
        HttpUtils.init(new OKHttpEngine());
        //全局异常捕捉类
        ExceptionCrashHandler.getInstance().init(this);

    }
}
