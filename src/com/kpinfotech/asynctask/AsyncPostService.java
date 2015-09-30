package com.kpinfotech.asynctask;

import com.kpinfotech.global.AppConstant;
import com.kpinfotech.global.AppMethod;
import com.kpinfotech.interfaces.WSAsync;
import com.kpinfotech.model.ResponseObject;
import com.kpinfotech.webservice.WSRequest;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

public class AsyncPostService extends AsyncTask<String, Void, Object> {

	private final String LOG_TAG = "AsyncPostService";

	Activity activity;
	Fragment fragment;
	
	String serviceType;
	ContentValues values;
	boolean isLoaderEnable;
	
	Exception error = null;

	WSAsync i_WSAsync;

	public AsyncPostService(Activity activity, String serviceType, ContentValues values, boolean isLoaderEnable) {
		this.i_WSAsync = (WSAsync) activity;
		this.activity = activity;
		this.serviceType = serviceType;
		this.values = values;
		this.isLoaderEnable = isLoaderEnable;
	}

	public AsyncPostService(Activity activity, Fragment Fragment, String serviceType, ContentValues values, boolean isLoaderEnable) {
		this.i_WSAsync = (WSAsync) Fragment;
		this.activity = activity;
		this.fragment = Fragment;
		this.serviceType = serviceType;
		this.values = values;
		this.isLoaderEnable = isLoaderEnable;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		if (isLoaderEnable)
			AppMethod.showProgressDialog(activity, AppConstant.PLEASE_WAIT);
	}

	@Override
	protected Object doInBackground(String... params) {

		try {
			return new WSRequest().getPostRequest(params[0], ResponseObject.class, null, values);
		} catch (Exception e) {
			this.error = e;
            Log.e(LOG_TAG, e.getMessage());
		}

		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);

        if (isLoaderEnable) {
            AppMethod.dismissProgressDialog(activity);
        }
        
        i_WSAsync.onWSResponse(serviceType, result, error);
	}

}