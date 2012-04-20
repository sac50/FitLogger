package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.ExerciseGoalBankFragment;

/**
 * Activity to display Exercise goals.  Falls under the Exercise Goals tab under the Goals Module
 * @author lkissling
 *
 */
public class ExerciseGoalActivity extends FragmentActivity {
	private TextView appTitleBar;
	
	/**
	 * onCreate Callback.  Sets layout and launches the ExerciseGoalBank fragment to the main frame.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.goal_information);
		
		ExerciseGoalBankFragment goalBank = new ExerciseGoalBankFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		// Tablet
		if (HomeScreen.isTablet) {
			transaction.add(R.id.flGoalLeftFrame, goalBank);
		
		// Phone
		} else {
			transaction.add(R.id.flGoalMainFrame, goalBank);
		}
		
		transaction.commit();
	}

}