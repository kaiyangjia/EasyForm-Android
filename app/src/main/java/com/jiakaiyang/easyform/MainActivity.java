package com.jiakaiyang.easyform;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jiakaiyang.library.easyform.core.SimpleFormBuilder;
import com.jiakaiyang.library.easyform.tools.Constant;
import com.jiakaiyang.library.easyform.tools.ResourcesTools;
import com.jiakaiyang.library.easyform.view.BorderLinearLayout;
import com.jiakaiyang.library.easyform.view.EFFormView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        test();
    }

    private void test(){
        final EFFormView formView = (EFFormView) findViewById(R.id.ef_form);

        List<Map<String, Object>> data = new ArrayList<>();
        for(int i=0;i<12;i++){
            Map map = new HashMap();
            map.put(Constant.KEY.KEY_DATA, i + "--");
            data.add(map);
        }
        formView.setData(data);
        formView.fillForm();
        formView.setOnItemClickListener(new EFFormView.OnItemClickListener() {
            @Override
            public void onClick(BorderLinearLayout itemView) {
                //使表格的某一行点击时会有颜色变化
                int i = formView.getFormItemList().indexOf(itemView);
                i = i / formView.getColumnCount();
                Log.e("onclick, ", "  " + i);
                if (i >= 0) {
                    formView.setRowBackgroundColorOnly(i
                            , getResources().getColor(R.color.light_blue)
                            , getResources().getColor(R.color.light_gray));
                }

                formView.setRowTextColorOnly(i, Color.WHITE);
            }
        });

        String jsonStr = ResourcesTools.getAssets(this, "form.json");
        try {
            EFFormView efFormView = (EFFormView) findViewById(R.id.combination_form);
            JSONObject formJson = new JSONObject(jsonStr);
            SimpleFormBuilder simpleFormBuilder = new SimpleFormBuilder(this, formJson, efFormView);
            simpleFormBuilder.build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
