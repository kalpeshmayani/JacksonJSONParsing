package com.kpinfotech.webservice;

import com.kpinfotech.asynctask.AsyncGetService;
import com.kpinfotech.asynctask.AsyncPostService;
import com.kpinfotech.global.WSConstant;

import android.app.Activity;
import android.content.ContentValues;

public class WebServices {
	
	public void getFileList(Activity activity, String queryid) {
        
        ContentValues values = new ContentValues();
        values.put("queryid", queryid);

        new AsyncPostService(activity, WSConstant.RT_SENTFILES, values, true).execute(WSConstant.WS_SENTFILES);
    }

    public void getSecurityQuestion(Activity activity, String responseType, String url) {
        new AsyncGetService(activity, responseType, true).execute(url);
    }

}