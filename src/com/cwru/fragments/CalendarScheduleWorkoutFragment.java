package com.cwru.fragments;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.cwru.R;
import com.cwru.controller.CalendarActivity;
import com.cwru.controller.HomeScreen;
import com.cwru.controller.WorkoutListingActivity;
import com.cwru.dao.DbAdapter;
import com.cwru.fragments.CalendarFragment.returnDateListener;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;

public class CalendarScheduleWorkoutFragment extends Fragment {
	private DbAdapter mDbHelper;
	private String date;
	private int month;
	private int year;
	private int day;
	private Context context;
	private static returnWorkoutListener listenerWorkoutReturn;
	
	private Spinner spnRepeatOptions;
	private RadioGroup rgScheduleOptions;
	private RadioButton rbNumOccurances;
	private RadioButton rbScheduleByDate;
	private LinearLayout llNumOccurancesContainer;
	private EditText etNumOccurances;
	private Button btnEndWorkoutDate;
	private Button btnWorkoutSelector;
	private Button btnCalendarWorkoutScheduleWorkout;
	private CheckBox sunday;
	private CheckBox monday;
	private CheckBox tuesday;
	private CheckBox wednesday;
	private CheckBox thursday;
	private CheckBox friday;
	private CheckBox saturday;
	
	public CalendarScheduleWorkoutFragment(Context context, String date) {
		this.context = context;
		mDbHelper = new DbAdapter(context);
		this.date = date;
		String [] dateSplit = date.split("/");
		month = Integer.parseInt(dateSplit[1]);
		day = Integer.parseInt(dateSplit[2]);
		year = Integer.parseInt(dateSplit[0]);
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}		
		Log.d("Steve", "WorkoutListingFragment");
		View view = (ScrollView) inflater.inflate(R.layout.calendar_workout_schedule, container, false);

		spnRepeatOptions = (Spinner) view.findViewById(R.id.spnCalendarWorkoutScheduleRepeatsWeek);
		rgScheduleOptions = (RadioGroup) view.findViewById(R.id.rgCalendarWorkoutScheduleRepeatRadioButtons);
		rbNumOccurances = (RadioButton) view.findViewById(R.id.rbCalendarWorkoutScheduleNumOccurances);
		rbScheduleByDate = (RadioButton) view.findViewById(R.id.rbCalendarWorkoutScheduleEndOnDate);
		llNumOccurancesContainer = (LinearLayout) view.findViewById(R.id.llCalendarWorkoutScheduleNumOccurances);
		etNumOccurances = (EditText) view.findViewById(R.id.etCalendarWorkoutScheduleNumOccurances);
		btnEndWorkoutDate = (Button) view.findViewById(R.id.btnCalendarWorkoutScheduleEndDate);
		btnWorkoutSelector = (Button) view.findViewById(R.id.btnCalendarWorkoutScheduleGetWorkout);
		btnCalendarWorkoutScheduleWorkout = (Button) view.findViewById(R.id.btnCalendarWorkoutScheduleWorkout);
		
		btnWorkoutSelector.setOnClickListener(getWorkoutListener);
		btnEndWorkoutDate.setOnClickListener(getEndDateListener);
		
		sunday = new CheckBox(this.getActivity());
		monday = new CheckBox(this.getActivity());
		tuesday = new CheckBox(this.getActivity());
		wednesday = new CheckBox(this.getActivity());
		thursday = new CheckBox(this.getActivity());
		friday = new CheckBox(this.getActivity());
		saturday = new CheckBox(this.getActivity());
		
		Configuration c = this.getResources().getConfiguration();
		/* Lanscape View */
		if (c.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Log.d("Steve", "Horizontal1");
			/* Place Checkboxes all in one row */
			TableLayout tl = (TableLayout)  view.findViewById(R.id.tlCalendarWorkoutScheduleCheckBoxTable);
			TableRow tr = new TableRow(this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			sunday.setText("S");
			sunday.setTag("chkRepeatSunday");
			tr.addView(sunday);

			monday.setText("M");
			monday.setTag("chkRepeatMonday");
			tr.addView(monday);
			
			tuesday = new CheckBox(this.getActivity());
			tuesday.setText("T");
			tuesday.setTag("chkRepeatTuesday");
			tr.addView(tuesday);
			
			wednesday.setText("W");
			wednesday.setTag("chkRepeatWednesday");
			tr.addView(wednesday);

			thursday.setText("R");
			thursday.setTag("chkRepeatThursday");
			tr.addView(thursday);
			
			friday.setText("F");
			friday.setTag("chkRepeatFriday");
			tr.addView(friday);
			
			saturday.setText("S");
			saturday.setTag("chkRepeatSaturday");
			tr.addView(saturday);

			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		} 
		/* Portrait View */
		else {
			/* Place Checkboxes in two rows */
			Log.d("STEVE", "PORTRAIT VIEW ");
			LinearLayout l1 = new LinearLayout(this.getActivity());
			l1.setOrientation(LinearLayout.HORIZONTAL);
			l1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			LinearLayout l2 = new LinearLayout(this.getActivity());
			l2.setOrientation(LinearLayout.HORIZONTAL);
			l2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			sunday.setText("S");
			sunday.setTag("chkRepeatSunday");
			l1.addView(sunday);

			monday.setText("M");
			monday.setTag("chkRepeatMonday");
			l1.addView(monday);
			
			tuesday = new CheckBox(this.getActivity());
			tuesday.setText("T");
			tuesday.setTag("chkRepeatTuesday");
			l1.addView(tuesday);
			
			wednesday.setText("W");
			wednesday.setTag("chkRepeatWednesday");
			l2.addView(wednesday);

			thursday.setText("R");
			thursday.setTag("chkRepeatThursday");
			l2.addView(thursday);
			
			friday.setText("F");
			friday.setTag("chkRepeatFriday");
			l2.addView(friday);
			
			saturday.setText("S");
			saturday.setTag("chkRepeatSaturday");
			l2.addView(saturday);
			
			LinearLayout llcontainer = (LinearLayout) view.findViewById(R.id.llCalendarWorkoutScheduleDayCheckboxContainer);
			llcontainer.addView(l1);
			llcontainer.addView(l2);
			
		}
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.workoutRepeatWeeks, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spnRepeatOptions.setAdapter(adapter);
	    
	    rbNumOccurances.setOnClickListener(radioButtonListener);
	    rbScheduleByDate.setOnClickListener(radioButtonListener);	    
	    btnCalendarWorkoutScheduleWorkout.setOnClickListener(scheduleListener);
		
		return view;
	}
	
	View.OnClickListener getEndDateListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(CalendarScheduleWorkoutFragment.this.getActivity(), CalendarActivity.class);
			intent.putExtra("RETURN-DATE", true);
			startActivityForResult(intent,2);
		}
	};
	
	View.OnClickListener getWorkoutListener = new View.OnClickListener() {
	
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(CalendarScheduleWorkoutFragment.this.getActivity(), WorkoutListingActivity.class);
			intent.putExtra("RETURN-WORKOUT", true);
			Log.d("Steve", "Get Workout =---------------------------");
			startActivityForResult(intent,1);			
		}
	};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Log.d("STEVE", "REquest Code: " + requestCode);
		Log.d("Steve", "On Result Return");
		if (resultCode == 1 && requestCode == 1) {
			Log.d("Steve", "inside if");
			if (intent.hasExtra("RETURN-WORKOUT")) {
				Log.d("Steve", "inside if2");				
				btnWorkoutSelector.setText(intent.getExtras().getString("RETURN-WORKOUT"));
			}
		}
		if (requestCode == 2) {
			if (intent.hasExtra("DATE-SELECTED")) {
				btnEndWorkoutDate.setText(intent.getExtras().getString("DATE-SELECTED"));
			}
		}
	}
	
	public interface returnWorkoutListener {
		void returnSelectedDate(String dateSelected);
	}
	
	public static void setGetWorkoutListener(returnWorkoutListener listener) {
		CalendarScheduleWorkoutFragment.listenerWorkoutReturn = listener;
	}
	
	View.OnClickListener scheduleListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			String workoutName = btnWorkoutSelector.getText().toString();
			int workoutId = mDbHelper.getWorkoutIdFromName(workoutName);
			// Get Repeat Weeks 
			String workoutRepeatWeeks = (String) spnRepeatOptions.getSelectedItem();
			
			String repeatDays = "";
			
			int repeatSunday = 1;
			int repeatMonday = 1;
			int repeatTuesday = 1;
			int repeatWednesday = 1;
			int repeatThursday = 1;
			int repeatFriday = 1;
			int repeatSaturday = 1;
						
			if (sunday.isChecked()) { repeatSunday = 0; }
			if (monday.isChecked()) { repeatMonday = 0; }
			if (tuesday.isChecked()) { repeatTuesday = 0; }
			if (wednesday.isChecked()) { repeatWednesday = 0; }
			if (thursday.isChecked()) { repeatThursday = 0; }
			if (friday.isChecked()) { repeatFriday = 0; }
			if (saturday.isChecked()) { repeatSaturday = 0; }
			
			Log.d("Steve", "Num OCcur: " + rbNumOccurances.isChecked());
			Log.d("Steve", "Schedule by Date: " + rbScheduleByDate.isChecked());
			
			if (rbNumOccurances.isChecked()) {
				Log.d("Steve", "NUM OCCURANCES IS CHECKED");
				int repeatNumber = Integer.parseInt(etNumOccurances.getText().toString());
				createCalendarEntryNumOccurances(workoutId, workoutRepeatWeeks, repeatSunday, repeatMonday, repeatTuesday,
												 repeatWednesday, repeatThursday, repeatFriday, repeatSaturday, repeatNumber);
			} 
			// Date Checked
			else if (rbScheduleByDate.isChecked()) {
					String endDate = btnEndWorkoutDate.getText().toString();
					createCalendarEntryByEndDate(workoutId, workoutRepeatWeeks, repeatSunday, repeatMonday, repeatTuesday, 
												 repeatWednesday, repeatThursday, repeatFriday, repeatSaturday, endDate);
			}
		}
	};
	
	public void createCalendarEntryNumOccurances(int workoutId, String repeats, int repeatSunday,
			int repeatMonday, int repeatTuesday, int repeatWednesday, int repeatThursday,
			int repeatFriday, int repeatSaturday, int numRepeats) {
		ArrayList<String> records = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, (month -1));
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		boolean insert = false;
		Log.d("Steve", "=======================================================");
		int counter = 1;
		while (counter <= numRepeats) {
			String date = dateFormat.format(calendar.getTime());
			Log.d("Steve", "Date: " + date);
			switch (calendar.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.SUNDAY:
					if (repeatSunday == 0) {
						insert = true;
					}
					break;
				case Calendar.MONDAY:
					if (repeatMonday == 0) {
						insert = true;
					}
					break;
				case Calendar.TUESDAY:
					if (repeatTuesday == 0) {
						insert = true;
					}
					break;
				case Calendar.WEDNESDAY:
					if (repeatWednesday == 0) { 
						insert = true;
					}
					break;
				case Calendar.THURSDAY:
					if (repeatThursday == 0) {
						insert = true;
					}
					break;
				case Calendar.FRIDAY:
					if (repeatFriday == 0) {
						insert = true;
					}
					break;
				case Calendar.SATURDAY:
					if (repeatSaturday == 0) {
						insert = true;
					}
					break;
			}
			
			// does date 
			boolean dateScheduled = mDbHelper.isWorkoutScheduled(date, workoutId);
			
			if (insert && !dateScheduled) {
				
				// is that workout already scheduled on date?
				String insertQuery = "insert into calendar (date, workout_id) values ('" + date + "'," + workoutId + ")";
				records.add(insertQuery);
				counter++;
			}
			
			insert = false;

			/* Weekly, Bi-weekly, 3 weeks, 4 weeks */
			// only check after we do a week full of testing
			if ( calendar.get(Calendar.DAY_OF_WEEK)== 1) {
				if (repeats.equals("Weekly")) {
					// do nothing
				} else if (repeats.equals("Bi-weekly")) {
					// add 7 days
					calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 7);
					
				} else if (repeats.equals("3 weeks")) {
					// add 14 days
					calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 14);
				} else if (repeats.equals("4 weeks")) {
					// add 21 days
					calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 21);
				}
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);			

				continue;
			}
			
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);		
		}
		
		// insert records into the db
		mDbHelper.createCalendarEntry(records);
		
	}
	
	public void createCalendarEntryByEndDate(int workoutId, String repeats, int repeatSunday,
			int repeatMonday, int repeatTuesday, int repeatWednesday, int repeatThursday,
			int repeatFriday, int repeatSaturday, String untilDate) {
		// End Date is in form mm-dd-yyyy
		String [] dateFields = untilDate.split("/");
		String endDate = dateFields[2] + "/" + dateFields[0] + "/" + dateFields[1];
		
		ArrayList<String> records = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, (month -1));
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		boolean insert = false;
		String date = dateFormat.format(calendar.getTime());
		Log.d("Steve", "Date: " + date);
		while (continueInDateAdd(date, endDate)) {
			insert = false;
			date = dateFormat.format(calendar.getTime());
			Log.d("Steve", "Date2: " + date);
			switch (calendar.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.SUNDAY:
					if (repeatSunday == 0) {
						insert = true;
					}
					break;
				case Calendar.MONDAY:
					if (repeatMonday == 0) {
						insert = true;
					}
					break;
				case Calendar.TUESDAY:
					if (repeatTuesday == 0) {
						insert = true;
					}
					break;
				case Calendar.WEDNESDAY:
					if (repeatWednesday == 0) { 
						insert = true;
					}
					break;
				case Calendar.THURSDAY:
					if (repeatThursday == 0) {
						insert = true;
					}
					break;
				case Calendar.FRIDAY:
					if (repeatFriday == 0) {
						insert = true;
					}
					break;
				case Calendar.SATURDAY:
					if (repeatSaturday == 0) {
						insert = true;
					}
					break;
			}
			
			Log.d("Steve", "Date : " + date + " | Insert: " + insert);
			boolean dateScheduled = mDbHelper.isWorkoutScheduled(date, workoutId);
			if (insert && !dateScheduled) {
				String insertQuery = "insert into calendar (date, workout_id) values ('" + date + "'," + workoutId + ")";
				records.add(insertQuery);
			}
			
			/* Weekly, Bi-weekly, 3 weeks, 4 weeks */
			// only check after we do a week full of testing
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
				if (repeats.equals("Weekly")) {
					// do nothing
				} else if (repeats.equals("Bi-weekly")) {
					// add 7 days
					calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 7);
					
				} else if (repeats.equals("3 weeks")) {
					// add 14 days
					calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 14);
				} else if (repeats.equals("4 weeks")) {
					// add 21 days
					calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 21);
				}
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);			

				continue;
			}
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);			


		}
		
		Log.d("Steve", "Records Size: " + records.size());
		// insert records into the db
		mDbHelper.createCalendarEntry(records);
		
	}
	
	public boolean continueInDateAdd(String date1, String date2) {
		String [] dt1 = date1.split("/");
		String [] dt2 = date2.split("/");
		int m1 = Integer.parseInt(dt1[1])-1;
		int m2 = Integer.parseInt(dt2[1])-1;
		int y1 = Integer.parseInt(dt1[0]);
		int y2 = Integer.parseInt(dt2[0]);
		int d1 = Integer.parseInt(dt1[2]);
		int d2 = Integer.parseInt(dt2[2]);
		
		Date current = new Date(y1,m1,d1);
		Date end = new Date(y2,m2,d2);
		
		if (current.before(end)) {
			return true;
		} else {
			return false;
		}		
		
	}
	
	
	View.OnClickListener radioButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// NumOccurances Checked
			if (rbNumOccurances.isChecked()) {
				llNumOccurancesContainer.setVisibility(LinearLayout.VISIBLE);
				btnEndWorkoutDate.setVisibility(Button.GONE);
			} 
			// Date Checked
			else if (rbScheduleByDate.isChecked()) {
				llNumOccurancesContainer.setVisibility(LinearLayout.GONE);
				btnEndWorkoutDate.setVisibility(Button.VISIBLE);
				
			}
		}
	};
}
