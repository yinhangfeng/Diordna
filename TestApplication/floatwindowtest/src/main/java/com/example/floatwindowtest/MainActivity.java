package com.example.floatwindowtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FloatWindowTest";
    private FloatWindow floatWindow;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = (Button)findViewById(R.id.start);
        Button stop = (Button)findViewById(R.id.stop);
        floatWindow = new FloatWindow(this);
        View contentView = floatWindow.inflateAndSetContentView(R.layout.float_layout);
        Spinner spinner = (Spinner) contentView.findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[] {"aaa", "bbb"}));

        start.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                floatWindow.show(v);
            }
        });

        stop.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                floatWindow.dismiss();
            }
        });

        findViewById(R.id.popup).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(v);
            }
        });


    }

    private void popup(View v) {
        final View view = getLayoutInflater().inflate(R.layout.float_layout, null);
        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(v);
            }
        });
        popupWindow.showAsDropDown(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
