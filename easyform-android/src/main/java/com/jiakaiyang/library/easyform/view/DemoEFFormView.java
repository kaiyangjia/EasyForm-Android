package com.jiakaiyang.library.easyform.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.jiakaiyang.library.easyform.core.EFNode;

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
    /* END------ this fields for the class ------*/


    /* ------<<< the config from xml file for the class ------*/
    private int rowCount = 2;
    private int columnCount = 3;

    @ColorInt
    private int dividerColor = Color.RED;
    /* ------ the config from xml file for the class >>>------*/


    public DemoEFFormView(Context context) {
        super(context);
    }

    public DemoEFFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoEFFormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DemoEFFormView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {

    }

    /* ------START methods form super class ------*/
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        // TODO: 2018/1/5 Draw the dividers
    }

    /* ------END methods form super class ------*/


    /* ------START private methods ------*/

    /**
     * create mNodes by the row count and column count.
     * Each of them changed, should create mNodes again.
     */
    private void createNodes() {
        mNodes = new EFNode[columnCount][rowCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                EFNode efNode = new EFNode();

                mNodes[j][i] = efNode;
            }
        }
    }
    /* ------END private methods ------*/

}
