package com.cwru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cwru.R;
import com.cwru.fragments.TutorialBankFragment;

/**
 * Activity to view the tutorial. Launches the tutorial bank fragment
 * @author lkissling
 *
 */
public class TutorialActivity extends FragmentActivity {
	private TextView appTitleBar;
	
	@Override
	/**
	 * On Create Callback.  Sets layout for the tab and launches the tutorial bank fragment
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_information);
		
		appTitleBar = (TextView) findViewById(R.id.tvTutorialInformationAppTitleBar);
		appTitleBar.setOnClickListener(goHomeListener);

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
	
	/**
	 * Top title bar on click event handler.  Takes user back to home screen.  Resets back stack to clear history.
	 */
	View.OnClickListener goHomeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(TutorialActivity.this, HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
}