package com.example.a1234.animdemo;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.a1234.animdemo.utils.blurkit.BlurKit;

public class MainActivity extends AppCompatActivity {
    AnimView animView;
    AnimInterpolationView animInterpolationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animView = (AnimView) findViewById(R.id.animview);
        animInterpolationView = (AnimInterpolationView) findViewById(R.id.animinter);
        BlurKit.init(this);
        init();
    }

    private void init() {
        animView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Demo1Activity.class));
            }
        });
        animInterpolationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Demo2Activity.class));
            }
        });
    }
}
