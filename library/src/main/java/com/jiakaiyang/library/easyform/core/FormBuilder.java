package com.jiakaiyang.library.easyform.core;

import com.jiakaiyang.library.easyform.view.EFFormView;

/**
 * Created by kaiyangjia on 2016/2/24.
 * 用基础的表格组件FormView根据一定的条件构建复杂的表格
 */
public interface FormBuilder {
    public void buildFormConfig();

    public void buildFormStructure();

    public EFFormView getForm();
}
