package com.toksaitov.sms.game.utilities;

import com.toksaitov.sms.game.models.Field;
import com.toksaitov.sms.game.Params;
import com.toksaitov.sms.SnakeApplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.core.content.res.ResourcesCompat;

public class DrawingHelpers {
    public static float cellSize;
    public static float fieldPixelWidth;
    public static float fieldPixelHeight;
    public static float centeringShiftX;
    public static float centeringShiftY;
    public static float textSizingUnit = 1;

    private static final Paint _paint = new Paint();

    private DrawingHelpers() { }

    public static void recalcDrawingSizes(float w, float h) {
        recalcDrawingSizes(w, h, null);
    }

    public static void recalcDrawingSizes(float w, float h, Field field) {
        if (field != null) {
            cellSize = Math.min(w / field.getWidth(), h / field.getHeight()) * Params.CELL_SCALE;
            fieldPixelWidth = cellSize * field.getWidth();
            fieldPixelHeight = cellSize * field.getHeight();
            centeringShiftX = (w - fieldPixelWidth) * 0.5f;
            centeringShiftY = (h - fieldPixelHeight) * 0.5f;
        }
        textSizingUnit = w / Params.TEXT_SIZING_DIVIDER;
    }

    public static void fillRect(Canvas canvas, float x, float y, float width, float height, int color) {
        _paint.setColor(color);
        canvas.drawRect(x, y, x + width, y + height, _paint);
    }

    public static void fillText(Canvas canvas, String text, float x, float y, int color, int font, int fontSize) {
        _paint.setTypeface(ResourcesCompat.getFont(SnakeApplication.getContext(), font));
        _paint.setTextAlign(Paint.Align.CENTER);
        _paint.setTextSize(fontSize);
        _paint.setColor(color);
        canvas.drawText(text, x, y, _paint);
    }

    public static void clearScreen(Canvas canvas, float width, float height) {
        clearScreen(canvas, width, height, null);
    }

    public static void clearScreen(Canvas canvas, float width, float height, Integer color) {
        _paint.setColor(color == null ? Color.BLACK : color);
        canvas.drawRect(0.0f, 0.0f, width, height, _paint);
    }

    public static int scaledFontSize(float fontScale) {
        return (int) (textSizingUnit * fontScale);
    }

    public static float[] measureText(String text, int font, int fontSize) {
        Rect bounds = new Rect();
        _paint.setTypeface(ResourcesCompat.getFont(SnakeApplication.getContext(), font));
        _paint.setTextSize(fontSize);
        _paint.getTextBounds(text, 0, text.length(), bounds);
        float width = bounds.width();
        float height = bounds.height();
        return new float[] { width, height };
    }
}
