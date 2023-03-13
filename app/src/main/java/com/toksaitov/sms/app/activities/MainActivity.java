package com.toksaitov.sms.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.toksaitov.sms.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
    }

    public void onSinglePlayerButtonClick(View view) {
        Intent intent = new Intent(this, SinglePlayerGameActivity.class);
        startActivity(intent);
    }

    public void onNetworkMultiplayerPlayerButtonClick(View view) {
        Intent intent = new Intent(this, NetworkMultiPlayerGameActivity.class);
        startActivity(intent);
    }
}
