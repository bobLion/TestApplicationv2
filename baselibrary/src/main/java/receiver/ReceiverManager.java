package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author
 * @ClassName: ReceiverManager
 * @Description: 管理在应用中注册的广播，方便在应用退出或者其他场景取消注册所有已经注册的广播
 * @date 2015年7月16日 下午12:50:45
 */
public class ReceiverManager {

    private Context context;
    /**
     * @Fields receiverMap : 广播容器
     */
    private HashMap<String, List<BroadcastReceiver>> receiverMap;

    /**
     * @Fields instance : 单例
     */
    private static ReceiverManager instance;

    /**
     * @param context
     * @return
     * @Description 获取广播管理器实例
     */
    public static ReceiverManager getInstance(Context context) {
        if (instance == null) {
            instance = new ReceiverManager(context);
        }
        return instance;
    }

    /**
     * @Description 构造函数
     */
    private ReceiverManager(Context context) {
        this.context = context.getApplicationContext();
        receiverMap = new HashMap<String, List<BroadcastReceiver>>();
    }

    /**
     * @param broadcastReceiver 广播类类型
     * @return
     * @Description 获取某个广播类型受容器管理的所有广播列表
     */
    public List<BroadcastReceiver> getManagedReceiverList(Class<?> broadcastReceiver) {
        String key = broadcastReceiver.getName();
        if (receiverMap.containsKey(key)) {
            return receiverMap.get(key);
        }
        return new ArrayList<BroadcastReceiver>(0);
    }

    /**
     * @param receiver 广播
     * @Description 注册广播到容器中
     */
    public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        try {
            context.getApplicationContext().registerReceiver(receiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String key = receiver.getClass().getName();
        if (receiverMap.containsKey(key)) {
            List<BroadcastReceiver> receiverList = receiverMap.get(key);
            if (!receiverList.contains(receiver)) {
                receiverList.add(receiver);
            }
        } else {
            List<BroadcastReceiver> receiverList = new ArrayList<BroadcastReceiver>();
            receiverList.add(receiver);
            receiverMap.put(key, receiverList);
        }
    }

    /**
     * @Description 取消应用中注册的广播
     */
    public void unRegisterAllReceivers() {
        if (receiverMap.size() > 0) {
            Iterator<Entry<String, List<BroadcastReceiver>>> receiverIter = receiverMap
                    .entrySet().iterator();
            while (receiverIter.hasNext()) {
                Entry<String, List<BroadcastReceiver>> receiverEntry = receiverIter
                        .next();
                List<BroadcastReceiver> receiverList = receiverEntry.getValue();
                if (receiverList != null) {
                    for (BroadcastReceiver receiver : receiverList) {
                        try {
                            context.unregisterReceiver(receiver);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        receiverMap.clear();
    }

    /**
     * @param receiverName 广播类名称，取消注册所有与receiverName相同的广播类实例
     * @Description 从容器中删除与给定名称一致的所有广播
     */
    public void unRegisterReceiver(String receiverName) {
        if (receiverMap.size() > 0) {
            if (receiverMap.containsKey(receiverName)) {
                List<BroadcastReceiver> receiverList = receiverMap
                        .get(receiverName);
                if (receiverList != null) {
                    for (BroadcastReceiver receiver : receiverList) {
                        try {
                            context.unregisterReceiver(receiver);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    receiverList.clear();
                }
                receiverMap.remove(receiverName);
            }
        }
    }

    /**
     * @param receiver 必须是之前已经注册过的实例，否则取消失败 广播
     * @Description 取消某个特定的广播，相同广播的其它实例并不受影响
     */
    public void unRegisterReceiver(BroadcastReceiver receiver) {
        try {
            context.unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (receiverMap.size() > 0) {
            String receiverName = receiver.getClass().getName();
            if (receiverMap.containsKey(receiverName)) {
                List<BroadcastReceiver> receiverList = receiverMap
                        .get(receiverName);
                if (receiverList.contains(receiver)) {
                    receiverList.remove(receiver);
                    if (receiverList.size() == 0) {
                        receiverMap.remove(receiverName);
                    }
                }
            }
        }
    }
}
