package com.cwru.model;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.controller.WorkoutExerciseListing;

/**
 * 
 * @author sacrilley
 * This fragment handles entering information about the workout user creating
 *
 */
public class CreateWorkoutInformationFragment extends Fragment {
	
	/**
	 * UI View associated with the fragment
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;			
		}
		Log.d("Create Workout Information Fragment", "F");
		//return inflater.inflate(R.layout.create_workout_information, container, false);
		View view =  (ScrollView) inflater.inflate(R.layout.create_workout_information, container, false);
		Configuration c = this.getResources().getConfiguration();
		/* Lanscape View */
		if (c.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Log.d("Steve", "Horizontal1");
			/* Place Checkboxes all in one row */
			TableLayout tl = (TableLayout)  view.findViewById(R.id.tlCreateWorkoutInformationCheckBoxTable);
			TableRow tr = new TableRow(this.getActivity());
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			CheckBox sunday = new CheckBox(this.getActivity());
			sunday.setText("S");
			sunday.setTag("chkRepeatSunday");
			tr.addView(sunday);

			CheckBox monday = new CheckBox(this.getActivity());
			monday.setText("M");
			monday.setTag("chkRepeatMonday");
			tr.addView(monday);
			
			CheckBox tuesday = new CheckBox(this.getActivity());
			tuesday.setText("T");
			tuesday.setTag("chkRepeatTuesday");
			tr.addView(tuesday);
			
			CheckBox wednesday = new CheckBox(this.getActivity());
			wednesday.setText("W");
			wednesday.setTag("chkRepeatWednesday");
			tr.addView(wednesday);

			CheckBox thursday = new CheckBox(this.getActivity());
			thursday.setText("R");
			thursday.setTag("chkRepeatThursday");
			tr.addView(thursday);
			
			CheckBox friday = new CheckBox(this.getActivity());
			friday.setText("F");
			friday.setTag("chkRepeatFriday");
			tr.addView(friday);
			
			CheckBox saturday = new CheckBox(this.getActivity());
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
			
			CheckBox sunday = new CheckBox(this.getActivity());
			sunday.setText("S");
			sunday.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			CheckBox monday = new CheckBox(this.getActivity());
			monday.setText("M");
			monday.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			CheckBox tuesday = new CheckBox(this.getActivity());
			tuesday.setText("T");
			tuesday.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			CheckBox wednesday = new CheckBox(this.getActivity());
			wednesday.setText("W");
			wednesday.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			CheckBox thursday = new CheckBox(this.getActivity());
			thursday.setText("R");
			thursday.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			CheckBox friday = new CheckBox(this.getActivity());
			friday.setText("F");
			friday.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			CheckBox saturday = new CheckBox(this.getActivity());
			saturday.setText("S");
			saturday.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			l1.addView(sunday);
			l1.addView(monday);
			l1.addView(tuesday);
			l2.addView(wednesday);
			l2.addView(thursday);
			l2.addView(friday);
			l2.addView(saturday);
			
			LinearLayout llcontainer = (LinearLayout) view.findViewById(R.id.llCreateWorkoutInformationContainer);
			llcontainer.addView(l1);
			llcontainer.addView(l2);
			
		}
		
		// Button to Create Workout
		Button button = new Button(this.getActivity());
		button.setText("Create Workout");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/* TODO
				 * Insert Workout into database
				 */
				Intent intent = new Intent(CreateWorkoutInformationFragment.this.getActivity(), WorkoutExerciseListing.class);
				startActivity(intent);
			}			
		});
		
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.llCreateWorkoutInformationContainer);
		ll.addView(button);
		
		return view;
	}

}
