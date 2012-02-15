package com.cwru.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cwru.R;

public class ExerciseBankFragment extends ListFragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		View view = (LinearLayout) inflater.inflate(R.layout.exercise_bank, container, false);
		CheckBoxArrayAdapter adapter = new CheckBoxArrayAdapter(this.getActivity(), getExerciseBankList());
		this.setListAdapter(adapter);

		Button button = new Button(this.getActivity());
		button.setText("Order Exercises for Workout");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Create new transaction
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				// Replace the workout information fragment with the exercise bank
				transaction.replace(R.id.FLmainFrame, new ExerciseSequenceFragment());	
				transaction.addToBackStack(null);
				transaction.commit();
			}			
		});
		
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.llExerciseBank);
		ll.addView(button);
		return view;
	}
	
	private List<ExerciseBankRow> getExerciseBankList() {
		List<ExerciseBankRow> list = new ArrayList<ExerciseBankRow>();
		list.add(get("Bench Press"));
		list.add(get("Bicepts Curl"));
		list.add(get("Squats"));
		
		return list;
	}
	
	private ExerciseBankRow get(String s) {
		return new ExerciseBankRow(s);
	}
		
		
}
