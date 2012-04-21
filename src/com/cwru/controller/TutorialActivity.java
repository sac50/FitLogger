package com.cwru.controller;

import com.cwru.R;
import com.cwru.fragments.TutorialBankFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Activity to view the tutorial. Launches the tutorial bank fragment
 * @author lkissling
 *
 */
public class TutorialActivity extends FragmentActivity {
	
	@Override
	/**
	 * On Create Callback.  Sets layout for the tab and launches the tutorial bank fragment
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_information);
		
		if (HomeScreen.isTablet) {
			TutorialBankFragment bank = new TutorialBankFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flTutorialLeftFrame, bank);
//			transaction.add(R.id.flTutorialRightFrame, exExer);
			transaction.commit();
		} else {
			TutorialBankFragment bank = new TutorialBankFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.flTutorialMainFrame, bank);
			transaction.commit();
		}
	}
}