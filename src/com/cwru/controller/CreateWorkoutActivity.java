package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.CreateWorkoutInformationFragment;
import com.cwru.fragments.CreateWorkoutInformationFragment.onGoToExerciseBankListener;
import com.cwru.fragments.ExerciseBankFragment;
import com.cwru.fragments.ExerciseBankFragment.onGoToExerciseSequenceListener;
import com.cwru.fragments.ExerciseSequenceFragment;

/**
 * Activity to create workouts.  
 * Falls under the Create Workout tab under the Create Workouts and Exercises module.
 * Module launches fragments in following order
 * 		CreateWorkoutInformation->ExerciseBank->ExerciseSequence
 * @author sacrilley
 *
 */
public class CreateWorkoutActivity extends FragmentActivity implements onGoToExerciseSequenceListener, onGoToExerciseBankListener{
			
		@Override
		/** 
		 * onCreate Callback.  Sets layout and launches the Create Workout information fragment
		 */
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.create_workout_tab);	
			// Set Listener
			CreateWorkoutInformationFragment.setExerciseBankListener(this);
			ExerciseBankFragment.setExerciseSequenceListener(this);
			
		
			if (HomeScreen.isTablet) {
				CreateWorkoutInformationFragment information = new CreateWorkoutInformationFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.flCreateWorkoutMainFrame, information);
				transaction.commit();
				Log.d("STEVE", "TABLET NOT PHONE");
			}
			else {				
				System.out.println("HELLO STEVE");
				CreateWorkoutInformationFragment information = new CreateWorkoutInformationFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.flCreateWorkoutMainFrame, information);
				transaction.commit();
			}			
			
		}

		@Override
		/**
		 * Performs the transaction to replace the main frame with the exercise sequence fragment
		 */
		public void goToExerciseSequence(String workoutName) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			ExerciseSequenceFragment esequence = new ExerciseSequenceFragment(workoutName);
			transaction.replace(R.id.flCreateWorkoutMainFrame, esequence);	
			transaction.addToBackStack(null);
			transaction.commit();			
		}

		@Override
		/**
		 * Performs the transaction to replace main frame with the exercise bank fragment
		 */
		public void goToExerciseBank(String workoutName) {
			/* Tablet - Show split pane of exercise bank and exercise sequence */
			if (HomeScreen.isTablet) {
				ExerciseBankFragment ebank = new ExerciseBankFragment(workoutName);
				ExerciseSequenceFragment esequence = new ExerciseSequenceFragment(workoutName);
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.flWorkoutExerciseListingLeftFrame, ebank, "exerciseBank");
				transaction.add(R.id.flWorkoutExerciseListingRightFrame, esequence, "exerciseSequence");
				transaction.commit();
			}
			/* Phone - Show exercise bank and exercise sequence independently and linked to each other */
			else {
				ExerciseBankFragment ebank = new ExerciseBankFragment(workoutName);
				//ExerciseSequenceFragment esequence = new ExerciseSequenceFragment(workoutName);
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.flCreateWorkoutMainFrame, ebank, "exerciseBank");
				transaction.addToBackStack(null);
				transaction.commit();
			}
		}
		
			
}
