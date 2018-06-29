package com.example.bob.testlistener.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import com.example.bob.testlistener.data.Album;
import com.example.bob.testlistener.data.HandlingAlbums;
import com.example.bob.testlistener.database.greendao.DaoMaster;
import com.example.bob.testlistener.database.greendao.DaoSession;
import com.example.bob.testlistener.face.FaceDB;
import com.orhanobut.hawk.Hawk;

import org.xutils.x;

/**
 * Created by Administrator on 2017/11/13.
 */

public class GlobalApplication extends Application {

    public static GlobalApplication instance;
    public static Context mContext;
    FaceDB mFaceDB;
    Uri mImage;
    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = this;
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        Hawk.init(this).build();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.

//        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
//        mImage = null;

        initGreenDao();

    }

    /**
     * 初始化GreenDao
     */
    private void initGreenDao() {
        MyGreenHelper helper = new MyGreenHelper(this,"test_user.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    /**
     * 获取daoSession
     * @return
     */
    public DaoSession getDaoSession(){
        return daoSession;
    }

    public static GlobalApplication getInstance(){
        return instance;
    }

    @Deprecated
    public Album getAlbum() {
        return Album.getEmptyAlbum();
    }

    @Deprecated
    public HandlingAlbums getAlbums() {
        return HandlingAlbums.getInstance(getApplicationContext());
    }

    public void setCaptureImage(Uri uri) {
        mImage = uri;
    }

    public Uri getCaptureImage() {
        return mImage;
    }

    /**
     * @param path
     * @return
     */
    public static Bitmap decodeImage(String path) {
        Bitmap res;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 1;
            op.inJustDecodeBounds = false;
            //op.inMutable = true;
            res = BitmapFactory.decodeFile(path, op);
            //rotate and scale.
            Matrix matrix = new Matrix();

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }

            Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);
            Log.d("com.arcsoft", "check target Image:" + temp.getWidth() + "X" + temp.getHeight());

            if (!temp.equals(res)) {
                res.recycle();
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
