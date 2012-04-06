package com.cwru.fragments;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.cwru.R;
import com.cwru.controller.CalendarActivity;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Workout;

/**
 * 
 * @author sacrilley
 * This fragment handles entering information about the workout user creating
 *
 */
public class CreateWorkoutInformationFragment extends Fragment {
	private EditText etWorkoutName;
	private Spinner spnWorkoutType;
	private Spinner spnRepeatWeeks;
	private CheckBox sunday;
	private CheckBox monday;
	private CheckBox tuesday;
	private CheckBox wednesday;
	private CheckBox thursday;
	private CheckBox friday;
	private CheckBox saturday;
	private RadioButton rgNumOccurances;
	private LinearLayout llNumOccurances;
	private EditText etNumOccurances;
	private RadioButton rgEndOnDate;
	private Button btnEndOnDate;
	private DbAdapter mDbHelper;
	private static onGoToExerciseBankListener listener;

	/**
	 * UI View associated with the fragment
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;			
		}
		View view =  (ScrollView) inflater.inflate(R.layout.create_workout_information, container, false);
		// Set DB Object
		mDbHelper = new DbAdapter(this.getActivity());
		/* Grab UI features */
		etWorkoutName = (EditText) view.findViewById(R.id.etWorkoutName);
		spnWorkoutType = (Spinner) view.findViewById(R.id.spnWorkoutType);
	    spnRepeatWeeks = (Spinner) view.findViewById(R.id.spnWorkoutRepeatsWeek);
		sunday = new CheckBox(this.getActivity());
		monday = new CheckBox(this.getActivity());
		tuesday = new CheckBox(this.getActivity());
		wednesday = new CheckBox(this.getActivity());
		thursday = new CheckBox(this.getActivity());
		friday = new CheckBox(this.getActivity());
		saturday = new CheckBox(this.getActivity());
		rgNumOccurances = (RadioButton) view.findViewById(R.id.rbCreateWorkoutNumOccurances);
		llNumOccurances = (LinearLayout) view.findViewById(R.id.llCreateWorkoutNumOccurances);
		etNumOccurances = (EditText) view.findViewById(R.id.etCreateWorkoutNumOccurances);
		rgEndOnDate = (RadioButton) view.findViewById(R.id.rbCreateWorkoutEndOnDate);
		btnEndOnDate = (Button) view.findViewById(R.id.btnCreateWorkoutEndDate);
		
		/* Set data for spinners type and repeat weeks */
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this.getActivity(), R.array.workoutTypes, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spnWorkoutType.setAdapter(adapter);
	    
	    adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.workoutRepeatWeeks, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spnRepeatWeeks.setAdapter(adapter);
	    
		Configuration c = this.getResources().getConfiguration();
		/* Lanscape View */
		if (c.orientation == Configuration.ORIENTATION_LANDSCAPE || HomeScreen.isTablet) {
			Log.d("Steve", "Horizontal1");
			/* Place Checkboxes all in one row */
			TableLayout tl = (TableLayout)  view.findViewById(R.id.tlCreateWorkoutInformationCheckBoxTable);
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
			
			LinearLayout llcontainer = (LinearLayout) view.findViewById(R.id.llCreateWorkoutInformationContainer);
			llcontainer.addView(l1);
			llcontainer.addView(l2);
			
		}
		
		// Button to Create Workout
		Button button = new Button(this.getActivity());
		button.setText("Create Workout");
		button.setOnClickListener(createWorkoutListener);
		rgNumOccurances.setOnClickListener(radioButtonListener);
		rgEndOnDate.setOnClickListener(radioButtonListener);
		btnEndOnDate.setOnClickListener(getEndDateListener);
		Calendar calendar = Calendar.getInstance();
		String currentDate = calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR);
		btnEndOnDate.setText(currentDate);
		
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.llCreateWorkoutInformationContainer);
		ll.addView(button);
		
		return view;
	}
	
	private boolean validateWorkoutName(String workoutName) {
		return mDbHelper.workoutNameExist(workoutName);
	}
	
	View.OnClickListener radioButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// NumOccurances Checked
			if (rgNumOccurances.isChecked()) {
				llNumOccurances.setVisibility(LinearLayout.VISIBLE);
				btnEndOnDate.setVisibility(Button.GONE);
			} 
			// Date Checked
			else if (rgEndOnDate.isChecked()) {
				llNumOccurances.setVisibility(LinearLayout.GONE);
				btnEndOnDate.setVisibility(Button.VISIBLE);
				
			}
		}
	};
	
	View.OnClickListener getEndDateListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(CreateWorkoutInformationFragment.this.getActivity(), CalendarActivity.class);
			intent.putExtra("RETURN-DATE", true);
			startActivityForResult(intent,1);
		}
	};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == 1 && requestCode == 1) {
			if (intent.hasExtra("DATE-SELECTED")) {
				btnEndOnDate.setText(intent.getExtras().getString("DATE-SELECTED"));
			}
		}
	}
	
	/**
	 * Create Workout Button Click Listener
	 */
	View.OnClickListener createWorkoutListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// Get Workout Name
			String workoutName = etWorkoutName.getText().toString();
			/* if name exists already
			 * alert error, must have unique name
			 */
			if (validateWorkoutName(workoutName)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(CreateWorkoutInformationFragment.this.getActivity());
				builder.setMessage("Error: " + workoutName + " already exists as a workout name.  Please select a unique name for the workout");
				builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
			else {
				// Get Workout Type
				String workoutType = (String) spnWorkoutType.getSelectedItem();
				// Get Repeat Weeks 
				String workoutRepeatWeeks = (String) spnRepeatWeeks.getSelectedItem();
				
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
				
				String exerciseSequence = "";
				Workout workoutToCreate = new Workout(workoutName, workoutType, exerciseSequence, workoutRepeatWeeks, 
													  repeatSunday, repeatMonday, repeatTuesday, 
													  repeatWednesday, repeatThursday, repeatFriday, repeatSaturday);
				Log.d("Button", "Create Workout Clicked");
				Log.d("Workout Name", workoutName);
				Log.d("Workout Type", workoutType);
				Log.d("Workout Repeat Weeks", workoutRepeatWeeks);
				
				/** TODO
				 * Add workout repeat information to insert command
				 */
				/* Create Workout in the Database */
				int workoutId = mDbHelper.createWorkout(workoutToCreate);
				// if num occurances checked
				if (rgNumOccurances.isChecked()) {
					int numRepeats = Integer.parseInt(etNumOccurances.getText().toString());
					createCalendarEntryNumOccurances(workoutId, workoutRepeatWeeks, repeatSunday, repeatMonday, repeatTuesday,
							repeatWednesday, repeatThursday, repeatFriday, repeatSaturday, numRepeats);
				} 
				// if end date selected 
				else if (rgEndOnDate.isChecked()) {
					String endDate = btnEndOnDate.getText().toString();
					createCalendarEntryByEndDate(workoutId, workoutRepeatWeeks, repeatSunday, repeatMonday, repeatTuesday,
							repeatWednesday, repeatThursday, repeatFriday, repeatSaturday, endDate);					
				}
				
				/** TODO
				 * Change intent launch so tabbed implementation remains
				 */
				
				listener.goToExerciseBank(workoutName);

				
				/* Launch intent to allow exercises to be added to workout and the sequence to be set */
				/*
				Intent intent = new Intent(CreateWorkoutInformationFragment.this.getActivity(), WorkoutExerciseListing.class);
				intent.putExtra("WorkoutName", workoutName);
				startActivity(intent);		
				*/
			}
		}
	};
	
	
	public void createCalendarEntryNumOccurances(int workoutId, String repeats, int repeatSunday,
			int repeatMonday, int repeatTuesday, int repeatWednesday, int repeatThursday,
			int repeatFriday, int repeatSaturday, int numRepeats) {
		ArrayList<String> records = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
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
			
			if (insert) {
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
			if (insert) {
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

	public interface onGoToExerciseBankListener {
		void goToExerciseBank(String workoutName);
	}
	
	public static void setExerciseBankListener(onGoToExerciseBankListener listener) {
		CreateWorkoutInformationFragment.listener = listener;
	}
	

}
