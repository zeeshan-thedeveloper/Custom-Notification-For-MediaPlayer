package com.example.reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class SongNotificationService extends Service {
    public static final String CHANNEL_ID="ForegroundServiceChannel";

    public SongNotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String song_name = intent.getStringExtra("song_name");
        Boolean is_playing = intent.getBooleanExtra("is_playing",false);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Creating notification channel
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"custom_notification",NotificationManager.IMPORTANCE_DEFAULT);
        //create channel
        notificationManager.createNotificationChannel(notificationChannel);
        //Creating notification custom layout holder
        RemoteViews expandedView = new RemoteViews(getPackageName(),R.layout.expanded_notif_view);
        //setting text view and other view values.
        expandedView.setTextViewText(R.id.txt_song_title_noti,song_name);

        //next btn
        Intent next_song_command_intent = new Intent();
        next_song_command_intent.setAction("next_song_change_command");
        PendingIntent next_song_command_pending_intent = PendingIntent.getBroadcast(this,0,next_song_command_intent,0);
        expandedView.setOnClickPendingIntent(R.id.btn_next_noti,next_song_command_pending_intent);

        //prev btn
        Intent prev_song_command_intent = new Intent();
        prev_song_command_intent.setAction("prev_song_change_command");
        PendingIntent prev_song_command_pending_intent = PendingIntent.getBroadcast(this,0,prev_song_command_intent,0);
        expandedView.setOnClickPendingIntent(R.id.btn_prev_noti,prev_song_command_pending_intent);

        //prev play/pause
        Intent stop_song_command_intent = new Intent();
        Intent play_song_command_intent = new Intent();
        PendingIntent play_song_command_pending_intent;
        PendingIntent stop_song_command_pending_intent;
        if(!is_playing) {

            play_song_command_intent.setAction("play_song_change_command");
            play_song_command_pending_intent = PendingIntent.getBroadcast(this, 0, play_song_command_intent, 0);
            expandedView.setOnClickPendingIntent(R.id.btn_play_noti, play_song_command_pending_intent);
        }else{

            stop_song_command_intent.setAction("stop_song_change_command");
            stop_song_command_pending_intent = PendingIntent.getBroadcast(this, 0, stop_song_command_intent, 0);
            expandedView.setOnClickPendingIntent(R.id.btn_play_noti, stop_song_command_pending_intent);
        }

//        Intent start_player = new Intent(this,MainActivity.class);
//        PendingIntent start_activity_pending_intent = PendingIntent.getActivity(this,0,start_player,0);
//        expandedView.setOnClickPendingIntent(R.id.btn_open_player,start_activity_pending_intent);

        //Creating notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Customized notification")
                .setSmallIcon(R.drawable.ic_baseline_library_music_24)
                .setCustomBigContentView(expandedView)
                .setAutoCancel(true);
        //Creating notification
        Notification notification = builder.build();
        startForeground(1234,notification);
        return START_STICKY;
    }
}