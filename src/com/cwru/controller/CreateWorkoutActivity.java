package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.cwru.R;
import com.cwru.model.CreateWorkoutInformationFragment;
import com.cwru.model.ExerciseBankFragment;
import com.cwru.model.ExerciseSequenceFragment;

public class CreateWorkoutActivity extends FragmentActivity {
	
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.create_workout_tab);			
			
			if (HomeScreen.isTablet) {
				CreateWorkoutInformationFragment information = new CreateWorkoutInformationFragment();
				ExerciseBankFragment ebank = new ExerciseBankFragment();
				ExerciseSequenceFragment esequence = new ExerciseSequenceFragment();
				
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.FLcreateWorkoutTabLeftFrame, information);
				transaction.add(R.id.FLcreateWorkoutTabMiddleFrame, ebank);
				transaction.add(R.id.FLcreateWorkoutTabRightFrame, esequence);
				transaction.commit();
			}
			else {				
				CreateWorkoutInformationFragment information = new CreateWorkoutInformationFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.FLmainFrame, information);
				transaction.commit();
			}
			
		}
			
}
