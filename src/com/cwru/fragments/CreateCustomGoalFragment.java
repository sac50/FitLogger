package com.cwru.fragments;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.CustomGoal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

public class CreateCustomGoalFragment extends Fragment{
	private DbAdapter mDbHelper;
	private View view;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		mDbHelper = new DbAdapter(this.getActivity());
		
		view = (ScrollView) inflater.inflate(R.layout.create_custom_goal, container, false);
		
		Button done = (Button) view.findViewById(R.id.btnCustomGoalDone);
		done.setOnClickListener(doneListener);
		
		return view;
	}
	
	View.OnClickListener doneListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			EditText nameText = (EditText) view.findViewById(R.id.etCustomGoalSummary);
			String name = nameText.getText().toString();
			
			EditText descriptionText = (EditText) view.findViewById(R.id.etCustomGoalDescription);
			String description = descriptionText.getText().toString();
			
			Context context = v.getContext();
			CharSequence toastText;
			int duration = Toast.LENGTH_SHORT;
			
			if (name != null && "+ Add Goal".equals(name)) {
				toastText = "Invalid summary.";
				Toast toast = Toast.makeText(context, toastText, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else if (name != null && name.length() > 0 && description != null && description.length() > 0) {
				CustomGoal goal = new CustomGoal(name, description, false);
				mDbHelper.createCustomGoal(goal);
				
				if (HomeScreen.isTablet) {
					FragmentTransaction transaction = CreateCustomGoalFragment.this.getFragmentManager().beginTransaction();
					CustomGoalBankFragment newFrag = new CustomGoalBankFragment();
					
					transaction.replace(R.id.flGoalLeftFrame, newFrag);
					transaction.remove(CreateCustomGoalFragment.this);
					transaction.commit();
				} else {
					FragmentTransaction transaction = CreateCustomGoalFragment.this.getFragmentManager().beginTransaction();
					CustomGoalBankFragment newFrag = new CustomGoalBankFragment();
					
					transaction.replace(R.id.flGoalMainFrame, newFrag);
					transaction.commit();
				}
			} else {
				toastText = "Please specify a summary and description.";
				Toast toast = Toast.makeText(context, toastText, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}	
		}
	};
}