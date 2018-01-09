package com.jiakaiyang.library.easyform.core.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.jiakaiyang.library.easyform.core.EFFormController;

/**
 * Created by jia on 2018/1/8.
 */

public class ComplexEFFormView extends EFFormView {
    private static final String TAG = "ComplexEFFormView";

    private EFFormController formController;

    public ComplexEFFormView(Context context) {
        super(context);
    }

    public ComplexEFFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ComplexEFFormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ComplexEFFormView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setFormController(EFFormController formController) {
        this.formController = formController;
    }
}
