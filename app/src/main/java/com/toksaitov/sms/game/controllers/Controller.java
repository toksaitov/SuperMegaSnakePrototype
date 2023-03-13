package com.toksaitov.sms.game.controllers;

import android.graphics.Canvas;

import java.util.Map;

public abstract class Controller {
    private Map<String, Runnable> _keyMappings;
    private Map<String, Runnable> _gestureMappings;

    private static Controller _currentController = null;

    public Controller() {
        this(null, null);
    }

    public Controller(Map<String, Runnable> keyMappings, Map<String, Runnable> gestureMappings) {
        _keyMappings = keyMappings;
        _gestureMappings = gestureMappings;
    }

    public void setKeyMappings(Map<String, Runnable> keyMappings) {
        _keyMappings = keyMappings;
    }

    public void setGestureMappings(Map<String, Runnable> gestureMappings) {
        _gestureMappings = gestureMappings;
    }

    public abstract void draw(Canvas canvas, float w, float h, boolean shouldSimulate);

    public abstract void click(int x, int y);

    public void keyDown(String key) {
        Runnable action = _keyMappings.get(key.toLowerCase());
        if (action != null) {
            action.run();
        }
    }

    public void gestureRecognized(String gesture) {
        Runnable action = _gestureMappings.get(gesture);
        if (action != null) {
            action.run();
        }
    }

    public static Controller getCurrentController() {
        return _currentController;
    }

    public static void changeController(Controller controller) {
        _currentController = controller;
    }
}
