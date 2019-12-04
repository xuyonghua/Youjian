package com.deepbay.youjian.utils;

import android.app.ProgressDialog;
import android.content.Context;


import com.deepbay.youjian.widget.LoadingDialog;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingUtils {
    private static ProgressDialog sProgressDialog;

    public static void showLoading(Context context) {
        try {
            if (sProgressDialog != null) {
                sProgressDialog.dismiss();
                sProgressDialog = null;
            }

            sProgressDialog = new LoadingDialog(context, android.R.style.Theme_Material_Light_Dialog);
            sProgressDialog.setCancelable(false);

//            sProgressDialog.setMessage("Loading ...");
            sProgressDialog.show();
            // 3秒后还未完成任务，则设置为可取消
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (sProgressDialog != null) {
                        sProgressDialog.setCancelable(true);
                    }
                }
            };
            Timer timer = new Timer(true);
            timer.schedule(task, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLoading(Context context, String message) {
        try {
            if (sProgressDialog != null) {
                sProgressDialog.dismiss();
                sProgressDialog = null;
            }

            sProgressDialog = new ProgressDialog(context);
            sProgressDialog.setCancelable(false);

            sProgressDialog.setMessage(message);
            sProgressDialog.show();
            // 3秒后还未完成任务，则设置为可取消
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (sProgressDialog != null) {
                        sProgressDialog.setCancelable(true);
                    }
                }
            };
            Timer timer = new Timer(true);
            timer.schedule(task, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissLoading() {
        try {
            if (sProgressDialog != null) {
                sProgressDialog.dismiss();
                sProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
