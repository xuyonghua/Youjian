package com.deepbay.youjian.entity;

public class AppInfo {
    /**
     * 首次安装时间
     */
    private long firstInstallTime;

    /**
     * 最后安装时间
     */
    private long lastUpdateTime;

    /**
     * 版本号
     */
    private int versionCode;

    /**
     * 版本名称
     */
    private String versionName;

    /**
     * app名称
     */
    private String appName;

    /**
     * app包名
     */
    private String packageName;

    public AppInfo(long firstInstallTime, long lastUpdateTime, int versionCode, String versionName, String appName, String packageName) {
        this.firstInstallTime = firstInstallTime;
        this.lastUpdateTime = lastUpdateTime;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.appName = appName;
        this.packageName = packageName;
    }

    public long getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(long firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "firstInstallTime=" + firstInstallTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
