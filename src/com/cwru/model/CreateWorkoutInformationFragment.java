package com.cwru.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
	
		return view;
	}
}
