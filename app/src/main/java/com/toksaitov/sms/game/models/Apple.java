package com.toksaitov.sms.game.models;

import android.graphics.Color;

import com.toksaitov.sms.game.Params;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class Apple {
    private final int _color;
    private final int _x, _y;

    private static boolean _isCollidingWithSnakes(int x, int y, Snake[] snakes) {
        for (Snake snake : snakes) {
            if (snake.isCollidingWith(x, y)) {
                return true;
            }
        }
        return false;
    }

    public Apple(Field field, Snake[] snakes) {
        this(field, snakes, Params.DEFAULT_APPLE_COLOR);
    }

    public Apple(Field field, Snake[] snakes, int color) {
        _color = color;

        ArrayList<int[]> candidates = new ArrayList<>();
        for (int y = 0; y < field.getWidth(); ++y) {
            for (int x = 0; x < field.getHeight(); ++x) {
                if (!_isCollidingWithSnakes(x, y, snakes)) {
                    candidates.add(new int[]{x, y});
                }
            }
        }
        assert candidates.size() != 0;
        int[] point = candidates.get((int) (Math.random() * candidates.size()));
        _x = point[0];
        _y = point[1];
    }

    public Apple(JSONObject json) {
        try {
            _x = json.getInt("x");
            _y = json.getInt("y");
            _color = Color.parseColor(json.getString("color"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public int getColor() {
        return _color;
    }

    public boolean isCollidingWith(int x, int y) {
        return x == _x && y == _y;
    }

    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        try {
            json.put("x", _x);
            json.put("y", _y);
            json.put("color", String.format("#%06X", 0xFFFFFF & _color));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
