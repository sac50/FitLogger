package com.cwru.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Workout;
/**
 * 
 * @author sacrilley
 *
 */
public class EditWorkoutInformationFragment extends Fragment {
	private EditText etWorkoutName;
	private Spinner spnWorkoutType;
	private Spinner spnRepeatWeeks;
	private DbAdapter mDbHelper;
	private String initialWorkoutName;
	private String initialWorkoutType;
	private String initialWorkoutRepeats;
	
	private static onGoToExerciseBankListener listener;
	private String storedWorkoutName;

	
	/**
	 * 
	 * @param workoutName
	 * @param activity
	 */
	public EditWorkoutInformationFragment(String workoutName, Activity activity) { 
		initialWorkoutName = workoutName;
		storedWorkoutName = workoutName;
		/* Get all parameters */
		mDbHelper = new DbAdapter(activity);
		Workout workout = mDbHelper.getWorkoutFromName(initialWorkoutName);
		initialWorkoutType = workout.getType();
		initialWorkoutRepeats = workout.getRepeatWeeks();
		
	}
	
	@Override
	/**
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}		
		View view = (ScrollView) inflater.inflate(R.layout.edit_create_workout_information, container, false);		
		
		/* Grab UI features */
		etWorkoutName = (EditText) view.findViewById(R.id.etWorkoutName);
		spnWorkoutType = (Spinner) view.findViewById(R.id.spnWorkoutType);
	    spnRepeatWeeks = (Spinner) view.findViewById(R.id.spnWorkoutRepeatsWeek);
	
		
		/* Set data for spinners type and repeat weeks */
		ArrayAdapter<CharSequence> adapterWorkoutTypes = ArrayAdapter.createFromResource(
	            this.getActivity(), R.array.workoutTypes, android.R.layout.simple_spinner_item);
	    adapterWorkoutTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spnWorkoutType.setAdapter(adapterWorkoutTypes);
	    
	    /* Set initial values */
		etWorkoutName.setText(initialWorkoutName);

		
		/* Set check boxes */
		// Add Buttons
		// Button to Create Workout
		Button button = new Button(this.getActivity());
		button.setText("Update Workout");
		button.setOnClickListener(updateWorkoutInformationListener);
		
		Button button1 = new Button(this.getActivity());
		button1.setText("Update Exercises and Exercise Ordering");
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/* Launch intent to allow exercises to be added to workout and the sequence to be set */
				/*
				Intent intent = new Intent(EditWorkoutInformationFragment.this.getActivity(), WorkoutExerciseListing.class);
				intent.putExtra("WorkoutName", initialWorkoutName);
				startActivity(intent);	
				*/
				Log.d("Steve", "XXXXXXXXxXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				Log.d("Steve", "WorkoutName: " + storedWorkoutName);
				listener.goToExerciseBank(storedWorkoutName);
				
			}

		});
		
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.llCreateWorkoutInformationContainer);
		ll.addView(button);
		ll.addView(button1);
		return view;
	}
	
	/**
	 * 
	 * @param workoutName
	 * @return
	 */
	private boolean validateWorkoutName(String workoutName) {
		return mDbHelper.workoutNameExist(workoutName);
	}
	
	/**
	 * Create Workout Button Click Listener
	 */
	View.OnClickListener updateWorkoutInformationListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// Get Workout Name
			String workoutName = etWorkoutName.getText().toString();
			Log.d("Steve", "Workout Name: " + workoutName);
			/* if name exists already
			 * alert error, must have unique name
			 */
			// Name different than current and not unique
			if (!workoutName.equals(initialWorkoutName) && validateWorkoutName(workoutName)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(EditWorkoutInformationFragment.this.getActivity());
				builder.setMessage("Error: " + workoutName + " already exists as a workout name.  Please select a unique name for the workout");
				builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
			else {
				// Get Workout Type
				String workoutType = (String) spnWorkoutType.getSelectedItem();
				// Get Repeat Weeks 
				String workoutRepeatWeeks = (String) spnRepeatWeeks.getSelectedItem();
								
				
							
				
				Log.d("STEVE", "-----------------------------------------------------");
				Workout workoutToUpdate = new Workout(workoutName, workoutType);
				Log.d("Button", "Create Workout Clicked");
				Log.d("Workout Name", workoutName);
				Log.d("Workout Type", workoutType);
				Log.d("Workout Repeat Weeks", workoutRepeatWeeks);
				
				/** TODO
				 * Add workout repeat information to insert command
				 */
				/* Update Workout in the Database */
				mDbHelper.updateWorkoutInformation(workoutToUpdate, initialWorkoutName);
				storedWorkoutName = workoutName;
				/* 
				 * Refresh Workout Listing Fragment
				 */
			
				// if tablet to update the workout listing with any update in workout name
				/*
				if (HomeScreen.isTablet) {
					WorkoutListingFragment workoutListing = new WorkoutListingFragment(WorkoutListingFragment.EDIT_WORKOUT_LIST);
					workoutListing.setRetainInstance(true);
					EditWorkoutInformationFragment editWorkoutInformation = new EditWorkoutInformationFragment(workoutToUpdate.getName(), EditWorkoutInformationFragment.this.getActivity());
					editWorkoutInformation.setRetainInstance(true);
					FragmentTransaction transaction = EditWorkoutInformationFragment.this.getFragmentManager().beginTransaction();
					transaction.replace(R.id.flEditWorkoutInformationLeftFrame, workoutListing);
					transaction.replace(R.id.flEditWorkoutInformationRightFrame, editWorkoutInformation);
					transaction.commit();		
				}
				*/
				
				/*
				 * Launch toast to alert user of workout updated
				 */
				Toast toast = Toast.makeText(EditWorkoutInformationFragment.this.getActivity(), "Workout Updated", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				
				
				/* Launch intent to allow exercises to be added to workout and the sequence to be set */
		
			}
		}
	};
	
	/**
	 * 
	 * @author sacrilley
	 *
	 */
	public interface onGoToExerciseBankListener {
		void goToExerciseBank(String workoutName);
	}
	
	/**
	 * 
	 * @param listener
	 */
	public static void setExerciseBankListener(onGoToExerciseBankListener listener) {
		EditWorkoutInformationFragment.listener = listener;
	}
	
	
}
