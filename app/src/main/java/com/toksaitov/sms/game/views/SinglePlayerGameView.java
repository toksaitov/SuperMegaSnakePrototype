package com.toksaitov.sms.game.views;

import android.graphics.Canvas;

import com.toksaitov.sms.game.models.Apple;
import com.toksaitov.sms.game.models.Field;
import com.toksaitov.sms.game.models.Snake;
import com.toksaitov.sms.game.Params;
import static com.toksaitov.sms.game.utilities.DrawingHelpers.*;

public class SinglePlayerGameView {
    private SinglePlayerGameView() { }

    public static void draw(Canvas canvas, float w, float h, Field field, Apple apple, Snake snake) {
        recalcDrawingSizes(w, h, field);
        clearScreen(canvas, w, h);

        FieldView.draw(canvas, field);
        AppleView.draw(canvas, apple);
        SnakeView.draw(canvas, snake);

        float topScoreMargin = h * Params.SCORE_MARGIN_FROM_HEIGHT_RATIO;
        SnakeView.drawScore(canvas, w * 0.5f, topScoreMargin, snake);
    }
}
