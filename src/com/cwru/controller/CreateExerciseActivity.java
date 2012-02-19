package com.cwru.controller;

import java.util.ArrayList;
import java.util.List;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateExerciseActivity extends FragmentActivity {
	DbAdapter mDbHelper;
	EditText mNameText;
	String exType;
	List<LinearLayout> inflatedSets = new ArrayList<LinearLayout>();
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new DbAdapter(this);
		
		TextView textView = new TextView(this);
		textView.setText("Create Exercise Tab");
		setContentView(R.layout.create_exercise_tab);
		
		mNameText = (EditText) findViewById(R.id.etCreateExerciseName);
		
		Spinner typeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseType);
		ArrayAdapter<CharSequence> exerTypeAdapter = ArrayAdapter.createFromResource(this,
				R.array.exersiseTypes, android.R.layout.simple_spinner_item);
		exerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(exerTypeAdapter);
		typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent,
			        View view, int pos, long id) {
				exType = parent.getItemAtPosition(pos).toString();
			}
			
			@Override
			public void onNothingSelected(AdapterView parent) {
			      // Do nothing.
			    }
		});
		
		Spinner subTypeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseSubType);
		ArrayAdapter<CharSequence> exerSubTypeAdapter = ArrayAdapter.createFromResource(this,
				R.array.exerciseSubTypes, android.R.layout.simple_spinner_item);
		exerSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		subTypeSpinner.setAdapter(exerSubTypeAdapter);
		subTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int pos, long id) {
				final LinearLayout setLayout = (LinearLayout) findViewById(R.id.llCreateExerciseSets);
//				inflatedSets = new ArrayList<LinearLayout>();
				
				if ("Set-based".equals(parent.getItemAtPosition(pos).toString())) {
					setLayout.setVisibility(0);
					
					for (int i = 0; i < 3; i++) {
						LinearLayout inflatedSet;
						inflatedSet = (LinearLayout) View.inflate(parent.getContext(), R.layout.create_exercise_set_builder, null);
						inflatedSets.add(inflatedSet);
						setLayout.addView(inflatedSet);
					}
					
					Button addSet = (Button) findViewById(R.id.btnCreateExerciseAddSet);
					addSet.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (inflatedSets.size() < 10) {
								LinearLayout inflatedSet;
								inflatedSet = (LinearLayout) View.inflate(v.getContext(), R.layout.create_exercise_set_builder, null);
								inflatedSets.add(inflatedSet);
								setLayout.addView(inflatedSet);
							}
						}
					});
					
					Button removeSet = (Button) findViewById(R.id.btnCreateExerciseRemoveSet);
					removeSet.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (inflatedSets.size() > 1) {
								int position = inflatedSets.size() - 1;
								setLayout.removeView(inflatedSets.get(position));
								inflatedSets.remove(position);
							}
						}
					});
				} else {
					while (!inflatedSets.isEmpty()) {
						int position = inflatedSets.size() - 1;
						setLayout.removeView(inflatedSets.get(position));
						inflatedSets.remove(position);
					}
					setLayout.setVisibility(8);
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView parent) {
				
			}
		});
		
		Button doneButton = (Button) findViewById(R.id.btnCreateExerciseDone);
		doneButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Exercise ex = new Exercise();
				ex.setName(mNameText.getText().toString());
				ex.setType(exType);
				mDbHelper.open();
				mDbHelper.createExercise(ex);
				mDbHelper.close();
			}
		});
		
		
	}
}
