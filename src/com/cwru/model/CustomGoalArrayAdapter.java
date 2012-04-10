package com.cwru.model;

import java.util.List;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.fragments.CreateCustomGoalFragment;
import com.cwru.fragments.ViewCustomGoalFragment;
import com.cwru.model.ExerciseGoalArrayAdapter.ExerciseGoalRow;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomGoalArrayAdapter extends ArrayAdapter<CustomGoal> {
	private final List<CustomGoal> list;
	private final Activity context;
	private final ListFragment fragment;
	private DbAdapter mDbHelper;
	
	public CustomGoalArrayAdapter(Activity context, List<CustomGoal> list, ListFragment fragment) {
		super(context, R.layout.exercise_bank_row, list);
		this.list = list;
		this.context = context;
		this.fragment = fragment;
		mDbHelper = new DbAdapter(context);
	}
	
	static class CustomGoalRow {
		protected TextView textView;
		protected TextView pos;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if (convertView == null) {
			LayoutInflater layoutInflater = context.getLayoutInflater();
			view = layoutInflater.inflate(R.layout.exercise_bank_row, null);
			final CustomGoalRow row = new CustomGoalRow();
			row.textView = (TextView) view.findViewById(R.id.tvEBRExerciseLabel);
			row.pos = (TextView) view.findViewById(R.id.tvEBRExercisePosition);
			view.setTag(row);
		} else {
			view = convertView;
			((CustomGoalRow) view.getTag()).textView.setTag(list.get(position));
			((CustomGoalRow) view.getTag()).pos.setTag(list.get(position));
		}
		
		CustomGoalRow row = (CustomGoalRow) view.getTag();
		row.textView.setText(list.get(position).getName());
		
		//if the goal has been completed, set text color to green
		if (list.get(position).getIsCompleted()) {
			row.textView.setTextColor(0xFF33FF00);
		}
		
		row.pos.setText(Integer.toString(position));
		view.setOnClickListener(customGoalListener);
		return view;
	}
	
	View.OnClickListener customGoalListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
			
			TextView tvGoalName = (TextView) v.findViewById(R.id.tvEBRExerciseLabel);
			String goalName = tvGoalName.getText().toString();
			
			TextView tvPos = (TextView) v.findViewById(R.id.tvEBRExercisePosition);
			int pos = Integer.parseInt(tvPos.getText().toString());
			
			if ("+ Add Goal".equals(goalName)) {
				CreateCustomGoalFragment newFragment = new CreateCustomGoalFragment();
				
				if (HomeScreen.isTablet) {
					transaction.replace(R.id.flGoalRightFrame, newFragment);
					transaction.commit();
				} else {
					transaction.remove(fragment);
					transaction.add(R.id.flGoalMainFrame, newFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				}
			} else {
				ViewCustomGoalFragment newFragment = new ViewCustomGoalFragment(list.get(pos));
				
				if (HomeScreen.isTablet) {
					transaction.replace(R.id.flGoalRightFrame, newFragment);
					transaction.commit();
				} else {
					transaction.remove(fragment);
					transaction.add(R.id.flGoalMainFrame, newFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				}
			}
		}
	};
}