package com.jiakaiyang.easyform.tools;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kaiyangjia on 2016/2/26.
 */
public class ResourcesTools {

    public static String getAssets(Context context, String name){
        String result = "";
        try {
            InputStream is = context.getAssets().open("name");
            result = IOTools.getStringFormStream(is);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return result;
    }
}
