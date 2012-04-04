package com.cwru.fragments;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cwru.R;

public class CalendarFragment extends Fragment {
	private Button btnPrevMonth;
	private Button btnNextMonth;
	private TextView tvMonth;
	private GridView gvCalendar;
	
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
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getCalendarArray());
		gvCalendar.setAdapter(adapter);
		
		return view;
	}
	
	public String [] getCalendarArray() {
		ArrayList<String> dates = new ArrayList<String>();
		// Add Day of Week Labels
		dates.add("S");
		dates.add("M");
		dates.add("T");
		dates.add("W");
		dates.add("R");
		dates.add("F");
		dates.add("S");
		// Set Day to first of month
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// Get Current Month
		int month = calendar.get(Calendar.MONTH);
		// Decrement until we hit sunday
		while (calendar.get(Calendar.DAY_OF_WEEK) != 1) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		}
		// Add everything to array until we hit next month
		while (calendar.get(Calendar.MONTH) == month) {
			dates.add(calendar.get(Calendar.DAY_OF_MONTH) + "");
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		// Finish adding until we get to sunday
		while (calendar.get(Calendar.DAY_OF_WEEK) != 1) {
			dates.add(calendar.get(Calendar.DAY_OF_MONTH) + "");
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		
		return dates.toArray(new String [0]);
		
	}
}
