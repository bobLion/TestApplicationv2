package receiver;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;


import java.util.List;

import manager.ActivityManager;
import manager.ScheduleTaskManager;

public class AppLifeReceiver extends BroadcastReceiver {
	public static final String ON_APP_QUIT = "com.sailing.android.cmjw.app.quit";
	public static final String ON_APP_TIMEOUT = "com.sailing.android.cmjw.app.timeout";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (action.equals(ON_APP_TIMEOUT)) {
			ReceiverManager.getInstance(context).unRegisterAllReceivers();
			ScheduleTaskManager.getInstance(context.getApplicationContext())
					.cancelAllTask();
			goLogin(context);
		} else if (action.equals(ON_APP_QUIT)) {
			ReceiverManager.getInstance(context).unRegisterAllReceivers();
			ActivityManager.getInstance(context).finishAllAcitivty();
			ScheduleTaskManager.getInstance(context).cancelAllTask();
			killProcess(context);
		}
	}

	public void goLogin(Context context) {
		/*if (isAppFront(context)) {
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClass(context, LoginActivity.class);
			context.startActivity(intent);
		} else {
			killProcess(context);
		}*/
	}

	/**
	 * 判断当前应用程序处于前台还是后台
	 */
	public boolean isAppFront(Context context) {
		android.app.ActivityManager am = (android.app.ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	public void killProcess(Context context) {
		android.app.ActivityManager activityManager = (android.app.ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcesses = activityManager
				.getRunningAppProcesses();
		if(runningAppProcesses!=null){
			for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
				if (runningAppProcessInfo.processName.endsWith(context
						.getApplicationInfo().processName)) {
					android.os.Process.killProcess(runningAppProcessInfo.pid);
					break;
				}
			}
		}
	}
}
