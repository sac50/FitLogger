package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.EditWorkoutInformationFragment;
import com.cwru.fragments.EditWorkoutInformationFragment.onGoToExerciseBankListener;
import com.cwru.fragments.ExerciseBankFragment;
import com.cwru.fragments.ExerciseBankFragment.onGoToExerciseSequenceListener;
import com.cwru.fragments.ExerciseSequenceFragment;
import com.cwru.fragments.WorkoutListingFragment;
import com.cwru.fragments.WorkoutListingFragment.onWorkoutListingClickListener;

public class EditWorkoutActivity extends FragmentActivity implements onGoToExerciseSequenceListener, onGoToExerciseBankListener, onWorkoutListingClickListener {
		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set Listener
		EditWorkoutInformationFragment.setExerciseBankListener(this);
		ExerciseBankFragment.setExerciseSequenceListener(this);
		WorkoutListingFragment.setOnWorkoutListingClickListener(this);
		
		setContentView(R.layout.edit_workout_information);
		
		// Tablet
		if (HomeScreen.isTablet) {
			WorkoutListingFragment workoutListings = new WorkoutListingFragment();
			workoutListings.setRetainInstance(true);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flEditWorkoutInformationLeftFrame, workoutListings);
			transaction.commit();
		} 
		// Phone
		else {
			WorkoutListingFragment workoutListings = new WorkoutListingFragment();
			workoutListings.setRetainInstance(true);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.flEditWorkoutInformationMainFrame, workoutListings, "workoutListings");
			transaction.commit();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d("STEVE", "Edit Workout Activity onPause() Called");
		
	}
	
	@Override
	public void goToExerciseSequence(String workoutName) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		ExerciseSequenceFragment esequence = new ExerciseSequenceFragment(workoutName);
		transaction.replace(R.id.flEditWorkoutInformationMainFrame, esequence);	
		transaction.addToBackStack(null);
		transaction.commit();			
	}

	@Override
	public void goToExerciseBank(String workoutName) {
		Log.d("Steve", "000000000000000000000000000000000000000000000000000000000000000000000000");
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
			transaction.replace(R.id.flEditWorkoutInformationMainFrame, ebank, "exerciseBank");
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}

	@Override
	public void onWorkoutListingListenerClick(String workoutName) {
		// TODO Auto-generated method stub
		EditWorkoutInformationFragment editWorkoutInformation = new EditWorkoutInformationFragment(workoutName, this);
		editWorkoutInformation.setRetainInstance(true);
		// if tablet
		if (HomeScreen.isTablet) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.flEditWorkoutInformationRightFrame, editWorkoutInformation);
			transaction.commit();		
		} else {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.addToBackStack(null);
			transaction.replace(R.id.flEditWorkoutInformationMainFrame, editWorkoutInformation);
			transaction.commit();
		}
		
	}

}

