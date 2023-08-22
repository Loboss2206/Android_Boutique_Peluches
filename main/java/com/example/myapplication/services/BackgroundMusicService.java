package com.example.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.R;



///////////////////////////////////////////////////
/////////////// Technologie non vue ///////////////
///////////////////////////////////////////////////
public class BackgroundMusicService extends Service {
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Création d'une méthode permettant d'activer la musique
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("mylog", "Started playing");
        mediaPlayer = MediaPlayer.create(this, R.raw.married_life);
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    // Création d'une méthode permettant de désactiver la musique
    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    // On arrète le mediaPlayer lors de l'arret de l'application
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}