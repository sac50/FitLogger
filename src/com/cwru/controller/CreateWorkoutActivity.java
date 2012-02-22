package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cwru.R;
import com.cwru.model.CreateWorkoutInformationFragment;
import com.cwru.model.ExerciseBankFragment;

public class CreateWorkoutActivity extends FragmentActivity {
	
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.create_workout_tab);			
			
			System.out.println("STEVE STEVE ");
			Log.d("HELLO", "STEVE");
			
			if (HomeScreen.isTablet) {
				/** TODO 
				 * Uncomment this to start with create workout information fragment
				 */
				/*
				CreateWorkoutInformationFragment information = new CreateWorkoutInformationFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.flCreateWorkoutMainFrame, information);
				transaction.commit();
				*/
				Intent intent = new Intent(this, WorkoutExerciseListing.class);
				startActivity(intent);
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
