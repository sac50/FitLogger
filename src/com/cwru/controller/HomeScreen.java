package com.cwru.controller;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cwru.R;

public class HomeScreen extends ListActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        
        String[] modules = getResources().getStringArray(R.array.modules);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modules));
    
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_LONG).show();
			}        	
        });
    }
}
