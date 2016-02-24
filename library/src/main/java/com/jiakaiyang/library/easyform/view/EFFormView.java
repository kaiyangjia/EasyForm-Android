package com.jiakaiyang.library.easyform.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiakaiyang.library.easyform.R;
import com.jiakaiyang.library.easyform.tools.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by kaiyangjia on 2016/2/23.
 */
public class EFFormView extends BorderLinearLayout {
    private Context context;
    @Setter
    @Getter
    private int frameColor; //边框颜色
    @Setter
    @Getter
    private int frameWidth; //边框宽度
    @Setter
    @Getter
    private int dividerColor; //分隔线颜色
    @Setter
    @Getter
    private int dividerWidth;  //分隔线宽度
    @Setter
    @Getter
    private int rowCount; //行数量
    @Setter
    @Getter
    private int columnCount; //列数量
    @Setter
    @Getter
    private ITEM_LAYOUT itemLayoutHorizontal; //水平方向的排列方式
    @Setter
    @Getter
    private ITEM_LAYOUT itemLayoutVertical;  //竖直方向的排列方式
    @Setter
    @Getter
    private int itemWidth;
    @Setter
    @Getter
    private int itemHeight;
    @Setter
    @Getter
    private int formItemTextSize;
    @Setter
    @Getter
    private int formItemTextColor;

    @Setter
    @Getter
    private int itemLayoutTextRes;
    @Setter
    @Getter
    private int itemLayoutImageRes;
    @Setter
    @Getter
    private int itemLayoutCustomRes;

    @Setter
    @Getter
    private List<Map<String, Object>> data;
    private List<BorderLinearLayout> formItemList;
    private List<BorderLinearLayout> formRowList;
    private BorderLinearLayout itemView;

    public enum ITEM_LAYOUT {
        ALIQUOT(0), WRAP(1);
        private int value;

        ITEM_LAYOUT(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ITEM_TYPE {
        TEXT, IMAGE, CUSTOM
    }

    public EFFormView(Context context) {
        super(context);
    }

    public EFFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setData(new ArrayList<Map<String, Object>>());
        formItemList = new ArrayList<>();
        formRowList = new ArrayList<>();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EFFormView, 0, 0);

        //获取边框颜色和宽度
        setFrameColor(a.getColor(R.styleable.EFFormView_frameColor, Color.WHITE));
        setFrameWidth(a.getDimensionPixelSize(R.styleable.EFFormView_frameWidth, 0));
        setBorderColor(getFrameColor());
        int size = getFrameWidth();
        setBorderSize(size, size, size, size);

        //获取内部分割线的颜色和宽度
        setDividerColor(a.getColor(R.styleable.EFFormView_dividerColor, Color.WHITE));
        setDividerWidth(a.getDimensionPixelSize(R.styleable.EFFormView_dividerWidth, 0));

        setRowCount(a.getInt(R.styleable.EFFormView_rowCount, 0));
        setColumnCount(a.getInt(R.styleable.EFFormView_columnCount, 0));

        //获取水平方向上的item排列
        if (ITEM_LAYOUT.ALIQUOT.getValue() == a.getInt(R.styleable.EFFormView_itemLayoutHorizontal, 0)) {
            setItemLayoutHorizontal(ITEM_LAYOUT.ALIQUOT);
        } else {
            setItemLayoutHorizontal(ITEM_LAYOUT.WRAP);
        }

        //获取竖直方向上的item排列
        if (ITEM_LAYOUT.ALIQUOT.getValue() == a.getInt(R.styleable.EFFormView_itemLayoutVertical, 0)) {
            setItemLayoutVertical(ITEM_LAYOUT.ALIQUOT);
        } else {
            setItemLayoutVertical(ITEM_LAYOUT.WRAP);
        }

        setItemWidth(a.getDimensionPixelSize(R.styleable.EFFormView_itemWidth, 0));
        setItemHeight(a.getDimensionPixelSize(R.styleable.EFFormView_itemHeight, 0));

        setFormItemTextSize(a.getDimensionPixelSize(R.styleable.EFFormView_formItemTextSize, 0));
        setFormItemTextColor(a.getColor(R.styleable.EFFormView_formItemTextColor, 0));

        int textSize = getFormItemTextSize();
        int textColor = getFormItemTextColor();
        Log.e("测试", " " + textSize + "  " + textColor);
        a.recycle();
        init();
    }

    private void init() {
        //初始化一些参数
        setItemLayoutTextRes(R.layout.item_text);
        setItemLayoutImageRes(R.layout.item_image);
        setItemLayoutCustomRes(R.layout.item_text);
        setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < getRowCount(); i++) {
            BorderLinearLayout rowView = (BorderLinearLayout) LayoutInflater.from(context).inflate(R.layout.form_row, null);
            rowView.setBorderColor(getDividerColor());

            for (int j = 0; j < getColumnCount(); j++) {
                BorderLinearLayout view = (BorderLinearLayout) getItemView(i, j, ITEM_TYPE.TEXT);
                view.setBorderColor(getDividerColor());

                // 设置item尺寸的时候
                if(getItemHeight() > 0
                        && getItemWidth() > 0){
                    LinearLayout.LayoutParams params = new LayoutParams(getItemWidth()
                            , getItemHeight(), 0.5f);
                    view.setLayoutParams(params);
                }else{
                    if (getItemLayoutHorizontal() == ITEM_LAYOUT.ALIQUOT) {
                        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT
                                , LayoutParams.WRAP_CONTENT, 0.5f);
                        view.setLayoutParams(params);
                    } else if (getItemLayoutHorizontal() == ITEM_LAYOUT.WRAP) {

                    }
                }

                //给出了第一个item之外的每一个设置左边界
                if (j != 0) {
                    view.setBorderSize(getDividerWidth(), 0, 0, 0);
                }

                rowView.addView(view);
                formItemList.add(view);
                formRowList.add(rowView);
            }

            //给出了第一个item之外的每一个设置左边界
            if (i != 0) {
                rowView.setBorderSize(0, getDividerWidth(), 0, 0);
            }
            addView(rowView);
        }
    }

    /**
     * 获取item的view对象，传入的(i,j)为表格矩阵的坐标
     *
     * @param i item的横坐标，从0开始
     * @param j item 的纵坐标，从0开始
     * @return
     */
    private View getItemView(int i, int j, ITEM_TYPE itemType) {
        if (itemType == ITEM_TYPE.TEXT) {
            return LayoutInflater.from(context).inflate(getItemLayoutTextRes(), null);
        } else if (itemType == ITEM_TYPE.IMAGE) {
            return LayoutInflater.from(context).inflate(getItemLayoutImageRes(), null);
        } else if (itemType == ITEM_TYPE.CUSTOM) {
            return LayoutInflater.from(context).inflate(getItemLayoutCustomRes(), null);
        } else {
            return LayoutInflater.from(context).inflate(getItemLayoutTextRes(), null);
        }
    }

    public void fillForm() {
        if (checkData()) {
            for (int j = 0; j < formItemList.size(); j++) {
                BorderLinearLayout itemView = formItemList.get(j);

                Map itemData = getData().get(j);
                String itemValue = "";
                ITEM_TYPE itemType = ITEM_TYPE.TEXT;

                if (itemData.containsKey(Constant.KEY.KEY_DATA)) {
                    if (itemData.containsKey(Constant.KEY.KEY_TYPE)) {
                        String type = (String) itemData.get(Constant.KEY.KEY_TYPE);
                        if (Constant.VALUE.VALUE_IMAGE.equals(type)) {
                            itemType = ITEM_TYPE.IMAGE;
                        } else if (Constant.VALUE.VALUE_CUSTOM.equals(type)) {
                            itemType = ITEM_TYPE.CUSTOM;
                        }
                    }

                    itemValue = (String) itemData.get(Constant.KEY.KEY_DATA);
                }

                if (itemType == ITEM_TYPE.TEXT) {
                    TextView tv = (TextView) itemView.findViewById(R.id.ef_item_text);
                    tv.setText(itemValue);
                } else if (itemType == ITEM_TYPE.IMAGE) {
                    ImageView iv = (ImageView) itemView.findViewById(R.id.ef_item_image);
                    Picasso.with(context).load(itemValue).into(iv);
                } else if (itemType == ITEM_TYPE.CUSTOM) {

                }
            }
        }
    }

    /**
     * 检查数据的合法性
     *
     * @return
     */
    private boolean checkData() {
        return getData().size() == getColumnCount() * getRowCount();
    }


    /**
     * 设置某行的背景色
     * @param rowIndex 行号，从0开始记
     * @param color 颜色
     */
    public void setRowBackgroundColor(int rowIndex, int color){
        if(rowIndex < formRowList.size()){
            formRowList.get(rowIndex).setBackgroundColor(color);
        }
    }
}
