package com.toksaitov.sms.game.views;

import com.toksaitov.sms.game.models.Field;
import static com.toksaitov.sms.game.utilities.DrawingHelpers.*;

import android.graphics.Canvas;

public class FieldView {
    private FieldView() { }

    public static void draw(Canvas canvas, Field field) {
        for (int y = 0; y < field.getHeight(); ++y) {
            for (int x = 0; x < field.getWidth(); ++x) {
                float pixelX = centeringShiftX + x * cellSize;
                float pixelY = centeringShiftY + y * cellSize;
                fillRect(canvas, pixelX, pixelY, cellSize - 1, cellSize - 1, field.getColor());
            }
        }
    }
}
