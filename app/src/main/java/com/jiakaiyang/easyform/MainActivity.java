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
import android.widget.RelativeLayout;

import com.jiakaiyang.library.easyform.tools.XMLUtils;
import com.jiakaiyang.library.easyform.view.EFFormView;

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
        /*final EFFormView formView = (EFFormView) findViewById(R.id.ef_form);

        List<Map<String, Object>> data = new ArrayList<>();
        for(int i=0;i<12;i++){
            Map map = new HashMap();
            map.put(Constant.KEY.KEY_DATA, i + "--");
            data.add(map);
        }
        formView.setData(data);
        formView.fillForm();

        View view = LayoutInflater.from(this).inflate(R.layout.test, null);
        formView.setItem(1, 2, view);
        formView.setRowHeight(1, getResources().getDimensionPixelSize(R.dimen.test_dimen));
        formView.setColumnWidth(1, 30);*/

        createTest();
    }

    public void createTest(){
        AttributeSet attrs = XMLUtils.getAttrs(this, R.xml.form_config, "form45");

        EFFormView efFormView = new EFFormView(this, attrs);

        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.main_root);
        rootView.addView(efFormView);
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
