package com.kpinfotech.activity;

import java.util.ArrayList;

import com.kpinfotech.adapter.Adapter_FileList;
import com.kpinfotech.global.AppConstant;
import com.kpinfotech.global.AppMethod;
import com.kpinfotech.global.WSConstant;
import com.kpinfotech.interfaces.WSAsync;
import com.kpinfotech.jacksonjsonparsing.R;
import com.kpinfotech.model.Files;
import com.kpinfotech.model.ResponseObject;
import com.kpinfotech.webservice.WebServices;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FileList extends Activity implements OnClickListener, OnRefreshListener, WSAsync {
	
	Activity activity;
	
	TextView tvtitle;
	Button btnleft, btnright;
	
	ListView lvlist;
	SwipeRefreshLayout swipeLayout;
	
	String userId;
	WebServices ws;
	
	ArrayList<Files> fileList;
	
	Adapter_FileList a_FileList;
	String queryId = "35";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_filelist);
		
		init();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		default:
			break;
		}
	}
	
	private void init() {
		activity = (Activity) FileList.this;
		userId = AppMethod.getStringPreference(activity, AppConstant.PREF_USERID);
		ws = new WebServices();
		
		btnleft = (Button) findViewById(R.id.btnleft);
		btnleft.setVisibility(View.INVISIBLE);
		
		btnright = (Button) findViewById(R.id.btnright);
		btnright.setVisibility(View.INVISIBLE);
		
		tvtitle = (TextView) findViewById(R.id.tvtitle);
		tvtitle.setSelected(true);
		tvtitle.setText("File list");
		
		lvlist = (ListView) findViewById(R.id.lvlist);
		
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);
		
		getFileList();
	}
	
	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeLayout.setRefreshing(false);
                getFileList();
            }
        }, AppConstant.PULL_TO_REFRESH);
	}
	
	private void getFileList() {
		if (AppMethod.isNetworkConnected(activity)) {
			ws.getFileList(activity, queryId);
		} else
			AppMethod.showToast(activity, AppConstant.NO_INTERNET_CONNECTION);
	}

	@Override
	public void onWSResponse(String serviceType, Object data, Exception error) {
		
		ResponseObject responseObj = (ResponseObject) data;
		
		if (error == null) {
			if(responseObj != null) {
				
				if (serviceType.equalsIgnoreCase(WSConstant.RT_SENTFILES)) {
					AppMethod.dismissProgressDialog(activity);
					
					if (responseObj.getStatus().equalsIgnoreCase(AppConstant.success)) {
						AppMethod.showToast(activity, responseObj.getStatus());
						updateViews(responseObj);
					} else {
						AppMethod.showToast(activity, responseObj.getStatus());
						fileList =  new ArrayList<Files>();
						
						a_FileList = new Adapter_FileList(activity, fileList);
						lvlist.setAdapter(a_FileList);
						a_FileList.notifyDataSetChanged();
					}
				}
				
				
			} else {
				AppMethod.showToast(activity, AppConstant.SOMETHING_WRONG_TRY_AGAIN);
			}
		} else
			AppMethod.showToast(activity, error.getLocalizedMessage());

	}
	
	private void updateViews(ResponseObject responseObj) {
		
		fileList = responseObj.getFileList();
		
		a_FileList = new Adapter_FileList(activity, fileList);
		lvlist.setAdapter(a_FileList);
		a_FileList.notifyDataSetChanged();

	}

}