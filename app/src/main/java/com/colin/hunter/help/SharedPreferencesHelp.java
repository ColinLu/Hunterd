package com.colin.hunter.help;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 本地存储数据工具类
 * 
 * @author Colin
 *
 */
public class SharedPreferencesHelp {
	private String name = "guru_sharedpreferences";
	private int mode = Context.MODE_PRIVATE;
	private static SharedPreferences.Editor editor = null;
	private static SharedPreferences sharedPreferences = null;

	private SharedPreferencesHelp() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("--SharedPreferencesHelp--cannot be instantiated--");
	}

	private static SharedPreferences getSharedPreferencesObject(Context context) {
		if (sharedPreferences == null)
			sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences;
	}

	private static SharedPreferences.Editor getEditorObject(Context context) {
		if (editor == null)
			editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		return editor;
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int get(Context context, String key, int defValue) {
		return getSharedPreferencesObject(context).getInt(key, defValue);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long get(Context context, String key, long defValue) {
		return getSharedPreferencesObject(context).getLong(key, defValue);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static Boolean get(Context context, String key, Boolean defValue) {
		return getSharedPreferencesObject(context).getBoolean(key, defValue);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static Boolean get(Context context, String key) {
		return getSharedPreferencesObject(context).getBoolean(key, false);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String get(Context context, String key, String defValue) {
		return getSharedPreferencesObject(context).getString(key, defValue);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static Boolean containKey(Context context, String key) {
		return getSharedPreferencesObject(context).contains(key);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void put(Context context, String key, int value) {
		getEditorObject(context).putInt(key, value).commit();
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void put(Context context, String key, long value) {
		getEditorObject(context).putLong(key, value).commit();
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void put(Context context, String key, Boolean value) {
		getEditorObject(context).putBoolean(key, value).commit();
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void put(Context context, String key, String value) {
		getEditorObject(context).putString(key, value).commit();
	}

	/**
	 * 
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		getEditorObject(context).remove(key).commit();
	}
}
