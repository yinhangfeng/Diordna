package com.example.darkwanderer.themetest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ListActivity {

    public static final String[] THEME_NAMES = new String[]{
            "Theme.AppCompat", "Theme.AppCompat.CompactMenu", "Theme.AppCompat.Dialog", "Theme.AppCompat.DialogWhenLarge", "Theme.AppCompat.Light", "Theme.AppCompat.Light.DarkActionBar", "Theme.AppCompat.Light.Dialog", "Theme.AppCompat.Light.DialogWhenLarge", "Theme.AppCompat.Light.NoActionBar", "Theme.AppCompat.NoActionBar",
            "android:Theme.Light",
            "android:Theme.Black",

            "android:Theme.Holo",
            "android:Theme.Holo.Light",
            "android:Theme.Holo.Light.DarkActionBar",
            "android:Theme.Holo.Light.Panel",
            "android:Theme.Holo.Panel",
            "android:Theme.Holo.Wallpaper",
            "android:Theme.Holo.Dialog",

            "android:ThemeOverlay",
            "android:ThemeOverlay.Material",
            "android:ThemeOverlay.Material.ActionBar",
            "android:ThemeOverlay.Material.Dark",
            "android:ThemeOverlay.Material.Dark.ActionBar",
            "android:ThemeOverlay.Material.Light",

            "android:Theme.Material",
            "android:Theme.Material.Dialog",
            "android:Theme.Material.Dialog.Alert",
            "android:Theme.Material.Light",
            "android:Theme.Material.Light.DarkActionBar",
            "android:Theme.Material.Light.Dialog",
            "android:Theme.Material.Light.NoActionBar",
            "android:Theme.Material.Light.NoActionBar.Fullscreen",
            "android:Theme.Material.Light.Voice",
            "android:Theme.Material.Light.Panel",
            "android:Theme.Material.Panel"
    };

    public static final int[] THEME_RESS = new int[]{
            android.support.v7.appcompat.R.style.Theme_AppCompat, android.support.v7.appcompat.R.style.Theme_AppCompat_CompactMenu, android.support.v7.appcompat.R.style.Theme_AppCompat_Dialog, android.support.v7.appcompat.R.style.Theme_AppCompat_DialogWhenLarge, android.support.v7.appcompat.R.style.Theme_AppCompat_Light, android.support.v7.appcompat.R.style.Theme_AppCompat_Light_DarkActionBar, android.support.v7.appcompat.R.style.Theme_AppCompat_Light_Dialog, android.support.v7.appcompat.R.style.Theme_AppCompat_Light_DialogWhenLarge, android.support.v7.appcompat.R.style.Theme_AppCompat_Light_NoActionBar, android.support.v7.appcompat.R.style.Theme_AppCompat_NoActionBar,
            android.R.style.Theme_Light,
            android.R.style.Theme_Black,

            android.R.style.Theme_Holo,
            android.R.style.Theme_Holo_Light,
            android.R.style.Theme_Holo_Light_DarkActionBar,
            android.R.style.Theme_Holo_Light_Panel,
            android.R.style.Theme_Holo_Panel,
            android.R.style.Theme_Holo_Wallpaper,
            android.R.style.Theme_Holo_Dialog,

            android.R.style.ThemeOverlay,
            android.R.style.ThemeOverlay_Material,
            android.R.style.ThemeOverlay_Material_ActionBar,
            android.R.style.ThemeOverlay_Material_Dark,
            android.R.style.ThemeOverlay_Material_Dark_ActionBar,
            android.R.style.ThemeOverlay_Material_Light,

            android.R.style.Theme_Material,
            android.R.style.Theme_Material_Dialog,
            android.R.style.Theme_Material_Dialog_Alert,
            android.R.style.Theme_Material_Light,
            android.R.style.Theme_Material_Light_DarkActionBar,
            android.R.style.Theme_Material_Light_Dialog,
            android.R.style.Theme_Material_Light_NoActionBar,
            android.R.style.Theme_Material_Light_NoActionBar_Fullscreen,
            android.R.style.Theme_Material_Light_Voice,
            android.R.style.Theme_Material_Light_Panel,
            android.R.style.Theme_Material_Panel
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int a = android.support.v7.appcompat.R.style.Theme_AppCompat_Light;
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, THEME_NAMES));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
