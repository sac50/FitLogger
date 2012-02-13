package com.cwru.controller;

import com.cwru.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateExerciseActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textView = new TextView(this);
		textView.setText("Create Exercise Tab");
		setContentView(R.layout.create_exercise_tab);
		
		Spinner typeSpinner = (Spinner) findViewById(R.id.spnCreateExerciseType);
		ArrayAdapter<CharSequence> exerTypeAdapter = ArrayAdapter.createFromResource(this,
				R.array.exersiseTypes, android.R.layout.simple_spinner_item);
		exerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(exerTypeAdapter);
	}
}
