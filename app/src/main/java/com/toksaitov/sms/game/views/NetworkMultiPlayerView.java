package com.toksaitov.sms.game.views;

import com.toksaitov.sms.game.models.Apple;
import com.toksaitov.sms.game.models.Field;
import com.toksaitov.sms.game.models.Snake;
import com.toksaitov.sms.game.models.Snakes;
import com.toksaitov.sms.game.Params;
import static com.toksaitov.sms.game.utilities.DrawingHelpers.*;

import android.graphics.Canvas;

public class NetworkMultiPlayerView {
    private NetworkMultiPlayerView() { }

    public static void draw(Canvas canvas, float w, float h, Field field, Apple apple, Snake snake, Snakes snakes) {
        recalcDrawingSizes(w, h, field);
        clearScreen(canvas, w, h);

        if (field != null) {
            FieldView.draw(canvas, field);
        }
        if (apple != null) {
            AppleView.draw(canvas, apple);
        }
        if (snakes != null) {
            SnakesView.draw(canvas, snakes);
        }

        if (snake != null) {
            float topScoreMargin = h * Params.SCORE_MARGIN_FROM_HEIGHT_RATIO;
            SnakeView.drawScore(canvas, w * 0.5f, topScoreMargin, snake);
        }
    }
}
