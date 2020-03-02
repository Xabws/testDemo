package com.example.a1234.miracle.activity;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.AcitivityLinechartBinding;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * author: wsBai
 * date: 2018/12/4
 */
public class LineChartActivity extends BaseActivity {
    private AcitivityLinechartBinding acitivityLoadingBinding;
    private ArrayList mlist;

    @Override
    public int getContentViewId() {
        return R.layout.acitivity_linechart;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        acitivityLoadingBinding = DataBindingUtil.setContentView(this, getContentViewId());
        initLineChart();
        Glide.with(this)
                .load(R.drawable.loading_cat)
                .into(acitivityLoadingBinding.ivLoading);


    }

    private void initLineChart() {
        //设置左右上下不留边
        acitivityLoadingBinding.linechart.setViewPortOffsets(0, 0, 0, 0);
        acitivityLoadingBinding.linechart.setBackgroundColor(Color.rgb(104, 241, 175));

        // 关闭描述文字
        acitivityLoadingBinding.linechart.getDescription().setEnabled(false);
        // 设置可以放大和拖动
        acitivityLoadingBinding.linechart.setDragEnabled(true);
        acitivityLoadingBinding.linechart.setScaleEnabled(false);
        //开启放大和滚动
//        acitivityLoadingBinding.linechart.setPinchZoom(true);
//        acitivityLoadingBinding.linechart.setDrawGridBackground(false);
        //关闭点击高亮显示
        acitivityLoadingBinding.linechart.setHighlightPerTapEnabled(false);
        //关闭样式图例
        acitivityLoadingBinding.linechart.getLegend().setEnabled(false);
        //设置样式
        YAxis rightAxis = acitivityLoadingBinding.linechart.getAxisRight();

        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        YAxis leftAxis = acitivityLoadingBinding.linechart.getAxisLeft();
        //设置图表左边的y轴禁用
        leftAxis.setEnabled(false);
        //设置x轴
        XAxis xAxis = acitivityLoadingBinding.linechart.getXAxis();
        xAxis.setEnabled(true);
        //x轴设置为底部(图表内)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setGridColor(Color.WHITE);
        //开启x轴对应点虚线
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        setData();
        //在封装完数据之后，再设置它最大值。(用于自由滑动)
        //注意这两步顺序一定不能错！不然会闪退的。
        xAxis.setAxisMaximum(mlist.size() - 1);
        //显示一个屏幕所展示的最大个数
        acitivityLoadingBinding.linechart.setVisibleXRange(0, 7);
        acitivityLoadingBinding.linechart.invalidate();
    }

    private void notifyLineChart() {
        acitivityLoadingBinding.linechart.invalidate();
        //acitivityLoadingBinding.linechart.getAxisRight().setEnabled(false);
    }

    private void setData() {
        int count = 20;
        int range = 30;
        mlist = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range);
            mlist.add(new Entry(i, val, getResources().getDrawable(R.drawable.back)));
        }

        LineDataSet set1;

        if (acitivityLoadingBinding.linechart.getData() != null &&
                acitivityLoadingBinding.linechart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) acitivityLoadingBinding.linechart.getData().getDataSetByIndex(0);
            set1.setValues(mlist);
            set1.notifyDataSetChanged();
            acitivityLoadingBinding.linechart.getData().notifyDataChanged();
            acitivityLoadingBinding.linechart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(mlist, "图表。。。");
            set1.setDrawIcons(false);
            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.GRAY);
            set1.setCircleColor(Color.WHITE);

            // line thickness and point size
            set1.setLineWidth(3f);
            set1.setCircleRadius(6f);

            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);//设置曲线类型为圆滑曲线
            // draw points as solid circles
            set1.setDrawCircleHole(false);
            // 自定义图例
//            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(11f);

            // draw selection line as dashed
            //关闭设置为虚线
            set1.disableDashedLine();
            //关闭点击高亮显示虚线
            set1.disableDashedHighlightLine();
            //  set1.enableDashedHighlightLine(10f, 5f, 0f);

            //开启填充区域
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return acitivityLoadingBinding.linechart.getAxisLeft().getAxisMinimum();
                }
            });

            //设置曲线内部填充颜色或图片
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
              /*  Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bg);
                set1.setFillDrawable(drawable);*/
                set1.setFillColor(Color.CYAN);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            acitivityLoadingBinding.linechart.setData(data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 保存折线图
     *
     * @param chart
     * @param name
     */
    protected void saveToGallery(Chart chart, String name) {
        if (chart.saveToGallery(name + "_" + System.currentTimeMillis(), 70))
            Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                    .show();
    }

}
