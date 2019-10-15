package com.example.baselib.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.baselib.R;
import com.example.baselib.databinding.CardDialogLayoutBinding;

/**
 * author: wsBai
 * date: 2018/11/20
 */
public class CardDialog extends Dialog {
    private CardDialogLayoutBinding cardDialogLayoutBinding;
    private Context context;
    private ClickInterFace clickInterFace;

    public CardDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public CardDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardDialogLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.card_dialog_layout, null, false);
        cardDialogLayoutBinding.setDialog(this);
        getWindow().getDecorView().setBackground(null);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.0f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().getDecorView().setPadding(48, 0, 48, 0);
    }

    public void onClick(View view) {
        if (view.getId() == cardDialogLayoutBinding.tvOk.getId()) {
            clickInterFace.ok();
        } else if (view.getId() == cardDialogLayoutBinding.tvCancel.getId()) {
            clickInterFace.cancel();
        }
        dismiss();
    }


    public void setClickInterFace(ClickInterFace clickInterFace) {
        this.clickInterFace = clickInterFace;
    }

    public interface ClickInterFace {
        void ok();

        void cancel();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
