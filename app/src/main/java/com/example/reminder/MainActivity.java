package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<SongsDataHolder> songsDataHolderList;
    MediaPlayer mediaPlayer;
    int index=0;
    ImageButton btn_play,btn_prev,btn_next;
    TextView txt_song_title;
    boolean isPlaying;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_next = findViewById(R.id.btn_next);
        btn_play = findViewById(R.id.btn_play);
        btn_prev = findViewById(R.id.btn_prev);
        txt_song_title = findViewById(R.id.txt_song);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("next_song_change_command");
        intentFilter.addAction("prev_song_change_command");
        intentFilter.addAction("stop_song_change_command");
        intentFilter.addAction("play_song_change_command");

        registerReceiver(new BroadCastReciever(this),intentFilter);

        isPlaying = false;

        songsDataHolderList = new ArrayList<>();
        loadSongs();




        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               play_prev_song();
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_next_song();
            }
        });

    }
    public void loadSongs(){
        songsDataHolderList.add(new SongsDataHolder(R.raw.sochta_hun_ktne_masoom_the,"Sochta hu k ktne masoom the"));
        songsDataHolderList.add(new SongsDataHolder(R.raw.yaar_ko_hamne_jan_baja_dekha,"Yar ko hamne jan bajan dekha"));
        songsDataHolderList.add(new SongsDataHolder(R.raw.ayi_thi_wo_bandooq_ban_k,"Ayi thi marjani bandook ban kr"));
    }



    public void playMusic(){
        Intent intent = new Intent(this,SongNotificationService.class);
        intent.putExtra("song_name",songsDataHolderList.get(index).getSong_name());
        intent.putExtra("is_playing",isPlaying);
        startService(intent);
        txt_song_title.setText(songsDataHolderList.get(index).getSong_name());
         if (!isPlaying){
             btn_play.setImageResource(R.drawable.ic_baseline_pause_24);
             if(mediaPlayer!=null){
                 mediaPlayer.stop();
             }
             mediaPlayer = MediaPlayer.create(this,songsDataHolderList.get(index).getSong());
             isPlaying=true;
             mediaPlayer.start();
        }
        else{
            mediaPlayer.stop();
            isPlaying=false;
             btn_play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        }
    }

    public void play_next_song(){
        if(nextSong())
            playMusic();
    }
    public void play_prev_song(){
        if(prevSong())
            playMusic();
    }

    public boolean nextSong(){
        isPlaying=false;
        if(index<songsDataHolderList.size()-1){
            index++;
            return true;
        }
        return false;
    }
    public boolean prevSong(){
        isPlaying=false;
        if(index>0){
            index--;
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
