package com.example.a1234.miracle.customview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.utils.BlurCallable;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by a1234 on 2018/8/23.
 */
public class CardDialog implements View.OnClickListener {
    private ValueAnimator valueAnimator;
    private Context context;
    private RelativeLayout layout;
    private RelativeLayout background;
    private CardView cardView;
    private TextView ok, cancel;
    private CardDialogCallback cardDialogCallback;
    private boolean isNeedBlur = true;

    public CardDialog(Context context, CardDialogCallback cardDialogCallback) {
        this.cardDialogCallback = cardDialogCallback;
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        initView();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        // 设置flag
        int flags = WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        windowManager.addView(layout, params);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        //获取浮动窗口视图所在布局
        layout = (RelativeLayout) inflater.inflate(R.layout.card_dialog_layout, null);
        background = layout.findViewById(R.id.rl_background);
        cardView = layout.findViewById(R.id.card_view);
        ok = layout.findViewById(R.id.tv_ok);
        cancel = layout.findViewById(R.id.tv_cancel);

    }

    public void setCardRadius(int radius) {
        cardView.setRadius(radius);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                cardDialogCallback.onOKClick();
                dismiss();
                break;
            case R.id.tv_cancel:
                cardDialogCallback.onCancelClick();
                dismiss();
                break;
        }
    }

    public void show() {
        if (isNeedBlur)
            setApplyBlur();
        startAnim(true);
    }

    public void setNeedBlur(boolean needBlur) {
        isNeedBlur = needBlur;
    }

    public void dismiss() {
        startAnim(false);
    }

    private void setApplyBlur() {
        BlurCallable blurCallable = new BlurCallable();
        FutureTask<Drawable>futureTask = new FutureTask<>(blurCallable);
        blurCallable.setActivity((Activity) context);
        try {
            background.setBackground(futureTask.get(3000,TimeUnit.MILLISECONDS));
            if (futureTask.isDone())
                futureTask.cancel(false);
            blurCallable.setActivity(null);
        } catch (Exception e) {
            blurCallable.setActivity(null);
        }
    }

    private void startAnim(final boolean isShow) {
        int startTime, endTime;
        if (isShow) {
            startTime = 0;
            endTime = 1;
        } else {
            startTime = 1;
            endTime = 0;
        }
        valueAnimator = ValueAnimator.ofFloat(startTime, endTime);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                cardView.setAlpha((float) animation.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    background.setVisibility(View.GONE);
                    cardDialogCallback.onDismiss();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    public interface CardDialogCallback {
        void onOKClick();

        void onCancelClick();

        void onDismiss();
    }

    public static CardDialog createInstance(Context context, CardDialogCallback cardDialogCallback) {
        return new CardDialog(context, cardDialogCallback);
    }

}
