package com.example.notificationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private static int NOTIFY_COUNT = 0;

    public static final int NOTIFICATION_ID = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_layout);

        Intent intent = getIntent();
        String s = "intent=null";
        if(intent != null) {
            s = "xxx: " + intent.getStringExtra("xxx");
        }
        Log.i(TAG, "onCreate " + s);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String s = "intent=null";
        if(intent != null) {
            s = "xxx: " + intent.getStringExtra("xxx");
        }
        Log.i(TAG, "onNewIntent " + s);
    }

    public void sendNotification(View view) {

//        Intent intent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));

        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("xxx", "notify:" + NOTIFY_COUNT++);

        Log.i(TAG, "sendNotification xxx: " + intent.getStringExtra("xxx"));

        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //flags很重要 PendingIntent.FLAG_UPDATE_CURRENT决定了目标Activity是否能收到新的Extra数据
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.ic_stat_notification);

        builder.setContentIntent(pendingIntent);

        builder.setAutoCancel(true);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

        builder.setContentTitle("BasicNotifications Sample");
        builder.setContentText("Time to learn about notifications!");
        //builder.setSubText("Tap to view documentation about notifications.");

        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void customNotification(View view) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);



        //Create Intent to launch this Activity again if the notification is clicked.
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent intent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);



        // Sets the ticker text
        builder.setTicker(getResources().getString(R.string.custom_notification));

        // Sets the small icon for the ticker
        builder.setSmallIcon(R.drawable.ic_stat_custom);



        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        // Build the notification
        Notification notification = builder.build();



        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);

        // Set text on a TextView in the RemoteViews programmatically.
        final String time = DateFormat.getTimeInstance().format(new Date()).toString();
        final String text = getResources().getString(R.string.collapsed, time);
        contentView.setTextViewText(R.id.textView, text);

        /* Workaround: Need to set the content view here directly on the notification.
         * NotificationCompatBuilder contains a bug that prevents this from working on platform
         * versions HoneyComb.
         * See https://code.google.com/p/android/issues/detail?id=30495
         */
        notification.contentView = contentView;

        // Add a big content view to the notification if supported.
        // Support for expanded notifications was added in API level 16.
        // (The normal contentView is shown when the notification is collapsed, when expanded the
        // big content view set here is displayed.)
        if (Build.VERSION.SDK_INT >= 16) {
            // Inflate and set the layout for the expanded notification view
            RemoteViews expandedView =
                    new RemoteViews(getPackageName(), R.layout.notification_expanded);
            notification.bigContentView = expandedView;
        }


        // START_INCLUDE(notify)
        // Use the NotificationManager to show the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID, notification);

    }

    public void clear(View v) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(NOTIFICATION_ID);
    }

    public void testActivity(View v) {
        startActivity(new Intent(this, TestActivity.class));
    }
}
