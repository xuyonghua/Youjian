package com.deepbay.youjian.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.deepbay.youjian.MainActivity;
import com.deepbay.youjian.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {
    private static NotificationManager sNotificationManager;
    private static final String CHANNEL_ID = "channel_id";

    private static NotificationManager getNotificationManager(Context context) {
        if (sNotificationManager == null) {
            sNotificationManager = (NotificationManager) context.getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        }
        return sNotificationManager;
    }

    public static Notification createNotification(Context context, String title, String content) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setFullScreenIntent(pendingIntent, true)
                .setContentIntent(null);
//                .addAction(R.drawable.warning_bg, context.getString(R.string.button_continue_apply), pendingIntent);
        return builder.build();
    }

    public static void showNotification(Context context, String title, String content) {
        Notification notification = createNotification(context, title, content);
        getNotificationManager(context).notify(1, notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void createNotificationChannel(Context context, String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.createNotificationChannel(channel);
    }

}
