package com.cwru.controller;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cwru.R;

public class HomeScreen extends ListActivity {
	
	public static boolean isTablet;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        setTabletStatus();
        
        String[] modules = getResources().getStringArray(R.array.modules);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modules));
    
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Log.d("STEVE", "Position: " + position);
				// Workout Workflow Module
				if (position == 0) {
					Intent intent = new Intent(HomeScreen.this, WorkoutWorkflow.class);
					startActivity(intent);
				}
				// Create/Edit Exercise and Workout Module
				else if (position == 1) {
					Intent intent = new Intent(HomeScreen.this, CreateEditTabActivity.class);
					startActivity(intent);
				} 
				else if (position == 2) {
					Intent intent = new Intent(HomeScreen.this, GoalsActivity.class);
					startActivity(intent);
				} 
				else if (position == 3) {
					Intent intent = new Intent(HomeScreen.this, HistoryActivity.class);
					startActivity(intent);
				}
				else if (position == 4) {
					Intent intent = new Intent(HomeScreen.this, AnalyticsActivity.class);
					startActivity(intent);
				}
				else if (position == 5) { 
					Intent intent = new Intent(HomeScreen.this, CalendarActivity.class);
					startActivity(intent);
				}
			}        	
        });
    }
    
    private void setTabletStatus() {
    	Display display = getWindowManager().getDefaultDisplay();
    	int width = display.getWidth();
    	int height = display.getHeight();
    	
    	int minTabWidth = 600;
    	int minTabHeight = 600;
    	if (width >= minTabWidth && height >= minTabHeight) {
    		isTablet = true;
    		Log.d("Tablet Status", "TABLET");
    	}
    	else {
    		isTablet = false;
    		Log.d("Tablet Status", "PHONE");
    	}
    }
}
