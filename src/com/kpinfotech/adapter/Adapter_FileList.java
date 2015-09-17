package com.kpinfotech.adapter;

import java.util.ArrayList;

import com.kpinfotech.global.AppConstant;
import com.kpinfotech.global.AppMethod;
import com.kpinfotech.jacksonjsonparsing.R;
import com.kpinfotech.model.Files;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_FileList extends BaseAdapter {
	
	LayoutInflater mInflater;
	Activity activity;

	ArrayList<Files> fileList;
	
	public Adapter_FileList(Activity activity, ArrayList<Files> fileList) {
		this.mInflater = LayoutInflater.from(activity);
		this.activity = activity;
		this.fileList = fileList;
	}

	@Override
	public int getCount() {
		return fileList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	public class Holder {
		TextView tvname, tvtype, tvudate;
		LinearLayout llbg;
	}

	public void intialize(View ConvertView, Holder holder) {
		holder.tvname = (TextView) ConvertView.findViewById(R.id.tvname);
		holder.tvtype = (TextView) ConvertView.findViewById(R.id.tvtype);
		holder.tvudate = (TextView) ConvertView.findViewById(R.id.tvudate);
		holder.llbg = (LinearLayout) ConvertView.findViewById(R.id.llbg);
	//	holder.llbg.getBackground().setAlpha(150);
		
		holder.tvname.setSelected(true);
		holder.tvtype.setSelected(true);
		holder.tvudate.setSelected(true);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final Holder holder;
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_filelist, null);
			holder = new Holder();
			intialize(convertView, holder);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		String title = fileList.get(position).getFilename();
		holder.tvname.setText(AppMethod.getString(title, AppConstant.EMPTY_STRING));
		
		String course = fileList.get(position).getFiletype();
		holder.tvtype.setText(AppMethod.getString(course, AppConstant.EMPTY_STRING));
		
		String rdate = fileList.get(position).getUploadeddate();
		holder.tvudate.setText(AppMethod.getString(rdate, AppConstant.EMPTY_STRING));
		
		OnClickListener onclick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				
				case R.id.llbg:
					break;
				
				default:
					break;
				}
			}
		};
		
		holder.llbg.setOnClickListener(onclick);

		return convertView;
	}

}