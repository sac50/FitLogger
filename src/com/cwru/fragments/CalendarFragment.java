package com.cwru.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
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
import com.cwru.dao.DbAdapter;
import com.cwru.model.DayOfWeekAdapter;

public class CalendarFragment extends Fragment {
	private Button btnPrevMonth;
	private Button btnNextMonth;
	private TextView tvMonth;
	private GridView gvCalendar;
	private GridCellAdapter adapter;
	private GridView gvDaysOfWeek;
	private boolean returnDate;
	private String dateWhereClause;
	private static returnDateListener listener;

	
	private Calendar calendar;
	
	private static String [] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August",
										   "September","October","November","December" };
	
	public CalendarFragment(Context context, boolean returnDate) {
		calendar = Calendar.getInstance();
		this.returnDate = returnDate;
		// Get Current Month
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		dateWhereClause = "where ";
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
		
		tvMonth.setText(monthNames[calendar.get(Calendar.MONTH)]);
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
		int month = calendar.get(Calendar.MONTH);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// Decrement until we hit sunday
		while (calendar.get(Calendar.DAY_OF_WEEK) != 1) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		}
		// Add prior month days
		// Date String ==> DD-COLOR-MM-YY
		if (month != calendar.get(Calendar.MONTH)) {
			while (calendar.get(Calendar.MONTH) == month) {
				dates.add((calendar.get(Calendar.MONTH)+1) + "-GREY-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR));
				/**
				 * TODO
				 */
				dateWhereClause += "date = '" + getDateInYearMonthForm(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)) + "' or ";
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
			}
		}
		// reset month
		month = calendar.get(Calendar.MONTH);
		// Add everything to array until we hit next month
		while (calendar.get(Calendar.MONTH) == month) {
			dates.add((calendar.get(Calendar.MONTH)+1) + "-WHITE-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR));
			dateWhereClause += "date = '" + getDateInYearMonthForm(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)) + "' or ";
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		// Finish adding with dates of next month until we get to sunday
		while (calendar.get(Calendar.DAY_OF_WEEK) != 1) {
			dates.add((calendar.get(Calendar.MONTH)+1) + "-GREY-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR));
			dateWhereClause += "date = '" + getDateInYearMonthForm(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)) + "' or ";
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		
		//Log.d("Steve", "Dates SIZE: " + dates.size());
		return dates;
		
	}
	
	public String getDateInYearMonthForm(int year, int month, int day) {
		month += 1;
		String date = year + "/";
		if (month < 10) { date += "0" + month + "/"; }
		else { date += month + "/"; }
		if (day < 10) { date += "0" + day; }
		else { date += day; }
		
		return date;
	}
	
	public String getDateInYearMonthFormCorrectMonth(int year, int month, int day) {
		String date = year + "/";
		if (month < 10) { date += "0" + month + "/"; }
		else { date += month + "/"; }
		if (day < 10) { date += "0" + day; }
		else { date += day; }
		
		return date;
	}

	public class GridCellAdapter extends BaseAdapter implements OnClickListener {
		private final List<String> list;
		private Button gridcell;
		private final Context context;
		private Hashtable<String, Boolean> workoutDateHash;
		private DbAdapter mDbHelper;
		
		public GridCellAdapter(Context context, int textViewResourceId)
		{
			super();
			mDbHelper = new DbAdapter(context);
			this.context = context;
			this.list = getCalendarArray();
			this.workoutDateHash = mDbHelper.getWorkoutDatesForCalendar(dateWhereClause.substring(0, dateWhereClause.length() - 3));

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
			//Log.d("Steve", "" + list.get(position));
			String[] day_color = list.get(position).split("-");
			String month = day_color[0];
			String day = day_color[2];
			String year = day_color[3];
			String hashDate = getDateInYearMonthFormCorrectMonth(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
			Log.d("Steve", "HashDate: " + hashDate);
			if (workoutDateHash.containsKey(hashDate)) {
				Log.d("Steve", "Workout Date Hash True");
				gridcell.setBackgroundColor(android.R.color.black);
			}
			gridcell.setTag(formatDate(month, day, year));
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
			if (returnDate) {
				listener.returnSelectedDate(dateSelected);
			}
			Log.d("Steve", "Date Selected: " + dateSelected);
		}
	}
	
	public interface returnDateListener {
		void returnSelectedDate(String dateSelected);
	}
	
	public static void setExerciseBankListener(returnDateListener listener) {
		CalendarFragment.listener = listener;
	}
	
	public String formatDate(String month, String day, String year) {
		int m = Integer.parseInt(month);
		int d = Integer.parseInt(day);
		int y = Integer.parseInt(year);
		
		String date = "";
		if ( m < 10) { date += "0" + m + "/";}
		else { date += m + "/"; }
		if (d < 10) { date += "0" + d + "/"; }
		else { date += d + "/"; }
		date += y;
		//Log.d("Steve", "Format Date: " + date);
		return date;		
	}
}
