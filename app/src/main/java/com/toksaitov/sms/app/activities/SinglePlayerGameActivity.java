package com.toksaitov.sms.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.toksaitov.sms.R;
import com.toksaitov.sms.app.utilities.CustomView;
import com.toksaitov.sms.app.utilities.OnSwipeTouchListener;
import com.toksaitov.sms.game.controllers.Controller;
import com.toksaitov.sms.game.controllers.PauseController;
import com.toksaitov.sms.game.controllers.SinglePlayerGameController;
import com.toksaitov.sms.game.Params;

public class SinglePlayerGameActivity extends AppCompatActivity {
    private Controller _previousController = null;
    private boolean _shouldSimulate = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_game);

        Button _pauseButton = findViewById(R.id.pauseButton);
        CustomView _gameView = findViewById(R.id.singlePlayerGameView);

        SinglePlayerGameController controller = new SinglePlayerGameController();
        controller.setOnDefeatListener(() -> {
            _gameView.setOnDrawListener(null);

            Intent intent = new Intent(this, DefeatActivity.class);
            finish();
            startActivity(intent);
        });
        _previousController = controller;
        Controller.changeController(controller);

        _gameView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onTap(float x, float y) {
                Controller.getCurrentController().click((int) x, (int) y);
            }
            @Override
            public void onSwipeUp() {
                Controller.getCurrentController().gestureRecognized("swipeUp");
            }
            @Override
            public void onSwipeDown() {
                Controller.getCurrentController().gestureRecognized("swipeDown");
            }
            @Override
            public void onSwipeLeft() {
                Controller.getCurrentController().gestureRecognized("swipeLeft");
            }
            @Override
            public void onSwipeRight() {
                Controller.getCurrentController().gestureRecognized("swipeRight");
            }
        });
        _gameView.setOnKeyListener((v, keyCode, event) -> {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_UP:
                case KeyEvent.KEYCODE_W:
                    Controller.getCurrentController().keyDown("up");
                    return true;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                case KeyEvent.KEYCODE_S:
                    Controller.getCurrentController().keyDown("down");
                    return true;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_A:
                    Controller.getCurrentController().keyDown("left");
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                case KeyEvent.KEYCODE_D:
                    Controller.getCurrentController().keyDown("right");
                    return true;
                case KeyEvent.KEYCODE_ESCAPE:
                    Controller.getCurrentController().keyDown("escape");
                    return true;
            }
            return false;
        });
        _gameView.setOnDrawListener((canvas, w, h) -> {
            if (_previousController != Controller.getCurrentController()) {
                if (Controller.getCurrentController() instanceof PauseController) {
                    _pauseButton.setText(getString(R.string.playButtonText));
                } else {
                    _pauseButton.setText(getString(R.string.pauseButtonText));
                }
                _previousController = Controller.getCurrentController();
            }

            Controller.getCurrentController().draw(canvas, w, h, _shouldSimulate);
            _gameView.invalidate();
        });

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> _shouldSimulate = true, Params.PAUSE_BEFORE_START_MS);
    }

    public void onUpButtonClick(View view) {
        Controller.getCurrentController().keyDown("up");
    }

    public void onDownButtonClick(View view) {
        Controller.getCurrentController().keyDown("down");
    }

    public void onLeftButtonClick(View view) {
        Controller.getCurrentController().keyDown("left");
    }

    public void onRightButtonClick(View view) {
        Controller.getCurrentController().keyDown("right");
    }

    public void onPauseButtonClick(View view) {
        Controller.getCurrentController().keyDown("escape");
    }
}
