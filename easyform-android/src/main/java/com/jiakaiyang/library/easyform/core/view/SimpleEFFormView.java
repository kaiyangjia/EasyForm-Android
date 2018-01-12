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
import com.jiakaiyang.library.easyform.core.EFNode;

/**
 * Created by jia on 2018/1/8.
 * 简单的表格
 */

public class SimpleEFFormView extends EFFormView {
    private static final String TAG = "SimpleEFFormView";
    private static final int TAG_NODE = 100;
    // 单元格的布局文件
    private @LayoutRes
    int cellLayout = -1;

    private int cellWidth;
    private int cellHeight;

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

        // TODO: 2018/1/12 this is wrong
        EFNode[][] efNodes = getNodes();
        for (EFNode[] nodes : efNodes) {
            for (EFNode node : nodes) {
                View child = inflater.inflate(cellLayout, null);
                child.setTag(TAG_NODE, node);
                addView(child);
            }
        }
    }

    /**
     * 初始化配置参数
     */
    private void initParams(TypedArray array) {
        cellLayout = array.getResourceId(R.styleable.SimpleEFFormView_cellLayout, -1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        cellWidth = width / getColumnCount();
        cellHeight = height / getRowCount();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            child.measure(
                    MeasureSpec.makeMeasureSpec(cellWidth,
                            MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(cellHeight,
                            MeasureSpec.EXACTLY));
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int originX = getPaddingLeft();
        int originY = getPaddingTop();
        float[] originPoint = {originX, originY};

        float[] returnValue = new float[2];

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            EFNode childNode = (EFNode) child.getTag(TAG_NODE);

            EFNode.calculateCoordinateByIndex(originPoint
                    , childNode.getIndexX()
                    , childNode.getIndexY()
                    , cellWidth
                    , cellHeight
                    , returnValue);


        }
    }

    public int getCellLayout() {
        return cellLayout;
    }

    public void setCellLayout(int cellLayout) {
        this.cellLayout = cellLayout;
    }
}
