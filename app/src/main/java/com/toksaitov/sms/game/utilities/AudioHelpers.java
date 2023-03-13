package com.toksaitov.sms.game.utilities;

import com.toksaitov.sms.SnakeApplication;

import android.media.MediaPlayer;

public class AudioHelpers {
    private AudioHelpers() { }

    public static void playSound(int sound) {
        MediaPlayer player = MediaPlayer.create(SnakeApplication.getContext(), sound);
        if (player != null) {
            player.setOnCompletionListener(MediaPlayer::release);
            player.start();
        }
    }
}
