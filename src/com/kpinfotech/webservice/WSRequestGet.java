package com.kpinfotech.webservice;

import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class WSRequestGet {

	private static final String LOG_TAG = "WebServiceRequestGet";

	private static final Lock lock = new ReentrantLock();

	private HttpGet httpGet;

	private HttpClient client;

	private String url;

	private HttpResponse response;

	private static ObjectMapper mapper = null;

	String data;

	public WSRequestGet(String _url) {
		client = new DefaultHttpClient();
		this.url = _url;
	}

	public <CLS> CLS execute(Class<CLS> responseType) throws Exception {
		CLS returnClass = null;
		InputStream is = null;

		try {

			if (Log.isLoggable(LOG_TAG, Log.INFO)) {
				Log.i(LOG_TAG, "URL :: " + url);
			}

			// url = URLEncoder.encode(url, "utf-8");
			url = url.replace(" ", "%20");
			httpGet = new HttpGet(url);

			// Send Request
			response = client.execute(httpGet);

			// Operation Aborted
			if (httpGet.isAborted()) {
				throw new Exception(String.format("Operation [%s] is aborted.",
						url));
			}

			// Read Response
			HttpEntity httpEntity = response.getEntity();

			// Build Return Object
			if (httpEntity != null && responseType != Void.class) {

				data = EntityUtils.toString(httpEntity);

				Log.i(LOG_TAG, "Response :: " + data);

				returnClass = getMapper().readValue(data, responseType);
				Log.i(LOG_TAG, "URL :: " + url + " Completed");

			}
		} catch (Exception ex) {
			if (ex != null)
				Log.e(LOG_TAG, "Exception :: " + ex.toString());
			returnClass = null;

			throw ex;
		} finally {

			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {
					throw ex;
				}
			}
			httpGet = null;
		}
		return returnClass;
	}

	public void abort() throws Exception {

		if (httpGet != null) {
			httpGet.abort();
			httpGet = null;
		}
	}

	protected synchronized ObjectMapper getMapper() {

		if (mapper != null) {
			return mapper;
		}
		try {
			lock.lock();
			if (mapper == null) {
				mapper = new ObjectMapper();
				mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,false);
			}
			lock.unlock();
		} catch (Exception ex) {
			if (ex != null)
				Log.e(LOG_TAG, "Mapper Initialization Failed. Exception :: "
						+ ex.getMessage());
		}

		return mapper;
	}
}