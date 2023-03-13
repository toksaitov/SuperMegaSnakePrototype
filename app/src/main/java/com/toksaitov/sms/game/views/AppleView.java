package com.toksaitov.sms.game.views;

import com.toksaitov.sms.game.models.Apple;
import static com.toksaitov.sms.game.utilities.DrawingHelpers.*;

import android.graphics.Canvas;

public class AppleView {
    private AppleView() { }

    public static void draw(Canvas canvas, Apple apple) {
        float pixelX = centeringShiftX + apple.getX() * cellSize;
        float pixelY = centeringShiftY + apple.getY() * cellSize;
        fillRect(canvas, pixelX, pixelY, cellSize - 1, cellSize - 1, apple.getColor());
    }
}
