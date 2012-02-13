package com.cwru.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cwru.R;
import com.cwru.model.CheckBoxArrayAdapter.CheckBoxRow;

public class ExerciseBankFragment extends ListFragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		CheckBoxArrayAdapter adapter = new CheckBoxArrayAdapter(this.getActivity(), getExerciseBankList());
		this.setListAdapter(adapter);
		return (LinearLayout) inflater.inflate(R.layout.exercise_bank, container, false);
	}
	
	private List<ExerciseBankRow> getExerciseBankList() {
		List<ExerciseBankRow> list = new ArrayList<ExerciseBankRow>();
		list.add(get("Bench Press"));
		list.add(get("Bicepts Curl"));
		list.add(get("Squats"));
		
		return list;
	}
	
	private ExerciseBankRow get(String s) {
		return new ExerciseBankRow(s);
	}
		
		
}
