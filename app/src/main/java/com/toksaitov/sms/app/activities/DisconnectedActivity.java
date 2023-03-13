package com.toksaitov.sms.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.toksaitov.sms.R;

public class DisconnectedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnected);

        TextView disconReasonTextView = findViewById(R.id.disconReasonTextView);
        if (disconReasonTextView != null) {
            disconReasonTextView.setText(getIntent().getStringExtra("reason"));
        }
    }

    public void onBackToMenuButtonClick(View view) {
        finish();
    }
}
