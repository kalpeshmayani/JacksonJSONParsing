package com.kpinfotech.webservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import android.util.Log;

public class WSRequestGet {

    static final String LOG_TAG = "WebServiceRequestGet";
    static final Lock lock = new ReentrantLock();
    static ObjectMapper mapper = null;

    HttpURLConnection connection;
    URL url;

    String result = "";

    public WSRequestGet(String url) {
        try {
            url = url.replace(" ", "");

            this.url = new URL(url);
            connection = (HttpURLConnection) this.url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true); // indicates POST method
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <CLS> CLS execute(Class<CLS> responseType) throws Exception {
        CLS returnClass = null;

        try {

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = br.readLine()) != null) {
                    result += line;
                }

                if (responseType != Void.class) {
                    returnClass = getMapper().readValue(result, responseType);
                }
            } else {
                result = "";
                returnClass = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "";
            returnClass = null;
        }

        return returnClass;
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