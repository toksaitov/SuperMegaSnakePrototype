package com.toksaitov.sms.game.models;

import com.toksaitov.sms.game.Params;
import com.toksaitov.sms.game.utilities.DrawingHelpers;

public class Button {
    private final String _text;
    private float _x, _y;
    private float _width, _height;
    private float _halfWidth, _halfHeight;
    private final int _font;
    private float _fontScale;
    private final int _fontSize;
    private final int _color;

    public Button(String text) {
        this(text, Params.DEFAULT_BTN_FONT_NAME, Params.DEFAULT_BTN_FONT_SCALE, Params.DEFAULT_BTN_COLOR);
    }

    public Button(String text, int font, float fontScale, int color) {
        _text = text;
        _x = 0;
        _y = 0;
        _font = font;
        _fontScale = fontScale;
        _fontSize = DrawingHelpers.scaledFontSize(fontScale);
        _recalculateSizes();
        _color = color;
    }

    public String getText() {
        return _text;
    }

    public float getX() {
        return _x;
    }

    public void setX(float x) {
        _x = x;
    }

    public float getY() {
        return _y;
    }

    public void setY(float y) {
        _y = y;
    }

    public float getWidth() {
        return _width;
    }

    public float getHeight() {
        return _height;
    }

    public int getFont() {
        return _font;
    }

    public int getFontSize() {
        return _fontSize;
    }

    public void setFontScale(float fontScale) {
        _fontScale = fontScale;
        _recalculateSizes();
    }

    public int getColor() {
        return _color;
    }

    public void handleClick(double x, double y, Runnable onClickListener) {
        if (x >= this._x - this._halfWidth &&
            y >= this._y - this._height    &&
            x < this._x  + this._halfWidth &&
            y < this._y  + this._halfHeight) {
            onClickListener.run();
        }
    }

    private void _recalculateSizes() {
        float[] sizes = DrawingHelpers.measureText(_text, _font, _fontSize);
        _width = sizes[0];
        _height = sizes[1];
        _halfWidth  = _width * 0.5f;
        _halfHeight = _height * 0.5f;
    }
}
