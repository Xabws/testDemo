package com.example.a1234.miracle.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a1234.miracle.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: wsBai
 * date: 2018/11/20
 */
public class CardDialog extends Dialog {
    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.iv)
    RelativeLayout iv;
    @BindView(R.id.card_view)
    CardView cardView;
    ClickInterFace clickInterFace;

    public CardDialog(@NonNull Context context) {
        super(context);
    }

    public CardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public CardDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_dialog_layout);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        getWindow().getDecorView().setBackground(null);
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.dimAmount=0.0f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().getDecorView().setPadding(48, 0, 48, 0);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(tvOk);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(tvCancel);
            }
        });

    }

    @OnClick({R.id.tv_title, R.id.tv_message, R.id.tv_ok, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                break;
            case R.id.tv_message:
                break;
            case R.id.tv_ok:
                clickInterFace.ok();
                dismiss();
                break;
            case R.id.tv_cancel:
                clickInterFace.cancel();
                dismiss();
                break;
        }
    }

    public void setClickInterFace(ClickInterFace clickInterFace){
        this.clickInterFace=clickInterFace;
    }

    public interface ClickInterFace{
        void ok();
        void cancel();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        unbinder.unbind();
    }
}
