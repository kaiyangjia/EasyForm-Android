package com.jiakaiyang.library.easyform.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by kaiyangjia on 2016/3/2.
 *
 */
public class XMLUtils {
    private static String TAG = "XMLUtils";

    /**
     * 使用自定义的xml文件获取一个参数的 AttrbuteSet 对象,
     * 同时把给定的map key-value 对当做参数对xml节点进行修改
     * 不需要修改的话则传递null
     * @param context
     * @param xmlResId
     * @param tagName
     * @param customAttrs 不需要修改xml的话就传递null
     * @return
     */
    public static AttributeSet getAttrs(Context context, int xmlResId, String tagName, Map<String, String> customAttrs){
        XmlPullParser parser = context.getResources().getXml(xmlResId);

        return getAttrs(parser, tagName, customAttrs);
    }


    public static AttributeSet getAttrs(XmlPullParser parser, String tagName, Map<String, String> customAttrs){
        AttributeSet attrs = null;
        int state = 0;
        do {
            try {
                state = parser.next();
            } catch (XmlPullParserException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (state == XmlPullParser.START_TAG) {
                if (parser.getName().equals(tagName)) {
                    String s = (String) parser.getProperty("android:id");

                    if(customAttrs != null){
                        for(String key : customAttrs.keySet()){
                            try {
                                parser.setProperty(key, customAttrs.get(key));
                            } catch (XmlPullParserException e) {
                                Log.e(TAG, "XmlPullParserException when call getAttrs");
                                e.printStackTrace();
                            }
                        }
                    }
                    attrs = Xml.asAttributeSet(parser);
                    break;
                }
            }
        } while(state != XmlPullParser.END_DOCUMENT);

        return attrs;
    }


    public static AttributeSet getAttrs (InputStream inputStream, String tagName, Map<String, String> customAttrs){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setValidating(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream,  "UTF-8");

            return getAttrs(parser, tagName, customAttrs);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }
}
