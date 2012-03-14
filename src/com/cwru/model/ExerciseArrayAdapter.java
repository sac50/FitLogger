package com.cwru.model;

import java.util.List;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.fragments.EditExerciseFragment;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
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
			row.textView = (TextView) view.findViewById(R.id.tvEBRExerciseLabel);
			view.setTag(row);
		} else {
			view = convertView;
			((ExerciseRow) view.getTag()).textView.setTag(list.get(position));
		}
		
		ExerciseRow row = (ExerciseRow) view.getTag();
		row.textView.setText(list.get(position).getName());
		
		view.setOnClickListener(editExerciseListener);
		
		return view;
	}
	
	View.OnClickListener editExerciseListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			TextView etExName = (TextView) v.findViewById(R.id.tvEBRExerciseLabel);
			String exName = etExName.getText().toString();
			
			Exercise ex;
			
			try {
				mDbHelper.open();
				ex = mDbHelper.getExerciseFromName(exName);
			} finally {
				mDbHelper.close();
			}
			
			
			FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
			EditExerciseFragment newFragment = new EditExerciseFragment(ex);
			
			if (!HomeScreen.isTablet) {
				transaction.remove(fragment);
				transaction.add(R.id.flEditExerciseMainFrame, newFragment);
//				transaction.replace(R.id.flEditExerciseMainFrame, newFragment);	
				transaction.addToBackStack(null);
				transaction.commit();
			} else {
				transaction.replace(R.id.flEditExerciseRightFrame, newFragment);
				transaction.commit();
			}
			
//			Intent intent = new Intent(EditExerciseBankFragment.this.getActivity(), EditExercise.class);
//			intent.putExtra("exercise", ex);
//			startActivity(intent);
		}
	};
	
}