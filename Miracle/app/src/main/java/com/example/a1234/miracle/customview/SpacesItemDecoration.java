package com.example.a1234.miracle.customview;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int margin;

    public SpacesItemDecoration(Context context) {
        margin = 10;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0 
        if (parent.getChildLayoutPosition(view) < 3) {
            outRect.set(margin / 2, 0, margin / 2, 0);
        } else {
            outRect.set(margin / 2, margin, margin / 2, 0);
        }
    }
}

