package com.jiakaiyang.library.easyform.core.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by jia on 2018/1/8.
 */

public class SimpleEFFormView extends EFFormView {
    private static final String TAG = "SimpleEFFormView";
    // 单元格的布局文件
    private @LayoutRes
    int cellLayout = -1;

    public SimpleEFFormView(Context context) {
        super(context);
    }

    public SimpleEFFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleEFFormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SimpleEFFormView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onInit() {
        super.onInit();
        initParams();

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
    private void initParams() {

    }

    public int getCellLayout() {
        return cellLayout;
    }

    public void setCellLayout(int cellLayout) {
        this.cellLayout = cellLayout;
    }
}
