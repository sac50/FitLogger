package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.ExerciseAnalyticsBankFragment;

/**
 * Activity to dispaly analytics regarding exercises.  Falls under the exercise tab under the
 * analytics module
 * @author lkissling
 *
 */
public class ExerciseAnalyticsActivity extends FragmentActivity {
	
	/**
	 * onCreate Callback.  Sets the layout and launches the ExerrciseAnalytics Bank fragment to the main frame
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.analytics_information);
		
		ExerciseAnalyticsBankFragment bank = new ExerciseAnalyticsBankFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		// Tablet
		if (HomeScreen.isTablet) {
			transaction.add(R.id.flAnalyticsLeftFrame, bank);
		
		// Phone
		} else {
			transaction.add(R.id.flAnalyticsMainFrame, bank);
		}
		
		transaction.commit();
	}
	
	
}