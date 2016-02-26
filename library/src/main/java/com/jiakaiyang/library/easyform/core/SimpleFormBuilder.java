package com.jiakaiyang.library.easyform.core;

import android.content.Context;
import android.util.Log;

import com.jiakaiyang.library.easyform.tools.Constant;
import com.jiakaiyang.library.easyform.view.EFFormView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.Setter;

/**
 * Created by kaiyangjia on 2016/2/25.
 */
public class SimpleFormBuilder implements FormBuilder{
    private Context context;
    private JSONObject formConfig;
    private JSONArray formStructure;
    @Setter
    private EFFormView rootFormView;

    public SimpleFormBuilder(Context context, JSONObject jsonObject, EFFormView rootFormView){
        this.context = context;
        try {
            this.formConfig = jsonObject.getJSONObject(Constant.KEY.KEY_CONFIG);
            this.formStructure = jsonObject.getJSONArray(Constant.KEY.KEY_FORM);
            this.rootFormView = rootFormView;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void build(){
        buildFormConfig();
        buildFormStructure();
    }

    @Override
    public void buildFormConfig() {
        rootFormView.setConfig(formConfig);
    }

    @Override
    public void buildFormStructure() {
        if(formStructure.length() > 0){
            for(int i=0;i<formStructure .length();i++){
                try {
                    JSONObject rowObject = formStructure.getJSONObject(i);
                    int rowIndex = rowObject.getInt(Constant.KEY.KEY_ROW);
                    JSONArray rowArray = rowObject.getJSONArray(Constant.KEY.KEY_DATA);

                    for(int j=0;j<rowArray.length();j++){
                        JSONObject columnObject = rowArray.getJSONObject(j);

                        String type = columnObject.getString(Constant.KEY.KEY_TYPE);
                        if(EFFormView.ITEM_TYPE.IMAGE.getValue().equals(type)){

                        }else if(EFFormView.ITEM_TYPE.CUSTOM.getValue().equals(type)){

                        }else if(EFFormView.ITEM_TYPE.FORM.getValue().equals(type)){
                            JSONObject childForm = columnObject.getJSONObject(Constant.KEY.KEY_DATA);
                            EFFormView tempFormView = rootFormView.getItem(i, j);
                            SimpleFormBuilder simpleFormBuilder = new SimpleFormBuilder(context, childForm, tempFormView);
                            simpleFormBuilder.build();

                            //内部的EFFormView设置为无边框
                            tempFormView.setBorderEnable(false);
                        }else{
                            String content = columnObject.getString(Constant.KEY.KEY_DATA);
                            Log.e(" 文本 ", " ,,, " + content);
                            rootFormView.setItem(i, j, content);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public EFFormView getForm() {
        return rootFormView;
    }
}
