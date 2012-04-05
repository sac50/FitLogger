package com.cwru.model;

import java.util.List;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.fragments.CreateExerciseGoalFragment;
import com.cwru.fragments.ViewExerciseGoalFragment;

import android.app.Activity;
import android.app.ListActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExerciseGoalArrayAdapter extends ArrayAdapter<ExerciseGoal> {
	private final List<ExerciseGoal> list;
	private final Activity context;
	private final ListFragment fragment;
	private DbAdapter mDbHelper;
	
	public ExerciseGoalArrayAdapter(Activity context, List<ExerciseGoal> list, ListFragment fragment) {
		super(context, R.layout.exercise_bank_row, list);
		this.list = list;
		this.context = context;
		this.fragment = fragment;
		mDbHelper = new DbAdapter(context);
	}
	
	static class ExerciseGoalRow {
		protected TextView textView;
		protected TextView pos;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if (convertView == null) {
			LayoutInflater layoutInflater = context.getLayoutInflater();
			view = layoutInflater.inflate(R.layout.exercise_bank_row, null);
			final ExerciseGoalRow row = new ExerciseGoalRow();
			row.textView = (TextView) view.findViewById(R.id.tvEBRExerciseLabel);
			row.pos = (TextView) view.findViewById(R.id.tvEBRExercisePosition);
			view.setTag(row);
		} else {
			view = convertView;
			((ExerciseGoalRow) view.getTag()).textView.setTag(list.get(position));
			((ExerciseGoalRow) view.getTag()).pos.setTag(list.get(position));
		}
		
		ExerciseGoalRow row = (ExerciseGoalRow) view.getTag();
		row.textView.setText(list.get(position).getName());
		
		//if the goal has been completed, set text color to green
		if (list.get(position).getIsCompleted()) {
			row.textView.setTextColor(0xFF33FF00);
		}
		row.pos.setText(Integer.toString(position));
		
		view.setOnClickListener(exerciseGoalListener);		
		return view;
	}
	
	View.OnClickListener exerciseGoalListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
			
			TextView tvGoalName = (TextView) v.findViewById(R.id.tvEBRExerciseLabel);
			String goalName = tvGoalName.getText().toString();
			
			TextView tvPos = (TextView) v.findViewById(R.id.tvEBRExercisePosition);
			int pos = Integer.parseInt(tvPos.getText().toString());
			
			if ("+ Add Goal".equals(goalName)) {
				CreateExerciseGoalFragment newFragment = new CreateExerciseGoalFragment();
				
				if (!HomeScreen.isTablet) {
					transaction.remove(fragment);
					transaction.add(R.id.flExerciseGoalMainFrame, newFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				} else {
					transaction.replace(R.id.flExerciseGoalRightFrame, newFragment);
					transaction.commit();
				}
			} else {
				ViewExerciseGoalFragment newFragment = new ViewExerciseGoalFragment(list.get(pos));
				
				if (!HomeScreen.isTablet) {
					transaction.remove(fragment);
					transaction.add(R.id.flExerciseGoalMainFrame, newFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				} else {
					transaction.replace(R.id.flExerciseGoalRightFrame, newFragment);
					transaction.commit();
				}
			}
		}
	};
}