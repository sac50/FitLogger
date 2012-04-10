package com.cwru.controller;

import com.cwru.R;
import com.cwru.fragments.CustomGoalBankFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class CustomGoalActivity extends FragmentActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.goal_information);
		
		CustomGoalBankFragment goalBank = new CustomGoalBankFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		// Tablet
		if (HomeScreen.isTablet) {
			transaction.add(R.id.flGoalLeftFrame, goalBank);
		
		// Phone
		} else {
			transaction.add(R.id.flGoalMainFrame, goalBank);
		}
		
		transaction.commit();
	}
}