package com.cwru.controller;

import java.util.ArrayList;
import java.util.List;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;
import com.cwru.model.Interval;
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
	int subType;
	EditText exDistanceText;
	String exDistanceType;
	EditText exCountdownText;
	String exCountdownType;
	String exIntervalTimeType;
	TextView tvIntervalSets;
	Interval intervalVar;
	List<LinearLayout> inflatedLayouts = new ArrayList<LinearLayout>();
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
		subType = R.array.exerciseCardioSubTypes;
		
		Spinner typeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseType);
		ArrayAdapter<CharSequence> exerTypeAdapter = ArrayAdapter.createFromResource(this,
				R.array.exersiseTypes, android.R.layout.simple_spinner_item);
		exerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(exerTypeAdapter);
		
		subTypeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseSubType);
		initSubTypeSpinner(subType);
		
		typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent,
			        View view, int pos, long id) {
				exType = parent.getItemAtPosition(pos).toString();
				if ("Cardio".equals(exType) && subType != R.array.exerciseCardioSubTypes) {
					subType = R.array.exerciseCardioSubTypes;
					initSubTypeSpinner(subType);
				} else if (!"Cardio".equals(exType) && subType != R.array.exerciseStrengthSubTypes) {
					subType = R.array.exerciseStrengthSubTypes;
					initSubTypeSpinner(subType);
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView parent) {
			      // Do nothing.
			    }
		});
		
//		subTypeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseSubType);
//		ArrayAdapter<CharSequence> exerSubTypeAdapter = ArrayAdapter.createFromResource(this,
//				subType, android.R.layout.simple_spinner_item);
//		exerSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		subTypeSpinner.setAdapter(exerSubTypeAdapter);
		subTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int pos, long id) {
				final LinearLayout distanceLayout = (LinearLayout) findViewById(R.id.llCreateExerciseDistance);
				final LinearLayout countdownLayout = (LinearLayout) findViewById(R.id.llCreateExerciseCountdown);
				final LinearLayout intervalLayout = (LinearLayout) findViewById(R.id.llCreateExerciseIntervals);
				final LinearLayout intervalSetLayout = (LinearLayout) findViewById(R.id.llCreateExerciseIntervalSets);
				final LinearLayout setLayout = (LinearLayout) findViewById(R.id.llCreateExerciseSets);
				
				if ("Distance".equals(parent.getItemAtPosition(pos).toString())) {
					countdownLayout.setVisibility(8);
					intervalLayout.setVisibility(8);
					intervalSetLayout.setVisibility(8);
					setLayout.setVisibility(8);
					while (!inflatedLayouts.isEmpty()) {
						int position = inflatedLayouts.size() - 1;
						intervalLayout.removeView(inflatedLayouts.get(position));
						setLayout.removeView(inflatedLayouts.get(position));
						inflatedLayouts.remove(position);
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
					intervalLayout.setVisibility(8);
					intervalSetLayout.setVisibility(8);
					setLayout.setVisibility(8);
					while (!inflatedLayouts.isEmpty()) {
						int position = inflatedLayouts.size() - 1;
						intervalLayout.removeView(inflatedLayouts.get(position));
						setLayout.removeView(inflatedLayouts.get(position));
						inflatedLayouts.remove(position);
					}
					
					countdownLayout.setVisibility(0);
					
					exCountdownText = (EditText) findViewById(R.id.etCreateExerciseCountdown);
					
					Spinner countdownSpinner = (Spinner) findViewById(R.id.spnCreateExerciseCountdown);
					ArrayAdapter<CharSequence> exerCountdownAdapter = ArrayAdapter.createFromResource(parent.getContext(),
							R.array.timeTypes, android.R.layout.simple_spinner_item);
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
					intervalLayout.setVisibility(8);
					intervalSetLayout.setVisibility(8);
					setLayout.setVisibility(8);
					while (!inflatedLayouts.isEmpty()) {
						int position = inflatedLayouts.size() - 1;
						intervalLayout.removeView(inflatedLayouts.get(position));
						setLayout.removeView(inflatedLayouts.get(position));
						inflatedLayouts.remove(position);
					}
				}
				
				else if ("Intervals".equals(parent.getItemAtPosition(pos).toString())) {
					countdownLayout.setVisibility(8);
					distanceLayout.setVisibility(8);
					setLayout.setVisibility(8);
					while (!inflatedLayouts.isEmpty()) {
						int position = inflatedLayouts.size() - 1;
						intervalLayout.removeView(inflatedLayouts.get(position));
						setLayout.removeView(inflatedLayouts.get(position));
						inflatedLayouts.remove(position);
					}
					
					intervalLayout.setVisibility(0);
					intervalSetLayout.setVisibility(0);
					
					for (int i = 0; i < 2; i++) {
						LinearLayout inflatedLayout;
						inflatedLayout = (LinearLayout) View.inflate(parent.getContext(), R.layout.create_exercise_interval_builder, null);
						inflatedLayouts.add(inflatedLayout);
						intervalLayout.addView(inflatedLayout);
						
						Spinner intervalSpinner = (Spinner) inflatedLayout.findViewById(R.id.spnCreateExerciseIntervalTimeType);
						ArrayAdapter<CharSequence> exerIntervalAdapter = ArrayAdapter.createFromResource(parent.getContext(),
								R.array.timeTypes, android.R.layout.simple_spinner_item);
						exerIntervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						intervalSpinner.setAdapter(exerIntervalAdapter);
					}
					
					Button addInterval = (Button) findViewById(R.id.btnCreateExerciseAddInterval);
					addInterval.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (inflatedLayouts.size() < 5) {
								LinearLayout inflatedLayout;
								inflatedLayout = (LinearLayout) View.inflate(v.getContext(), R.layout.create_exercise_interval_builder, null);
								inflatedLayouts.add(inflatedLayout);
								intervalLayout.addView(inflatedLayout);
								
								Spinner intervalSpinner = (Spinner) inflatedLayout.findViewById(R.id.spnCreateExerciseIntervalTimeType);
								ArrayAdapter<CharSequence> exerIntervalAdapter = ArrayAdapter.createFromResource(v.getContext(),
										R.array.timeTypes, android.R.layout.simple_spinner_item);
								exerIntervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								intervalSpinner.setAdapter(exerIntervalAdapter);
							}
						}
					});
					
					Button removeInterval = (Button) findViewById(R.id.btnCreateExerciseRemoveInterval);
					removeInterval.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (inflatedLayouts.size() > 1) {
								int position = inflatedLayouts.size() - 1;
								intervalLayout.removeView(inflatedLayouts.get(position));
								inflatedLayouts.remove(position);
							}
						}
					});
					
					Spinner intervalSpinner = (Spinner) findViewById(R.id.spnCreateExerciseIntervalTimeType);
					ArrayAdapter<CharSequence> exerIntervalAdapter = ArrayAdapter.createFromResource(parent.getContext(),
							R.array.timeTypes, android.R.layout.simple_spinner_item);
					exerIntervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					intervalSpinner.setAdapter(exerIntervalAdapter);
					intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int pos, long id) {
							exIntervalTimeType = parent.getItemAtPosition(pos).toString();
						}
						
						@Override
						public void onNothingSelected(AdapterView parent) {
							
						}
					});
					
					tvIntervalSets = (TextView) findViewById(R.id.tvIntervalSetNum);
					tvIntervalSets.setText("1");
					
					Button addSet = (Button) findViewById(R.id.btnCreateExerciseAddIntervalSet);
					addSet.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							int setNum = Integer.parseInt(tvIntervalSets.getText().toString());
							if (setNum < 10) {
								setNum++;
								tvIntervalSets.setText(Integer.toString(setNum));
							}
						}
					});
					
					Button removeSet = (Button) findViewById(R.id.btnCreateExerciseRemoveIntervalSet);
					removeSet.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							int setNum = Integer.parseInt(tvIntervalSets.getText().toString());
							if (setNum > 1) {
								setNum--;
								tvIntervalSets.setText(Integer.toString(setNum));
							}
						}
					});
					
				}
				
				else if ("Set-based".equals(parent.getItemAtPosition(pos).toString())) {
					countdownLayout.setVisibility(8);
					distanceLayout.setVisibility(8);
					intervalLayout.setVisibility(8);
					intervalSetLayout.setVisibility(8);
					setLayout.setVisibility(0);
					while (!inflatedLayouts.isEmpty()) {
						int position = inflatedLayouts.size() - 1;
						intervalLayout.removeView(inflatedLayouts.get(position));
						setLayout.removeView(inflatedLayouts.get(position));
						inflatedLayouts.remove(position);
					}
					
					for (int i = 0; i < 3; i++) {
						LinearLayout inflatedSet;
						inflatedSet = (LinearLayout) View.inflate(parent.getContext(), R.layout.create_exercise_set_builder, null);
						TextView setNum = (TextView) inflatedSet.findViewById(R.id.tvCreateExerciseSetNum);
						setNum.setText("Set " + (i + 1));
						inflatedLayouts.add(inflatedSet);
						setLayout.addView(inflatedSet);
					}
					
					Button addSet = (Button) findViewById(R.id.btnCreateExerciseAddSet);
					addSet.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (inflatedLayouts.size() < 10) {
								LinearLayout inflatedSet;
								inflatedSet = (LinearLayout) View.inflate(v.getContext(), R.layout.create_exercise_set_builder, null);
								TextView setNum = (TextView) inflatedSet.findViewById(R.id.tvCreateExerciseSetNum);
								setNum.setText("Set " + (inflatedLayouts.size() + 1));
								inflatedLayouts.add(inflatedSet);
								setLayout.addView(inflatedSet);
							}
						}
					});
					
					Button removeSet = (Button) findViewById(R.id.btnCreateExerciseRemoveSet);
					removeSet.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (inflatedLayouts.size() > 1) {
								int position = inflatedLayouts.size() - 1;
								setLayout.removeView(inflatedLayouts.get(position));
								inflatedLayouts.remove(position);
							}
						}
					});
					
					EditText weightText = (EditText) inflatedLayouts.get(0).findViewById(R.id.etCreateExerciseWeight);
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
							for (int i = 1; i < inflatedLayouts.size(); i++) {
								inflatedSet = inflatedLayouts.get(i);
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
					
					EditText repsText = (EditText) inflatedLayouts.get(0).findViewById(R.id.etCreateExerciseReps);
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
							for (int i = 1; i < inflatedLayouts.size(); i++) {
								inflatedSet = inflatedLayouts.get(i);
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
					while (!inflatedLayouts.isEmpty()) {
						int position = inflatedLayouts.size() - 1;
						setLayout.removeView(inflatedLayouts.get(position));
						inflatedLayouts.remove(position);
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
				intervalVar = new Interval();
				setVar = new Set();
				
				Context context = getApplicationContext();
				CharSequence text;
				int duration = Toast.LENGTH_SHORT;
				
				int layoutMarker = -1;
				
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
					layoutMarker = 0;
					ex.setIntevalNum(Integer.parseInt(tvIntervalSets.getText().toString()));
				} else {
					layoutMarker = 1;
					ex.setSets(inflatedLayouts.size());
				}
				exId = mDbHelper.createExercise(ex);
				if (exId == 0L) {
					text = "Exercise Name and Type required";
					Toast toast = Toast.makeText(context, text, duration);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				else {
					if (layoutMarker == 0) {
						List<Long> intervalIds = new ArrayList<Long>();
						for (LinearLayout inflatedLayout : inflatedLayouts) {							
							EditText nameText = (EditText) inflatedLayout.findViewById(R.id.etCreateExerciseIntervalName);
							EditText timeText = (EditText) inflatedLayout.findViewById(R.id.etCreateExerciseIntervalTime);
							Spinner timeTypeSpinner = (Spinner) inflatedLayout.findViewById(R.id.spnCreateExerciseIntervalTimeType);
							
							String name = nameText.getText().toString();
							String time = (timeText.getText().toString());
							
							if (name != null && name.length() > 0 && time != null && time.length() > 0) {
								intervalVar.setExerciseId(exId);
								intervalVar.setName(name);
								intervalVar.setTime(Long.parseLong(time));
								intervalVar.setTimeType(timeTypeSpinner.getSelectedItem().toString());
								
								intervalIds.add(mDbHelper.createInterval(intervalVar));
							} else {
								mDbHelper.trueDeleteExercise(exId);
								for (long intervalId : intervalIds) {
									mDbHelper.deleteInterval(intervalId);
								}
								
								text = "Interval names and times required.";
								Toast toast = Toast.makeText(context, text, duration);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								
								exId = 0L;
								break;
							}
							
						}
					} else if (layoutMarker == 1) {
						List<Long> setIds = new ArrayList<Long>();
						for (LinearLayout inflatedSet : inflatedLayouts) {
							EditText repsText = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps);
							EditText weightText = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseWeight);
							
							String reps = repsText.getText().toString();
							String weight = weightText.getText().toString();
							
							if (reps != null && reps.length() > 0 && weight != null && weight.length() > 0) {
								setVar.setReps(Integer.parseInt(reps));
								setVar.setWeight(Double.parseDouble(weight));
								setIds.add(mDbHelper.createSet(setVar));
							} else {
								mDbHelper.trueDeleteExercise(exId);
								for (long setId : setIds) {
									mDbHelper.deleteSet(setId);
								}
								
								text = "Set weight and reps required.";
								Toast toast = Toast.makeText(context, text, duration);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								
								exId = 0L;
								break;
							}
						}
					}
					
					if (exId != 0L) {
						text = "Exercise Created";
						Toast toast = Toast.makeText(context, text, duration);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}
				mDbHelper.close();
			}
		});
		
		
	}
	
	private void initSubTypeSpinner(int subType) {
		ArrayAdapter<CharSequence> exerSubTypeAdapter = ArrayAdapter.createFromResource(this,
				subType, android.R.layout.simple_spinner_item);
		exerSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		subTypeSpinner.setAdapter(exerSubTypeAdapter);
	}
}
