package com.toksaitov.sms.app.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {
    public interface OnDrawListener {
        void onDraw(Canvas canvas, int w, int h);
    }

    private OnDrawListener _onDrawListener;

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _onDrawListener = null;
    }

    public void setOnDrawListener(OnDrawListener onDrawListener) {
        _onDrawListener = onDrawListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (_onDrawListener != null) {
            _onDrawListener.onDraw(canvas, getWidth(), getHeight());
        }
    }
}
