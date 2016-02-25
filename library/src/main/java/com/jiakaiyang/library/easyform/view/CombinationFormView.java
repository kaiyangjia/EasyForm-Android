package com.jiakaiyang.library.easyform.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by kaiyangjia on 2016/2/25.
 * 该类是多个EFFormView 组合而成的组合表格，继承自EFFormView。与EFFormView不同的是
 * 该View的单元格是其他的 EFFormView 或者 基础View
 */
public class CombinationFormView extends EFFormView{
    public CombinationFormView(Context context) {
        super(context);
    }

    public CombinationFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
