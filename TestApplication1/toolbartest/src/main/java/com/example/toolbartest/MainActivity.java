package com.example.toolbartest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//对于AppCompatActivity以及Theme.Light返回箭头与左侧图标合并
//setDisplayHomeAsUpEnabled决定了是否显示左侧图标(默认返回箭头)和是否可点击,setHomeButtonEnabled setDisplayShowHomeEnabled无效
//对Toolbar也一样
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //android.app.ActionBar actionBar = getActionBar();

        ActionBar actionBar = getSupportActionBar();

        //设置显示返回箭头
        actionBar.setDisplayHomeAsUpEnabled(true);
//        //设置返回箭头是否可点击 无效
//        actionBar.setHomeButtonEnabled(false);
//        //设置是否显示左侧图标 无效
//        actionBar.setDisplayShowHomeEnabled(false);

        //设置返回箭头图标
        //actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        actionBar.setTitle("xx");

        //actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setCustomView(getLayoutInflater().inflate(R.layout.actionbar_custom_layout, null));

        toolbar1 = (Toolbar) findViewById(R.id.tool_bar1);
        //相当于 setDisplayHomeAsUpEnabled
        toolbar1.setNavigationIcon(R.mipmap.ic_launcher);

        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "NavigationOnClick");
            }
        });

        toolbar1.setTitle("xxx");

        toolbar1.inflateMenu(R.menu.menu_main_compat);
        toolbar1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        //toolbar1.setLogo(R.mipmap.ic_launcher);

        toolbar1.addView(getLayoutInflater().inflate(R.layout.actionbar_custom_layout, null));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_compat, menu);
        MenuItem searchItem = menu.findItem(R.id.search_view);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override public boolean onMenuItemActionExpand (MenuItem item){
                Log.i(TAG, "onMenuItemActionExpand");
                return true;
            }

            @Override public boolean onMenuItemActionCollapse (MenuItem item){
                Log.i(TAG, "onMenuItemActionCollapse");
                return true;
            }
        });
        Log.i(TAG, "onCreateOptionsMenu searchItem.getActionView()=" + MenuItemCompat.getActionView(searchItem));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
        case android.R.id.home:
            Log.i(TAG, "onOptionsItemSelected home");
            return true;
//        case R.id.action_settings:
//            Log.i(TAG, "onOptionsItemSelected settings");
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
