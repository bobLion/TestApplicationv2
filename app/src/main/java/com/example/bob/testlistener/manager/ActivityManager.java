package com.example.bob.testlistener.manager;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Bob on 2017/10/31.
 */

public class ActivityManager {

    /**
     * @Fields instance : 单例
     */
    private static ActivityManager instance;
    /**
     * @Fields activityMap : activity容器
     */
    private volatile HashMap<String, ArrayList<Activity>> activityMap;

    private ArrayList<Activity> activityStack;

    private volatile boolean isFinishAll = false;

    /**
     * @param context
     * @Description 构造函数
     */
    private ActivityManager(Context context) {
        activityMap = new HashMap<String, ArrayList<Activity>>();
        activityStack = new ArrayList<>();
        isFinishAll = false;
    }

    /**
     * @param context
     * @return
     * @Description 获取ActivityManager实例
     */
    public static ActivityManager getInstance(Context context) {
        if (instance == null) {
            instance = new ActivityManager(context);
        }
        return instance;
    }

    /**
     * @param activity Activity类
     * @return
     * @Description 返回Activity 所有管理的实例
     */
    public ArrayList<Activity> getManagedActivityList(Class<?> activity) {
        String key = activity.getName();
        if (activityMap.containsKey(key)) {
            return activityMap.get(key);
        }
        return new ArrayList<Activity>(0);
    }

    public ArrayList<Activity> getManagedActivityList(String activityName) {
        String key = activityName;
        if (activityMap.containsKey(key)) {
            return activityMap.get(key);
        }
        return new ArrayList<Activity>(0);
    }

    /**
     * @param activity
     * @Description 将Activity放入管理容器中
     */
    public synchronized void registerActivity(Activity activity) {
        if (!isFinishAll) {
//            logger.e("registerActivity:" + activity.getClass().getName());
            String key = activity.getClass().getName();
            try {
                if (activityMap.containsKey(key)) {
                    ArrayList<Activity> list = activityMap.get(key);
                    if (!list.contains(activity)) {
                        list.add(activity);
                    }
                } else {
                    ArrayList<Activity> list = new ArrayList<Activity>(10);
                    list.add(activity);
                    activityMap.put(key, list);
                }
                activityStack.add(activity);
            } catch (Exception e) {
                e.printStackTrace();
//                logger.e(e.toString());
            }
        }
    }

    /**
     * @param activity
     * @Description 结束Activity时，将Activity从容器中删除
     */
    public synchronized void unRegisterAcitivty(Activity activity) {
        if (!isFinishAll) {
//            logger.e("unRegisterAcitivty:" + activity.getClass().getName());
            String key = activity.getClass().getName();
            try {
                if (activityMap.containsKey(key)) {
                    ArrayList<Activity> list = activityMap.get(key);
                    if (list != null) {
                        if (list.contains(activity)) {
                            list.remove(activity);
                        }
                        if (list.size() == 0) {
                            activityMap.remove(key);
                        }
                    }
                    if (activityStack.size() > 0) {
                        Activity topActivity = activityStack.get(activityStack.size() - 1);
                        if (topActivity.equals(activity)) {
                            activityStack.remove(activity);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                logger.e(e.toString());
            }
        }
    }

    /**
     * @Description 结束 所有activity
     */
    public synchronized void finishAllAcitivty() {
//        logger.e("finishAllAcitivty");
        isFinishAll = true;
        try {
            String[] keyArray = new String[activityMap.size()];
            activityMap.keySet().toArray(keyArray);
            for (int i = 0; i < keyArray.length; i++) {
                ArrayList<Activity> list = activityMap.get(keyArray[i]);
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            logger.e(e.toString());
        } finally {
            activityMap.clear();
            activityStack.clear();
            isFinishAll = false;
        }
    }

    /**
     * @Description 结束 所有activity,除了最近一次添加的Activity
     */
    public synchronized void finishAllAcitivtyExcludeOne(Class clazz) {
        try {
            Iterator<Map.Entry<String, ArrayList<Activity>>> iter = activityMap
                    .entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, ArrayList<Activity>> entry = iter.next();
                String key = entry.getKey();
                if (key.equals(clazz.getName())) {
                    continue;
                }
                ArrayList<Activity> list = entry.getValue();
                for (int i = 0; i < list.size(); i++) {
                    try {
                        list.get(i).finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                activityMap.remove(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            logger.e(e.toString());
        }
    }

    /**
     * 返回栈顶的activity，仅仅是应用内被管理起来的activity，对于未管理的activity，此方法失效
     * @return
     */
    public Activity getTopActivity() {
        if (activityStack.size() > 0) {
            return activityStack.get(activityStack.size() - 1);
        }
        return null;
    }
}
