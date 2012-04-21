package com.cwru.fragments;

import com.cwru.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Fragment class displays tutorial text
 * 
 * @author lkissling
 *
 */
public class ViewTutorial extends Fragment {
	String str;
	View view;
	
	public ViewTutorial(String str) {
		this.str = str;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		view = inflater.inflate(R.layout.view_tutorial, container, false);
		
		TextView tv = (TextView) view.findViewById(R.id.tvTutorial);
		tv.setText(str);
		
		if (str.startsWith("Create/Edit")) {
			TextView tv2 = (TextView) view.findViewById(R.id.tvTutorial2);
			tv2.setVisibility(0);
			tv2.setText(getResources().getString(R.string.createWorkout));
			
			RadioGroup rg = (RadioGroup) view.findViewById(R.id.rgTutorial);
			rg.setVisibility(0);
			rg.setOnCheckedChangeListener(listener);
		}
		
		return view;
	}
	
	RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			TextView tv2 = (TextView) view.findViewById(R.id.tvTutorial2);
			switch (checkedId) {
			case R.id.rbTutCW:
				tv2.setText(getResources().getString(R.string.createWorkout));
				break;
			case R.id.rbTutEW:
				tv2.setText(getResources().getString(R.string.editWorkout));
				break;
			case R.id.rbTutCE:
				tv2.setText(getResources().getString(R.string.createExercise));
				break;
			case R.id.rbTutEE:
				tv2.setText(getResources().getString(R.string.editExercise));
				break;
			}
		}
	};
}