package com.example.a1234.miracle.customview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.a1234.miracle.utils.ContentUtil;


/**
 * Created by a1234 on 2018/8/16.
 */

public class BottomDrawerView extends RelativeLayout {
    private ViewDragHelper viewDragHelper;

    public BottomDrawerView(Context context) {
        super(context);
        init();
    }

    public BottomDrawerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomDrawerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        //创建需要三个参数，第一个为当前的ViewGroup，第二个为sensitivity，主要用于设置touchSlop：
        //第三个参数就是ViewDragHelper.Callback，触摸过程中会回调相关方法。
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /**
     * tryCaptureView：如果返回true表示捕获相关View，你可以根据第一个参数child决定捕获哪个View。
     * clampViewPositionVertical：计算child垂直方向的位置，top表示y轴坐标（相对于ViewGroup），默认返回0（如果不复写该方法）。这里，你可以控制垂直方向可移动的范围。
     * clampViewPositionHorizontal：与clampViewPositionVertical类似，只不过是控制水平方向的位置。
     * <p>
     * 作者：zhuhf
     * 链接：https://www.jianshu.com/p/111a7bc76a0e
     * 來源：简书
     * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
     */
    private class ViewDragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //下限
            if (top >= ContentUtil.dip2px(getContext(), 160)) {
                top = ContentUtil.dip2px(getContext(), 160);
            } else if (top < 0) {
                //上限
                top = 0;
            }

            return top;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }
}
