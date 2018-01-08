package com.jiakaiyang.library.easyform.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import com.jiakaiyang.library.easyform.core.EFFormController;
import com.jiakaiyang.library.easyform.core.EFNode;


/**
 * Created by jia on 2018/1/5.
 * This is a dev EFForm for v2.0. This will be replace the old EFFormView when it be done.
 * <p>
 * This class should not be use. you should use the child classes.
 */

public abstract class EFFormView extends ViewGroup {
    private static final String TAG = "DemoEFFormView";


    /**
     * brow form the old one.
     * <p>
     * private ITEM_GRAVITY itemGravity;
     * <p>
     * private int formItemTextSize;
     * private int formItemTextColor;
     * private int itemLayoutTextRes;
     * private int itemLayoutEditRes;
     * private int itemLayoutImageRes;
     * private int itemLayoutCustomRes;
     * private List<Map<String, Object>> formTitleNames;
     * private boolean isFormInput;
     * private List<Integer> inputRow; // 是输入框的行数
     * private List<Map<String, Object>> data;
     * private boolean dialogWhenOnClciked = false;
     * //所有单元格的链表
     * private List<BorderLinearLayout> formItemList;
     * //所有行的链表
     * private List<BorderLinearLayout> formRowList;
     * //表格以及子表格的所有项
     * private List<BorderLinearLayout> allItemList;
     * private OnItemClickListener onItemClickListener;
     */

    /* START------ this fields for the class ------*/
    // nodes for this form.
    private EFNode[][] mNodes;
    private Paint dividerPaint;
    private Paint framePaint;
    private boolean drawn = false;
    /* END------ this fields for the class ------*/


    /* ------<<< the config from xml file for the class ------*/
    // 行数，列数
    private int rowCount = 2;
    private int columnCount = 3;

    private int frameColor; //边框颜色
    private int frameWidth; //边框宽度

    // item content gravity. value from Gravity
    private int itemGravity;

    @ColorInt
    private int dividerColor = Color.RED;
    // the width of divider, default value is 2 pixels
    private int dividerWidth = 10;
    /* ------ the config from xml file for the class >>>------*/


    public EFFormView(Context context) {
        this(context, null, 0);
    }

    public EFFormView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EFFormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EFFormView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /* ------START init methods ------*/
    private void init() {
        createNodes();
        createDividerPaint();
        createFramePaint();

        initParams();

        resetPadding();
    }

    protected void onInit() {

    }

    /**
     * 初始化xml参数
     */
    private void initParams() {

    }

    /**
     * 重新设置 padding ，添加边框的适配
     */
    private void resetPadding() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        int halfDivider = dividerWidth >> 1;
        paddingLeft += halfDivider;
        paddingTop += halfDivider;

        setPadding(paddingLeft, paddingTop, getPaddingRight(), getPaddingBottom());
    }

    /* ------END init methods ------*/


    /* ------START methods form super class ------*/

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        // 保证有一个最小的 padding值可以用来容纳边框的绘制
        int halfDivider = dividerWidth >> 1;

        left = Math.max(left, halfDivider);
        top = Math.max(top, halfDivider);
        right = Math.max(right, halfDivider);
        bottom = Math.max(bottom, halfDivider);
        super.setPadding(left, top, right, bottom);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!drawn) {
            drawDividers(canvas);
            drawFrame(canvas);
        }

        drawn = true;
    }

    /* ------END methods form super class ------*/


    /* ------START private methods ------*/

    // TODO: 2018/1/8 把 drawLines 的形式替换成drawRect
    private void drawDividers(Canvas canvas) {
        Log.i(TAG, "drawDividers: ");
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        int originX = getPaddingLeft();
        int originY = getPaddingTop();
        float[] originPoint = {originX, originY};

        int cellHeight = (height - (rowCount + 1) * dividerWidth) / rowCount;
        int cellWidth = (width - (columnCount + 1) * dividerWidth) / columnCount;

        for (EFNode[] nodes : mNodes) {
            for (EFNode node : nodes) {
                Log.d(TAG, "dispatchDraw: startX: "
                        + node.getIndexX()
                        + ", startY : " + node.getIndexY() + ", node == " + node.toString());

                // 单向绘制（只向右、向下）就行，否则会重复

                if (node.getIndexY() != 0
                        && node.getIndexY() != rowCount) {
                    if (node.isShouldDrawRight()) {
                        float[] rightLine = EFNode.calculateDivider(node
                                , originPoint
                                , EFNode.DIRECTION_RIGHT
                                , cellWidth, cellHeight);
/*
                    int offsetWidth = dividerWidth;

                    int minIndex = 0;
                    int maxIndex = 2;
                    if (rightLine[0] > rightLine[2]) {
                        minIndex = 2;
                        maxIndex = 0;
                    }

                    // 处理四个角的小方块
                    if ((node.getIndexX() == 0 && node.getIndexY() == 0)
                            || (node.getIndexX() == 0 && node.getIndexY() == rowCount)) {
                        // 左上角 , 左下角
                        Log.i(TAG, "drawDividers: 左上角 , 左下角");
                        rightLine[minIndex] -= offsetWidth;
                    }

                    if ((node.getIndexX() == (columnCount - 1) && node.getIndexY() == 0)
                            || (node.getIndexX() == (columnCount - 1) && node.getIndexY() == rowCount)) {
                        // 右上角左边 , 右下角左边
                        Log.i(TAG, "drawDividers: 右上角左边 , 右下角左边");
                        rightLine[maxIndex] += (offsetWidth >> 2);
                    }*/

                        canvas.drawLines(rightLine, dividerPaint);
                    }
                }

                if (node.getIndexX() != 0
                        && node.getIndexX() != columnCount) {
                    if (node.isShouldDrawDown()) {
                        float[] downLine = EFNode.calculateDivider(node
                                , originPoint
                                , EFNode.DIRECTION_DOWM
                                , cellWidth, cellHeight);

                        canvas.drawLines(downLine, dividerPaint);
                    }
                }
            }
        }
    }


    private void drawFrame(Canvas canvas) {
        Path path = new Path();

        // todo 保存这些常用变量
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        int originX = getPaddingLeft();
        int originY = getPaddingTop();
        float[] originPoint = {originX, originY};

        int cellHeight = (height - (rowCount + 1) * dividerWidth) / rowCount;
        int cellWidth = (width - (columnCount + 1) * dividerWidth) / columnCount;


        float[] startNode = new float[2];
        float[] coordinateNode = new float[2];

        EFNode frameNode = mNodes[0][0];
        EFNode.calculateCoordinateByIndex(
                originPoint
                , frameNode.getIndexX()
                , frameNode.getIndexY()
                , cellWidth
                , cellHeight
                , startNode);
        path.moveTo(startNode[0], startNode[1]);

        frameNode = mNodes[columnCount][0];
        EFNode.calculateCoordinateByIndex(
                originPoint
                , frameNode.getIndexX()
                , frameNode.getIndexY()
                , cellWidth
                , cellHeight
                , coordinateNode);
        path.lineTo(coordinateNode[0], coordinateNode[1]);

        frameNode = mNodes[columnCount][rowCount];
        EFNode.calculateCoordinateByIndex(
                originPoint
                , frameNode.getIndexX()
                , frameNode.getIndexY()
                , cellWidth
                , cellHeight
                , coordinateNode);
        path.lineTo(coordinateNode[0], coordinateNode[1]);

        frameNode = mNodes[0][rowCount];
        EFNode.calculateCoordinateByIndex(
                originPoint
                , frameNode.getIndexX()
                , frameNode.getIndexY()
                , cellWidth
                , cellHeight
                , coordinateNode);
        path.lineTo(coordinateNode[0], coordinateNode[1]);
        path.close();

        canvas.drawPath(path, framePaint);
    }

    /**
     * create mNodes by the row count and column count.
     * Each of them changed, should create mNodes again.
     */
    private void createNodes() {
        int nodeColumnCount = columnCount + 1;
        int nodeRowCount = rowCount + 1;


        mNodes = new EFNode[nodeColumnCount][nodeRowCount];

        for (int i = 0; i < nodeRowCount; i++) {
            for (int j = 0; j < nodeColumnCount; j++) {
                EFNode efNode = new EFNode(j, i, nodeColumnCount, nodeRowCount);
                mNodes[j][i] = efNode;
            }
        }
    }

    private void createDividerPaint() {
        dividerPaint = new Paint();
        dividerPaint.setColor(dividerColor);
        dividerPaint.setStrokeWidth(dividerWidth);
    }

    private void createFramePaint() {
        framePaint = new Paint();
        framePaint.setStrokeWidth(dividerWidth);
        framePaint.setColor(Color.GREEN);
        framePaint.setStyle(Paint.Style.STROKE);
    }

    private void calculateLines(int formWidth, int formHeight) {
        int cellWidth = (formWidth - (dividerWidth * (columnCount + 1)))
                / (columnCount - 1);
        int cellHeight = (formHeight - (dividerWidth * (rowCount + 1)))
                / (rowCount - 1);

    }

    /* ------END private methods ------*/

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
}
