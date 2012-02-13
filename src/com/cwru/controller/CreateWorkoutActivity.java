package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.cwru.R;
import com.cwru.model.CreateWorkoutInformationFragment;
import com.cwru.model.ExerciseBankFragment;
import com.cwru.model.ExerciseSequenceFragment;

public class CreateWorkoutActivity extends FragmentActivity {
	
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.create_workout_tab);
			
		//	FrameLayout left = (FrameLayout) this.findViewById(R.id.FLcreateWorkoutTabLeftFrame);
		//	FrameLayout middle = (FrameLayout) this.findViewById(R.id.FLcreateWorkoutTabMiddleFrame);
		//	FrameLayout right = (FrameLayout) this.findViewById(R.id.FLcreateWorkoutTabRightFrame);
			
			CreateWorkoutInformationFragment information = new CreateWorkoutInformationFragment();
			ExerciseBankFragment ebank = new ExerciseBankFragment();
			ExerciseSequenceFragment esequence = new ExerciseSequenceFragment();
			
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.FLcreateWorkoutTabLeftFrame, information);
			transaction.add(R.id.FLcreateWorkoutTabMiddleFrame, ebank);
			transaction.add(R.id.FLcreateWorkoutTabRightFrame, esequence);
			transaction.commit();
			
			/*
			 * 
			 * FrameLayout f = new FrameLayout(this);
    
        

        CreateWorkoutFragment leftFragment = new CreateWorkoutFragment();
        CreateExerciseFragment rightFragment = new CreateExerciseFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.leftFrame, leftFragment);
        FrameLayout fl = (FrameLayout) this.findViewById(R.id.leftFrame);
        // Set layout weight to 100
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 100);
        fl.setLayoutParams(lp);
        
        FrameLayout fr = (FrameLayout) this.findViewById(R.id.rightFrame);
        // Set Layout weight to 0
        LinearLayout.LayoutParams lpr = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 0);
        fr.setLayoutParams(lpr);

        //transaction.add(R.id.rightFrame, rightFragment);
        transaction.commit();
		
			 */
		}
}
