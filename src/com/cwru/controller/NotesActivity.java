package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cwru.R;
import com.cwru.fragments.NotesFragment;

public class NotesActivity extends FragmentActivity {
	private int exerciseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes_activity_layout);
	
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.containsKey("EXERCISE-ID")) {
				exerciseId = extras.getInt("EXERCISE-ID");
			}
		}
		
		NotesFragment notes = new NotesFragment(this, exerciseId);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flNotesMainFrame, notes);
		transaction.commit();		
	}
}
