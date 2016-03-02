package com.jiakaiyang.library.easyform.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by kaiyangjia on 2016/3/2.
 */
public class XMLUtils {
    public static AttributeSet getAttrs(Context context, int xmlResId, String tagName){
        XmlPullParser parser = context.getResources().getXml(xmlResId);
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
                    attrs = Xml.asAttributeSet(parser);
                    break;
                }
            }
        } while(state != XmlPullParser.END_DOCUMENT);

        return attrs;
    }
}
