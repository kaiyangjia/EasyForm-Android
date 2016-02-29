package com.jiakaiyang.library.easyform.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiakaiyang.library.easyform.R;
import com.jiakaiyang.library.easyform.tools.Constant;
import com.jiakaiyang.library.easyform.tools.ResourcesTools;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by kaiyangjia on 2016/2/23.
 */
public class EFFormView extends BorderLinearLayout{
    private static String TAG = "EFFormView";
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
    private ITEM_GRAVITY itemGravity;
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
    @Setter @Getter private List<Map<String, Object>> formTitleNames;

    @Setter
    @Getter
    private List<Map<String, Object>> data;
    //所有单元格的链表
    @Setter @Getter private List<BorderLinearLayout> formItemList;
    //所有行的链表
    @Setter @Getter private List<BorderLinearLayout> formRowList;
    @Setter @Getter private OnItemClickListener onItemClickListener;

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

    /**
     * 条目的类型有文本、图片、自定义view、子表格
     */
    public enum ITEM_TYPE {
        TEXT(Constant.VALUE.VALUE_TEXT),
        IMAGE(Constant.VALUE.VALUE_IMAGE),
        CUSTOM(Constant.VALUE.VALUE_CUSTOM),
        FORM(Constant.VALUE.VALUE_FORM);
        private String value;

        ITEM_TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ITEM_GRAVITY {
        CENTER(0), LEFT(1), RIGHT(2);
        private int value;

        ITEM_GRAVITY(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
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
        formTitleNames = new ArrayList<>();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EFFormView, 0, 0);

        initArgs(a);
        a.recycle();
        init();
    }

    public void resetArgs(){
        init();
        invalidate();
    }

    private JSONObject attrToJsonConfig(TypedArray a) {
        List<String> names = ResourcesTools.getAttrNames(context, R.styleable.EFFormView);

        Map map = ResourcesTools.getResourceIntMap(context, "EFFormView", names, "styleable");
        JSONObject config = new JSONObject(map);

        return config;
    }

    public void initArgs(TypedArray a) {
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

        initFormTitle(a);

        //获取表格item中内容的对齐方式，有左对齐，右对齐，居中。默认为居中对齐
        int i = a.getInt(R.styleable.EFFormView_itemGravity, 0);
        if (i == ITEM_GRAVITY.LEFT.getValue()) {
            setItemGravity(ITEM_GRAVITY.LEFT);
        } else if (i == ITEM_GRAVITY.RIGHT.getValue()) {
            setItemGravity(ITEM_GRAVITY.LEFT);
        } else {
            setItemGravity(ITEM_GRAVITY.CENTER);
        }

        setFormItemTextSize(a.getDimensionPixelSize(R.styleable.EFFormView_formItemTextSize, 0));
        setFormItemTextColor(a.getColor(R.styleable.EFFormView_formItemTextColor, 0));
    }


    private void initFormTitle(TypedArray a){
        String names = a.getString(R.styleable.EFFormView_formTitleNames);
        if(names != null){
            String[] namesArray = names.split(",");

            if(namesArray.length == getColumnCount()){
                for(int i=0;i<namesArray.length;i++){
                    Map map = new HashMap();
                    map.put(Constant.KEY.KEY_DATA, namesArray[i]);
                    getFormTitleNames().add(map);
                }
            }else{
                Log.e(TAG, "attr formTitleNames is wrong");
            }
        }
    }

    /**
     * 动态给表格设置配置信息
     *
     * @param formConfig
     */
    public void setConfig(JSONObject formConfig) {
        setRowCount(formConfig.optInt(Constant.KEY.KEY_ROW));
        setColumnCount(formConfig.optInt(Constant.KEY.KEY_COLUMN));

        removeAllViews();
        init();
    }

    private void init() {
        //初始化一些参数
        setItemLayoutTextRes(R.layout.item_text);
        setItemLayoutImageRes(R.layout.item_image);
        setItemLayoutCustomRes(R.layout.item_text);
        setOrientation(LinearLayout.VERTICAL);
        this.setClickable(true);
        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.e("子view", "click");
                if(getOnItemClickListener() != null){
                    BorderLinearLayout efFormView = (BorderLinearLayout) v;
                    getOnItemClickListener().onClick(efFormView);
                }
            }
        };

        for (int i = 0; i < getRowCount(); i++) {
            BorderLinearLayout rowView = (BorderLinearLayout) LayoutInflater.from(context).inflate(R.layout.form_row, null);
            rowView.setOrientation(LinearLayout.HORIZONTAL);
            rowView.setBorderColor(getDividerColor());

            for (int j = 0; j < getColumnCount(); j++) {
                BorderLinearLayout view = getItemView(i, j, ITEM_TYPE.TEXT);
                view.setBorderColor(getDividerColor());
                view.setOnClickListener(listener);


                // 设置item尺寸的时候
                if (getItemHeight() > 0
                        && getItemWidth() > 0) {
                    LinearLayout.LayoutParams params = new LayoutParams(getItemWidth()
                            , getItemHeight(), 0.5f);
                    view.setLayoutParams(params);
                } else {
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
            }

            //给出了第一个item之外的每一个设置左边界
            if (i != 0) {
                rowView.setBorderSize(0, getDividerWidth(), 0, 0);
            }
            addView(rowView);
            formRowList.add(rowView);
        }
    }

    /**
     * 获取item的view对象，传入的(i,j)为表格矩阵的坐标
     *
     * @param i item的横坐标，从0开始
     * @param j item 的纵坐标，从0开始
     * @return
     */
    private BorderLinearLayout getItemView(int i, int j, ITEM_TYPE itemType) {
        int gravity = Gravity.CENTER;
        BorderLinearLayout view;

        if (getItemGravity() == ITEM_GRAVITY.LEFT) {
            gravity = Gravity.LEFT;
        } else if (getItemGravity() == ITEM_GRAVITY.RIGHT) {
            gravity = Gravity.RIGHT;
        }

        if (itemType == ITEM_TYPE.IMAGE) {
            view = (BorderLinearLayout) LayoutInflater.from(context).inflate(getItemLayoutImageRes(), null);
        } else if (itemType == ITEM_TYPE.CUSTOM) {
            //TODO 添加添加自定义视图的实现
            view = (BorderLinearLayout) LayoutInflater.from(context).inflate(getItemLayoutCustomRes(), null);
        } else if (itemType == ITEM_TYPE.FORM) {
            //TODO 添加子表格
            view = (BorderLinearLayout) LayoutInflater.from(context).inflate(getItemLayoutTextRes(), null);
        } else {
            view = (BorderLinearLayout) LayoutInflater.from(context).inflate(getItemLayoutTextRes(), null);
        }

        view.setGravity(gravity);
        return view;
    }

    public void fillForm() {
        if (checkData()) {
            for (int j = 0; j < formItemList.size(); j++) {
                BorderLinearLayout itemView = formItemList.get(j);

                List<Map<String, Object>> tempData = new ArrayList<>();
                tempData.addAll(getFormTitleNames());
                tempData.addAll(getData());

                Map itemData = tempData.get(j);
                String itemValue = "";
                ITEM_TYPE itemType = ITEM_TYPE.TEXT;

                if (itemData.containsKey(Constant.KEY.KEY_DATA)) {
                    if (itemData.containsKey(Constant.KEY.KEY_TYPE)) {
                        String type = (String) itemData.get(Constant.KEY.KEY_TYPE);
                        if (Constant.VALUE.VALUE_IMAGE.equals(type)) {
                            itemType = ITEM_TYPE.IMAGE;
                        } else if (Constant.VALUE.VALUE_CUSTOM.equals(type)) {
                            itemType = ITEM_TYPE.CUSTOM;
                        } else if (Constant.VALUE.VALUE_FORM.equals(type)) {
                            itemType = ITEM_TYPE.FORM;
                        }
                    }

                    itemValue = (String) itemData.get(Constant.KEY.KEY_DATA);
                }

                if (itemType == ITEM_TYPE.TEXT) {
                    TextView tv = (TextView) itemView.findViewById(R.id.ef_item_text);
                    tv.setText(itemValue);
                    tv.setTextColor(getFormItemTextColor());
                    if(getFormItemTextSize() > 0){
                        tv.setTextSize(getFormItemTextSize());
                    }
                } else if (itemType == ITEM_TYPE.IMAGE) {
                    ImageView iv = (ImageView) itemView.findViewById(R.id.ef_item_image);
                    Picasso.with(context).load(itemValue).into(iv);
                } else if (itemType == ITEM_TYPE.CUSTOM) {
                    //TODO
                } else if (itemType == ITEM_TYPE.FORM) {
                    //TODO
                }
            }
        }else{
            Log.e(TAG, "Check data error");
        }
    }

    /**
     * 检查数据的合法性
     *
     * @return
     */
    private boolean checkData() {
        return getData().size() + getFormTitleNames().size() == getColumnCount() * getRowCount();
    }


    /**
     * 设置某行的背景色
     *
     * @param rowIndex 行号，从0开始记
     * @param color    颜色
     */
    public void setRowBackgroundColor(int rowIndex, int color) {
        if (rowIndex < formRowList.size()) {
            formRowList.get(rowIndex).setBackgroundColor(color);
        }
    }


    /**
     * 设置某一行为一种颜色，其他行为另外一种颜色
     * @param rowIndex
     * @param color
     * @param otherColor
     */
    public void setRowBackgroundColorOnly(int rowIndex, int color, int otherColor) {
        if (rowIndex < formRowList.size()) {
            formRowList.get(rowIndex).setBackgroundColor(color);
            for(int i=0;i<formRowList.size();i++){
                if(i != rowIndex){
                    formRowList.get(i).setBackgroundColor(otherColor);
                }
            }
        }
        invalidate();
    }

    /**
     * 设置某行的背景色
     *
     * @param rowIndex 行号，从0开始记
     * @param resId   行背景
     */
    public void setRowBackgroundResource(int rowIndex, int resId) {
        if (rowIndex < formRowList.size()) {
            formRowList.get(rowIndex).setBackgroundResource(resId);
        }
    }


    public void setRowBackgroundResource(int resId) {
        for(BorderLinearLayout view : formRowList){
            view.setBackgroundResource(resId);
        }
    }


    /**
     * 设置某一行中字体的颜色
     * @param rowIndex
     * @param color
     */
    public void setRowTextColorOnly(int rowIndex, int color){
        int start = rowIndex * getColumnCount();

        Log.e("测试", start + ",,," + getFormItemTextSize());
        for(int i = 0; i<getFormItemList().size();i++){
            List<Map> tempData = new ArrayList<>();
            tempData.addAll(getFormTitleNames());
            tempData.addAll(getData());

            Map map = tempData.get(i);

            boolean isText = !map.containsKey(Constant.KEY.KEY_TYPE)
                    || (map.containsKey(Constant.KEY.KEY_TYPE)
                    && map.get(Constant.KEY.KEY_TYPE).equals(Constant.VALUE.VALUE_TEXT));
            if(isText){
                TextView tv = (TextView) getFormItemList().get(i).findViewById(R.id.ef_item_text);
                //需要改变颜色的单元格
                if(i>=start
                        && i<start + getColumnCount()){
                    tv.setTextColor(color);
                }else{// 需要设置为默认颜色的单元格
                    tv.setTextColor(getFormItemTextColor());
                }
            }
        }
    }


    /**
     * 设置某一个单元格为
     *
     * @param rowIndex
     * @param columnIndex
     * @param itemFormView
     */
    public void setItem(int rowIndex, int columnIndex, BorderLinearLayout itemFormView) {
        int index = rowIndex * getColumnCount() + columnIndex;
        if (index < formItemList.size()) {
            formItemList.set(index, itemFormView);
        }
    }


    /**
     * 设置某一个单元格的文本
     *
     * @param rowIndex
     * @param columnIndex
     * @param content
     */
    public void setItem(int rowIndex, int columnIndex, String content) {
        int index = rowIndex * getColumnCount() + columnIndex;
        if (index < formItemList.size()) {
            TextView tv = (TextView) formItemList.get(index).findViewById(R.id.ef_item_text);
            tv.setText(content);
        }
    }


    /**
     * 获取某一个单元格
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public BorderLinearLayout getItem(int rowIndex, int columnIndex) {
        int index = rowIndex * getColumnCount() + columnIndex;
        BorderLinearLayout item = null;
        if (index < formItemList.size()) {
            item = formItemList.get(index);
        }

        return item;
    }

    /**
     * 获取某一行的View
     *
     * @param rowIndex
     * @return
     */
    public BorderLinearLayout getRowView(int rowIndex) {
        BorderLinearLayout row = null;
        if (rowIndex < formRowList.size()) {
            row = formRowList.get(rowIndex);
        }

        return row;
    }

    public interface OnItemClickListener{
        public void onClick(BorderLinearLayout itemView);
    }
}