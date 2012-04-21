package com.cwru.fragments;

import com.cwru.R;
import com.cwru.controller.HomeScreen;
import com.cwru.dao.DbAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TutorialBankFragment extends ListFragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		View view = (LinearLayout) inflater.inflate(R.layout.tutorial_bank, container, false);
		
//		String[] tutorials = getResources().getStringArray(R.array.tutorial_modules);
//		setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, tutorials));
//		
//		ListView lv = getListView();
////				(ListView) view.findViewById(android.R.id.list);
////		lv.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, tutorials));
//		lv.setTextFilterEnabled(true);
//		
//		lv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//					long arg3) {
//				String str = "";
//				if (position == 0) {
//					str = getResources().getString(R.string.exercise);
//				}
//				
//				if (HomeScreen.isTablet) {
//					
//				} else {
//					ViewTutorial vt = new ViewTutorial(str);
//					FragmentTransaction transaction = TutorialBankFragment.this.getFragmentManager().beginTransaction();
//					transaction.replace(R.id.flEditExerciseMainFrame, vt);
//					transaction.commit();
//				}
//			}
//			
//		});
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] tutorials = getResources().getStringArray(R.array.tutorial_modules);
		
		ListView lv = getListView();
		setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, tutorials));
		
		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String str = "";
				switch (position) {
				case 0:
					str = getResources().getString(R.string.exercise);
					break;
				case 1:
					str = getResources().getString(R.string.workout);
					break;
				case 2:
					str = getResources().getString(R.string.startWorkout);
					break;
				case 3:
					str = getResources().getString(R.string.createEdit);
					break;
				case 4:
					str = getResources().getString(R.string.goals);
					break;
				case 5:
					str = getResources().getString(R.string.history);
					break;
				case 6:
					str = getResources().getString(R.string.analytics);
					break;
				case 7:
					str = getResources().getString(R.string.calendar);
					break;
				}
				

				
				if (HomeScreen.isTablet) {
					ViewTutorial vt = new ViewTutorial(str);
					FragmentTransaction transaction = TutorialBankFragment.this.getFragmentManager().beginTransaction();
					transaction.replace(R.id.flTutorialRightFrame, vt);
					transaction.commit();
				} else {
					ViewTutorial vt = new ViewTutorial(str);
					FragmentTransaction transaction = TutorialBankFragment.this.getFragmentManager().beginTransaction();
					transaction.addToBackStack(null);
					transaction.replace(R.id.flTutorialMainFrame, vt);
					transaction.commit();
				}
			}
			
		});
	}
}