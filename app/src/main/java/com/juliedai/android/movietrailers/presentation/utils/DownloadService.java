package com.juliedai.android.movietrailers.presentation.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.juliedai.android.movietrailers.R;
import com.juliedai.android.movietrailers.presentation.view.activity.download.DownloadActivity;

/**
 * @author  Julie Dai
 */

public class DownloadService extends Service {

    public static final String LOG_TAG = "_DownloadService_";
    public static final String URL = "URL";
    public static final int ONGOING_NOTIFICATION_ID = 14000605;
    private static final String CHANNEL_DEFAULT_IMPORTANCE = "Channel";
    public static final String BROADCAST_ACTION = "com.academy.fundamentals.DOWNLOAD_COMPLETE";


    public static void startService(Activity activity, String url){
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putExtra(URL, url);
        activity.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId){
        Log.d(LOG_TAG,"> In onStartCommand");
        startForegroud();

        String url = intent.getStringExtra(URL);
        Log.d(LOG_TAG, "# URL: " + url);

        startDownloadThread(url);

        return START_STICKY;
    }

    private void startDownloadThread(String url) {
        new DownloadThread(url, new DownloadThread.DownloadCallBack() {
            @Override
            public void onProgressUpdate(int percent) {
                Log.d(LOG_TAG, "> startDownloadThread > DownloadThread > onProgressUpdate: " + percent + "%");
                updateNotification(percent);
            }

            @Override
            public void onDownloadFinished(String filePath) {
                Log.d(LOG_TAG, "> startDownloadThread > DownloadThread > onDownloadFinished: " + filePath);
                sendBroadcastMsgDownloadComplete(filePath);
                stopSelf();
            }

            @Override
            public void onError(String error) {
                Log.d(LOG_TAG, "> startDownloadThread > DownloadThread > onError: " + error);
                stopSelf();
            }
        }).start();
    }

    private void updateNotification(int progress) {
        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notifManager != null){
            Notification notif = createNotification(progress);
            notifManager.notify(ONGOING_NOTIFICATION_ID, notif);
        }
    }

    private void sendBroadcastMsgDownloadComplete(String filePath) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(DownloadActivity.ARG_FILE_PATH, filePath);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void startForegroud(){
        createNotificationChannel();
        Notification notif = createNotification(0);
        startForeground(ONGOING_NOTIFICATION_ID, notif);
    }

    private Notification createNotification(int progress) {
        Log.d(LOG_TAG, "> createNotification");
        Intent notifIntent = new Intent(this, DownloadActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, 0);
        String progressStr = getString(R.string.notification_message, progress);
        Log.d(LOG_TAG, "# progress: " + progressStr);

        return new NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(progressStr)
                .setSmallIcon(android.R.drawable.stat_sys_upload)
                .setProgress(100, progress, false)
                .setContentIntent(pendingIntent)
                .build();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // When the API level is equal or greater then 26
            CharSequence name = getString(R.string.channel_name); // The user-visible name of the channel.
            String description = getString(R.string.channel_description); // The user-visible description of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE, name, importance);

            // Configure the notification channel
            mChannel.setDescription(description);
            mChannel.enableLights(true);

            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.GREEN);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if(notifManager != null) {
                notifManager.createNotificationChannel(mChannel);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "> onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
