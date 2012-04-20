package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.CustomGoalBankFragment;

/**
 * Activity to show user custom goals.  Falls under the custom goal tab under the goals module
 * @author sacrilley
 *
 */
public class CustomGoalActivity extends FragmentActivity {
	private TextView appTitleBar;
	
	/**
	 * on create callback.  Sets layout and launches the CustomGoalBankFragment to the main frame
	 */
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