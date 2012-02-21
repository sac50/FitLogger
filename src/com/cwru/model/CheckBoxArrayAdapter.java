package com.cwru.model;

import java.util.List;

import android.app.Activity;
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

public class CheckBoxArrayAdapter extends ArrayAdapter<ExerciseBankRow> {
	private final List<ExerciseBankRow> list;
	private final Activity context;
	private final ListFragment fragment;
	
	public CheckBoxArrayAdapter(Activity context, List<ExerciseBankRow> list, ListFragment fragment) {
		super(context, com.cwru.R.layout.exercise_bank_checkbox_row, list);
		this.context = context;
		this.list = list;
		this.fragment = fragment;
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
					ebRow.setSelected(buttonView.isChecked());
					Log.d("Steve", "Checked box has been checked");
					Log.d("Steve", "CheckBox Tag: " + ebRow.getExerciseName());
					//ExerciseSequenceFragment esequence = new ExerciseSequenceFragment();
					ExerciseSequenceFragment esequence = (ExerciseSequenceFragment) fragment.getFragmentManager().findFragmentByTag("exerciseSequence");
					esequence.addItems(ebRow.getExerciseName());
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
