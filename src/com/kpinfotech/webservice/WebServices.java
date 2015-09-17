package com.kpinfotech.webservice;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.kpinfotech.asynctask.AsyncGetService;
import com.kpinfotech.asynctask.AsyncPostService;
import com.kpinfotech.global.WSConstant;

import android.app.Activity;

public class WebServices {
	
	@SuppressWarnings("deprecation")
	public void getFileList(Activity activity, String queryid) {

        ArrayList<NameValuePair> namevalue = new ArrayList<NameValuePair>();
        namevalue.add(new BasicNameValuePair("queryid", queryid));

        new AsyncPostService(activity, WSConstant.RT_SENTFILES, namevalue, true).execute(WSConstant.WS_SENTFILES);
    }

    public void getSecurityQuestion(Activity activity, String responseType, String url) {
        new AsyncGetService(activity, responseType, true).execute(url);
    }

}