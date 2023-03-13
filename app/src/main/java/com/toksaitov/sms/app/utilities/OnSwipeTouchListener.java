package com.toksaitov.sms.app.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener implements OnTouchListener {
    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context ctx) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onTap(e.getX(), e.getY());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velX, float velY) {
            float diffX = e2.getX() - e1.getX();
            float distX = Math.abs(diffX);
            float diffY = e2.getY() - e1.getY();
            float distY = Math.abs(diffY);
            if (distX > distY) {
                velX = Math.abs(velX);
                if (distX > SWIPE_THRESHOLD && velX > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX <= 0) onSwipeLeft(); else onSwipeRight();
                    return true;
                }
            } else {
                velY = Math.abs(velY);
                if (distY > SWIPE_THRESHOLD && velY > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY <= 0) onSwipeUp(); else onSwipeDown();
                    return true;
                }
            }
            return false;
        }
    }

    public void onTap(float x, float y) { }

    public void onSwipeUp() { }

    public void onSwipeDown() { }

    public void onSwipeRight() { }

    public void onSwipeLeft() { }
}
