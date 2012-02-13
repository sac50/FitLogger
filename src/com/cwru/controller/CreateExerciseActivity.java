package com.cwru.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CreateExerciseActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textView = new TextView(this);
		textView.setText("Create Exercise Tab");
		setContentView(textView);
	}
}
