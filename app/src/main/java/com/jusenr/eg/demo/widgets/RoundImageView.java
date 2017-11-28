package com.jusenr.eg.demo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.jusenr.eg.demo.R;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/24
 * Time       : 11:11
 * Project    ：RX_Demo.
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    private float width, height;
    private int imageRadius;
    private int imagePosition;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0);
        imageRadius = (int) array.getDimension(R.styleable.RoundImageView_imageRadius, 4);
        imageRadius = dp2px(context, imageRadius);
        imagePosition = array.getInt(R.styleable.RoundImageView_imagePosition, 0);
        array.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width > imageRadius && height > imageRadius) {
            Path path = new Path();
            switch (imagePosition) {
                case 0://all
                    path.moveTo(imageRadius, 0);
                    path.lineTo(width - imageRadius, 0);
                    path.quadTo(width, 0, width, imageRadius);
                    path.lineTo(width, height - imageRadius);
                    path.quadTo(width, height, width - imageRadius, height);
                    path.lineTo(imageRadius, height);
                    path.quadTo(0, height, 0, height - imageRadius);
                    path.lineTo(0, imageRadius);
                    path.quadTo(0, 0, imageRadius, 0);
                    break;
                case 1://left
                    path.moveTo(imageRadius, 0);
                    path.lineTo(width, 0);
                    path.lineTo(width, height);
                    path.lineTo(imageRadius, height);
                    path.quadTo(0, height, 0, height - imageRadius);
                    path.lineTo(0, imageRadius);
                    path.quadTo(0, 0, imageRadius, 0);
                    break;
                case 2://top
                    path.moveTo(imageRadius, 0);
                    path.lineTo(width - imageRadius, 0);
                    path.quadTo(width, 0, width, imageRadius);
                    path.lineTo(width, height);
                    path.lineTo(0, height);
                    path.lineTo(0, imageRadius);
                    path.quadTo(0, 0, imageRadius, 0);
                    break;
                case 3://right
                    path.lineTo(width - imageRadius, 0);
                    path.quadTo(width, 0, width, imageRadius);
                    path.lineTo(width, height - imageRadius);
                    path.quadTo(width, height, width - imageRadius, height);
                    path.lineTo(0, height);
                    path.lineTo(0, 0);
                    break;
                case 4://bottom
                    path.lineTo(width, 0);
                    path.lineTo(width, height - imageRadius);
                    path.quadTo(width, height, width - imageRadius, height);
                    path.lineTo(imageRadius, height);
                    path.quadTo(0, height, 0, height - imageRadius);
                    path.lineTo(0, 0);
                    break;
            }
            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }

    /**
     * DP turn PX
     *
     * @param context context
     * @param dpValue dp value
     * @return px value
     */
    private int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

}
