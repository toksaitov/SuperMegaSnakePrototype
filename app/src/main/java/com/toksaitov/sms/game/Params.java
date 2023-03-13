package com.toksaitov.sms.game;

import com.toksaitov.sms.R;

import android.graphics.Color;

public class Params {
    public static final String SERVER_URL = "http://10.10.4.18:8080";
    public static final int DEFAULT_FONT = R.font.press_start_2p_regular;
    public static final int DEFAULT_APPLE_COLOR = Color.RED;
    public static final int DEFAULT_FIELD_WIDTH = 20;
    public static final int DEFAULT_FIELD_HEIGHT = 20;
    public static final int DEFAULT_FIELD_COLOR = Color.parseColor("#1B1B1B");
    public static final String DEFAULT_SNAKE_1_NAME = "Snake 1";
    public static final String DEFAULT_SNAKE_2_NAME = "Snake 2";
    public static final String DEFAULT_SNAKE_3_NAME = "Snake 3";
    public static final String DEFAULT_SNAKE_4_NAME = "Snake 4";
    public static final int DEFAULT_SNAKE_1_COLOR = Color.RED;
    public static final int DEFAULT_SNAKE_2_COLOR = Color.BLUE;
    public static final int DEFAULT_SNAKE_3_COLOR = Color.GREEN;
    public static final int DEFAULT_SNAKE_4_COLOR = Color.YELLOW;
    public static final int TICKS_TO_REMOVE_DEAD_SNAKES = 300;
    public static final String DEFAULT_SNAKE_NAME = "Snake";
    public static final int DEFAULT_SNAKE_X = 0;
    public static final int DEFAULT_SNAKE_Y = 0;
    public static final int DEFAULT_SNAKE_DX = 1;
    public static final int DEFAULT_SNAKE_DY = 0;
    public static final int DEFAULT_SNAKE_SPEED = 50; // [1..60]
    public static final int DEFAULT_SNAKE_COLOR = Color.RED;
    public static final int DEFAULT_DEAD_SNAKE_COLOR = Color.parseColor("#181818");
    public static final int DEFAULT_SNAKE_LENGTH = 10;
    public static final int DEFAULT_BTN_FONT_NAME = DEFAULT_FONT;
    public static final float DEFAULT_BTN_FONT_SCALE = 40.0f;
    public static final int DEFAULT_BTN_COLOR = Color.WHITE;
    public static final float CELL_SCALE = 0.85f;
    public static final float TEXT_SIZING_DIVIDER = 1366.0f;
    public static final float MENU_BTNS_VERTICAL_SHIFT = 150.0f;
    public static final int SCORE_FONT_NAME = DEFAULT_FONT;
    public static final float SCORE_FONT_SCALE = 150.0f;
    public static final int SCORE_COLOR = Color.WHITE;
    public static final float SCORE_MARGIN_FROM_HEIGHT_RATIO = 0.165f;
    public static final int PAUSE_BEFORE_START_MS = 1000;
    public static final float PAUSE_MSG_VERTICAL_SHIFT = -20.0f;
    public static final int PAUSE_MSG_FONT_NAME = DEFAULT_FONT;
    public static final float PAUSE_MSG_FONT_SCALE = 80.0f;
    public static final int PAUSE_MSG_COLOR = Color.GRAY;
    public static final int PAUSE_SCREEN_OPACITY = 10;
    public static final int BACK_TO_GAME_BTN_FONT_NAME = DEFAULT_BTN_FONT_NAME;
    public static final float BACK_TO_GAME_BTN_FONT_SCALE = DEFAULT_BTN_FONT_SCALE;
    public static final int BACK_TO_GAME_BTN_COLOR = DEFAULT_BTN_COLOR;
}
