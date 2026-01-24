package com.example.cooktogether;

import android.content.Context;
import android.util.AttributeSet;

public class WrapContentRecyclerView extends androidx.recyclerview.widget.RecyclerView {

    public WrapContentRecyclerView(Context context) {
        super(context);
    }

    public WrapContentRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        // Make height "as big as needed"
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST
        );
        super.onMeasure(widthSpec, expandSpec);
    }
}
