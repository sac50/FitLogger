package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cwru.R;
import com.cwru.fragments.CalendarFragment;
import com.cwru.fragments.CalendarFragment.returnDateListener;

public class CalendarActivity extends FragmentActivity implements returnDateListener {
	private boolean returnDate;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		
		// Set Listener
		CalendarFragment.setExerciseBankListener(this);
		
		Bundle extras = getIntent().getExtras();
		if (extras.containsKey("RETURN-DATE")) {
			returnDate = true;
		} else {
			returnDate = false;
		}
		
		CalendarFragment calendar = new CalendarFragment(this, returnDate);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.flCalendarMainFrame, calendar);
		transaction.commit();
		
	}

	@Override
	public void returnSelectedDate(String dateSelected) {
		Intent intent = new Intent();
		intent.putExtra("DATE-SELECTED", dateSelected);
		setResult(1, intent);
		super.finish();
	}

}
