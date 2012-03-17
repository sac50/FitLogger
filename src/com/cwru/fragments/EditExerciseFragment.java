package com.cwru.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Distance;
import com.cwru.model.Exercise;
import com.cwru.model.Interval;
import com.cwru.model.Set;
import com.cwru.model.Time;
import com.cwru.utils.AutoFillListener;

public class EditExerciseFragment extends Fragment {
	private DbAdapter mDbHelper;
	private View topView;
	private LinearLayout view;
	private LinearLayout setView;
	private Exercise ex;
	private Distance distance;
	private Time time;
	private List<LinearLayout> inflatedLayouts;
	private List<Integer> ids = new ArrayList<Integer>();
	private AutoFillListener autoFillListener = new AutoFillListener();

	public EditExerciseFragment() {

	}

	public EditExerciseFragment(Exercise ex) {
		this.ex = ex;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new DbAdapter(this.getActivity());

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		topView = (ScrollView) inflater.inflate(
				R.layout.edit_exercise_editor, container, false);

		if (ex != null) {
			ex.setMode(mDbHelper.getExerciseMode(ex.getId()));
			
			setTextView(topView, R.id.tvEditExerciseName, ex.getName());
			setTextView(topView, R.id.tvEditExerciseType, ex.getType());
			setSubType(topView, R.id.tvEditExerciseSubType, ex);
			
			Button doneButton = (Button) topView.findViewById(R.id.btnEditExerciseDone);
			doneButton.setOnClickListener(doneButtonListener);
		} else {
			topView.setVisibility(8);
		}
		
//		setTextView(topView, R.id.tvEditExerciseName, ex.getName());
//		setTextView(topView, R.id.tvEditExerciseType, ex.getType());
//		setSubType(topView, R.id.tvEditExerciseSubType, ex);
//		
//		Button doneButton = (Button) topView.findViewById(R.id.btnEditExerciseDone);
//		doneButton.setOnClickListener(doneButtonListener);

		return topView;
	}

	private void setTextView(View parent, int id, String str) {
		TextView tv = (TextView) parent.findViewById(id);
		tv.setText(str);
	}

	private void setSubType(View parent, int id, Exercise ex) {
		String str = "";
		
		switch (ex.getMode()) {
		
			case Exercise.DISTANCE_BASED_EXERCISE:
				str = "Distance";
				view = (LinearLayout) parent.findViewById(R.id.llEditExerciseDistance);
				view.setVisibility(0);
				populateDistance();
				break;
			
			case Exercise.COUNTDOWN_BASED_EXERCISE:
				str = "Countdown Timer";
				view = (LinearLayout) parent.findViewById(R.id.llEditExerciseCountdown);
				view.setVisibility(0);
				populateCountdown();
				break;
			
			case Exercise.COUNTUP_BASED_EXERCISE:
				str = "Countup Timer";
				break;
			
			case Exercise.INTERVAL_BASED_EXERCISE:
				str = "Intervals";
				
				view = (LinearLayout) parent.findViewById(R.id.llEditExerciseIntervals);
				view.setVisibility(0);
				populateIntervals((LinearLayout) view);
				defineIntervalButtons();
				
				setView = (LinearLayout) parent.findViewById(R.id.llEditExerciseIntervalSets);
				setView.setVisibility(0);
				populateIntervalSets();
				defineIntervalSetButtons();
				
				break;
				
			case Exercise.SET_BASED_EXERCISE:
				str = "Sets";
				view = (LinearLayout) parent.findViewById(R.id.llEditExerciseSets);
				view.setVisibility(0);
				populateSets((LinearLayout) view);
				defineSetButtons();
				break;
				
			default:
				return;
		}

		setTextView(parent, R.id.tvEditExerciseSubType, str);
	}

	private void populateDistance() {
		distance = mDbHelper.getDistanceForExercise(ex.getId());
		EditText exDistanceText = (EditText) view.findViewById(R.id.etEditExerciseDistance);
		exDistanceText.setText(String.valueOf(distance.getLength()));

		Spinner distanceSpinner = (Spinner) view.findViewById(R.id.spnEditExerciseDistance);
		initSpinner(R.array.exerciseDistances, distanceSpinner);
		
		ArrayAdapter<String> adapter = (ArrayAdapter<String>) distanceSpinner.getAdapter();
		int position = adapter.getPosition(distance.getUnits());
		distanceSpinner.setSelection(position);
		
		distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int pos, long id) {
				distance.setUnits(parent.getItemAtPosition(pos).toString());
			}

			@Override
			public void onNothingSelected(AdapterView parent) {

			}
		});
	}
	
	private void populateCountdown() {
		time = mDbHelper.getTimeForExercise(ex.getId());
		EditText exCountdownText = (EditText) view.findViewById(R.id.etEditExerciseCountdown);
		exCountdownText.setText(String.valueOf(time.getLength()));

		Spinner countdownSpinner = (Spinner) view.findViewById(R.id.spnEditExerciseCountdown);
		initSpinner(R.array.timeTypes, countdownSpinner);
		
		ArrayAdapter<String> adapter = (ArrayAdapter<String>) countdownSpinner.getAdapter();
		int position = adapter.getPosition(time.getUnits());
		countdownSpinner.setSelection(position);
		
		countdownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int pos, long id) {
				time.setUnits(parent.getItemAtPosition(pos).toString());				
			}		
			@Override
			public void onNothingSelected(AdapterView parent) {
				
			}
		});
	}
	
	private void populateIntervals(LinearLayout view) {
		inflatedLayouts = new ArrayList<LinearLayout>();
		LinearLayout inflatedLayout;
		EditText name;
		EditText length;
		Spinner unit;
		
		ArrayList<Interval> intervals = mDbHelper.getIntervalsForExercise(ex.getId());
		for (Interval interval : intervals) {
			inflatedLayout = (LinearLayout) View.inflate(view.getContext(),
					R.layout.create_exercise_interval_builder, null);
			
			name = (EditText) inflatedLayout.findViewById(R.id.etCreateExerciseIntervalName);
			name.setText(interval.getName());
			
			length = (EditText) inflatedLayout.findViewById(R.id.etCreateExerciseIntervalLength);
			length.setText(Double.toString(interval.getLength()));
			
			unit = (Spinner) inflatedLayout.findViewById(R.id.spnCreateExerciseIntervalUnit);
			initSpinner(R.array.timeTypes, unit);
			ArrayAdapter<String> adapter = (ArrayAdapter<String>) unit.getAdapter();
			int position = adapter.getPosition(interval.getUnits());
			unit.setSelection(position);
			
			inflatedLayouts.add(inflatedLayout);
			view.addView(inflatedLayout);
			ids.add(interval.getId());
		}
	}
	
	private void populateIntervalSets() {
		TextView setNum = (TextView) setView.findViewById(R.id.tvEditExerciseIntervalSetNum);
//		setNum.setText(Integer.toString(ex.getIntervalSets()));
	}
	
	private void defineIntervalButtons() {
		Button add = (Button) view.findViewById(R.id.btnEditExerciseAddInterval);
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout inflatedLayout;
				
				if (inflatedLayouts.size() < 5) {
					inflatedLayout = (LinearLayout) View.inflate(v.getContext(),
							R.layout.create_exercise_interval_builder, null);
					
					inflatedLayouts.add(inflatedLayout);
					view.addView(inflatedLayout);
					
					Spinner timeType = (Spinner) inflatedLayout.findViewById(R.id.spnCreateExerciseIntervalUnit);
					initSpinner(R.array.timeTypes, timeType);
				}
			}
		});
		
		Button remove = (Button) view.findViewById(R.id.btnEditExerciseRemoveInterval);
		remove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (inflatedLayouts.size() > 1) {
					int position = inflatedLayouts.size() - 1;
					view.removeView(inflatedLayouts.get(position));
					inflatedLayouts.remove(position);
				}
			}
		});
	}
	
	private void defineIntervalSetButtons() {
		Button add = (Button) setView.findViewById(R.id.btnEditExerciseAddIntervalSet);
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tv = (TextView) setView.findViewById(R.id.tvEditExerciseIntervalSetNum);
				int setNum = Integer.parseInt(tv.getText().toString());
				
				if (setNum < 10) {
					setNum++;
					tv.setText(Integer.toString(setNum));
				}
			}
		});
		
		Button remove = (Button) setView.findViewById(R.id.btnEditExerciseRemoveIntervalSet);
		remove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tv = (TextView) setView.findViewById(R.id.tvEditExerciseIntervalSetNum);
				int setNum = Integer.parseInt(tv.getText().toString());
				
				if (setNum > 1) {
					setNum--;
					tv.setText(Integer.toString(setNum));
				}
			}
		});
	}
	
	private void populateSets(LinearLayout view) {
		inflatedLayouts = new ArrayList<LinearLayout>();
		LinearLayout inflatedLayout;
		EditText weightText;
		TextView setNumText;
		EditText repsText;
		
		ArrayList<Set> sets = mDbHelper.getSetsForExercise(ex.getId());
		for (Set set : sets) {
			inflatedLayout = (LinearLayout) View.inflate(view.getContext(),
					R.layout.create_exercise_set_builder, null);
			
			weightText = (EditText) inflatedLayout.findViewById(R.id.etCreateExerciseWeight);
			weightText.setText(Double.toString(set.getWeight()));
			
			setNumText = (TextView) inflatedLayout.findViewById(R.id.tvCreateExerciseSetNum);
			setNumText.setText("Set " + (inflatedLayouts.size() + 1));
			
			repsText = (EditText) inflatedLayout.findViewById(R.id.etCreateExerciseReps);
			repsText.setText(Integer.toString(set.getReps()));
			
			inflatedLayouts.add(inflatedLayout);
			view.addView(inflatedLayout);
			ids.add(set.getId());
		}
		
		weightText = (EditText) inflatedLayouts.get(0).findViewById(R.id.etCreateExerciseWeight);
		autoFillListener.weightAutoFillListener(weightText, inflatedLayouts);		
		repsText = (EditText) inflatedLayouts.get(0).findViewById(R.id.etCreateExerciseReps);
		autoFillListener.repsAutoFillListener(repsText, inflatedLayouts);
	}
	
	TextWatcher repsAutoFillListener = new TextWatcher() {
		@Override
		public void afterTextChanged(
				Editable arg0) {

		}

		@Override
		public void beforeTextChanged(
				CharSequence arg0, int arg1,
				int arg2, int arg3) {

		}

		// when text is changed in first set's reps param
		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
			LinearLayout inflatedSet;
			String replace = s.toString();
			// for every inflated set after the first
			for (int i = 1; i < inflatedLayouts.size(); i++) {
				inflatedSet = inflatedLayouts.get(i);
				String old = ((EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps))
						.getText().toString();
				// if the set's reps value is null or empty
				if (old == null|| old.length() < 1
						// OR the first set's reps value is notempty
						// AND equal to candidate set's reps value, minus the new char
						|| replace.length() > 0 && old.equals(replace.substring(0,replace.length() - 1))
						// OR equal to first set's reps value before a digit was deleted
						|| old.substring(0, old.length() - 1).equals(replace)) {
					// set the weight text equal to the first set's weight value
					EditText replaceReps = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps);
					replaceReps.setText(replace);
				}
			}
		}
	};
	
	private void defineSetButtons() {
		Button add = (Button) view.findViewById(R.id.btnEditExerciseAddSet);
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (inflatedLayouts.size() < 10) {
					LinearLayout inflatedLayout = (LinearLayout) View.inflate(view.getContext(),
							R.layout.create_exercise_set_builder, null);
					TextView setNumText = (TextView) inflatedLayout.findViewById(R.id.tvCreateExerciseSetNum);
					setNumText.setText("Set " + (inflatedLayouts.size() + 1));
					inflatedLayouts.add(inflatedLayout);
					view.addView(inflatedLayout);
				}
			}
		});
		
		Button remove = (Button) view.findViewById(R.id.btnEditExerciseRemoveSet);
		remove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (inflatedLayouts.size() > 1) {
					int position = inflatedLayouts.size() - 1;
					view.removeView(inflatedLayouts.get(position));
					inflatedLayouts.remove(position);
				}
			}
		});
	}
	
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
	
	View.OnClickListener doneButtonListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			// simple params for a Toast display
			Context context = view.getContext().getApplicationContext();
			CharSequence text;
			int duration = Toast.LENGTH_SHORT;

			switch (ex.getMode()) {
				
				case Exercise.DISTANCE_BASED_EXERCISE:
					
					EditText lengthText = (EditText) view.findViewById(R.id.etEditExerciseDistance);
					Spinner unitSpinner = (Spinner) view.findViewById(R.id.spnEditExerciseDistance);
					
					String length = lengthText.getText().toString();
					String unit = unitSpinner.getSelectedItem().toString();
					if (length.length() > 0) {
						distance.setLength(Double.parseDouble(length));
						distance.setUnits(unit);
						ex.setDistance(distance);
						break;
					} else {
						// user needs to submit more info
						text = "Please specify a distance.";
						Toast toast = Toast.makeText(context, text, duration);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						return;
					}
					
				case Exercise.COUNTUP_BASED_EXERCISE:
					break;
				
				case Exercise.COUNTDOWN_BASED_EXERCISE:
					EditText timeText = (EditText) view.findViewById(R.id.etEditExerciseCountdown);
					Spinner timeType = (Spinner) view.findViewById(R.id.spnEditExerciseCountdown);
					
					String timeString = timeText.getText().toString();
					String timeUnit = timeType.getSelectedItem().toString();
					
					if (timeString.length() > 0) {
						time.setLength(Integer.parseInt(timeString));
						time.setUnits(timeUnit);
						ex.setTime(time);
						break;
					} else {
						// user needs to submit more info
						text = "Please specify a time.";
						Toast toast = Toast.makeText(context, text, duration);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						return;
					}
					
				case Exercise.INTERVAL_BASED_EXERCISE:
					ArrayList<Interval> intervals = new ArrayList<Interval>();
					
					TextView setNumText = (TextView) setView.findViewById(R.id.tvEditExerciseIntervalSetNum);
					
					
					for (int i = 0; i < inflatedLayouts.size(); i++) {
						LinearLayout inflatedLayout = inflatedLayouts.get(i);
						Interval interval = new Interval();
						
						EditText nameText = (EditText) inflatedLayout.findViewById(R.id.etCreateExerciseIntervalName);
						interval.setName(nameText.getText().toString());
						
						EditText intervalTimeText = (EditText) inflatedLayout.findViewById(R.id.etCreateExerciseIntervalLength);
						interval.setLength(Double.parseDouble(intervalTimeText.getText().toString().length() > 0 ?
								intervalTimeText.getText().toString() : "0"));
						
						Spinner intervalUnit = (Spinner) inflatedLayout.findViewById(R.id.spnCreateExerciseIntervalUnit);
						interval.setUnits(intervalUnit.getSelectedItem().toString());
						
						interval.setExerciseId(ex.getId());
						
						if (i < ids.size()) {
							interval.setId(ids.get(i));
						}
						if (interval.getName() != null && interval.getName().length() > 0
								&& interval.getLength() > 0) {
							intervals.add(interval);
						} else {
							intervals.clear();
							
							text = "Please specify a name and time.";
							Toast toast = Toast.makeText(context, text, duration);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							return;
						}
					}
					
					while (intervals.size() < ids.size()) {
						mDbHelper.deleteInterval(ids.get(ids.size() - 1));
						ids.remove(ids.size() - 1);
					}
					
					ex.setInterval(intervals);
					break;
					
				case Exercise.SET_BASED_EXERCISE:
					ArrayList<Set> sets = new ArrayList<Set>();
					
					for (int i = 0; i < inflatedLayouts.size(); i++) {
						LinearLayout inflatedLayout = inflatedLayouts.get(i);
						Set set = new Set();
						
						EditText weightText = (EditText) inflatedLayout.findViewById(R.id.etCreateExerciseWeight);
						set.setWeight(Double.parseDouble(weightText.getText().toString().length() > 0 ?
								weightText.getText().toString() : "0"));
						
						EditText repsText = (EditText) inflatedLayout.findViewById(R.id.etCreateExerciseReps);
						set.setReps(Integer.parseInt(repsText.getText().toString().length() > 0 ?
								repsText.getText().toString() : "0"));
						
						set.setExerciseId(ex.getId());
						
						if (i < ids.size()) {
							set.setId(ids.get(i));
						}
						
						if (set.getWeight() > 0 && set.getReps() > 0) {
							sets.add(set);
						} else {
							sets.clear();
							
							text = "Please specify weight and reps.";
							Toast toast = Toast.makeText(context, text, duration);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							return;
						}
					}
					while (sets.size() < ids.size()) {
						mDbHelper.deleteSet(ids.get(ids.size() - 1));
						ids.remove(ids.size() - 1);
					}
					ex.setSets(sets);
					break;
			}
				
			mDbHelper.updateExercise(ex);

			text = "Exercise has been updated.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	};
}