package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cwru.R;
import com.cwru.model.CreateWorkoutInformationFragment;

public class CreateWorkoutActivity extends FragmentActivity {
	
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.create_workout_tab);			
			
			System.out.println("STEVE STEVE ");
			Log.d("HELLO", "STEVE");
			
			if (HomeScreen.isTablet) {
				CreateWorkoutInformationFragment information = new CreateWorkoutInformationFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.flCreateWorkoutMainFrame, information);
				transaction.commit();
				/*
				CreateWorkoutInformationFragment information = new CreateWorkoutInformationFragment();
				ExerciseBankFragment ebank = new ExerciseBankFragment();
				ExerciseSequenceFragment esequence = new ExerciseSequenceFragment();
				
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.FLcreateWorkoutTabLeftFrame, information);
				transaction.add(R.id.FLcreateWorkoutTabMiddleFrame, ebank);
				transaction.add(R.id.FLcreateWorkoutTabRightFrame, esequence);
				transaction.commit();
				*/
				Log.d("STEVE", "TABLET NOT PHONE");
			}
			else {				
				System.out.println("HELLO STEVE");
				CreateWorkoutInformationFragment information = new CreateWorkoutInformationFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.flCreateWorkoutMainFrame, information);
				transaction.commit();
				Log.d("Fragment Commited", "FrAGMENT");
				
			}
			
			
		}
			
}
