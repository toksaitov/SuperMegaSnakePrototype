package com.toksaitov.sms.game.controllers;

import android.graphics.Canvas;

import com.toksaitov.sms.game.views.PauseView;

import java.util.HashMap;
import java.util.Map;

public class PauseController extends Controller {
    private final Controller _previousController;

    public PauseController(Controller previousController) {
        _previousController = previousController;

        Map<String, Runnable> keyMappings = new HashMap<>();
        keyMappings.put("escape", this::_goBackToGame);
        this.setKeyMappings(keyMappings);

        Map<String, Runnable> gestureMappings = new HashMap<>();
        this.setGestureMappings(gestureMappings);
    }

    @Override
    public void draw(Canvas canvas, float w, float h, boolean shouldSimulate) {
        _previousController.draw(canvas, w, h, false);

        PauseView.draw(canvas, w, h);
    }

    @Override
    public void click(int x, int y) {
        PauseView.getBackToGameButton().handleClick(x, y, this::_goBackToGame);
    }

    private void _goBackToGame() {
        changeController(_previousController);
    }
}
