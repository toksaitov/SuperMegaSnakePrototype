package com.toksaitov.sms.game.controllers;

import android.app.Activity;
import android.graphics.Canvas;

import com.toksaitov.sms.game.models.Apple;
import com.toksaitov.sms.game.models.Field;
import com.toksaitov.sms.game.models.Snake;
import com.toksaitov.sms.R;
import com.toksaitov.sms.game.models.Snakes;
import com.toksaitov.sms.game.Params;
import com.toksaitov.sms.game.views.NetworkMultiPlayerView;
import com.toksaitov.sms.game.utilities.AudioHelpers;

import org.json.JSONArray;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;

public class NetworkMultiPlayerGameController extends Controller {
    public interface OnDisconnectedListener {
        void disconnected(String reason);
    }

    private Field _field;
    private Snakes _snakes;
    private int _snakePrevLength;
    private boolean _snakeSpawned;
    private Apple _apple;

    private final Activity _activity;
    private Runnable _onDefeatListener;
    private OnDisconnectedListener _onDisconnectedListener;

    private Socket _socket;

    public NetworkMultiPlayerGameController(Activity activity, OnDisconnectedListener onDisconnectedListener) {
        _field = null;
        _snakes = null;
        _snakePrevLength = -1;
        _snakeSpawned = false;
        _apple  = null;

        _activity = activity;
        _onDefeatListener = null;
        _onDisconnectedListener = onDisconnectedListener;

        try {
            _socket = IO.socket(Params.SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();

            String reason = _activity.getString(R.string.disconFullRoomReasonText);
            _onDisconnectedListener.disconnected(reason);
        }
        _socket.on(Socket.EVENT_DISCONNECT, args -> _activity.runOnUiThread(() -> {
            String reason;
            if (args.length > 0 && args[0] instanceof String && args[0].equals("io server disconnect")) {
                reason = _activity.getString(R.string.disconFullRoomReasonText);
            } else {
                reason = _activity.getString(R.string.disconConnErrReasonText);
            }
            _onDisconnectedListener.disconnected(reason);
        }));
        _socket.on("update", args -> _activity.runOnUiThread(() -> {
            try {
                JSONArray data = (JSONArray) args[0];
                if (data.length() >= 1) {
                    _field = new Field(data.getJSONObject(0));
                }
                if (data.length() >= 2) {
                    _apple = new Apple(data.getJSONObject(1));
                }
                if (data.length() >= 3) {
                    _snakes = new Snakes(_field, data.getJSONObject(2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        Map<String, Runnable> keyMappings = new HashMap<>();
        keyMappings.put("up", () -> _socket.emit("command", "w"));
        keyMappings.put("left", () -> _socket.emit("command", "a"));
        keyMappings.put("down", () -> _socket.emit("command", "s"));
        keyMappings.put("right", () -> _socket.emit("command", "d"));
        this.setKeyMappings(keyMappings);

        Map<String, Runnable> gestureMappings = new HashMap<>();
        gestureMappings.put("swipeUp", () -> _socket.emit("command", "w"));
        gestureMappings.put("swipeLeft", () -> _socket.emit("command", "a"));
        gestureMappings.put("swipeDown", () -> _socket.emit("command", "s"));
        gestureMappings.put("swipeRight", () -> _socket.emit("command", "d"));
        this.setGestureMappings(gestureMappings);
    }

    public void connect() {
        _socket.connect();
        _socket.emit("spawn");
    }

    public void disconnect() {
        _socket.off();
        _socket.disconnect();
    }

    public void setOnDefeatListener(Runnable onDefeatListener) {
        _onDefeatListener = onDefeatListener;
    }

    public void setOnDisconnectedListener(OnDisconnectedListener onDisconnectedListener) {
        _onDisconnectedListener = onDisconnectedListener;
    }

    public Snake getSnake() {
        String id = _socket.id();
        if (id != null && _snakes != null) {
            return _snakes.getSnake(id);
        }

        return null;
    }

    @Override
    public void draw(Canvas canvas, float w, float h, boolean shouldSimulate) {
        Snake snake = getSnake();
        if (snake != null || _snakeSpawned) {
            if (shouldSimulate) {
                if ((_snakeSpawned && snake == null) || snake.isDead()) {
                    if (_onDefeatListener != null) {
                        _onDefeatListener.run();
                    }
                    return;
                }
                _snakeSpawned = true;

                int currentSnakeLength = snake.getBody().size();
                if (_snakePrevLength != -1 && _snakePrevLength != currentSnakeLength) {
                    AudioHelpers.playSound(R.raw.pickup_apple);
                }
                _snakePrevLength = currentSnakeLength;
            }
        }

        NetworkMultiPlayerView.draw(canvas, w, h, _field, _apple, snake, _snakes);
    }

    @Override
    public void click(int x, int y) { }
}
