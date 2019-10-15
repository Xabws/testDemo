package com.example.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.baselib.R;


/**
 * author: wsBai
 * date: 2019/3/13
 */
public class FloatingLayout extends ConstraintLayout {
    public FloatingLayout(Context context) {
        super(context);
        init(context);
    }

    public FloatingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.floating_view, this);
    }
}
