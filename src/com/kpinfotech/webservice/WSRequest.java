package com.kpinfotech.webservice;

import android.content.ContentValues;

public class WSRequest {

	/** Get Request **/
	public <CLS> CLS getGetRequest(String url, Class<CLS> cls) throws Exception {
		return new WSRequestGet(url).execute(cls);
	}

	/** Post Request **/
	public <CLS> CLS getPostRequest(String url, Class<CLS> cls, Object reqCls, ContentValues values) throws Exception {
		return new WSRequestPost(url).execute(values, cls, reqCls);
	}

}