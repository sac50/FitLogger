package com.cwru.controller;

import java.util.ArrayList;
import java.util.List;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;
import com.cwru.model.Set;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
	List<Set> setList;
	Set setVar;
	Long exId;
	
	
	
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
					
					EditText weightText = (EditText) inflatedSets.get(0).findViewById(R.id.etCreateExerciseWeight);
					weightText.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							
						}

						@Override
						public void beforeTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence s, int arg1,
								int arg2, int arg3) {
							LinearLayout inflatedSet;
							String replace = s.toString();
							for (int i = 1; i < inflatedSets.size(); i++) {
								inflatedSet = inflatedSets.get(i);
								String old = ((EditText) inflatedSet.findViewById(R.id.etCreateExerciseWeight)).getText().toString();
								if (old == null || old.length() < 1 
										|| replace.length() > 0 && old.equals(replace.substring(0, replace.length() - 1))
										|| old.substring(0, old.length() - 1).equals(replace)) {
									EditText replaceWeight = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseWeight);
									replaceWeight.setText(replace);
								}
							}
						}
						
					});
					
					EditText repsText = (EditText) inflatedSets.get(0).findViewById(R.id.etCreateExerciseReps);
					repsText.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							
						}

						@Override
						public void beforeTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onTextChanged(CharSequence s, int arg1,
								int arg2, int arg3) {
							LinearLayout inflatedSet;
							String replace = s.toString();
							for (int i = 1; i < inflatedSets.size(); i++) {
								inflatedSet = inflatedSets.get(i);
								String old = ((EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps)).getText().toString();
								if (old == null || old.length() < 1 
										|| replace.length() > 0 && old.equals(replace.substring(0, replace.length() - 1))
										|| old.substring(0, old.length() - 1).equals(replace)) {
									EditText replaceReps = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps);
									replaceReps.setText(replace);
								}
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
				ex.setSets(inflatedSets.size());
				setVar = new Set();
				
				mDbHelper.open();
				exId = mDbHelper.createExercise(ex);
				for (LinearLayout inflatedSet : inflatedSets) {
					EditText repsText = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps);
					EditText weightText = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseWeight);
					
					setVar.setReps(Integer.parseInt(repsText.getText().toString()));
					setVar.setWeight(Double.parseDouble(weightText.getText().toString()));
					mDbHelper.createSet(setVar);
				}
				mDbHelper.close();
			}
		});
		
		
	}
}
