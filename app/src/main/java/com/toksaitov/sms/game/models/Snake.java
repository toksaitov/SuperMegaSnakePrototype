package com.toksaitov.sms.game.models;

import android.graphics.Color;

import com.toksaitov.sms.game.Params;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Snake {
    public static class BodySegment {
        private int _x, _y;

        public BodySegment(int x, int y) {
            _x = x;
            _y = y;
        }

        public BodySegment(JSONObject json) {
            try {
                _x = json.getInt("x");
                _y = json.getInt("y");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        public int getX() {
            return _x;
        }

        public void setX(int x) {
            _x = x;
        }

        public int getY() {
            return _y;
        }

        public void setY(int y) {
            _y = y;
        }

        public JSONObject serialize() {
            JSONObject json = new JSONObject();
            try {
                json.put("x", _x);
                json.put("y", _y);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return json;
        }
    }

    private String _name;
    private int _head;
    private int _dx, _dy;
    private int _prevDX, _prevDY;
    private boolean _dead;
    private int _deadFor;
    private int _score;
    private int _moveRequest;
    private final int _moveRequestDiv;
    private final int _normalColor;
    private final int _deadColor;
    private int _color;
    private List<BodySegment> _body;
    private final Runnable _onDeadListener;

    public Snake(Map<String, Object> params) {
        this(params, null);
    }

    public Snake(Map<String, Object> params, Runnable onDeadListener) {
        _name = (String) params.get("name");
        if (_name == null) {
            _name = Params.DEFAULT_SNAKE_NAME;
        }

        _head = 0;

        Integer dx = (Integer) params.get("dx");
        if (dx == null) {
            _dx = Params.DEFAULT_SNAKE_DX;
        } else {
            _dx = dx;
        }

        Integer dy = (Integer) params.get("dy");
        if (dy == null) {
            _dy = Params.DEFAULT_SNAKE_DY;
        } else {
            _dy = dy;
        }

        _prevDX = _dx;
        _prevDY = _dy;

        _dead = false;
        _deadFor = 0;

        _score = 0;

        _moveRequest = 0;
        Integer speed = (Integer) params.get("speed");
        if (speed == null) {
            speed = Params.DEFAULT_SNAKE_SPEED;
        }
        _moveRequestDiv = Math.max(60 - speed, 1);
        Integer color = (Integer) params.get("color");
        if (color == null) {
            _normalColor = Params.DEFAULT_SNAKE_COLOR;
        } else {
            _normalColor = color;
        }
        _deadColor = Params.DEFAULT_DEAD_SNAKE_COLOR;
        _color = _normalColor;

        Integer initialLength = (Integer) params.get("initialLength");
        if (initialLength == null) {
            initialLength = Params.DEFAULT_SNAKE_LENGTH;
        }

        Integer x = (Integer) params.get("x");
        if (x == null) {
            x = Params.DEFAULT_SNAKE_X;
        }
        Integer y = (Integer) params.get("y");
        if (y == null) {
            y = Params.DEFAULT_SNAKE_Y;
        }
        _body = new ArrayList<>();
        for (int i = 0; i < initialLength; ++i) {
            _body.add(new BodySegment(x, y));
        }

        _onDeadListener = onDeadListener;
    }

    public Snake(JSONObject json) {
        try {
            _name = json.getString("name");
            _head = json.getInt("head");
            _dx = json.getInt("dx");
            _dy = json.getInt("dy");
            _prevDX = json.getInt("prevDX");
            _prevDY = json.getInt("prevDY");
            _dead = json.getBoolean("dead");
            _deadFor = _dead ? 1 : 0;
            _score = json.getInt("score");
            _normalColor = Params.DEFAULT_SNAKE_COLOR;
            _deadColor = Params.DEFAULT_DEAD_SNAKE_COLOR;
            _color = Color.parseColor(json.getString("color"));
            _moveRequest = 0;
            int speed = Params.DEFAULT_SNAKE_SPEED;
            _moveRequestDiv = Math.max(60 - speed, 1);
            JSONArray body = json.getJSONArray("body");
            _body = new ArrayList<>();
            for (int i = 0; i < body.length(); ++i) {
                _body.add(new BodySegment(body.getJSONObject(i)));
            }
            _onDeadListener = null;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return _name;
    }

    public int getHead() {
        return _head;
    }

    public int getDX() {
        return _dx;
    }

    public int getDY() {
        return _dy;
    }

    public int getPrevDX() {
        return _prevDX;
    }

    public int getPrevDY() {
        return _prevDY;
    }

    public List<BodySegment> getBody() {
        return _body;
    }

    public int getColor() {
        return _color;
    }

    public int getScore() {
        return _score;
    }

    public boolean isDead() {
        return _dead;
    }

    public void setDead(boolean dead) {
        _dead = dead;
        if (_dead) {
            _color = _deadColor;
            _deadFor = 1;
            if (_onDeadListener != null) {
                _onDeadListener.run();
            }
        } else {
            _color = _normalColor;
        }
    }

    public int getDeadFor() {
        return _deadFor;
    }

    public void turnUp() {
        if (_dy != 1) {
            _dx = 0;
            _dy = -1;
        }
    }

    public void turnDown() {
        if (_dy != -1) {
            _dx = 0;
            _dy = 1;
        }
    }

    public void turnLeft() {
        if (_dx != 1) {
            _dx = -1;
            _dy = 0;
        }
    }

    public void turnRight() {
        if (_dx != -1) {
            _dx = 1;
            _dy = 0;
        }
    }

    public boolean isCollidingWith(int x, int y) {
        for (BodySegment segment : _body) {
            if (segment.getX() == x && segment.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void move(Field field, Apple apple, Snake[] snakes, Runnable onHaveEatenAppleListener) {
        if (_dead) {
            ++_deadFor;
            return;
        }
        if (_moveRequest++ % _moveRequestDiv != 0) { return; }

        BodySegment headSegment = _body.get(_head);
        int nextX = headSegment.getX() + _dx;
        int nextY = headSegment.getY() + _dy;
        if (!field.areCoordsInside(nextX, nextY) || isCollidingWith(nextX, nextY)) {
            setDead(true);
            return;
        }

        Snake otherSnake = _isCollidingWithSnakes(snakes);
        if (otherSnake != null) {
            setDead(true); otherSnake.setDead(true);
            return;
        }

        _head = (_head + 1) % _body.size();
        BodySegment nextHeadSegment = _body.get(_head);
        if (apple.isCollidingWith(nextX, nextY))  {
            ++_score;
            _body.add(_head, new BodySegment(nextX, nextY));
            onHaveEatenAppleListener.run();
        } else {
            nextHeadSegment.setX(nextX);
            nextHeadSegment.setY(nextY);
        }

        _prevDX = _dx;
        _prevDY = _dy;
    }

    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", _name);
            json.put("head", _head);
            json.put("dx", _dx);
            json.put("dy", _dy);
            json.put("prevDX", _prevDX);
            json.put("prevDY", _prevDY);
            json.put("dead", _dead);
            json.put("score", _score);
            json.put("color", String.format("#%06X", 0xFFFFFF & _color));
            JSONArray body = new JSONArray();
            for (BodySegment segment : _body) {
                body.put(segment.serialize());
            }
            json.put("body", body);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    private Snake _isCollidingWithSnakes(Snake[] snakes) {
        BodySegment headSegment = _body.get(_head);
        int x = headSegment.getX();
        int y = headSegment.getY();
        for (Snake snake : snakes) {
            if (snake != this) {
                if (!snake.isDead() && snake.isCollidingWith(x, y)) {
                    return snake;
                }
            }
        }
        return null;
    }
}
