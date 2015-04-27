package com.example.appcompactv7test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if(id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.appcompat_dlg) {
            showAppcompatDlg();
            return true;
        }

        if(id == R.id.v7alert_dlg) {
            showV7AlertDlg();
            return true;
        }

        if(id == R.id.alert_dlg) {
            showAlertDlg();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAlertDlg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("V7AlertDlg");
        builder.setView(getLayoutInflater().inflate(R.layout.dlg_content, null));
        builder.show();
    }

    private void showAppcompatDlg() {
        AppCompatDialog appCompatDialog = new AppCompatDialog(this);
        appCompatDialog.setTitle("AppCompatDialog");
        appCompatDialog.setContentView(R.layout.dlg_content);
        appCompatDialog.setCancelable(true);
        appCompatDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        appCompatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        appCompatDialog.show();
    }

    private void showV7AlertDlg() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("V7AlertDlg");
        builder.setView(R.layout.dlg_content);
        builder.show();
    }
}
