package com.toksaitov.sms.game.views;

import com.toksaitov.sms.game.models.Button;
import com.toksaitov.sms.game.utilities.DrawingHelpers;

import android.graphics.Canvas;

public class ButtonView {
    private ButtonView() { }

    public static void draw(Canvas canvas, Button button) {
        DrawingHelpers.fillText(canvas, button.getText(), button.getX(), button.getY(), button.getColor(), button.getFont(), button.getFontSize());
    }
}
