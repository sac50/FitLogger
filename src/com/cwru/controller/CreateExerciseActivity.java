package com.cwru.controller;

import java.util.ArrayList;
import java.util.List;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;
import com.cwru.model.Set;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateExerciseActivity extends FragmentActivity {
	DbAdapter mDbHelper;
	EditText mNameText;
	String exType;
	EditText exDistanceText;
	String exDistanceType;
	EditText exCountdownText;
	String exCountdownType;
	List<LinearLayout> inflatedSets = new ArrayList<LinearLayout>();
	List<Set> setList;
	Set setVar;
	Long exId;
	Spinner subTypeSpinner;
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new DbAdapter(this);
		
		TextView textView = new TextView(this);
		textView.setText("Create Exercise Tab");
		setContentView(R.layout.create_exercise_tab);
		
		mNameText = (EditText) findViewById(R.id.etCreateExerciseName);
		
		Spinner typeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseType);
		ArrayAdapter<CharSequence> exerTypeAdapter = ArrayAdapter.createFromResource(this,
				R.array.exersiseTypes, android.R.layout.simple_spinner_item);
		exerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(exerTypeAdapter);
		typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent,
			        View view, int pos, long id) {
				exType = parent.getItemAtPosition(pos).toString();
			}
			
			@Override
			public void onNothingSelected(AdapterView parent) {
			      // Do nothing.
			    }
		});
		
		subTypeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseSubType);
		ArrayAdapter<CharSequence> exerSubTypeAdapter = ArrayAdapter.createFromResource(this,
				R.array.exerciseSubTypes, android.R.layout.simple_spinner_item);
		exerSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		subTypeSpinner.setAdapter(exerSubTypeAdapter);
		subTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int pos, long id) {
				final LinearLayout distanceLayout = (LinearLayout) findViewById(R.id.llCreateExerciseDistance);
				final LinearLayout countdownLayout = (LinearLayout) findViewById(R.id.llCreateExerciseCountdown);
				final LinearLayout setLayout = (LinearLayout) findViewById(R.id.llCreateExerciseSets);
				
				if ("Distance".equals(parent.getItemAtPosition(pos).toString())) {
					countdownLayout.setVisibility(8);
					setLayout.setVisibility(8);
					while (!inflatedSets.isEmpty()) {
						int position = inflatedSets.size() - 1;
						setLayout.removeView(inflatedSets.get(position));
						inflatedSets.remove(position);
					}
					
					distanceLayout.setVisibility(0);
					
					exDistanceText = (EditText) findViewById(R.id.etCreateExerciseDistance);
					
					Spinner distanceSpinner = (Spinner) findViewById(R.id.spnCreateExerciseDistance);
					ArrayAdapter<CharSequence> exerDistanceAdapter = ArrayAdapter.createFromResource(parent.getContext(),
							R.array.exerciseDistances, android.R.layout.simple_spinner_item);
					exerDistanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					distanceSpinner.setAdapter(exerDistanceAdapter);
					distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int pos, long id) {
							exDistanceType = parent.getItemAtPosition(pos).toString();
						}
						
						@Override
						public void onNothingSelected(AdapterView parent) {
							
						}
					});
				}
				
				else if ("Countdown Timer".equals(parent.getItemAtPosition(pos).toString())) {
					distanceLayout.setVisibility(8);
					setLayout.setVisibility(8);
					while (!inflatedSets.isEmpty()) {
						int position = inflatedSets.size() - 1;
						setLayout.removeView(inflatedSets.get(position));
						inflatedSets.remove(position);
					}
					
					countdownLayout.setVisibility(0);
					
					exCountdownText = (EditText) findViewById(R.id.etCreateExerciseCountdown);
					
					Spinner countdownSpinner = (Spinner) findViewById(R.id.spnCreateExerciseCountdown);
					ArrayAdapter<CharSequence> exerCountdownAdapter = ArrayAdapter.createFromResource(parent.getContext(),
							R.array.exerciseCountdowns, android.R.layout.simple_spinner_item);
					exerCountdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					countdownSpinner.setAdapter(exerCountdownAdapter);
					countdownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int pos, long id) {
							exCountdownType = parent.getItemAtPosition(pos).toString();
						}
						
						@Override
						public void onNothingSelected(AdapterView parent) {
							
						}
					});
				}
				
				else if ("Countup Timer".equals(parent.getItemAtPosition(pos).toString())) {
					countdownLayout.setVisibility(8);
					distanceLayout.setVisibility(8);
					setLayout.setVisibility(8);
					while (!inflatedSets.isEmpty()) {
						int position = inflatedSets.size() - 1;
						setLayout.removeView(inflatedSets.get(position));
						inflatedSets.remove(position);
					}
				}
				
				else if ("Intervals".equals(parent.getItemAtPosition(pos).toString())) {
					countdownLayout.setVisibility(8);
					distanceLayout.setVisibility(8);
					setLayout.setVisibility(8);
					while (!inflatedSets.isEmpty()) {
						int position = inflatedSets.size() - 1;
						setLayout.removeView(inflatedSets.get(position));
						inflatedSets.remove(position);
					}
				}
				
				else if ("Set-based".equals(parent.getItemAtPosition(pos).toString())) {
					countdownLayout.setVisibility(8);
					distanceLayout.setVisibility(8);
					setLayout.setVisibility(0);
					
					for (int i = 0; i < 3; i++) {
						LinearLayout inflatedSet;
						inflatedSet = (LinearLayout) View.inflate(parent.getContext(), R.layout.create_exercise_set_builder, null);
						inflatedSets.add(inflatedSet);
						setLayout.addView(inflatedSet);
					}
					
					Button addSet = (Button) findViewById(R.id.btnCreateExerciseAddSet);
					addSet.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (inflatedSets.size() < 10) {
								LinearLayout inflatedSet;
								inflatedSet = (LinearLayout) View.inflate(v.getContext(), R.layout.create_exercise_set_builder, null);
								inflatedSets.add(inflatedSet);
								setLayout.addView(inflatedSet);
							}
						}
					});
					
					Button removeSet = (Button) findViewById(R.id.btnCreateExerciseRemoveSet);
					removeSet.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (inflatedSets.size() > 1) {
								int position = inflatedSets.size() - 1;
								setLayout.removeView(inflatedSets.get(position));
								inflatedSets.remove(position);
							}
						}
					});
					
					EditText weightText = (EditText) inflatedSets.get(0).findViewById(R.id.etCreateExerciseWeight);
					weightText.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							
						}

						@Override
						public void beforeTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence s, int arg1,
								int arg2, int arg3) {
							LinearLayout inflatedSet;
							String replace = s.toString();
							for (int i = 1; i < inflatedSets.size(); i++) {
								inflatedSet = inflatedSets.get(i);
								String old = ((EditText) inflatedSet.findViewById(R.id.etCreateExerciseWeight)).getText().toString();
								if (old == null || old.length() < 1 
										|| replace.length() > 0 && old.equals(replace.substring(0, replace.length() - 1))
										|| old.substring(0, old.length() - 1).equals(replace)) {
									EditText replaceWeight = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseWeight);
									replaceWeight.setText(replace);
								}
							}
						}	
					});
					
					EditText repsText = (EditText) inflatedSets.get(0).findViewById(R.id.etCreateExerciseReps);
					repsText.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							
						}

						@Override
						public void beforeTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence s, int arg1,
								int arg2, int arg3) {
							LinearLayout inflatedSet;
							String replace = s.toString();
							for (int i = 1; i < inflatedSets.size(); i++) {
								inflatedSet = inflatedSets.get(i);
								String old = ((EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps)).getText().toString();
								if (old == null || old.length() < 1 
										|| replace.length() > 0 && old.equals(replace.substring(0, replace.length() - 1))
										|| old.substring(0, old.length() - 1).equals(replace)) {
									EditText replaceReps = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps);
									replaceReps.setText(replace);
								}
							}
						}
					});
				} else {
					while (!inflatedSets.isEmpty()) {
						int position = inflatedSets.size() - 1;
						setLayout.removeView(inflatedSets.get(position));
						inflatedSets.remove(position);
					}
					setLayout.setVisibility(8);
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView parent) {
				
			}
		});
		
		Button doneButton = (Button) findViewById(R.id.btnCreateExerciseDone);
		doneButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Exercise ex = new Exercise();
				ex.setName(mNameText.getText().toString());
				ex.setType(exType);
				ex.setSets(inflatedSets.size());
				setVar = new Set();
				
				Context context = getApplicationContext();
				CharSequence text;
				int duration = Toast.LENGTH_SHORT;
				
				mDbHelper.open();
				if ("Distance".equals(subTypeSpinner.getSelectedItem().toString())) {
					String exDistanceString = exDistanceText.getText().toString();
					if (exDistanceString.length() > 0) {
						ex.setDistance(Double.parseDouble(exDistanceString));
					} else {
						text = "Please specify a distance.";
						Toast toast = Toast.makeText(context, text, duration);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						return;
					}
					ex.setDistanceType(exDistanceType);
				} else if ("Countdown Timer".equals(subTypeSpinner.getSelectedItem().toString())) {
					String exCountdownString = exCountdownText.getText().toString();
					if (exCountdownString.length() > 0) {
						ex.setTime(Long.parseLong(exCountdownString));
						ex.setIsCountdown(true);
					} else {
						text = "Please specify a time.";
						Toast toast = Toast.makeText(context, text, duration);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						return;
					}
					ex.setTimeType(exCountdownType);
				} else if ("Countup Timer".equals(subTypeSpinner.getSelectedItem().toString())) {
					ex.setIsCountdown(false);
				} else if ("Intervals".equals(subTypeSpinner.getSelectedItem().toString())) {
					
				} else {
					for (LinearLayout inflatedSet : inflatedSets) {
						EditText repsText = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps);
						EditText weightText = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseWeight);
						
						setVar.setReps(Integer.parseInt(repsText.getText().toString()));
						setVar.setWeight(Double.parseDouble(weightText.getText().toString()));
						mDbHelper.createSet(setVar);
					}
				}
				exId = mDbHelper.createExercise(ex);
				if (exId == 0L) {
					text = "Exercise Name and Type required";
					Toast toast = Toast.makeText(context, text, duration);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				else {
					text = "Exercise Created";
					Toast toast = Toast.makeText(context, text, duration);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				mDbHelper.close();
			}
		});
		
		
	}
}
