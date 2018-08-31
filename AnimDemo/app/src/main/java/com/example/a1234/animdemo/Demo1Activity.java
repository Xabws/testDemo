package com.example.a1234.animdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1234.animdemo.utils.ContentUtil;

/**
 * Created by a1234 on 2018/8/16.
 */

public class Demo1Activity extends Activity {
    private DrawerView bottomDrawerView;
    private TextView tv_content;
    TextView draw_handle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        bottomDrawerView =  findViewById(R.id.vertical_drawer);
        draw_handle = findViewById(R.id.draw_handle);
        tv_content = findViewById(R.id.tv_content);
        bottomDrawerView.setHandlerHeight( ContentUtil.dip2px(Demo1Activity.this, 160));
        draw_handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDrawerView.openDrawer();
                Log.d("sssss","draw_handle");
            }
        });
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sssss","content");
            }
        });
    }

}
