package com.toksaitov.sms.game.views;

import com.toksaitov.sms.game.models.Snake;
import com.toksaitov.sms.game.models.Snakes;

import android.graphics.Canvas;

public class SnakesView {
    private SnakesView() { }

    public static void draw(Canvas canvas, Snakes snakes) {
        for (Snake snake : snakes) {
            if (snake.isDead()) {
                SnakeView.draw(canvas, snake);
            }
        }

        // Draw alive snakes on top of dead ones.
        for (Snake snake : snakes) {
            if (!snake.isDead()) {
                SnakeView.draw(canvas, snake);
            }
        }
    }
}
