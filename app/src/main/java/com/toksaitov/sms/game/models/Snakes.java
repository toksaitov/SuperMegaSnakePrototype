package com.toksaitov.sms.game.models;

import com.toksaitov.sms.game.Params;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Snakes implements Iterable<Snake> {
    private static class SpawnPoint {
        private final int _id;
        private final int _x, _y;
        private final int _dx, _dy;
        private final String _name;
        private final int _color;

        public SpawnPoint(int id, int x, int y, int dx, int dy, String name, int color) {
            this._id = id;
            this._x = x;
            this._y = y;
            this._dx = dx;
            this._dy = dy;
            this._name = name;
            this._color = color;
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("id", _id);
            map.put("x", _x);
            map.put("y", _y);
            map.put("dx", _dx);
            map.put("dy", _dy);
            map.put("name", _name);
            map.put("color", _color);
            return map;
        }
    }

    private final Map<String, Snake> _snakes;
    private final List<SpawnPoint> _spawnPoints;
    private final Field _field;

    public Snakes(Field field) {
        _snakes = new HashMap<>();

        int w = field.getWidth(); int h = field.getHeight();
        _spawnPoints = new ArrayList<>();
        _spawnPoints.add(new SpawnPoint(0, 0,     0,      1, 0, Params.DEFAULT_SNAKE_1_NAME, Params.DEFAULT_SNAKE_1_COLOR));
        _spawnPoints.add(new SpawnPoint(1, w - 1, 0,     -1, 0, Params.DEFAULT_SNAKE_2_NAME, Params.DEFAULT_SNAKE_2_COLOR));
        _spawnPoints.add(new SpawnPoint(2, w - 1, h - 1, -1, 0, Params.DEFAULT_SNAKE_3_NAME, Params.DEFAULT_SNAKE_3_COLOR));
        _spawnPoints.add(new SpawnPoint(3, 0,     h - 1,  1, 0, Params.DEFAULT_SNAKE_4_NAME, Params.DEFAULT_SNAKE_4_COLOR));
        _field = field;
    }

    public Snakes(Field field, JSONObject json) {
        this(field);

        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                _snakes.put(key, new Snake(json.getJSONObject(key)));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public Iterator<Snake> iterator() {
        return _snakes.values().iterator();
    }

    public Snake spawnSnake(String id) {
        if (_spawnPoints.isEmpty()) {
            return null;
        }

        SpawnPoint spawnPoint = _spawnPoints.remove(0);
        return _snakes.put(id, new Snake(spawnPoint.toMap(), () -> {
            _spawnPoints.add(spawnPoint);
            Collections.sort(_spawnPoints, (p1, p2) -> Integer.compare(p1._id, p2._id));
        }));
    }

    public Snake getSnake(String id) {
        return _snakes.get(id);
    }

    public void move(Apple apple, Runnable onHaveEatenAppleListener) {
        _removeLongDeadSnakes();

        Snake[] snakes = _snakes.values().toArray(new Snake[0]);
        for (Snake snake : snakes) {
            snake.move(_field, apple, snakes, onHaveEatenAppleListener);
        }
    }

    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        try {
            for (Map.Entry<String, Snake> entry : _snakes.entrySet()) {
                json.put(entry.getKey().toString(), entry.getValue().serialize());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    private void _removeLongDeadSnakes() {
        for (Map.Entry<String, Snake> entry : _snakes.entrySet()) {
            Snake snake = entry.getValue();
            if (snake.isDead() && snake.getDeadFor() >= Params.TICKS_TO_REMOVE_DEAD_SNAKES) {
                _snakes.remove(entry.getKey());
            }
        }
    }
}
