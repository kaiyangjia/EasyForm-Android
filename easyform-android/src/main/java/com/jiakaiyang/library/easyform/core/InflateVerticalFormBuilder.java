package com.jiakaiyang.library.easyform.core;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import com.jiakaiyang.library.easyform.tools.Constant;
import com.jiakaiyang.library.easyform.tools.XMLUtils;
import com.jiakaiyang.library.easyform.view.BorderLinearLayout;
import com.jiakaiyang.library.easyform.view.EFFormView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by kaiyangjia on 2016/3/2.
 */
public class InflateVerticalFormBuilder {
    private Context context;
    private JSONObject formJson;
    private int baseFormConfigId;
    @Setter @Getter private int baseRowHeight;

    public InflateVerticalFormBuilder(int baseFormConfigId, Context context, JSONObject formJson) {
        this.baseFormConfigId = baseFormConfigId;
        this.context = context;
        this.formJson = formJson;
        setBaseRowHeight(60);
    }

    /**
     * 核心算法,递归实现
     */
    public EFFormView buildForm(JSONObject rootConfig) {
        JSONArray childs = rootConfig.optJSONArray(Constant.KEY.KEY_CHILD);

        int rowCount = rootConfig.optInt(Constant.KEY.KEY_ROW);
        int columnCount = rootConfig.optInt(Constant.KEY.KEY_COLUMN);

        String setTitleColor = rootConfig.optString(Constant.KEY.KEY_SET_TITLE_COLOR);
        String titles = rootConfig.optString(Constant.KEY.KEY_TITLES);

        String s = "form" + rowCount + columnCount;
        AttributeSet attrs = XMLUtils.getAttrs(context, baseFormConfigId, s, null);
        final EFFormView efFormView = new EFFormView(context, attrs);
        //正确是表示不是最外层的form
        if(rootConfig.optInt(Constant.KEY.KEY_IS_ROOT) != 1){
            efFormView.setBorderEnable(false);
        }
        if("1".equals(setTitleColor)){
            efFormView.setRowClickable(0, false);
            efFormView.setRowBackgroundColorOnly(0, Color.parseColor("#f1f1f1"), Color.WHITE);
        }
        efFormView.setFormTitles(titles);

        List<Map<String, Object>> data = new ArrayList<>();
        for(int i=0;i<rowCount * columnCount - columnCount;i++){
            Map map = new HashMap();
            map.put(Constant.KEY.KEY_DATA, "");
            data.add(map);
        }
        efFormView.setData(data);
        efFormView.fillForm();

        JSONArray heightConfig = rootConfig.optJSONArray(Constant.KEY.KEY_ROW_HEIGHT);

        for(int i = 0;i<heightConfig.length();i++){
            JSONObject jo = heightConfig.optJSONObject(i);
            int row = jo.optInt(Constant.KEY.KEY_ROW);
            int height = jo.optInt(Constant.KEY.KEY_HEIGHT);
            height *= getBaseRowHeight();

            efFormView.setRowHeight(row, height);
        }

        //递归结束条件
        if (childs.length() > 0) {
            for (int i = 0; i < childs.length(); i++) {
                JSONObject childForm = childs.optJSONObject(i);
                int x = childForm.optInt(Constant.KEY.KEY_X);
                int y = childForm.optInt(Constant.KEY.KEY_Y);
                JSONObject newRootFormConfig = childForm.optJSONObject(Constant.KEY.KEY_FORM);
                EFFormView e = buildForm(newRootFormConfig);
                e.setOnItemClickListener(new EFFormView.OnItemClickListener() {
                    @Override
                    public void onClick(BorderLinearLayout itemView) {
                        efFormView.getOnItemClickListener().onClick(itemView);
                    }
                });
                efFormView.setItem(x, y, e);
                Log.e("测试", efFormView.toString() + "  " + i);
            }
        }

        return efFormView;
    }
}
