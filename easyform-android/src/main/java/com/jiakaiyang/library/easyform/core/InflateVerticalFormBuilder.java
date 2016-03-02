package com.jiakaiyang.library.easyform.core;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.jiakaiyang.library.easyform.tools.Constant;
import com.jiakaiyang.library.easyform.tools.XMLUtils;
import com.jiakaiyang.library.easyform.view.EFFormView;

import org.json.JSONArray;
import org.json.JSONObject;

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

        String s = "form" + rowCount + columnCount;
        AttributeSet attrs = XMLUtils.getAttrs(context, baseFormConfigId, s, null);
        EFFormView efFormView = new EFFormView(context, attrs);
        //正确是表示不是最外层的form
        if(rootConfig.optInt(Constant.KEY.KEY_IS_ROOT) != 1){
            efFormView.setBorderEnable(false);
        }
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
                Log.e("测试", efFormView.toString() + "  " + i);
                JSONObject childForm = childs.optJSONObject(i);
                int x = childForm.optInt(Constant.KEY.KEY_X);
                int y = childForm.optInt(Constant.KEY.KEY_Y);
                JSONObject newRootFormConfig = childForm.optJSONObject(Constant.KEY.KEY_FORM);
                efFormView.setItem(x, y, buildForm(newRootFormConfig));
            }
        }

        return efFormView;
    }
}
