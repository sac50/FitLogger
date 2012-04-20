package com.cwru.model;

import java.util.List;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.fragments.CreateBodyGoalFragment;
import com.cwru.fragments.ViewBodyGoalFragment;
import com.cwru.model.CustomGoalArrayAdapter.CustomGoalRow;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author lkissling
 *
 */
public class BodyGoalArrayAdapter extends ArrayAdapter<BodyGoal> {
	private final List<BodyGoal> list;
	private final Activity context;
	private final ListFragment fragment;
	private DbAdapter mDbHelper;
	
	/**
	 * 
	 * @param context
	 * @param list
	 * @param fragment
	 */
	public BodyGoalArrayAdapter(Activity context, List<BodyGoal> list, ListFragment fragment) {
		super(context, R.layout.exercise_bank_row, list);
		this.list = list;
		this.context = context;
		this.fragment = fragment;
		mDbHelper = new DbAdapter(context);
	}
	
	/**
	 * 
	 * @author sacrilley
	 *
	 */
	static class BodyGoalRow {
		protected TextView textView;
		protected TextView pos;
	}
	
	@Override
	/**
	 * 
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if (convertView == null) {
			LayoutInflater layoutInflater = context.getLayoutInflater();
			view = layoutInflater.inflate(R.layout.exercise_bank_row, null);
			final BodyGoalRow row = new BodyGoalRow();
			row.textView = (TextView) view.findViewById(R.id.tvEBRExerciseLabel);
			row.pos = (TextView) view.findViewById(R.id.tvEBRExercisePosition);
			view.setTag(row);
		} else {
			view = convertView;
			((BodyGoalRow) view.getTag()).textView.setTag(list.get(position));
			((BodyGoalRow) view.getTag()).pos.setTag(list.get(position));
		}
		
		BodyGoalRow row = (BodyGoalRow) view.getTag();
		row.textView.setText(list.get(position).getCategory());
		
		//if the goal has been completed, set text color to green
		if (list.get(position).isCompleted()) {
			row.textView.setTextColor(0xFF33FF00);
		}
		
		row.pos.setText(Integer.toString(position));
		view.setOnClickListener(bodyGoalListener);
		
		return view;
	}
	
	/**
	 * 
	 */
	View.OnClickListener bodyGoalListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
			
			TextView tvGoalName = (TextView) v.findViewById(R.id.tvEBRExerciseLabel);
			String goalName = tvGoalName.getText().toString();
			
			TextView tvPos = (TextView) v.findViewById(R.id.tvEBRExercisePosition);
			int pos = Integer.parseInt(tvPos.getText().toString());
			
			if ("+ Add Goal".equals(goalName)) {
				CreateBodyGoalFragment newFragment = new CreateBodyGoalFragment();
				
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
				ViewBodyGoalFragment newFragment = new ViewBodyGoalFragment(list.get(pos));
				
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