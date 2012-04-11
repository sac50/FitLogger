package com.cwru.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;

public class CalendarScheduleWorkoutFragment extends Fragment {
	private DbAdapter mDbHelper;
	private String date;
	private Context context;
	private static onWorkoutListingClickListener listener;
	
	private Spinner spnRepeatOptions;
	private RadioGroup rgScheduleOptions;
	private RadioButton rbNumOccurances;
	private RadioButton rbScheduleByDate;
	private LinearLayout llNumOccurancesContainer;
	private EditText etNumOccurances;
	private Button btnEndWorkoutDate;
	
	public CalendarScheduleWorkoutFragment(Context context) {
		this.context = context;
		mDbHelper = new DbAdapter(context);
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
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.workoutRepeatWeeks, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spnRepeatOptions.setAdapter(adapter);
	    
	    rbNumOccurances.setOnClickListener(radioButtonListener);
	    rbScheduleByDate.setOnClickListener(radioButtonListener);	    
		
		return view;
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
