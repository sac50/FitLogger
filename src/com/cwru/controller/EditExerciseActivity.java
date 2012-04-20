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

public class EditExerciseActivity extends FragmentActivity {

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
			Log.d("STEVE", "TABLET NOT PHONE");
		} else {
			EditExerciseBankFragment exBank = new EditExerciseBankFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flEditExerciseMainFrame, exBank);
			transaction.commit();
			Log.d("Fragment Commited", "FrAGMENT");
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
			Log.d("STEVE", "TABLET NOT PHONE");
		} else {
			EditExerciseBankFragment exBank = new EditExerciseBankFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flEditExerciseMainFrame, exBank);
			transaction.commitAllowingStateLoss();
			Log.d("Fragment Commited", "FrAGMENT");
		}
	}

}
