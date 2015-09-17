package com.kpinfotech.global;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class AppMethod {
	
	public static final String PREFS_NAME = "AppName";
	public static ProgressDialog p_Dialog;

	
	// to check whether internet is connected or not
	public static boolean isNetworkConnected(Activity activity) {
		ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	
	// display toast
	public static void showToast(Activity activity, String msg) {
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
	
	
	// show progress
	public static void showProgressDialog(Activity activity, String msg) {
		p_Dialog = new ProgressDialog(activity);
		p_Dialog.setMessage(msg);
		p_Dialog.setCanceledOnTouchOutside(false);
		p_Dialog.show();
	}

	
	// dismiss progress
	public static void dismissProgressDialog(Activity activity) {
		if (p_Dialog != null && p_Dialog.isShowing())
			p_Dialog.dismiss();
	}
	

	// string preference
	public static boolean setStringPreference(Activity activity, String key, String value) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	public static String getStringPreference(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String value = settings.getString(key, "");
		return value;
	}
	

	// int preference
	public static boolean setIntegerPreference(Activity activity, String key, int value) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	public static int getIntegerPreference(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		int value = settings.getInt(key, -1);
		return value;
	}
	
	
	// boolean preference
	public static boolean setBooleanPreference(Activity activity, String key, Boolean value) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
	public static Boolean getBooleanPreference(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Boolean value = settings.getBoolean(key, false);
		return value;
	}

    
	// check whether string is valid or not
	public static Boolean isStringValid(String text) {
		if(text != null && !text.equalsIgnoreCase("null") && !text.equalsIgnoreCase(""))
			return true;
		else
			return false;
	}
    
	
	// return valid string text else defaultText
    public static String getString(String text, String defaultText) {
		if(text != null && !text.equalsIgnoreCase("null") && !text.equalsIgnoreCase(""))
			return text;
		else
			return defaultText;
	}
    
}