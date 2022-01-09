package com.example.reminder;

public class SongsDataHolder {
    int song;
    String song_name;

    public SongsDataHolder(int song, String song_name) {
        this.song = song;
        this.song_name = song_name;
    }

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }
}
