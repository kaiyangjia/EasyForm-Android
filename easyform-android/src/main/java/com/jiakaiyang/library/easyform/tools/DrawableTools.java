package com.jiakaiyang.library.easyform.tools;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by kaiyangjia on 2016/2/24.
 */
public class DrawableTools {

    /**
     * 根据一个基础的Shape Drawable 资源获取Drawable对象
     *
     * @param context
     * @param ResId
     * @param color   为负值时表示重新设定该Drawable的颜色(16进制RGB)
     * @param width   为负值时表示重新设定该Drawable的宽度
     * @param height  为负值时表示重新设定该Drawable的高度
     * @return
     */
    public static GradientDrawable getDrawable(Context context, int ResId, int color, int width, int height) {
        GradientDrawable gradientDrawable = (GradientDrawable) context.getResources().getDrawable(ResId);

        gradientDrawable.setColor(color);

        if (width >= 0) {
            gradientDrawable.setSize(width, gradientDrawable.getIntrinsicHeight());
        }

        if (height >= 0) {
            gradientDrawable.setSize(gradientDrawable.getIntrinsicWidth(), height);
        }

        return gradientDrawable;
    }
}
