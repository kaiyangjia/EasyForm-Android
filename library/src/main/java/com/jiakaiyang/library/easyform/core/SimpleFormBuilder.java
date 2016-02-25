package com.jiakaiyang.library.easyform.core;

import com.jiakaiyang.library.easyform.tools.Constant;
import com.jiakaiyang.library.easyform.view.CombinationFormView;
import com.jiakaiyang.library.easyform.view.EFFormView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kaiyangjia on 2016/2/25.
 */
public class SimpleFormBuilder implements FormBuilder{
    private JSONObject formConfig;
    private JSONArray formStructure;
    private CombinationFormView combinationFormView;

    public SimpleFormBuilder(JSONObject formConfig, JSONArray formStructure) {
        this.formConfig = formConfig;
        this.formStructure = formStructure;

        build();
    }

    public SimpleFormBuilder(JSONObject jsonObject){
        try {
            this.formConfig = jsonObject.getJSONObject(Constant.KEY.KEY_CONFIG);
            this.formStructure = jsonObject.getJSONArray(Constant.KEY.KEY_FORM);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        build();
    }

    private void build(){
        buildFormConfig();
        buildFormStructure();
    }

    @Override
    public void buildFormConfig() {

    }

    @Override
    public void buildFormStructure() {
        if(formStructure.length() > 0){
            for(int i=0;i<formStructure.length();i++){
                try {
                    JSONObject rowObject = formStructure.getJSONObject(i);
                    int rowIndex = rowObject.getInt(Constant.KEY.KEY_ROW);
                    JSONArray rowArray = rowObject.getJSONArray(Constant.KEY.KEY_DATA);

                    for(int j=0;j<rowArray.length();j++){
                        JSONObject columnObject = rowArray.getJSONObject(j);

                        String type = columnObject.getString(Constant.KEY.KEY_TYPE);

                        if(EFFormView.ITEM_TYPE.IMAGE.equals(type)){

                        }else if(EFFormView.ITEM_TYPE.CUSTOM.equals(type)){

                        }else if(EFFormView.ITEM_TYPE.FORM.equals(type)){
                            JSONObject childForm = columnObject.getJSONObject(Constant.KEY.KEY_DATA);
                            SimpleFormBuilder simpleFormBuilder = new SimpleFormBuilder(childForm);
                            CombinationFormView c = simpleFormBuilder.getForm();
                            combinationFormView.setItem(i, j, c);
                        }else{

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public CombinationFormView getForm() {
        return null;
    }
}
