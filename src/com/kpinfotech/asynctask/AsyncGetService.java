package com.kpinfotech.asynctask;

import com.kpinfotech.global.AppConstant;
import com.kpinfotech.global.AppMethod;
import com.kpinfotech.interfaces.WSAsync;
import com.kpinfotech.model.ResponseObject;
import com.kpinfotech.webservice.WSRequest;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

public class AsyncGetService extends AsyncTask<String, Void, Object> {
	
	private final String LOG_TAG = "AsyncGetService";

    Activity activity;
    Fragment fragment;
    
    String serviceType;
    boolean isLoaderEnable;

    Exception error = null;

    WSAsync i_WSAsync;

    public AsyncGetService(Activity activity, String serviceType, boolean isLoaderEnable) {
    	this.i_WSAsync = (WSAsync) activity;
        this.activity = activity;
        this.serviceType = serviceType;
        this.isLoaderEnable = isLoaderEnable;
    }

    public AsyncGetService(Activity activity, Fragment fragment, String serviceType, boolean isLoaderEnable) {
    	this.i_WSAsync = (WSAsync) fragment;
        this.activity = activity;
        this.fragment = fragment;
        this.serviceType = serviceType;
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
            return new WSRequest().getGetRequest(params[0], ResponseObject.class);
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