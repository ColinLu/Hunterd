package com.colin.hunter.help;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastHelp {
	private static Handler handler = new Handler(Looper.getMainLooper());
	private static Toast toast = null;
	private static Object synObj = new Object();

	/**
	 * Toast短时间显示消息
	 * 
	 * @param context
	 * @param message
	 */
	public static void showToast(final Context context, final String message) {
		showToast(context, message, Toast.LENGTH_SHORT);
	}

	/**
	 * Toast长时间显示消息
	 * 
	 * @param context
	 * @param message
	 */
	public static void showToastLong(final Context context, final String message) {
		showToast(context, message, Toast.LENGTH_LONG);
	}

	/**
	 * Toast短时间显示消息
	 * 
	 * @param context
	 * @param message
	 */
	public static void showToast(final Context context, final int message) {
		showToast(context, message, Toast.LENGTH_SHORT);
	}

	/**
	 * Toast长时间显示消息
	 * 
	 * @param context
	 * @param message
	 */
	public static void showToastLong(final Context context, final int message) {
		showToast(context, message, Toast.LENGTH_LONG);
	}

	/**
	 * 开启线程显示Toast提示
	 * 
	 * @param context
	 * @param message
	 * @param length
	 */
	public static void showToast(final Context context, final int message, final int length) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (synObj) {
							if (toast != null) {
								toast.cancel();
								toast.setText(String.valueOf(message));
								toast.setDuration(length);
							} else {
								toast = Toast.makeText(context, String.valueOf(message), length);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	/**
	 * 开启线程显示Toast提示
	 * 
	 * @param context
	 * @param message
	 * @param length
	 */
	public static void showToast(final Context context, final String message, final int length) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (synObj) {
							if (toast != null) {
								toast.cancel();
								toast.setText(message);
								toast.setDuration(length);
							} else {
								toast = Toast.makeText(context, message, length);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	/**
	 * * 关闭当前Toast
	 */
	public static void cancelCurrentToast() {
		if (toast != null) {
			toast.cancel();
		}
	}
}