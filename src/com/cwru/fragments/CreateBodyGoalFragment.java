package com.cwru.fragments;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.BodyGoal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateBodyGoalFragment extends Fragment {
	private DbAdapter mDbHelper;
	private View view;
	private Spinner spinner;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		view = (ScrollView) inflater.inflate(R.layout.create_body_goal, container, false);
		
		spinner = (Spinner) view.findViewById(R.id.spnBodyGoal);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this.getActivity(), R.array.bodyGoalUnits, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		Button done = (Button) view.findViewById(R.id.btnBodyGoalDone);
		done.setOnClickListener(listener);
		
		return view;
	}
	
	View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			EditText categoryText = (EditText) view.findViewById(R.id.etBodyGoalCategory);
			String category = categoryText.getText().toString();
			
			EditText startedText = (EditText) view.findViewById(R.id.etBodyGoalStarted);
			String startedString = startedText.getText().toString();
			
			EditText goalText = (EditText) view.findViewById(R.id.etBodyGoalGoal);
			String goalString = goalText.getText().toString();
			
			String unit = spinner.getSelectedItem().toString();
			
			Context context = v.getContext();
			CharSequence toastText;
			int duration = Toast.LENGTH_SHORT;
			
			if (category != null && "+ Add Goal".equals(category)) {
				toastText = "Invalid summary.";
				Toast toast = Toast.makeText(context, toastText, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else if (category == null || category.length() <= 0) {
				toastText = "Please specify a category.";
				Toast toast = Toast.makeText(context, toastText, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else if (startedString == null || startedString.length() <= 0 
					|| goalString == null || goalString.length() <= 0) {
				toastText = "Please specify starting and goal values.";
				Toast toast = Toast.makeText(context, toastText, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				double started = Double.parseDouble(startedString);
				double goal = Double.parseDouble(goalString);
				mDbHelper.createBodyGoal(new BodyGoal(category, unit, started, started, goal, false));
				
				if (HomeScreen.isTablet) {
					FragmentTransaction transaction = CreateBodyGoalFragment.this.getFragmentManager().beginTransaction();
					BodyGoalBankFragment newFrag = new BodyGoalBankFragment();
					
					transaction.replace(R.id.flGoalLeftFrame, newFrag);
					transaction.remove(CreateBodyGoalFragment.this);
					transaction.commit();
				} else {
					FragmentTransaction transaction = CreateBodyGoalFragment.this.getFragmentManager().beginTransaction();
					BodyGoalBankFragment newFrag = new BodyGoalBankFragment();
					
					transaction.replace(R.id.flGoalMainFrame, newFrag);
					transaction.commit();
				}
			}
		}
	};
}