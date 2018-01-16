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
import com.jiakaiyang.library.easyform.core.EFCell;
import com.jiakaiyang.library.easyform.core.EFNode;

/**
 * Created by jia on 2018/1/8.
 * 简单的表格
 */

public class SimpleEFFormView extends EFFormView {
    private static final String TAG = "SimpleEFFormView";
    private static final int TAG_CELL = 100;
    // 单元格的布局文件
    private @LayoutRes
    int cellLayout = -1;

    private int cellWidth;
    private int cellHeight;

    private EFCell[][] mCells;

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

        createCells();
    }

    /**
     * 初始化配置参数
     */
    private void initParams(TypedArray array) {
        cellLayout = array.getResourceId(R.styleable.SimpleEFFormView_cellLayout, -1);
    }


    private void createCells() {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        mCells = new EFCell[columnCount][rowCount];
        EFNode[][] nodes = getNodes();

        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                EFCell efCell = new EFCell(j, i);
                View cellView = inflater.inflate(cellLayout, this, true);
                // set tag
                cellView.setTag(efCell);
                efCell.setRealCellView(cellView);
                efCell.setNodes(nodes);
                mCells[j][i] = efCell;
            }
        }
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

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int originX = getPaddingLeft();
        int originY = getPaddingTop();
        int[] originPoint = {originX, originY};

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            EFCell efCell = (EFCell) child.getTag();
            efCell.layout(originPoint, cellWidth, cellHeight);
        }
    }

    public int getCellLayout() {
        return cellLayout;
    }

    public void setCellLayout(int cellLayout) {
        this.cellLayout = cellLayout;
    }
}
