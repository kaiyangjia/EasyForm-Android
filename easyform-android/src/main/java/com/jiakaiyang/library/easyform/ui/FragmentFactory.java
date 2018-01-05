package com.jiakaiyang.library.easyform.ui;

import android.widget.TextView;

/**
 * Created by kaiyangjia on 2016/3/9.
 */
public class FragmentFactory {

    public static InputDialogFragment createInputDialog(TextView textView) {
        InputDialogFragment inputDialogFragment = new InputDialogFragment();
        inputDialogFragment.setClickedTextView(textView);

        return inputDialogFragment;
    }
}
