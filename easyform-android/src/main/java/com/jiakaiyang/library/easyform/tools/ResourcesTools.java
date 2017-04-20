package com.jiakaiyang.library.easyform.tools;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaiyangjia on 2016/2/26.
 */
public class ResourcesTools {

    /**
     * 获取asse目录下的文本的内容，用字符串返回
     *
     * @param context
     * @param name
     * @return
     */
    public static String getAssets(Context context, String name) {
        String result = "";
        try {
            InputStream is = context.getAssets().open(name);
            result = IOTools.getStringFormStream(is);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return result;
    }


    /**
     * 根据asset文件获取输入流
     *
     * @param context
     * @param name
     * @return
     */
    public static InputStream getAssetsStream(Context context, String name) {
        try {
            InputStream is = context.getAssets().open(name);
            return is;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取自定义的styleable-attr 的每个属性名称的数组，以list的格式返回
     *
     * @param context
     * @param styleableIds
     * @return
     */
    public static List<String> getAttrNames(Context context, int[] styleableIds) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < styleableIds.length; i++) {
            String name = context.getResources().getResourceName(styleableIds[i]);
            if (name != null) {
                int index = name.lastIndexOf("/");
                if (index > 0) {
                    name = name.substring(index + 1, name.length());
                }
            }
            result.add(name);
        }

        return result;
    }

    public static final Map getResourceIntMap(Context context, String className, List<String> names, String type) {
        try {
            Map map = new HashMap();
            //use reflection to access the resource class
            Field[] fields = Class.forName(context.getPackageName() + ".R$" + type).getFields();

            for (String n : names) {
                //browse all fields
                String name = className + "_" + n;
                for (Field f : fields) {
                    //pick matching field
                    if (f.getName().equals(name)) {
                        //return as int array
                        Log.e("测试 ", " name " + name);
                        int ret = (int) f.get(null);
                        map.put(name, ret);
                        break;
                    }
                }
            }

            return map;
        } catch (Throwable t) {
        }
        return null;
    }
}
