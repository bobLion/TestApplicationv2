package com.example.bob.testlistener.application;

import android.content.BroadcastReceiver;
import android.content.Context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class AppReceiverManager {

	private HashMap<Context, HashMap<String, BroadcastReceiver>> receiverMap;
	private static AppReceiverManager instance;

	public static AppReceiverManager getInstance(Context context) {
		if (instance == null) {
			instance = new AppReceiverManager();
		}
		return instance;
	}

	private AppReceiverManager() {
		receiverMap = new HashMap<Context, HashMap<String, BroadcastReceiver>>();
	}

	public void addReceiver(Context context, BroadcastReceiver receiver) {
		if (receiverMap.containsKey(context)) {
			HashMap<String, BroadcastReceiver> map = receiverMap.get(context);
			if (!map.containsKey(receiver.getClass().getName())) {
				map.put(receiver.getClass().getName(), receiver);
			}
		} else {
			HashMap<String, BroadcastReceiver> map = new HashMap<String, BroadcastReceiver>();
			map.put(receiver.getClass().getName(), receiver);
			receiverMap.put(context, map);
		}
	}

	public void unRegisterAllReceivers() {
		if (receiverMap.size() > 0) {
			Iterator<Entry<Context, HashMap<String, BroadcastReceiver>>> rootIter = receiverMap
					.entrySet().iterator();
			while (rootIter.hasNext()) {
				Entry<Context, HashMap<String, BroadcastReceiver>> rootEntry = rootIter
						.next();
				HashMap<String, BroadcastReceiver> rootValue = rootEntry
						.getValue();

				Iterator<Entry<String, BroadcastReceiver>> receiverIter = rootValue
						.entrySet().iterator();
				while (receiverIter.hasNext()) {
					try {
						Entry<String, BroadcastReceiver> receiverEntry = receiverIter
								.next();
						rootEntry.getKey().unregisterReceiver(
								receiverEntry.getValue());
					} catch (Exception e) {

					}
				}

			}
			receiverMap.clear();
		}
	}

	public void unRegisterAllReceiver(Context context, String receiverName) {
		if (receiverMap.size() > 0) {
			Iterator<Entry<Context, HashMap<String, BroadcastReceiver>>> rootIter = receiverMap
					.entrySet().iterator();
			while (rootIter.hasNext()) {
				Entry<Context, HashMap<String, BroadcastReceiver>> rootEntry = rootIter
						.next();
				if (rootEntry.getKey().equals(context)) {
					HashMap<String, BroadcastReceiver> rootValue = rootEntry
							.getValue();
					Iterator<Entry<String, BroadcastReceiver>> receiverIter = rootValue
							.entrySet().iterator();
					while (receiverIter.hasNext()) {
						try {
							Entry<String, BroadcastReceiver> receiverEntry = receiverIter
									.next();
							if (receiverEntry.getKey().equals(receiverName)) {
								rootEntry.getKey().unregisterReceiver(
										receiverEntry.getValue());
								receiverIter.remove();
								break;
							}
						} catch (Exception e) {
						}
					}
					if (rootValue.size() == 0) {
						receiverMap.remove(context);
					}
				}
			}
		}
	}
}
