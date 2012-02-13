package com.cwru.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EditExerciseActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textView = new TextView(this);
		textView.setText("Edit Activity Tab");
		setContentView(textView);
	}
}
