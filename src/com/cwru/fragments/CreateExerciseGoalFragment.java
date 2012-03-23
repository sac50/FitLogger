package com.cwru.fragments;

import java.util.ArrayList;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Distance;
import com.cwru.model.Exercise;
import com.cwru.model.ExerciseGoal;
import com.cwru.model.Set;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateExerciseGoalFragment extends Fragment {
	private DbAdapter mDbHelper;
	private ExerciseGoal exGoal;
	private ArrayList<Exercise> cardioExercises;
	private ArrayList<Exercise> strengthExercises;
	private Exercise exercise;
	private Distance distance;
	private Set set;
	private Time time;
	private View topView;
	private LinearLayout cardioView;
	private LinearLayout strengthView;
	private LinearLayout exerciseView;
	private View distanceView;
	private View setView;
	private View timeCardioView;
	private View timeStrengthView;
	private Spinner typeSpinner;
	private Spinner exCardioSpinner;
	private Spinner exStrengthSpinner;
	private RadioGroup distanceOrTime;
	
	private void init() {
		exGoal.setMode(ExerciseGoal.RUN);
		exGoal.setType(ExerciseGoal.DISTANCE);
		
		cardioView = (LinearLayout) topView.findViewById(R.id.llCreateExerciseGoalCardio);
		strengthView = (LinearLayout) topView.findViewById(R.id.llCreateExerciseGoalStrength);
		
		distanceView = View.inflate(getActivity(), R.layout.create_exercise_goal_distance, null);
		setView = View.inflate(getActivity(), R.layout.create_exercise_set_builder, null);
		timeCardioView = View.inflate(getActivity(), R.layout.create_exercise_goal_time, null);
		timeStrengthView = View.inflate(getActivity(), R.layout.create_exercise_goal_time, null);
		
		setView.findViewById(R.id.tvCreateExerciseSetNum).setVisibility(8);
		
		initSpinner(R.array.exerciseDistances, (Spinner) distanceView.findViewById(R.id.spnCreateExerciseGoalDistance));
		initSpinner(R.array.timeTypes, (Spinner) timeCardioView.findViewById(R.id.spnCreateExerciseGoalTime));
		initSpinner(R.array.timeTypes, (Spinner) timeStrengthView.findViewById(R.id.spnCreateExerciseGoalTime));
		
		cardioView.addView(distanceView);
		
		distanceOrTime = (RadioGroup) topView.findViewById(R.id.rgDistanceOrTime);
		distanceOrTime.setOnCheckedChangeListener(distanceOrTimeListener);
		
		exCardioSpinner = (Spinner) cardioView.findViewById(R.id.spnExGoalCardioExercise);
		initSpinner(exCardioSpinner, cardioExercises);
		exCardioSpinner.setOnItemSelectedListener(exCardioListener);
		
		exStrengthSpinner = (Spinner) strengthView.findViewById(R.id.spnExGoalStrengthExercise);
		initSpinner(exStrengthSpinner, strengthExercises);
		exStrengthSpinner.setOnItemSelectedListener(exStrengthListener);
		
		Exercise ex = strengthExercises.get(exStrengthSpinner.getSelectedItemPosition());
		if (ex.getMode() == Exercise.SET_BASED_EXERCISE) {
			strengthView.addView(setView);
		} else {
			strengthView.addView(timeStrengthView);
		}
		
		typeSpinner = (Spinner) topView.findViewById(R.id.spnExGoalType);
		initSpinner(R.array.goalCardioTypes, typeSpinner);
		typeSpinner.setOnItemSelectedListener(typeListener);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		exGoal = new ExerciseGoal();
		
		cardioExercises = mDbHelper.getAllCardioExercises();
		strengthExercises = mDbHelper.getAllStrengthExercises();
		
		topView = (ScrollView) inflater.inflate(R.layout.create_exercise_goal, container, false);
		init();
				
		RadioGroup cardioOrStrength = (RadioGroup) topView.findViewById(R.id.rgCardioOrStrength);
		cardioOrStrength.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//generate type spinner
				if (checkedId == R.id.rbCardio) {
					typeSpinner.setVisibility(0);
					
					strengthView.setVisibility(8);
					
					inflateCardio();
					
				//generate exercise spinner
				} else {
					exGoal.setMode(ExerciseGoal.SPECIFIC_STRENGTH_EXERCISE);
					exGoal.setType(-1);
					
					Exercise ex = strengthExercises.get(exStrengthSpinner.getSelectedItemPosition());
					if (ex.getMode() == Exercise.SET_BASED_EXERCISE) {
						exGoal.setType(ExerciseGoal.SET);
					} else {
						exGoal.setType(ExerciseGoal.TIME);
					}
					
					strengthView.setVisibility(0);
					
					//hide type spinner if present
					typeSpinner.setVisibility(8);
					//hide cardio view
					cardioView.setVisibility(8);
				}
			}
		});
		
		Button button = (Button) topView.findViewById(R.id.btnCreateExerciseGoalDone);
		button.setOnClickListener(doneListener);
		
		return topView;
	}
	
	private void inflateCardio() {
		exGoal.setMode((int) typeSpinner.getSelectedItemId());
		
		if (exCardioSpinner.getSelectedItemPosition() == 5) {
			if (exercise.getMode() == Exercise.DISTANCE_BASED_EXERCISE) {
				exGoal.setType(ExerciseGoal.DISTANCE);
			} else {
				exGoal.setType(ExerciseGoal.TIME);
			}
		}
		if (distanceOrTime.getCheckedRadioButtonId() == R.id.rbDistance) {
			exGoal.setType(ExerciseGoal.DISTANCE);
		} else {
			exGoal.setType(ExerciseGoal.TIME);
		}
		
		cardioView.setVisibility(0);

	}
	
	AdapterView.OnItemSelectedListener typeListener = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			exGoal.setMode(pos);
			
			switch (pos) {
			
			default:
				cardioView.removeAllViews();
				
				cardioView.addView(distanceOrTime);
				distanceOrTime.setVisibility(0);
				
				if (distanceOrTime.getCheckedRadioButtonId() == R.id.rbDistance) {
					cardioView.addView(distanceView);
					exGoal.setType(ExerciseGoal.DISTANCE);
				} else {
					cardioView.addView(timeCardioView);
					exGoal.setType(ExerciseGoal.TIME);
				}
				
				break;
			
			//Specific Exercise
			case 5:
				exGoal.setType(-1);
				
				cardioView.addView(exCardioSpinner);
				
				if (distanceOrTime.getCheckedRadioButtonId() == R.id.rbDistance) {
					cardioView.removeView(distanceView);
				} else {
					cardioView.removeView(timeCardioView);
				}
				cardioView.removeView(distanceOrTime);
				
				Exercise ex = cardioExercises.get(exCardioSpinner.getSelectedItemPosition());
				if (ex.getMode() == Exercise.DISTANCE_BASED_EXERCISE) {
					cardioView.addView(distanceView);
					exGoal.setType(ExerciseGoal.DISTANCE);
				} else {
					cardioView.addView(timeCardioView);
					exGoal.setType(ExerciseGoal.TIME);
				}
				
				exCardioSpinner.setVisibility(0);
				break;
				
			
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			//do nothing
		}
	};
	
	AdapterView.OnItemSelectedListener exCardioListener = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			Exercise ex = cardioExercises.get(pos);
			if (ex.getMode() == Exercise.DISTANCE_BASED_EXERCISE && exGoal.getType() != ExerciseGoal.DISTANCE) {
				cardioView.removeView(timeCardioView);
				cardioView.addView(distanceView);
				exGoal.setType(ExerciseGoal.DISTANCE);
			} else  if (ex.getMode() != Exercise.DISTANCE_BASED_EXERCISE && exGoal.getType() != ExerciseGoal.TIME) {
				cardioView.removeView(distanceView);
				cardioView.addView(timeCardioView);
				exGoal.setType(ExerciseGoal.TIME);
			}
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			//do nothing
		}
	};
	
	AdapterView.OnItemSelectedListener exStrengthListener = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			Exercise ex = strengthExercises.get(pos);
			if (ex.getMode() == Exercise.SET_BASED_EXERCISE && exGoal.getType() != ExerciseGoal.SET) {
				strengthView.removeView(timeStrengthView);
				strengthView.addView(setView);
				exGoal.setType(ExerciseGoal.SET);
			} else if (ex.getMode() != Exercise.SET_BASED_EXERCISE && exGoal.getType() != ExerciseGoal.TIME) {
				strengthView.removeView(setView);
				strengthView.addView(timeStrengthView);
				exGoal.setType(ExerciseGoal.TIME);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			//do nothing
		}
	};
	
	RadioGroup.OnCheckedChangeListener distanceOrTimeListener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == R.id.rbDistance) {
				cardioView.addView(distanceView);
				cardioView.removeView(timeCardioView);
				exGoal.setType(ExerciseGoal.DISTANCE);
			} else {
				cardioView.addView(timeCardioView);
				cardioView.removeView(distanceView);
				exGoal.setType(ExerciseGoal.TIME);
			}
		}
	};
	
	View.OnClickListener doneListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			EditText nameText = (EditText) topView.findViewById(R.id.etCreateExerciseGoalName);
			String name = nameText.getText().toString();
			
			Context context = v.getContext();
			CharSequence toastText;
			int duration = Toast.LENGTH_SHORT;
			
			if (name != null && name.length() > 0) {
				exGoal.setName(name);
			} else {
				toastText = "Please specify a name for your goal.";
				Toast toast = Toast.makeText(context, toastText, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
			
			switch (exGoal.getMode()) {
			
			default:
				break;
				
			case ExerciseGoal.SPECIFIC_CARDIO_EXERCISE:
				exGoal.setExerciseId(cardioExercises.get(exCardioSpinner.getSelectedItemPosition()).getId());
				break;
			
			case ExerciseGoal.SPECIFIC_STRENGTH_EXERCISE:
				exGoal.setExerciseId(strengthExercises.get(exStrengthSpinner.getSelectedItemPosition()).getId());
				break;
			}
			
			switch (exGoal.getType()) {
			
			case ExerciseGoal.DISTANCE:
				EditText text = (EditText) distanceView.findViewById(R.id.etCreateExerciseGoalDistance);
				Spinner spinner = (Spinner) distanceView.findViewById(R.id.spnCreateExerciseGoalDistance);
				
				String distance = text.getText().toString();
				String distanceUnit = spinner.getSelectedItem().toString();
				
				if (distance != null && distance.length() > 0) {
					exGoal.setGoalOne(Double.parseDouble(distance));
					exGoal.setUnit(distanceUnit);
					mDbHelper.createExerciseGoal(exGoal);
				} else {
					toastText = "Please specify a distance.";
					Toast toast = Toast.makeText(context, toastText, duration);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				}
				break;
				
			case ExerciseGoal.SET:
				EditText weightText = (EditText) setView.findViewById(R.id.etCreateExerciseWeight);
				EditText repsText = (EditText) setView.findViewById(R.id.etCreateExerciseReps);
				
				String weight = weightText.getText().toString();
				String reps = repsText.getText().toString();
				
				if (weight != null && weight.length() > 0
						&& reps != null && reps.length() > 0) {
					exGoal.setGoalOne(Double.parseDouble(weight));
					exGoal.setGoalTwo(Double.parseDouble(reps));
					mDbHelper.createExerciseGoal(exGoal);
				} else {
					toastText = "Please specify weight and rep values.";
					Toast toast = Toast.makeText(context, toastText, duration);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				}
				break;
				
			case ExerciseGoal.TIME:
				String time = new String();
				String timeUnit = new String();
				
				EditText timeText;
				Spinner timeSpinner;
				
				if (exGoal.getMode() == ExerciseGoal.SPECIFIC_STRENGTH_EXERCISE) {
					timeText = (EditText) timeStrengthView.findViewById(R.id.etCreateExerciseGoalTime);
					timeSpinner = (Spinner) timeStrengthView.findViewById(R.id.spnCreateExerciseGoalTime);
				} else {
					timeText = (EditText) timeCardioView.findViewById(R.id.etCreateExerciseGoalTime);
					timeSpinner = (Spinner) timeCardioView.findViewById(R.id.spnCreateExerciseGoalTime);
				}
				
				time = timeText.getText().toString();
				timeUnit = timeSpinner.getSelectedItem().toString();
				
				if (time != null && time.length() > 0) {
					exGoal.setGoalOne(Double.parseDouble(time));
					exGoal.setUnit(timeUnit);
					mDbHelper.createExerciseGoal(exGoal);
				} else {
					toastText = "Please specify a time.";
					Toast toast = Toast.makeText(context, toastText, duration);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				}
				break;
			}
			if (HomeScreen.isTablet) {
				FragmentTransaction transaction = CreateExerciseGoalFragment.this.getFragmentManager().beginTransaction();
				ExerciseGoalBankFragment newFrag = new ExerciseGoalBankFragment();
				
				transaction.replace(R.id.flExerciseGoalLeftFrame, newFrag);
				transaction.commit();
			} else {
				FragmentTransaction transaction = CreateExerciseGoalFragment.this.getFragmentManager().beginTransaction();
				ExerciseGoalBankFragment newFrag = new ExerciseGoalBankFragment();
				
				transaction.replace(R.id.flExerciseGoalMainFrame, newFrag);
				transaction.commit();
			}
		}
	};
	
	/**
	 * defines and initializes the array adapter for spinner, based on the
	 * provided arrayID
	 * 
	 * @param arrayID
	 * @param spinner
	 */
	private void initSpinner(int arrayID, Spinner spinner) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this.getActivity(), arrayID, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	
	/**
	 * defines and initializes the array adapter for spinner, based on the
	 * provided arraylist of exercises
	 * 
	 * @param spinner
	 * @param exercises
	 */
	private void initSpinner(Spinner spinner, ArrayList<Exercise> exercises) {
		ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (this.getActivity(), android.R.layout.simple_spinner_item );
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (Exercise ex : exercises) {
			adapter.add(ex.getName());
		}
		spinner.setAdapter(adapter);
	}
}