package com.cwru.model;

import java.util.List;

import com.cwru.R;
import com.cwru.dao.DbAdapter;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExerciseArrayAdapter extends ArrayAdapter<Exercise>{
	private final List<Exercise> list;
	private final Activity context;
	private final ListFragment fragment;
	private DbAdapter mDbHelper;
	
	public ExerciseArrayAdapter(Activity context, List<Exercise> list, ListFragment fragment) {
		super(context, com.cwru.R.layout.exercise_bank_row, list);
		this.context = context;
		this.list = list;
		this.fragment = fragment;
		mDbHelper = new DbAdapter(context);

	}
	
	static class ExerciseRow {
		protected TextView textView;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = context.getLayoutInflater();
			view = layoutInflater.inflate(R.layout.exercise_bank_row, null);
			final ExerciseRow row = new ExerciseRow();
			row.textView = (TextView) view.findViewById(R.id.tvEBExerciseLabel);
			view.setTag(row);
		} else {
			view = convertView;
			((ExerciseRow) view.getTag()).textView.setTag(list.get(position));
		}
		
		ExerciseRow row = (ExerciseRow) view.getTag();
		row.textView.setText(list.get(position).getName());
		return view;
	}
	
}