package com.cwru.model;

import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.dao.DbAdapter;

public class CheckBoxArrayAdapter extends ArrayAdapter<ExerciseBankRow> {
	private final List<ExerciseBankRow> list;
	private final Activity context;
	private final ListFragment fragment;
	private DbAdapter mDbHelper;

	
	public CheckBoxArrayAdapter(Activity context, List<ExerciseBankRow> list, ListFragment fragment) {
		super(context, com.cwru.R.layout.exercise_bank_checkbox_row, list);
		this.context = context;
		this.list = list;
		this.fragment = fragment;
		mDbHelper = new DbAdapter(context);

	}
	
	static class CheckBoxRow {
		protected TextView textView;
		protected CheckBox checkBox;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = context.getLayoutInflater();
			view = layoutInflater.inflate(R.layout.exercise_bank_checkbox_row, null);
			final CheckBoxRow row = new CheckBoxRow();
			row.textView = (TextView) view.findViewById(R.id.tvEBExerciseLabel);
			row.checkBox = (CheckBox) view.findViewById(R.id.cbEBExerciseCheckbox);
			// Set Listener for checkbox
			row.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
					ExerciseBankRow ebRow = (ExerciseBankRow) row.checkBox.getTag();
					Long exerciseId = ebRow.getExerciseId();
					String workoutName = ebRow.getWorkoutName();
					String exerciseSequence = "";
					Exercise exercise = new Exercise(exerciseId, ebRow.getExerciseName());
					if (isChecked) {
						ebRow.setSelected(buttonView.isChecked());
						ExerciseSequenceFragment esequenceFragment = (ExerciseSequenceFragment) fragment.getFragmentManager().findFragmentByTag("exerciseSequence");
						esequenceFragment.addItems(exercise);
						ebRow.setSelected(true);
						/** 
						 * TODO add exercise to database for this workout
						 */
						/* 
						 * Exercises are part of the exercise sequence for a workout
						 * format is id,id,id,id,id,
						 * append exercise to end of list
						 */
						mDbHelper.open();
						Cursor cursor = mDbHelper.getExerciseSequence(workoutName);
						while (cursor.moveToNext()) {
							exerciseSequence = cursor.getString(cursor.getColumnIndex("exercise_sequence"));
						}
						// Append exercise to end of sequence
						exerciseSequence += exerciseId + ",";
						// Update Exercise Sequence for workout
						Log.d("Exercise Sequence", exerciseSequence);
						mDbHelper.updateWorkoutExerciseSequence(exerciseSequence, workoutName);
						mDbHelper.close();
					} 
					else {
						ebRow.setSelected(buttonView.isChecked());
						ExerciseSequenceFragment esequence = (ExerciseSequenceFragment) fragment.getFragmentManager().findFragmentByTag("exerciseSequence");
						esequence.removeItem(exercise);
						ebRow.setSelected(false);
						/**
						 * TODO remove exercise in database for workout
						 */
						mDbHelper.open();
						Cursor cursor = mDbHelper.getExerciseSequence(workoutName);
						while (cursor.moveToNext()) {
							exerciseSequence = cursor.getString(cursor.getColumnIndex("exercise_sequence"));
						}
						// Sequence is #,#,#,#,#,
						String exerciseToRemove = exerciseId + ",";
						Log.d("Exercise Sequence BEFORE: ", exerciseSequence);
						exerciseSequence = exerciseSequence.replace(exerciseToRemove, "");
						Log.d("Exercise Sequence AFTER: ", exerciseSequence);
						// update sequence in db
						mDbHelper.updateWorkoutExerciseSequence(exerciseSequence, workoutName);
						mDbHelper.close();					
					}					
					
				}
			});
			
			view.setTag(row);
			row.checkBox.setTag(list.get(position));
		} else {
			view = convertView;
			((CheckBoxRow) view.getTag()).checkBox.setTag(list.get(position));
		}
		
		CheckBoxRow row = (CheckBoxRow) view.getTag();
		row.textView.setText(list.get(position).getExerciseName());
		row.checkBox.setChecked(list.get(position).isSelected());
		return view;
	}
}
