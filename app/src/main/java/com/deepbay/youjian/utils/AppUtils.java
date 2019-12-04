package com.deepbay.youjian.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;


import com.deepbay.youjian.entity.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    public static List<AppInfo> getAppList(Context context) {
        PackageManager pm = context.getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        List<AppInfo> appList = new ArrayList<>();
        for (PackageInfo packageInfo : packages) {
            // 非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appList.add(getAppInfo(context, packageInfo.packageName));
            }
        }
        return appList;
    }

    public static AppInfo getAppInfo(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            long firstInstallTime = packageInfo.firstInstallTime;
            long lastUpdateTime = packageInfo.lastUpdateTime;
            int versionCode = packageInfo.versionCode;//应用现在的版本号
            String versionName = packageInfo.versionName;//应用现在的版本名称
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            String appName = (String) pm.getApplicationLabel(applicationInfo);
            return new AppInfo(firstInstallTime, lastUpdateTime, versionCode, versionName, appName, packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getVersion(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void goGooglePlay(final Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
            intent.setPackage("com.android.vending");//这里对应的是谷歌商店，跳转别的商店改成对应的即可
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {//没有应用市场，通过浏览器跳转到Google Play
                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName()));
                if (intent2.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent2);
                }
            }
        } catch (ActivityNotFoundException activityNotFoundException1) {
            Log.e(AppUtils.class.getSimpleName(), "GoogleMarket Intent not found");
        }
    }


}
