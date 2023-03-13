package com.toksaitov.sms.game.views;

import com.toksaitov.sms.game.models.Button;
import com.toksaitov.sms.game.Params;
import com.toksaitov.sms.R;
import com.toksaitov.sms.SnakeApplication;
import static com.toksaitov.sms.game.utilities.DrawingHelpers.*;

import android.graphics.Canvas;
import android.graphics.Color;

public class PauseView {
    private final static Button _backToGameButton =
        new Button(
            SnakeApplication.getContext().getString(R.string.backToGameBtnText),
            Params.BACK_TO_GAME_BTN_FONT_NAME,
            Params.BACK_TO_GAME_BTN_FONT_SCALE,
            Params.BACK_TO_GAME_BTN_COLOR
        );

    private PauseView() { }

    public static Button getBackToGameButton() {
        return _backToGameButton;
    }

    public static void draw(Canvas canvas, float w, float h) {
        recalcDrawingSizes(w, h);
        clearScreen(canvas, w, h, Color.BLACK | ((Params.PAUSE_SCREEN_OPACITY & 0xff) << 24));

        float cx = w * 0.5f;
        float cy = h * 0.5f + Params.PAUSE_MSG_VERTICAL_SHIFT;
        int pauseMsgFontSize = scaledFontSize(Params.PAUSE_MSG_FONT_SCALE);
        String pauseMsgText = SnakeApplication.getContext().getString(R.string.pauseMsgText);
        fillText(canvas, pauseMsgText, cx, cy, Params.PAUSE_MSG_COLOR, Params.PAUSE_MSG_FONT_NAME, pauseMsgFontSize);

        _backToGameButton.setFontScale(Params.BACK_TO_GAME_BTN_FONT_SCALE);
        _backToGameButton.setX(cx);
        _backToGameButton.setY(cy + Params.MENU_BTNS_VERTICAL_SHIFT);
        ButtonView.draw(canvas, _backToGameButton);
    }
}
