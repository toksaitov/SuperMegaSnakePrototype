package com.toksaitov.sms.game.controllers;

import android.graphics.Canvas;

import com.toksaitov.sms.game.models.Apple;
import com.toksaitov.sms.game.models.Field;
import com.toksaitov.sms.game.models.Snake;
import com.toksaitov.sms.R;
import com.toksaitov.sms.game.views.SinglePlayerGameView;
import com.toksaitov.sms.game.utilities.AudioHelpers;

import java.util.HashMap;
import java.util.Map;

public class SinglePlayerGameController extends Controller {
    private final Field _field;
    private final Snake _snake;
    private final Snake[] _snakes;
    private Apple _apple;

    private Runnable _onDefeatListener;

    public SinglePlayerGameController() {
        _field = new Field();

        Map<String, Object> snakeParams = new HashMap<>();
        snakeParams.put("x", 0); snakeParams.put("y", 0);
        snakeParams.put("dx", 1); snakeParams.put("dy", 0);
        _snake  = new Snake(snakeParams);
        _snakes = new Snake[] { _snake };
        _apple  = new Apple(_field, _snakes);

        Map<String, Runnable> keyMappings = new HashMap<>();
        keyMappings.put("up", _snake::turnUp);
        keyMappings.put("down", _snake::turnDown);
        keyMappings.put("left", _snake::turnLeft);
        keyMappings.put("right", _snake::turnRight);
        keyMappings.put("escape", () -> changeController(new PauseController(this)));
        this.setKeyMappings(keyMappings);

        Map<String, Runnable> gestureMappings = new HashMap<>();
        gestureMappings.put("swipeUp", _snake::turnUp);
        gestureMappings.put("swipeDown", _snake::turnDown);
        gestureMappings.put("swipeLeft", _snake::turnLeft);
        gestureMappings.put("swipeRight", _snake::turnRight);
        this.setGestureMappings(gestureMappings);

        _onDefeatListener = null;
    }

    public void setOnDefeatListener(Runnable onDefeatListener) {
        _onDefeatListener = onDefeatListener;
    }

    @Override
    public void draw(Canvas canvas, float w, float h, boolean shouldSimulate) {
        if (shouldSimulate) {
            _snake.move(_field, _apple, _snakes, () -> {
                _apple = new Apple(_field, _snakes);
                AudioHelpers.playSound(R.raw.pickup_apple);
            });

            if (_snake.isDead()) {
                if (_onDefeatListener != null) {
                    _onDefeatListener.run();
                }
            }
        }

        SinglePlayerGameView.draw(canvas, w, h, _field, _apple, _snake);
    }

    @Override
    public void click(int x, int y) { }
}
