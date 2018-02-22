package com.example.bob.testlistener.manager;

import android.content.Context;

/**
 * Created by Bob on 2017/10/31.
 */

public class ScheduleTaskManager {
        private Context context;
        private static ScheduleTaskManager instance;

        private ScheduleTaskManager(Context context) {
            this.context = context;
        }

        public static ScheduleTaskManager getInstance(Context context) {
            if (instance == null) {
                instance = new ScheduleTaskManager(context);
            }
            return instance;
        }

        /**
         * Start all ScheduleTasks at here when login succeed .
         * */
        public void scheduleAllTasks() {
//		DataUpdateTaskRun dataUpdateTask = new DataUpdateTaskRun(context);
//		ScheduleManager.getInstance(context).scheduleTask(dataUpdateTask);
            //FlagUpdateTaskRun flagUpdateTaskRun = new FlagUpdateTaskRun(context);
            //ScheduleManager.getInstance(context).scheduleTask(flagUpdateTaskRun);


          /*  TrialLocationTask uploadLocationTask = new TrialLocationTask(context);
            ScheduleManager.getInstance(context).scheduleTask(uploadLocationTask);

            OfflineDataCheckTaskRun offlineDataCheckTask = new OfflineDataCheckTaskRun(
                    context);
            ScheduleManager.getInstance(context).scheduleTask(offlineDataCheckTask);
            UploadOfflineCheckLogTask uploadTask = new UploadOfflineCheckLogTask(context);
            ScheduleManager.getInstance(context).scheduleTask(uploadTask);*/

        }

        /**
         * @Description 取消所有已注册任务
         */
        public void cancelAllTask() {
//            ScheduleManager.getInstance(context).cancelAllTask();
        }

        /**
         * @Method: cancelTask
         * @Description: 关闭任务
         * @param @param task 任务
         * @return void
         * @throws
         */
//        public void cancelTask(TaskDetail task) {
//            ScheduleManager.getInstance(context).cancelTask(task);
//        }
}
