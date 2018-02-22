package com.example.bob.testlistener.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/22.
 */

public class CheckApplications {

    public static boolean isApplicationAvailable(Context context,String packageName){
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> pNames = new ArrayList<String>();
        if(null != packageInfos){
            for(int i = 0; i< packageInfos.size();i++){
                String pName = packageInfos.get(i).packageName;
                pNames.add(pName);
            }
        }

        return pNames.contains(packageName);
    }
}
