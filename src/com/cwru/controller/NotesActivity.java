package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.NotesFragment;

public class NotesActivity extends FragmentActivity {
	private int exerciseId;
	private TextView appTitleBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes_activity_layout);
		
		appTitleBar = (TextView) this.findViewById(R.id.tvNotesAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);
	
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
	
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(NotesActivity.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
}
