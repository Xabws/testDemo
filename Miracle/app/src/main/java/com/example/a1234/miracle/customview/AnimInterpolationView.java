package com.example.a1234.miracle.customview;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by a1234 on 2018/8/15.
 */

public class AnimInterpolationView extends View {
    Paint paint;
    ValueAnimator valueAnimator;
    float starX = 500, starY = 0;

    public AnimInterpolationView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setColor(Color.CYAN);
        initValueAnimator();
    }

    public AnimInterpolationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setColor(Color.CYAN);
        initValueAnimator();
    }

    public AnimInterpolationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setColor(Color.CYAN);
        initValueAnimator();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(starX, starY, 200, paint);
    }

    private void initValueAnimator() {
        valueAnimator = ValueAnimator.ofInt(0, 800);
        //valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
       // valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new DecelerateAccelerateInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                starY = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public class DecelerateAccelerateInterpolator implements TimeInterpolator {

        @Override
        public float getInterpolation(float input) {
            float result;
            result = (float) (Math.sin(Math.PI * input));
          /*  if (input <= 0.5) {
                result = (float) (Math.sin(Math.PI * input)) / 2;
            } else {
                result = (float) (2 - Math.sin(Math.PI * input)) / 2;
            }*/
            Log.d("asssss", result + "");
            return result;
        }

    }
}
