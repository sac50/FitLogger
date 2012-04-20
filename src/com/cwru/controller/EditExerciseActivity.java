package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.EditExerciseBankFragment;
import com.cwru.fragments.EditExerciseFragment;

/**
 * Activity to perform editing of exercises.  Launches the edit exercise bank fragment
 * @author lkissling
 *
 */
public class EditExerciseActivity extends FragmentActivity {

	@Override
	/**
	 * On Create Callback.  Sets layout for the tab and launches the edit exercise bank fragment
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_exercise_tab);
		
		if (HomeScreen.isTablet) {
			EditExerciseBankFragment exBank = new EditExerciseBankFragment();
			EditExerciseFragment exExer = new EditExerciseFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flEditExerciseLeftFrame, exBank);
			transaction.add(R.id.flEditExerciseRightFrame, exExer);
			transaction.commit();
		} else {
			EditExerciseBankFragment exBank = new EditExerciseBankFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flEditExerciseMainFrame, exBank);
			transaction.commit();
		}
	}
	
	public void onResume() {
		super.onResume();
		setContentView(R.layout.edit_exercise_tab);
		
		if (HomeScreen.isTablet) {
			EditExerciseBankFragment exBank = new EditExerciseBankFragment();
			EditExerciseFragment exExer = new EditExerciseFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flEditExerciseLeftFrame, exBank);
			transaction.add(R.id.flEditExerciseRightFrame, exExer);
			transaction.commitAllowingStateLoss();
		} else {
			EditExerciseBankFragment exBank = new EditExerciseBankFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flEditExerciseMainFrame, exBank);
			transaction.commitAllowingStateLoss();
		}
	}

}
