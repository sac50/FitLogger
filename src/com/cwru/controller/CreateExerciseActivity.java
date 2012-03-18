package com.cwru.controller;

import java.util.ArrayList;
import java.util.List;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Distance;
import com.cwru.model.Exercise;
import com.cwru.model.Interval;
import com.cwru.model.IntervalSet;
import com.cwru.model.Set;
import com.cwru.model.Time;
import com.cwru.utils.AutoFillListener;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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
	List<LinearLayout> inflatedLayouts = new ArrayList<LinearLayout>();
	List<Set> setList;
	Long exId;
	Spinner subTypeSpinner;
	Exercise ex = new Exercise();

	/*
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new DbAdapter(this);

		TextView textView = new TextView(this);
		textView.setText("Create Exercise Tab");
		setContentView(R.layout.create_exercise_tab);

		// Exercise name
		mNameText = (EditText) findViewById(R.id.etCreateExerciseName);

		// Exercise type spinner
		Spinner typeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseType);
		initSpinner(R.array.exerciseTypes, typeSpinner);

		// Exercise subtype spinner. Initialized to the Cardio spinner
		subTypeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseSubType);
		subType = R.array.exerciseCardioSubTypes;
		initSpinner(subType, subTypeSpinner);

		typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,View view, int pos, long id) {
						exType = parent.getItemAtPosition(pos).toString();
						// if the user selects Cardio, display the Cardio
						// subtype spinner
						if ("Cardio".equals(exType) && subType != R.array.exerciseCardioSubTypes) {
							subType = R.array.exerciseCardioSubTypes;
							initSpinner(subType, subTypeSpinner);
							// else if the user selects a Strength type, display
							// Strength subtype spinner
						} else if (!"Cardio".equals(exType) && subType != R.array.exerciseStrengthSubTypes) {
							subType = R.array.exerciseStrengthSubTypes;
							initSpinner(subType, subTypeSpinner);
						}
					}

					@Override
					public void onNothingSelected(AdapterView parent) {
						// Do nothing.
					}
				});

		subTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						// layouts for various sub type representations
						final LinearLayout distanceLayout = (LinearLayout) findViewById(R.id.llCreateExerciseDistance);
						final LinearLayout countdownLayout = (LinearLayout) findViewById(R.id.llCreateExerciseCountdown);
						final LinearLayout intervalLayout = (LinearLayout) findViewById(R.id.llCreateExerciseIntervals);
						final LinearLayout intervalSetLayout = (LinearLayout) findViewById(R.id.llCreateExerciseIntervalSets);
						final LinearLayout setLayout = (LinearLayout) findViewById(R.id.llCreateExerciseSets);

						// if sub type is distance
						if ("Distance".equals(parent.getItemAtPosition(pos).toString())) {
							// remove any other sub type layout and purge
							// inflatedLayouts
							countdownLayout.setVisibility(8);
							intervalLayout.setVisibility(8);
							intervalSetLayout.setVisibility(8);
							setLayout.setVisibility(8);
							clearLayouts(inflatedLayouts, intervalLayout, setLayout);

							// display the proper layout
							distanceLayout.setVisibility(0);
							
							ex.setMode(Exercise.DISTANCE_BASED_EXERCISE);

							exDistanceText = (EditText) findViewById(R.id.etCreateExerciseDistance);

							Spinner distanceSpinner = (Spinner) findViewById(R.id.spnCreateExerciseDistance);
							initSpinner(R.array.exerciseDistances,
									distanceSpinner);
							distanceSpinner
									.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> parent,
												View view, int pos, long id) {
											exDistanceType = parent
													.getItemAtPosition(pos)
													.toString();
										}

										@Override
										public void onNothingSelected(
												AdapterView parent) {

										}
									});
						}

						else if ("Countdown Timer".equals(parent
								.getItemAtPosition(pos).toString())) {
							// remove any other sub type layout and purge
							// inflatedLayouts
							distanceLayout.setVisibility(8);
							intervalLayout.setVisibility(8);
							intervalSetLayout.setVisibility(8);
							setLayout.setVisibility(8);
							clearLayouts(inflatedLayouts, intervalLayout,
									setLayout);

							// display the proper layout
							countdownLayout.setVisibility(0);
							
							ex.setMode(Exercise.COUNTDOWN_BASED_EXERCISE);

							exCountdownText = (EditText) findViewById(R.id.etCreateExerciseCountdown);

							Spinner countdownSpinner = (Spinner) findViewById(R.id.spnCreateExerciseCountdown);
							initSpinner(R.array.timeTypes, countdownSpinner);
							countdownSpinner
									.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> parent,
												View view, int pos, long id) {
											exCountdownType = parent
													.getItemAtPosition(pos)
													.toString();
										}

										@Override
										public void onNothingSelected(
												AdapterView parent) {

										}
									});
						}

						else if ("Countup Timer".equals(parent
								.getItemAtPosition(pos).toString())) {
							// remove any other sub type layout and purge
							// inflatedLayouts
							countdownLayout.setVisibility(8);
							distanceLayout.setVisibility(8);
							intervalLayout.setVisibility(8);
							intervalSetLayout.setVisibility(8);
							setLayout.setVisibility(8);
							clearLayouts(inflatedLayouts, intervalLayout,
									setLayout);
							
							ex.setMode(Exercise.COUNTUP_BASED_EXERCISE);
						}

						else if ("Intervals".equals(parent.getItemAtPosition(
								pos).toString())) {
							// remove any other sub type layout and purge
							// inflatedLayouts
							countdownLayout.setVisibility(8);
							distanceLayout.setVisibility(8);
							setLayout.setVisibility(8);
							clearLayouts(inflatedLayouts, intervalLayout,
									setLayout);

							// display the proper layouts
							intervalLayout.setVisibility(0);
							intervalSetLayout.setVisibility(0);
							
							ex.setMode(Exercise.INTERVAL_BASED_EXERCISE);

							// when first displaying layout, display two
							// intervals
							for (int i = 0; i < 2; i++) {
								LinearLayout inflatedLayout;
								// inflate the interval's layout
								inflatedLayout = (LinearLayout) View.inflate(
										parent.getContext(),
										R.layout.create_exercise_interval_builder,
										null);
								inflatedLayouts.add(inflatedLayout);
								intervalLayout.addView(inflatedLayout);

								Spinner intervalSpinner = (Spinner) inflatedLayout
										.findViewById(R.id.spnCreateExerciseIntervalUnit);
								initSpinner(R.array.units, intervalSpinner);
							}

							Button addInterval = (Button) findViewById(R.id.btnCreateExerciseAddInterval);
							addInterval
									.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											LinearLayout inflatedLayout;
											// a maximum of five intervals per
											// exercise can be made
											if (inflatedLayouts.size() < 5) {
												// inflate the interval's layout
												inflatedLayout = (LinearLayout) View.inflate(
														v.getContext(),
														R.layout.create_exercise_interval_builder,
														null);
												inflatedLayouts
														.add(inflatedLayout);
												intervalLayout
														.addView(inflatedLayout);

												Spinner intervalSpinner = (Spinner) inflatedLayout
														.findViewById(R.id.spnCreateExerciseIntervalUnit);
												initSpinner(R.array.units,
														intervalSpinner);
											}
										}
									});

							Button removeInterval = (Button) findViewById(R.id.btnCreateExerciseRemoveInterval);
							removeInterval
									.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											// an interval exercise must have at
											// least one interval
											if (inflatedLayouts.size() > 1) {
												int position = inflatedLayouts
														.size() - 1;
												intervalLayout
														.removeView(inflatedLayouts
																.get(position));
												inflatedLayouts
														.remove(position);
											}
										}
									});

							Spinner intervalTimeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseIntervalUnit);
							initSpinner(R.array.units, intervalTimeSpinner);
							intervalTimeSpinner
									.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> parent,
												View view, int pos, long id) {
											exIntervalTimeType = parent
													.getItemAtPosition(pos)
													.toString();
										}

										@Override
										public void onNothingSelected(
												AdapterView parent) {

										}
									});

							// how many sets of the intervals a user wants to do
							tvIntervalSets = (TextView) findViewById(R.id.tvIntervalSetNum);
							tvIntervalSets.setText("1");

							// increments how many times an interval will be
							// done
							Button addSet = (Button) findViewById(R.id.btnCreateExerciseAddIntervalSet);
							addSet.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									int setNum = Integer
											.parseInt(tvIntervalSets.getText()
													.toString());
									// an interval exercise can be performed up
									// to ten times
									if (setNum < 10) {
										setNum++;
										tvIntervalSets.setText(Integer
												.toString(setNum));
									}
								}
							});

							// decrements how many times an interval will be
							// done
							Button removeSet = (Button) findViewById(R.id.btnCreateExerciseRemoveIntervalSet);
							removeSet
									.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											int setNum = Integer
													.parseInt(tvIntervalSets
															.getText()
															.toString());
											// an interval exercise must be done
											// at least once
											if (setNum > 1) {
												setNum--;
												tvIntervalSets.setText(Integer
														.toString(setNum));
											}
										}
									});

						}

						else if ("Set-based".equals(parent.getItemAtPosition(
								pos).toString())) {
							// remove any other sub type layout and purge
							// inflatedLayouts
							countdownLayout.setVisibility(8);
							distanceLayout.setVisibility(8);
							intervalLayout.setVisibility(8);
							intervalSetLayout.setVisibility(8);
							setLayout.setVisibility(0);
							clearLayouts(inflatedLayouts, intervalLayout,
									setLayout);
							
							ex.setMode(Exercise.SET_BASED_EXERCISE);

							// initialize the layout with three sets
							for (int i = 0; i < 3; i++) {
								LinearLayout inflatedSet;
								// inflate the set
								inflatedSet = (LinearLayout) View.inflate(
										parent.getContext(),
										R.layout.create_exercise_set_builder,
										null);
								// display the number of the set
								TextView setNum = (TextView) inflatedSet
										.findViewById(R.id.tvCreateExerciseSetNum);
								setNum.setText("Set " + (i + 1));

								inflatedLayouts.add(inflatedSet);
								setLayout.addView(inflatedSet);
							}

							// adds another set to the exercise
							Button addSet = (Button) findViewById(R.id.btnCreateExerciseAddSet);
							addSet.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									LinearLayout inflatedSet;
									// no more than ten sets per exercise
									if (inflatedLayouts.size() < 10) {
										// inflate the set
										inflatedSet = (LinearLayout) View.inflate(
												v.getContext(),
												R.layout.create_exercise_set_builder,
												null);

										// display the number of the set
										TextView setNum = (TextView) inflatedSet
												.findViewById(R.id.tvCreateExerciseSetNum);
										setNum.setText("Set "
												+ (inflatedLayouts.size() + 1));

										inflatedLayouts.add(inflatedSet);
										setLayout.addView(inflatedSet);
									}
								}
							});

							// removes a set from the exercise
							Button removeSet = (Button) findViewById(R.id.btnCreateExerciseRemoveSet);
							removeSet
									.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											// must be at least one set per
											// exercise
											if (inflatedLayouts.size() > 1) {
												int position = inflatedLayouts
														.size() - 1;
												setLayout
														.removeView(inflatedLayouts
																.get(position));
												inflatedLayouts
														.remove(position);
											}
										}
									});

							AutoFillListener autoFillListener = new AutoFillListener();
							
							EditText weightText = (EditText) inflatedLayouts
									.get(0).findViewById(
											R.id.etCreateExerciseWeight);
							autoFillListener.weightAutoFillListener(weightText, inflatedLayouts);

							EditText repsText = (EditText) inflatedLayouts.get(
									0).findViewById(R.id.etCreateExerciseReps);
							autoFillListener.repsAutoFillListener(repsText, inflatedLayouts);
						}
					}

					@Override
					public void onNothingSelected(AdapterView parent) {

					}
				});

		// submits the exercise to the database if valid data is present
		Button doneButton = (Button) findViewById(R.id.btnCreateExerciseDone);
		doneButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ex.setName(mNameText.getText().toString());
				ex.setType(exType);

				// simple params for a Toast display
				Context context = getApplicationContext();
				CharSequence text;
				int duration = Toast.LENGTH_SHORT;
				
				if (ex.getName().length() == 0) {
					text = "Please specify an exercise name";
					Toast toast = Toast.makeText(context, text, duration);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				}
				
				switch (ex.getMode()) {
				
					case Exercise.DISTANCE_BASED_EXERCISE:
						String exDistanceString = exDistanceText.getText().toString();
						if (exDistanceString.length() > 0) {
							Distance distance = new Distance(Double.parseDouble(exDistanceString), exDistanceType);
							ex.setDistance(distance);
							break;
						} else {
							text = "Please specify a distance.";
							Toast toast = Toast.makeText(context, text, duration);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							return;
						}
						
					case Exercise.COUNTUP_BASED_EXERCISE:
						break;
						
					case Exercise.COUNTDOWN_BASED_EXERCISE:
						String exCountdownString = exCountdownText.getText().toString();
						if (exCountdownString.length() > 0) {
							Time time = new Time(Integer.parseInt(exCountdownString), exCountdownType);
							ex.setTime(time);
							break;
						} else {
							text = "Please specify a time.";
							Toast toast = Toast.makeText(context, text, duration);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							return;
						}
						
					case Exercise.INTERVAL_BASED_EXERCISE:
						Interval interval = new Interval();
						ArrayList<IntervalSet> intervalSets = new ArrayList<IntervalSet>();
						
						for (LinearLayout inflatedInterval: inflatedLayouts) {
							EditText nameText = (EditText) inflatedInterval.findViewById(R.id.etCreateExerciseIntervalName);
							EditText lengthText = (EditText) inflatedInterval.findViewById(R.id.etCreateExerciseIntervalLength);
							Spinner unitSpinner = (Spinner) inflatedInterval.findViewById(R.id.spnCreateExerciseIntervalUnit);

							String name = nameText.getText().toString();
							String length = (lengthText.getText().toString());
							String unit = unitSpinner.getSelectedItem().toString();
							
							if (name == null || name.length() <= 0
									|| length == null || length.length() <= 0) {
								text = "Interval names and lengths required.";
								Toast toast = Toast.makeText(context, text,
										duration);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								return;
							} else if ("seconds".equals(unit) || "minutes".equals(unit) || "hours".equals(unit)) {
								IntervalSet intervalSet = new IntervalSet(name, Double.parseDouble(length), "time", unit);
								intervalSets.add(intervalSet);
							} else {
								IntervalSet intervalSet = new IntervalSet(name, Double.parseDouble(length), "time", unit);
								intervalSets.add(intervalSet);
							}
						}
						
						interval.setNumRepeats(Integer.parseInt(tvIntervalSets.getText().toString()));
						interval.setIntervalSets(intervalSets);
						ex.setInterval(interval);
						break;
					
					case Exercise.SET_BASED_EXERCISE:
						ArrayList<Set> sets = new ArrayList<Set>();
						
						for (LinearLayout inflatedSet : inflatedLayouts) {
							EditText repsText = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps);
							EditText weightText = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseWeight);

							String reps = repsText.getText().toString();
							String weight = weightText.getText().toString();
							
							if (reps == null || reps.length() == 0
									|| weight == null || weight.length() == 0) {
								text = "Set reps and weight required.";
								Toast toast = Toast.makeText(context, text,
										duration);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								return;
							} else {
								Set set = new Set(Integer.parseInt(reps), Double.parseDouble(weight));
								sets.add(set);
							}
						}
						
						ex.setSets(sets);
						break;
					
					default:
						return;
				}
				
				try {
					mDbHelper.createExercise(ex);
					text = "Exercise Created";
					Toast toast = Toast.makeText(context, text, duration);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} catch (IllegalArgumentException e) {
					Toast toast = Toast.makeText(context, e.getMessage(), duration);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				
//				} else if ("Intervals".equals(subTypeSpinner.getSelectedItem()
//						.toString())) {
//					// indicate that intervals need to be stored
//					layoutMarker = 0;
//					ex.setIntervals(inflatedLayouts.size());
//					ex.setIntervalSets(Integer.parseInt(tvIntervalSets.getText()
//							.toString()));
//				} else {
//					// indicate that sets need to be stored
//					layoutMarker = 1;
//					ex.setSets(inflatedLayouts.size());
//				}
				// store the exercise data in the db
//				try {
//					exId = mDbHelper.createExercise(ex);
//					// if intervals need to be stored
//					if (layoutMarker == 0) {
//						// list holds the new IDs for deletion from DB if
//						// something goes wrong
//						List<Long> intervalIds = new ArrayList<Long>();
//
//						// for each interval
//						for (LinearLayout inflatedLayout : inflatedLayouts) {
//							EditText nameText = (EditText) inflatedLayout
//									.findViewById(R.id.etCreateExerciseIntervalName);
//							EditText timeText = (EditText) inflatedLayout
//									.findViewById(R.id.etCreateExerciseIntervalTime);
//							Spinner timeTypeSpinner = (Spinner) inflatedLayout
//									.findViewById(R.id.spnCreateExerciseIntervalTimeType);
//
//							String name = nameText.getText().toString();
//							String time = (timeText.getText().toString());
//
//							// if the user has specified both name and time,
//							// store the interval
//							if (name != null && name.length() > 0
//									&& time != null && time.length() > 0) {
//								intervalVar.setExerciseId(exId);
//								intervalVar.setName(name);
//								intervalVar.setTime(Long.parseLong(time));
//								intervalVar.setTimeType(timeTypeSpinner
//										.getSelectedItem().toString());
//
//								intervalIds.add(mDbHelper
//										.createInterval(intervalVar));
//								// else the user needs to specify more info,
//								// previous insertions in DB must be rolled
//								// back
//							} else {
//								// delete newly inserted exercise from DB
//								mDbHelper.trueDeleteExercise(exId);
//								// delete any intervals that may have
//								// already been inserted to DB
//								for (long intervalId : intervalIds) {
//									mDbHelper.deleteInterval(intervalId);
//								}
//
//								// display to user that more info required
//								text = "Interval names and times required.";
//								Toast toast = Toast.makeText(context, text,
//										duration);
//								toast.setGravity(Gravity.CENTER, 0, 0);
//								toast.show();
//
//								// end the button press sequence
//								return;
//							}
//
//						}
//						// if sets need to be stored
//					} else if (layoutMarker == 1) {
//						// list holds the new IDs for deletion from DB if
//						// something goes wrong
//						List<Long> setIds = new ArrayList<Long>();
//
//						// for each set
//						for (LinearLayout inflatedSet : inflatedLayouts) {
//							EditText repsText = (EditText) inflatedSet
//									.findViewById(R.id.etCreateExerciseReps);
//							EditText weightText = (EditText) inflatedSet
//									.findViewById(R.id.etCreateExerciseWeight);
//
//							String reps = repsText.getText().toString();
//							String weight = weightText.getText().toString();
//
//							// if user has specified both weight and reps,
//							// store the set
//							if (reps != null && reps.length() > 0
//									&& weight != null && weight.length() > 0) {
//								setVar.setExerciseId(exId);
//								setVar.setReps(Integer.parseInt(reps));
//								setVar.setWeight(Double.parseDouble(weight));
//								setIds.add(mDbHelper.createSet(setVar));
//								// else the user needs to specify more info,
//								// previous insertions in DB must be rolled
//								// back
//							} else {
//								// delete newly inserted exercise from DB
//								mDbHelper.trueDeleteExercise(exId);
//								// delete any sets that may have already
//								// been inserted to the DB
//								for (long setId : setIds) {
//									mDbHelper.deleteSet(setId);
//								}
//
//								// display to user that more info is
//								// required
//								text = "Set weight and reps required.";
//								Toast toast = Toast.makeText(context, text,
//										duration);
//								toast.setGravity(Gravity.CENTER, 0, 0);
//								toast.show();
//
//								// end the button press sequence
//								return;
//							}
//						}
//					}
//
//					// display that the exercise was created
//					text = "Exercise Created";
//					Toast toast = Toast.makeText(context, text, duration);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				} catch (IllegalArgumentException e) {
//					Toast toast = Toast.makeText(context, e.getMessage(),
//							duration);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				}
			}
		});

	}
*/
	/**
	 * defines and initializes the array adapter for spinner, based on the
	 * provided arrayID
	 * 
	 * @param arrayID
	 * @param spinner
	 */
	private void initSpinner(int arrayID, Spinner spinner) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, arrayID, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	/**
	 * purges a list of LinearLayouts for future use, and removes these Layout
	 * Views from the provided two LinearLayouts
	 * 
	 * @param inflatedLayouts
	 * @param intervalLayout
	 * @param setLayout
	 */
	private void clearLayouts(List<LinearLayout> inflatedLayouts,
			LinearLayout intervalLayout, LinearLayout setLayout) {
		while (!inflatedLayouts.isEmpty()) {
			int position = inflatedLayouts.size() - 1;
			intervalLayout.removeView(inflatedLayouts.get(position));
			setLayout.removeView(inflatedLayouts.get(position));
			inflatedLayouts.remove(position);
		}
	}
}
