package com.toksaitov.sms.game.models;

import android.graphics.Color;

import com.toksaitov.sms.game.Params;

import org.json.JSONException;
import org.json.JSONObject;

public class Field {
    private final int _width, _height;
    private final int _color;

    public Field() {
        this(Params.DEFAULT_FIELD_WIDTH, Params.DEFAULT_FIELD_HEIGHT, Params.DEFAULT_FIELD_COLOR);
    }

    public Field(int width, int height, int color) {
        _width  = width;
        _height = height;
        _color  = color;
    }

    public Field(JSONObject json) {
        try {
            _width = json.getInt("width");
            _height = json.getInt("height");
            _color = Color.parseColor(json.getString("color"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public int getColor() {
        return _color;
    }

    public boolean areCoordsInside(int x, int y) {
        return x >= 0 && x < _width &&
               y >= 0 && y < _height;
    }

    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        try {
            json.put("width", _width);
            json.put("height", _height);
            json.put("color", String.format("#%06X", 0xFFFFFF & _color));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
