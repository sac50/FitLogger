package com.cwru.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cwru.R;

public class HomeScreen extends Activity {
	
	GridView homeGrid1;
	GridView homeGrid2;
	GridView homeGrid3;
	public static boolean isTablet;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen_grid);
		
		homeGrid1 = (GridView) findViewById(R.id.gvHomeScreenGrid1);
		homeGrid2 = (GridView) findViewById(R.id.gvHomeScreenGrid2);
		homeGrid3 = (GridView) findViewById(R.id.gvHomeScreenGrid3);

		homeGrid1.setAdapter(new ImageAdapter1(this));
		homeGrid2.setAdapter(new ImageAdapter2(this));
		homeGrid3.setAdapter(new ImageAdapter3(this));

		
	}
	
	public class ImageAdapter1 extends BaseAdapter {
		Context context;

		public ImageAdapter1(Context context) { 
			this.context = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3; // number of elements we want in the grid
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View MyView = convertView;
	         
	         if ( convertView == null )
	         {
	            
		            //Inflate the layout
		            LayoutInflater li = getLayoutInflater();
		            MyView = li.inflate(R.layout.grid_item, null);
		            
		            TextView tv = (TextView)MyView.findViewById(R.id.grid_item_text);
		            ImageView iv = (ImageView)MyView.findViewById(R.id.grid_item_image);

		            
		            if (position == 0) {
		            	tv.setText("Manage Workouts");
		            	iv.setImageResource(R.drawable.createedit);	   
		            	iv.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(HomeScreen.this, CreateEditTabActivity.class);
								startActivity(intent);
							}
						});
		            }
		            else if (position == 1) {
		            	tv.setText("Goals");
		            	iv.setImageResource(R.drawable.goals);
		            	iv.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(HomeScreen.this, GoalsActivity.class);
								startActivity(intent);
							}
						});
		            }
		            
		            else if (position == 2) {
		            	tv.setText("Tutorial");
		            	iv.setImageResource(R.drawable.tutorial);
		            	iv.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(HomeScreen.this, TutorialActivity.class);
								startActivity(intent);
							}
						});
		            }
		            else {
		            	iv.setImageResource(R.drawable.ic_launcher);
		            }
	         }
	         
	         return MyView;
		}
		
	}
	
	public class ImageAdapter2 extends BaseAdapter {
		Context context;

		public ImageAdapter2(Context context) { 
			this.context = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3; // number of elements we want in the grid
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View MyView = convertView;
	         
	         if ( convertView == null )
	         {
	        	 
	        	 //Inflate the layout
		            LayoutInflater li = getLayoutInflater();
		            MyView = li.inflate(R.layout.grid_item, null);
		            
		            TextView tv = (TextView)MyView.findViewById(R.id.grid_item_text);
		            ImageView iv = (ImageView)MyView.findViewById(R.id.grid_item_image);
		            

		            
		            
		            if (position == 1) {
		            	tv.setText("Workout");
		            	iv.setImageResource(R.drawable.workout);
		            	iv.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(HomeScreen.this, WorkoutWorkflow.class);
								startActivity(intent);
							}
						});
		            }
		          
	         
	         }
	         
	         return MyView;
		}
		
	}
	
	public class ImageAdapter3 extends BaseAdapter {
		Context context;

		public ImageAdapter3(Context context) { 
			this.context = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3; // number of elements we want in the grid
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View MyView = convertView;
	         
	         if ( convertView == null )
	         {
	            
	            //Inflate the layout
	            LayoutInflater li = getLayoutInflater();
	            MyView = li.inflate(R.layout.grid_item, null);
	            
	            TextView tv = (TextView)MyView.findViewById(R.id.grid_item_text);
	            ImageView iv = (ImageView)MyView.findViewById(R.id.grid_item_image);

	            
	            if (position == 0) {
	            	tv.setText("Calendar");
	            	iv.setImageResource(R.drawable.calendar);	
	            	iv.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(HomeScreen.this, CalendarActivity.class);
							startActivity(intent);
						}
					});
	            }
	            else if (position == 1) {
	            	tv.setText("History");
	            	iv.setImageResource(R.drawable.history);
	            	iv.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(HomeScreen.this, HistoryActivity.class);
							startActivity(intent);
						}
					});
	            }
	            else if (position == 2) {
	            	tv.setText("Analytics");
	            	iv.setImageResource(R.drawable.analytics);
	            	iv.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(HomeScreen.this, AnalyticsActivity.class);
							startActivity(intent);
						}
					});
	            }
	            else {
	            	iv.setImageResource(R.drawable.ic_launcher);
	            }

	         }
	         
	         return MyView;
		}
		
	}
	
    /**
     * Function to set the flag for whether or not the screen size is large enough to support table functionality
     */
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
