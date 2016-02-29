package com.jiakaiyang.library.easyform.tools;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaiyangjia on 2016/2/26.
 */
public class JSONTools {


    public static JSONObject getJsonUseKeyList(List<String> keys){
        Map map = new HashMap<>();

        for(String key : keys){
           map.put(key, "");
        }

        JSONObject result = new JSONObject(map);
        return result;
    }
}
