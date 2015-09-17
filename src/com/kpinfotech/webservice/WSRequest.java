package com.kpinfotech.webservice;

import java.util.List;

import org.apache.http.NameValuePair;

public class WSRequest {

	/** Get Request **/
	public <CLS> CLS getGetRequest(String url, Class<CLS> cls) throws Exception {
		return new WSRequestGet(url).execute(cls);
	}

	/** Post Request **/
	public <CLS> CLS getPostRequest(String url, Class<CLS> cls, Object reqCls, List<NameValuePair> nameValuePairs) throws Exception {
		return new WSRequestPost(url).execute(nameValuePairs, cls, reqCls);
	}

}