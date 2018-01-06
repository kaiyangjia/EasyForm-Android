package com.jiakaiyang.library.easyform.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import com.jiakaiyang.library.easyform.core.EFNode;

import java.util.Set;

/**
 * Created by jia on 2018/1/5.
 * This is a dev EFForm for v2.0. This will be replace the old EFFormView when it be done.
 */

public class DemoEFFormView extends ViewGroup {
    private static final String TAG = "DemoEFFormView";


    /**
     * brow form the old one.
     *
     * @Setter
     * @Getter private int frameColor; //边框颜色
     * @Setter
     * @Getter private int frameWidth; //边框宽度
     * @Setter
     * @Getter private int dividerColor; //分隔线颜色
     * @Setter
     * @Getter private int dividerWidth;  //分隔线宽度
     * @Setter
     * @Getter private int rowCount; //行数量
     * @Setter
     * @Getter private int columnCount; //列数量
     * @Setter
     * @Getter private ITEM_LAYOUT itemLayoutHorizontal; //水平方向的排列方式
     * @Setter
     * @Getter private ITEM_LAYOUT itemLayoutVertical;  //竖直方向的排列方式
     * @Setter
     * @Getter private int itemWidth;
     * @Setter
     * @Getter private int itemHeight;
     * @Setter
     * @Getter private ITEM_GRAVITY itemGravity;
     * @Setter
     * @Getter private int formItemTextSize;
     * @Setter
     * @Getter private int formItemTextColor;
     * @Setter
     * @Getter private int itemLayoutTextRes;
     * @Setter
     * @Getter private int itemLayoutEditRes;
     * @Setter
     * @Getter private int itemLayoutImageRes;
     * @Setter
     * @Getter private int itemLayoutCustomRes;
     * @Setter
     * @Getter private List<Map<String, Object>> formTitleNames;
     * @Setter
     * @Getter private boolean isFormInput;
     * @Setter
     * @Getter private List<Integer> inputRow; // 是输入框的行数
     * @Setter
     * @Getter private List<Map<String, Object>> data;
     * @Setter
     * @Getter private boolean dialogWhenOnClciked = false;
     * //所有单元格的链表
     * @Setter
     * @Getter private List<BorderLinearLayout> formItemList;
     * //所有行的链表
     * @Setter
     * @Getter private List<BorderLinearLayout> formRowList;
     * //表格以及子表格的所有项
     * @Setter
     * @Getter private List<BorderLinearLayout> allItemList;
     * @Setter
     * @Getter private OnItemClickListener onItemClickListener;
     */

    /* START------ this fields for the class ------*/
    // nodes for this form.
    private EFNode[][] mNodes;
    //
    private float[] drawDividerLines;
    private Paint dividerPaint;
    private boolean drawn = false;
    private Set<Line> drawnDividers;
    /* END------ this fields for the class ------*/


    /* ------<<< the config from xml file for the class ------*/
    private int rowCount = 2;
    private int columnCount = 3;

    @ColorInt
    private int dividerColor = Color.RED;
    // the width of divider, default value is 2 pixels
    private int dividerWidth = 10;
    /* ------ the config from xml file for the class >>>------*/


    public DemoEFFormView(Context context) {
        this(context, null, 0);
    }

    public DemoEFFormView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DemoEFFormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DemoEFFormView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        createNodes();
        createDividerPaint();
    }

    /* ------START methods form super class ------*/
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!drawn) {
            drawDividers(canvas);
        }
        drawn = true;
    }

    /* ------END methods form super class ------*/


    /* ------START private methods ------*/

    // TODO: 2018/1/6 解决中间的线有重复绘制的问题
    private void drawDividers(Canvas canvas) {
        Log.i(TAG, "drawDividers: ");
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        int cellHeight = height / rowCount;
        int cellWidth = width / columnCount;

        for (EFNode[] nodes : mNodes) {
            for (EFNode node : nodes) {
                Log.d(TAG, "dispatchDraw: startX: "
                        + node.getIndexX()
                        + ", startY : " + node.getIndexY() + ", node == " + node.toString());

                // 单向绘制就行，否则会重复
                /*if (node.isShouldDrawLeft()) {
                    float[] leftLine = EFNode.calculateDivider(node
                            , EFNode.DIRECTION_LEFT
                            , cellWidth, cellHeight);

                    canvas.drawLines(leftLine, dividerPaint);
                }*/

                /*if (node.isShouldDrawTop()) {
                    float[] topLine = EFNode.calculateDivider(node
                            , EFNode.DIRECTION_TOP
                            , cellWidth, cellHeight);

                    canvas.drawLines(topLine, dividerPaint);
                }*/

                if (node.isShouldDrawRight()) {
                    float[] rightLine = EFNode.calculateDivider(node
                            , EFNode.DIRECTION_RIGHT
                            , cellWidth, cellHeight);

                    canvas.drawLines(rightLine, dividerPaint);
                }

                if (node.isShouldDrawDown()) {
                    float[] downLine = EFNode.calculateDivider(node
                            , EFNode.DIRECTION_DOWM
                            , cellWidth, cellHeight);

                    canvas.drawLines(downLine, dividerPaint);
                }
            }
        }
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

    private void calculateLines(int formWidth, int formHeight) {
        int cellWidth = (formWidth - (dividerWidth * (columnCount + 1)))
                / (columnCount - 1);
        int cellHeight = (formHeight - (dividerWidth * (rowCount + 1)))
                / (rowCount - 1);

    }

    /* ------END private methods ------*/


    /**
     * 表格中的一条线
     */
    class Line {
        private int startX;
        private int startY;

        public Line(int startX, int startY) {
            this.startX = startX;
            this.startY = startY;
        }

        @Override
        public boolean equals(Object obj) {
            if ((obj instanceof Line)) {
                return false;
            }

            Line target = (Line) obj;
            return (target.getStartX() == this.getStartX())
                    && (target.getStartY() == this.getStartY());
        }


        @Override
        public int hashCode() {
            return (31 * startX + 43)
                    + (31 * startY + 3);
        }

        public int getStartX() {
            return startX;
        }

        public void setStartX(int startX) {
            this.startX = startX;
        }

        public int getStartY() {
            return startY;
        }

        public void setStartY(int startY) {
            this.startY = startY;
        }
    }
}
