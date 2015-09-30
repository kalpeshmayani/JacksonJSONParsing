package com.kpinfotech.webservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import android.content.ContentValues;
import android.util.Log;

public class WSRequestPost {

    static final String LOG_TAG = "WebServiceRequestPost";
    static final Lock lock = new ReentrantLock();
    static ObjectMapper mapper = null;

    HttpURLConnection connection;
    URL url;

    String result = "";

    public WSRequestPost(String url) {
        try {
            url = url.replace(" ", "");

            this.url = new URL(url);
            connection = (HttpURLConnection) this.url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <Request, Response> Response execute(
            ContentValues values, Class<Response> responseType, Request request) throws Exception {

        Response response = null;

        try {
            OutputStream os = connection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(getPostData(values));

            bw.flush();
            bw.close();
            os.close();
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = br.readLine()) != null) {
                    result += line;
                }

                response = getMapper().readValue(result, responseType);
            } else {
                result = "";
                response = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "";
            response = null;
        }

        return response;
    }

    private String getPostData(ContentValues values) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Set<Map.Entry<String, Object>> set = values.valueSet();

        for (Map.Entry me : set) {
            if (first)
                first = false;
            else
                result.append("&");

            String key = me.getKey().toString();
            String value = me.getValue().toString();

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value, "UTF-8"));
        }

        return result.toString();
    }

    protected synchronized ObjectMapper getMapper() {

        if (mapper != null) {
            return mapper;
        }
        try {
            lock.lock();
            if (mapper == null) {
                mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            }
            lock.unlock();
        } catch (Exception e) {
            if (e != null)
                Log.e(LOG_TAG, "Mapper Initialization Failed. Exception :: " + e.getMessage());
        }

        return mapper;
    }

}