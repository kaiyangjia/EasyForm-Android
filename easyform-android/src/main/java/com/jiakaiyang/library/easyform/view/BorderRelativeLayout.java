package com.jiakaiyang.library.easyform.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.jiakaiyang.library.easyform.R;


/**
 * Created by kaiyangjia on 2016/3/2.
 */
public class BorderRelativeLayout extends RelativeLayout {

    private Context context;
    private final static int DEFAULT_BORDER_SIZE = 1;
    private int mBorderColor = Color.parseColor("#c1c1c1");
    private int mBoderLeftSize = DEFAULT_BORDER_SIZE;
    private int mBoderTopSize = DEFAULT_BORDER_SIZE;
    private int mBoderRightSize = DEFAULT_BORDER_SIZE;
    private int mBoderBottomSize = DEFAULT_BORDER_SIZE;
    private boolean isBorderEnable = true;
    private boolean checked = false;
    private int colorChecked;
    private int colorUnchecked;
    private int textColorChecked;
    private int textColorUnchecked;
    private Paint borderPaint;

    public BorderRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.BorderLinearLayout, 0, 0);
        mBorderColor = a.getColor(R.styleable.BorderLinearLayout_border_color,
                Color.RED);
        mBoderLeftSize = a.getDimensionPixelSize(
                R.styleable.BorderLinearLayout_border_left_size,
                DEFAULT_BORDER_SIZE);
        mBoderTopSize = a.getDimensionPixelSize(
                R.styleable.BorderLinearLayout_border_top_size,
                DEFAULT_BORDER_SIZE);
        mBoderRightSize = a.getDimensionPixelSize(
                R.styleable.BorderLinearLayout_border_right_size,
                DEFAULT_BORDER_SIZE);
        mBoderBottomSize = a.getDimensionPixelSize(
                R.styleable.BorderLinearLayout_border_bottom_size,
                DEFAULT_BORDER_SIZE);
        isBorderEnable = a.getBoolean(
                R.styleable.BorderLinearLayout_border_enable, true);
        a.recycle();
        init();
    }

    public BorderRelativeLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        borderPaint = new Paint();
        borderPaint.setColor(mBorderColor);

        setColorChecked(getResources().getColor(R.color.light_blue));
        setColorUnchecked(getResources().getColor(R.color.light_gray));
        setTextColorChecked(getResources().getColor(R.color.default_text_color_checked));
        setTextColorUnchecked(getResources().getColor(R.color.default_text_color_unchecked));
    }

    public void setBorderSize(int left, int top, int right, int bottom) {
        mBoderLeftSize = left;
        mBoderTopSize = top;
        mBoderRightSize = right;
        mBoderBottomSize = bottom;
        invalidate();
    }

    public void setBorderColor(int color) {
        mBorderColor = color;
        borderPaint.setColor(mBorderColor);
        invalidate();
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            setBackgroundColor(getColorChecked());
        } else {
            setBackgroundColor(getColorUnchecked());
        }
    }

    public boolean isBorderEnable() {
        return isBorderEnable;
    }

    public void setBorderEnable(boolean enable) {
        if (enable == isBorderEnable) {
            return;
        }
        isBorderEnable = enable;
        invalidate();
    }

    private Rect tempRect = new Rect();

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!isBorderEnable) {
            return;
        }
        // draw border
        int vw = getWidth();
        int vh = getHeight();
        if (mBoderLeftSize > 0) {
            tempRect.setEmpty();
            tempRect.set(0, 0, mBoderLeftSize, vh);
            canvas.drawRect(tempRect, borderPaint);
        }
        if (mBoderTopSize > 0) {
            tempRect.setEmpty();
            tempRect.set(0, 0, vw, mBoderTopSize);
            canvas.drawRect(tempRect, borderPaint);
        }
        if (mBoderRightSize > 0) {
            tempRect.setEmpty();
            tempRect.set(vw - mBoderRightSize, 0, vw, vh);
            canvas.drawRect(tempRect, borderPaint);
        }
        if (mBoderBottomSize > 0) {
            tempRect.setEmpty();
            tempRect.set(0, vh - mBoderBottomSize, vw, vh);
            canvas.drawRect(tempRect, borderPaint);
        }
    }

    public int getmBorderColor() {
        return mBorderColor;
    }

    public void setmBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
    }

    public int getmBoderLeftSize() {
        return mBoderLeftSize;
    }

    public void setmBoderLeftSize(int mBoderLeftSize) {
        this.mBoderLeftSize = mBoderLeftSize;
    }

    public int getmBoderTopSize() {
        return mBoderTopSize;
    }

    public void setmBoderTopSize(int mBoderTopSize) {
        this.mBoderTopSize = mBoderTopSize;
    }

    public int getmBoderRightSize() {
        return mBoderRightSize;
    }

    public void setmBoderRightSize(int mBoderRightSize) {
        this.mBoderRightSize = mBoderRightSize;
    }

    public int getmBoderBottomSize() {
        return mBoderBottomSize;
    }

    public void setmBoderBottomSize(int mBoderBottomSize) {
        this.mBoderBottomSize = mBoderBottomSize;
    }

    public boolean isChecked() {
        return checked;
    }

    public int getColorChecked() {
        return colorChecked;
    }

    public void setColorChecked(int colorChecked) {
        this.colorChecked = colorChecked;
    }

    public int getColorUnchecked() {
        return colorUnchecked;
    }

    public void setColorUnchecked(int colorUnchecked) {
        this.colorUnchecked = colorUnchecked;
    }

    public int getTextColorChecked() {
        return textColorChecked;
    }

    public void setTextColorChecked(int textColorChecked) {
        this.textColorChecked = textColorChecked;
    }

    public int getTextColorUnchecked() {
        return textColorUnchecked;
    }

    public void setTextColorUnchecked(int textColorUnchecked) {
        this.textColorUnchecked = textColorUnchecked;
    }

    public Paint getBorderPaint() {
        return borderPaint;
    }

    public void setBorderPaint(Paint borderPaint) {
        this.borderPaint = borderPaint;
    }

    public Rect getTempRect() {
        return tempRect;
    }

    public void setTempRect(Rect tempRect) {
        this.tempRect = tempRect;
    }
}
