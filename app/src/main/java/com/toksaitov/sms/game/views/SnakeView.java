package com.toksaitov.sms.game.views;

import com.toksaitov.sms.game.models.Snake;
import com.toksaitov.sms.game.Params;
import static com.toksaitov.sms.game.utilities.DrawingHelpers.*;

import android.graphics.Canvas;

public class SnakeView {
    private SnakeView() { }

    static void draw(Canvas canvas, Snake snake) {
        for (Snake.BodySegment segment : snake.getBody()) {
            float pixelX = centeringShiftX + segment.getX() * cellSize;
            float pixelY = centeringShiftY + segment.getY() * cellSize;
            fillRect(canvas, pixelX, pixelY, cellSize - 1, cellSize - 1, snake.getColor());
        }
    }

    static void drawScore(Canvas canvas, float x, float y, Snake snake) {
        int fontSize = scaledFontSize(Params.SCORE_FONT_SCALE);
        fillText(canvas, String.valueOf(snake.getScore()), x, y, Params.SCORE_COLOR, Params.SCORE_FONT_NAME, fontSize);
    }
}
