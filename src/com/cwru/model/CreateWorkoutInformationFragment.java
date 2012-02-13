package com.cwru.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.cwru.R;

/**
 * 
 * @author sacrilley
 * This fragment handles entering information about the workout user creating
 *
 */
public class CreateWorkoutInformationFragment extends Fragment {
	
	/**
	 * UI View associated with the fragment
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;			
		}
		
		View view =  (LinearLayout) inflater.inflate(R.layout.create_workout_information, container, false);
		Spinner spinner = (Spinner) view.findViewById(R.id.spnWorkoutType);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity().getBaseContext(), R.array.workoutTypes, android.R.layout.simple_spinner_item);
		spinner.setAdapter(adapter);
		
		Button button = new Button(this.getActivity());
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Create new transaction
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				// Replace the workout information fragment with the exercise bank
				transaction.replace(R.id.FLmainFrame, new ExerciseBankFragment());	
				transaction.commit();
			}			
		});
		
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.LLCreateWorkoutInfo);
		ll.addView(button);
	
		return view;
	}
}
