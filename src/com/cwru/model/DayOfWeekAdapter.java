package com.cwru.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DayOfWeekAdapter extends BaseAdapter implements OnClickListener {
	private final List<String> list;
	private Button gridcell;
	private final Context context;
	
	public DayOfWeekAdapter(Context context)
	{
		super();
		this.context = context;
		this.list = new ArrayList<String>();
		list.add("SUN");
		list.add("MON");
		list.add("TUE");
		list.add("WED");
		list.add("THU");
		list.add("FRI");
		list.add("SAT");

	}

	public String getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override 
	public long getItemId(int position) {
		return position;
	}

	@Override 
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv;
		if (convertView == null) {
			tv = new TextView(context);
		} else {
			tv = (TextView) convertView;
		}
		
		// Get Reference to the day grid cell
		
		// Color past and next motnths gray
		// Date String ==> DD-COLOR-MM-YY
		Log.d("Steve", "" + list.get(position));
		String day = list.get(position);
		tv.setText(day);
		tv.setTextSize(10);
		tv.setGravity(Gravity.CENTER);	
		
		return tv;

	}
	
	@Override
	public void onClick(View view) {
		String dateSelected = (String) view.getTag();
		Log.d("Steve", "Date Selected: " + dateSelected);
	}
}