package com.jiakaiyang.library.easyform.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.jiakaiyang.library.easyform.R;

/**
 * Created by jia on 2018/1/8.
 * 简单的表格
 */

public class SimpleEFFormView extends EFFormView {
    private static final String TAG = "SimpleEFFormView";
    // 单元格的布局文件
    private @LayoutRes
    int cellLayout = -1;

    public SimpleEFFormView(Context context) {
        this(context, null, 0);
    }

    public SimpleEFFormView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleEFFormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SimpleEFFormView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleEFFormView);
        initParams(a);
        a.recycle();

        if (cellLayout == -1) {
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());

        int length = getRowCount() * getColumnCount();
        for (int i = 0; i < length; i++) {
            View child = inflater.inflate(cellLayout, null);
            addView(child);
        }
    }

    /**
     * 初始化配置参数
     */
    private void initParams(TypedArray array) {
        cellLayout = array.getResourceId(R.styleable.SimpleEFFormView_cellLayout, -1);
    }

    public int getCellLayout() {
        return cellLayout;
    }

    public void setCellLayout(int cellLayout) {
        this.cellLayout = cellLayout;
    }
}
