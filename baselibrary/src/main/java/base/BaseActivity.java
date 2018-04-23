package base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import config.AppConfig;
import ioc.ViewUtils;
import receiver.AppLifeReceiver;
import util.PreferenceUtil;

/**
 * Created by Bob on 2018/4/12.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private long exitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ViewUtils.inject(this);
        initView();
        initTitle();
        initData();
    }

    /**
     * 设置布局
     * **/
    protected abstract void  setContentView();

    /**
     * 初始化头部
     * */
    protected abstract void  initTitle();

    /**
     * 初始化界面
     * */
    protected abstract void  initView();

    /**
     * 初始化数据
     * */
    protected abstract void  initData();

    /**
     * 启动 activity
     * @param clazz
     * */
    protected  void startActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

    /**
     * findViewById
     * @return View
     * */
    protected  <T extends View> T viewById(int viewId){
        return (T)findViewById(viewId);
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

    protected boolean onBackQuit() {
        return false;
    }

}
