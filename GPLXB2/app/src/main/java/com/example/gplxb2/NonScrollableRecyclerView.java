package com.example.gplxb2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class NonScrollableRecyclerView extends RecyclerView {
    public NonScrollableRecyclerView(Context context) {
        super(context);
    }

    public NonScrollableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        // Trả về false để không xử lý cuộn
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // Trả về false để không xử lý cuộn
        return false;
    }
}
