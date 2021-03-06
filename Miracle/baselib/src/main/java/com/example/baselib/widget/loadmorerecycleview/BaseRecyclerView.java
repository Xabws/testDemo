package com.example.baselib.widget.loadmorerecycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * RecyclerView的基类，封装了设置empty view，load failed view等基本功能
 */

public class BaseRecyclerView extends RecyclerView {
    private static final String TAG = BaseRecyclerView.class.getSimpleName();

    public BaseRecyclerView(Context context) {
        this(context, null);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private MyAdapterDataObserver mDataObserver;

    private void init() {
        mDataObserver = new MyAdapterDataObserver();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mDataObserver);
        }
    }

    private class MyAdapterDataObserver extends AdapterDataObserver {
        @Override
        public void onChanged() {
            onDataChange();
        }
    }

    protected void onDataChange() {
        //nothing to do
    }

    protected int findLastVisibleItemPosition() {
        int lastPosition;
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] into = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            lastPosition = -1;
            for (int v : into) {
                if (lastPosition < v) {
                    lastPosition = v;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else {
            View lastView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
            lastPosition = layoutManager.getPosition(lastView);
        }
        return lastPosition;
    }

    protected int findLastCompletelyVisibleItemPosition() {
        int lastPosition;
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            lastPosition = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] into = ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(null);
            lastPosition = -1;
            for (int v : into) {
                if (lastPosition < v) {
                    lastPosition = v;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        } else {
            View lastView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
            lastPosition = layoutManager.getPosition(lastView);
        }
        return lastPosition;
    }
}
