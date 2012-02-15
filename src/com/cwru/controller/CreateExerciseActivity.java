package com.cwru.controller;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.model.Exercise;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateExerciseActivity extends Activity {
	DbAdapter mDbHelper;
	EditText mNameText;
	String exType;
	
	
	
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
		/*
		Spinner subTypeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseSubType);
		ArrayAdapter<CharSequence> exerSubTypeAdapter = ArrayAdapter.createFromResource(this,
				R.array.exerciseSubTypes, android.R.layout.simple_spinner_item);
		exerSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		subTypeSpinner.setAdapter(exerSubTypeAdapter);
		subTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int pos, long id) {
				
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
		
		*/
	}
}
