package com.jiakaiyang.library.easyform.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiakaiyang.library.easyform.R;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by kaiyangjia on 2016/3/8.
 */
public class InputDialogFragment extends DialogFragment implements View.OnClickListener{
    @Setter @Getter private TextView clickedTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_input, null);
        view.findViewById(R.id.dialog_input_yes).setOnClickListener(this);
        view.findViewById(R.id.dialog_input_no).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // TODO
        }
    }
}
