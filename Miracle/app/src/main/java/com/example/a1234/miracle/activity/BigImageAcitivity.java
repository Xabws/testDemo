package com.example.a1234.miracle.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.customview.CardDialog;
import com.example.a1234.miracle.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BigImageAcitivity extends BaseActivity {
    @BindView(R.id.iv_image)
    ImageView ivImage;

    @Override
    public int getContentViewId() {
        return R.layout.acitivity_bigimage;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("image");
        if (url != null && !TextUtils.isEmpty(url))
            Picasso.get().load(url).into(ivImage);
        ivImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                savePics(ivImage.getDrawable());
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_image)
    public void onViewClicked() {
        finish();
    }

    /**
     * 长按保存图片
     */
    private void savePics(Drawable drawable){
        if (drawable!=null){
            // 取 drawable 的长宽
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();

            // 取 drawable 的颜色格式
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                    : Bitmap.Config.RGB_565;
            // 建立对应 bitmap
           final Bitmap bitmap = Bitmap.createBitmap(w, h, config);
            // 建立对应 bitmap 的画布
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            // 把 drawable 内容画到画布中
            drawable.draw(canvas);
            CardDialog cardDialog = new CardDialog(BigImageAcitivity.this);
            cardDialog.show();
            cardDialog.setClickInterFace(new CardDialog.ClickInterFace() {
                @Override
                public void ok() {
                    ImageUtils.saveBmp2Gallery(bitmap,System.currentTimeMillis()+"");
                }

                @Override
                public void cancel() {

                }
            });
        }
    }
}
