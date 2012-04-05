package com.cwru.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.model.DayOfWeekAdapter;

public class CalendarFragment extends Fragment {
	private Button btnPrevMonth;
	private Button btnNextMonth;
	private TextView tvMonth;
	private GridView gvCalendar;
	private GridCellAdapter adapter;
	private GridView gvDaysOfWeek;
	
	private Calendar calendar;
	
	private static String [] a = {"1","2","3","4","5","6","7","8","9" };

	
	public CalendarFragment(Context context) {
		calendar = Calendar.getInstance();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		View view = (LinearLayout) inflater.inflate(R.layout.calendar_view, container, false);
		
		btnPrevMonth = (Button) view.findViewById(R.id.btnCalendarViewPrevMonth);
		btnNextMonth = (Button) view.findViewById(R.id.btnCalendarViewNextMonth);
		tvMonth = (TextView) view.findViewById(R.id.tvCalendarViewCurrentMonth);
		gvCalendar = (GridView) view.findViewById(R.id.gvCalendarViewCalendar);
		gvCalendar.setBackgroundColor(Color.LTGRAY);
		gvDaysOfWeek = (GridView) view.findViewById(R.id.gvCalendarViewDaysOfWeekHeading);

		
		DayOfWeekAdapter dowAdapter = new DayOfWeekAdapter(this.getActivity());
		gvDaysOfWeek.setAdapter(dowAdapter);
		
		adapter = new GridCellAdapter(this.getActivity(), R.id.btnCalendarDayGridCell);
		adapter.notifyDataSetChanged();
		gvCalendar.setAdapter(adapter);
		
		return view;
	}
	
	public ArrayList<String> getCalendarArray() {
		ArrayList<String> dates = new ArrayList<String>();
		/*
		// Add Day of Week Labels
		dates.add("S");
		dates.add("M");
		dates.add("T");
		dates.add("W");
		dates.add("R");
		dates.add("F");
		dates.add("S");
		*/
		// Set Day to first of month
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// Get Current Month
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		// Decrement until we hit sunday
		while (calendar.get(Calendar.DAY_OF_WEEK) != 1) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		}
		// Add prior month days
		// Date String ==> DD-COLOR-MM-YY
		int month = calendar.get(Calendar.MONTH);
		while (calendar.get(Calendar.MONTH) == month) {
			dates.add(calendar.get(Calendar.DAY_OF_MONTH) + "-GREY-" + month + "-" + calendar.get(Calendar.YEAR));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		// reset month
		month = calendar.get(Calendar.MONTH);
		// Add everything to array until we hit next month
		while (calendar.get(Calendar.MONTH) == month) {
			dates.add(calendar.get(Calendar.DAY_OF_MONTH) + "-WHITE-" + month + "-" + calendar.get(Calendar.YEAR));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		// Finish adding with dates of next month until we get to sunday
		while (calendar.get(Calendar.DAY_OF_WEEK) != 1) {
			dates.add(calendar.get(Calendar.DAY_OF_MONTH) + "-GREY-" + month + "-" + calendar.get(Calendar.YEAR));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		
		Log.d("Steve", "Dates SIZE: " + dates.size());
		return dates;
		
	}

	public class GridCellAdapter extends BaseAdapter implements OnClickListener {
		private final List<String> list;
		private Button gridcell;
		private final Context context;
		
		public GridCellAdapter(Context context, int textViewResourceId)
		{
			super();
			this.context = context;
			this.list = getCalendarArray();

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
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.calendar_day_gridcell, parent, false); 
			}
			// Get Reference to the day grid cell
			gridcell = (Button) row.findViewById(R.id.btnCalendarDayGridCell);
			gridcell.setOnClickListener(this);
			// Color past and next motnths gray
			// Date String ==> DD-COLOR-MM-YY
			Log.d("Steve", "" + list.get(position));
			String[] day_color = list.get(position).split("-");
			String day = day_color[0];
			String month = day_color[2];
			String year = day_color[3];
			gridcell.setTag(day + "-" + month + "-" + year);
			gridcell.setText(day);

			if (day_color[1].equals("GREY")) {
				gridcell.setTextColor(Color.LTGRAY);
			} else if (day_color[1].equals("WHITE")) {
				gridcell.setTextColor(Color.WHITE);
			}
			
			return row;
	
		}
		
		@Override
		public void onClick(View view) {
			String dateSelected = (String) view.getTag();
			Log.d("Steve", "Date Selected: " + dateSelected);
		}
	}
}
