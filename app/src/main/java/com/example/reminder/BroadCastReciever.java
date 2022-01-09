package com.example.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class BroadCastReciever extends BroadcastReceiver {

    MainActivity mainActivity;

    public BroadCastReciever(MainActivity mainActivity) {
     this.mainActivity=mainActivity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.expanded_notif_view);

        Log.d("cast","Received: "+intent.getAction());
        switch (intent.getAction()){
            case "next_song_change_command":
                mainActivity.play_next_song();
                break;
            case "prev_song_change_command":
                mainActivity.play_prev_song();
                break;
            case "play_song_change_command":
            case "stop_song_change_command":
                mainActivity.playMusic();
                break;
        }
    }
}