package com.jiakaiyang.easyform;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jiakaiyang.library.easyform.core.InflateVerticalFormBuilder;
import com.jiakaiyang.library.easyform.tools.Constant;
import com.jiakaiyang.library.easyform.tools.ResourcesTools;
import com.jiakaiyang.library.easyform.tools.XMLUtils;
import com.jiakaiyang.library.easyform.view.EFFormView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
        formView.setRowClickChange();
        //设置第一行不可点击，用于设置表头
        formView.setRowClickable(0, false);
        formView.resetColumnWeight(new float[]{0.1f,0.1f, 0.1f, 0.2f});

//        testBuilder();

//        createTest();
//        testJson();
    }

    public void testBuilder(){
        String strJson = ResourcesTools.getAssets(this, "buildForm.json");
        try {
            JSONObject rootConfig = new JSONObject(strJson);
            InflateVerticalFormBuilder builder = new InflateVerticalFormBuilder(R.xml.form_config, this, rootConfig);
            EFFormView efFormView = builder.buildForm(rootConfig);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 360);
            efFormView.setLayoutParams(params);
            RelativeLayout rootView = (RelativeLayout) findViewById(R.id.main_root);
            rootView.addView(efFormView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createTest(){
        Map<String, String> map = new HashMap<>();
        AttributeSet attrs = XMLUtils.getAttrs(this, R.xml.form_config, "form22", map);

        EFFormView efFormView = new EFFormView(this, attrs);

        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.main_root);
        rootView.addView(efFormView);
    }

    public void testJson(){
        String s = ResourcesTools.getAssets(this, "baseForm.json");
        try {
            JSONObject jo = new JSONObject(s);
            String xml =  XML.toString(jo);
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));

            AttributeSet attrs = XMLUtils.getAttrs(is, "baseForm", null);

            EFFormView efFormView = new EFFormView(this, attrs);

            RelativeLayout rootView = (RelativeLayout) findViewById(R.id.main_root);
            rootView.addView(efFormView);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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
