package com.example.directorytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SDBroadCastReceiver extends BroadcastReceiver {
	private static final String TAG = "SDBroadCastReceiverextends";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.i(TAG, "intent=" + intent + " action=" + action + " dat=" + intent.getDataString());
//		Toast.makeText(context, "SDBroadCastReceiverextends action=" + action + " dat=" + intent.getDataString(),
//				Toast.LENGTH_LONG).show();
	}
}