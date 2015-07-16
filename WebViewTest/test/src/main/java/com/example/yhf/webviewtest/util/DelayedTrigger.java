package com.example.yhf.webviewtest.util;

import android.os.Handler;
import android.os.SystemClock;

/**
 * 高效的延迟触发执行
 */
public class DelayedTrigger implements Runnable {
	
	private Runnable target;
	private long delayMillis;
	private long execTime;
	private boolean isMsgSend = false;
	private Handler handler = new Handler();
	private Runnable innerRunnable = new Runnable() {
		
		@Override
		public void run() {
			long time = SystemClock.elapsedRealtime();
			if(time < execTime) {
				handler.postDelayed(this, execTime - time);
			} else {
				DelayedTrigger.this.run();
				isMsgSend = false;
			}
		}
	};
	
	public DelayedTrigger(long delayMillis) {
		if(delayMillis < 0) {
			this.delayMillis = 0;
		} else {
			this.delayMillis = delayMillis;
		}
	}
	
	public DelayedTrigger(Runnable runnable, long delayMillis) {
		this(delayMillis);
		target = runnable;
	}

	@Override
	public void run() {
		if(target != null) {
			target.run();
		}
	}
	
	/**
	 * 延迟触发
	 */
	public void postDelayed() {
		if(isMsgSend) {
			execTime = SystemClock.elapsedRealtime() + delayMillis;
		} else {
			handler.postDelayed(innerRunnable, delayMillis);
			isMsgSend = true;
		}
	}
	
	/**
	 * 清除正在等待的触发
	 */
	public void removeCallbacks() {
		if(isMsgSend) {
			handler.removeCallbacks(innerRunnable);
			isMsgSend = false;
		}
	}

}
