package com.jiakaiyang.library.easyform.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jia on 2018/1/4.
 * 单元格，使用代理模式
 */

public class EFFormCellView extends View {
    private View realCellView;


    public EFFormCellView(Context context) {
        super(context);
    }

    public EFFormCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EFFormCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EFFormCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
