package com.cwru.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.cwru.R;

public class CreateWorkoutFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;			
		}

		return (LinearLayout) inflater.inflate(R.layout.create_workout_tab, container, false);
	}
	
	
}
