package com.example.directorytest;

import java.io.File;
import java.util.Arrays;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private TextView info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		info = (TextView) findViewById(R.id.info);
	}

	@SuppressLint("SdCardPath")
	public void testOnClick(View v) {
		StringBuilder sb = new StringBuilder();
		sb.append("Volume requires API level 9");
		String[] vps = DirectoryUtils.getVolumePaths(this);
		sb.append("\nvps=").append(Arrays.toString(vps));
		for(int i = 0, len = vps.length; i < len; ++i) {
			sb.append("\nvps[").append(i).append("]=").append(vps[i]).append(" getVolumeState=").append(DirectoryUtils.getVolumeState(this, vps[i]));
		}
		for(int i = 0, len = vps.length; i < len; ++i) {
			sb.append("\nvps[").append(i).append("]=").append(vps[i]).append(" new File(vps[i]).exists()=").append(new File(vps[i]).exists());
		}
		sb.append("\ninternal");
		sb.append("\ngetFilesDir=").append(getFilesDir().getAbsolutePath());
		sb.append("\ngetFileStreamPath(\"file\")=").append(getFileStreamPath("file"));//openFileInput(name) openFileOutput(name, mode)存储目录
		sb.append("\ngetCacheDir=").append(getCacheDir().getAbsolutePath());
		sb.append("\nexternal Environment");
		sb.append("\nEnvironment.getExternalStorageState()=").append(Environment.getExternalStorageState());
		sb.append("\nEnvironment.getExternalStorageDirectory()=").append(Environment.getExternalStorageDirectory());
		//requires API level 19
		//sb.append("Environment.getStorageState(Environment.getExternalStorageDirectory())=").append(Environment.getStorageState(Environment.getExternalStorageDirectory()));
		sb.append("\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)=")
				.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
		sb.append("\nEnvironment.getDataDirectory()=").append(Environment.getDataDirectory().getAbsolutePath());
		sb.append("\nEnvironment.getDownloadCacheDirectory()=").append(Environment.getDownloadCacheDirectory().getAbsolutePath());
		sb.append("\nEnvironment.getRootDirectory()=").append(Environment.getRootDirectory().getAbsolutePath());
		sb.append("\nexternal context");
		sb.append("\ngetExternalFilesDir(Environment.DIRECTORY_PICTURES)=").append(getExternalFilesDir(Environment.DIRECTORY_PICTURES));
		sb.append("\ngetExternalFilesDir(null)=").append(getExternalFilesDir(null));
		sb.append("\ngetExternalFilesDir(\"xxx\")=").append(getExternalFilesDir("xxx"));
		sb.append("\ngetExternalCacheDir=").append(getExternalCacheDir());
		//requires API level 19
//		sb.append("\nrequires API level 19 getExternalFilesDirs(Environment.DIRECTORY_PICTURES)=").append(Arrays.toString(getExternalFilesDirs(Environment.DIRECTORY_PICTURES)));
//		sb.append("\nrequires API level 19 getExternalCacheDirs=").append(Arrays.toString(getExternalCacheDirs()));
		
		File file1 = new File("/sdcard/TEST");
		File file2 = new File("/storage/emulated/0/TEST");
		File file3 = new File("/storage/emulated/legacy/TEST");
		File file4 = new File("/storage/sdcard0/TEST");
		File file5 = new File("/mnt/sdcard/TEST");
		
		sb.append("\nfile1.getAbsolutePath()=").append(file1.getAbsolutePath());
		sb.append("\nfile2.getAbsolutePath()=").append(file2.getAbsolutePath());
		sb.append("\nfile3.getAbsolutePath()=").append(file3.getAbsolutePath());
		sb.append("\nfile4.getAbsolutePath()=").append(file4.getAbsolutePath());
		sb.append("\nfile5.getAbsolutePath()=").append(file5.getAbsolutePath());
		
		sb.append("\nfile1.exists()=").append(file1.exists());
		sb.append("\nfile2.exists()=").append(file2.exists());
		sb.append("\nfile3.exists()=").append(file3.exists());
		sb.append("\nfile4.exists()=").append(file4.exists());
		sb.append("\nfile5.exists()=").append(file5.exists());
		
		try {
			sb.append("\nfile1.getCanonicalPath()=").append(file1.getCanonicalPath());
			sb.append("\nfile2.getCanonicalPath()=").append(file2.getCanonicalPath());
			sb.append("\nfile3.getCanonicalPath()=").append(file3.getCanonicalPath());
			sb.append("\nfile4.getCanonicalPath()=").append(file4.getCanonicalPath());
			sb.append("\nfile5.getCanonicalPath()=").append(file5.getCanonicalPath());
		} catch(Exception e) {
			Log.e(TAG, "e=" + e);
		}
		
		String infoString = sb.toString();
		Log.i(TAG, infoString);
		info.setText(infoString);
	}

}
