package com.cwru.model;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cwru.R;
import com.cwru.dao.DbAdapter;
import com.cwru.utils.DragListener;
import com.cwru.utils.DragNDropAdapter;
import com.cwru.utils.DragNDropListView;
import com.cwru.utils.DropListener;
import com.cwru.utils.RemoveListener;

public class ExerciseSequenceFragment extends ListFragment {
	private DbAdapter mDbHelper;
//	private ArrayList<String> exerciseNameList = new ArrayList<String>(); // For List to Display to Screen
	private ArrayList<Exercise> exerciseList;
	public DragNDropAdapter adapter;
	private String workoutName;
	
	public ExerciseSequenceFragment(String workoutName) {
		this.workoutName = workoutName;
		/* Get exercise sequence for workout */
		exerciseList = new ArrayList<Exercise>();
	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("onCreate", "Adpater created");
		mDbHelper = new DbAdapter(this.getActivity());
		adapter = new DragNDropAdapter(this.getActivity(), new int[]{R.layout.exercise_sequence_row}, new int[]{R.id.TextView01}, exerciseList);//new DragNDropAdapter(this,content)
		
		/** 
		 * TODO 
		 * Get exercise sequence parse and get name for each exercise
		 */
		initializeExerciseArray();
	}
	
	private void initializeExerciseArray() {
		// Get the exercise Sequence
		String exerciseSequence = mDbHelper.getExerciseSequence(workoutName);
		StringTokenizer st = new StringTokenizer(exerciseSequence,",");
		while (st.hasMoreTokens()) {
			Long exerciseId =  Long.parseLong(st.nextToken());
			Exercise exercise = mDbHelper.getExerciseFromId(exerciseId);
			exerciseList.add(exercise);
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		/*
		ArrayList<String> content = new ArrayList<String>(mListContent.length);
		for (int i=0; i < mListContent.length; i++) {
		  	content.add(mListContent[i]);
		}
		*/
		Log.d("onActivityCreated", "set adapter");
		setListAdapter(adapter);
		ListView listView = getListView();
		
		if (listView instanceof DragNDropListView) {
			((DragNDropListView) listView).setDropListener(mDropListener);
			((DragNDropListView) listView).setRemoveListener(mRemoveListener);
			((DragNDropListView) listView).setDragListener(mDragListener);
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		View view =  (LinearLayout) inflater.inflate(R.layout.exercise_sequence, container, false);

	      
		return view;
	}
	
	private DropListener mDropListener = 
			new DropListener() {
	        public void onDrop(int from, int to) {
	        	ListAdapter adapter = getListAdapter();
	        	
	        	if (adapter instanceof DragNDropAdapter) {
	        		((DragNDropAdapter)adapter).onDrop(from, to);
	        		getListView().invalidateViews();
	        	}
	        	
	        	// Get updated sequence
		        String exerciseSequence = "";
		        for (int i = 0; i < exerciseList.size(); i++) {
		        	exerciseSequence += exerciseList.get(i).getId()+",";
		        	Log.d("Exercise Sequence List AFTER: " + i, exerciseList.get(i).getName() + "(" + exerciseList.get(i).getId() + ")");
		        }
		        /*
		         * Update DB workout exercise sequence to refelct order change
		         */
		        mDbHelper.open();
				// Sequence is #,#,#,#,#,
				
				// update sequence in db
				mDbHelper.updateWorkoutExerciseSequence(exerciseSequence, workoutName);
				mDbHelper.close();					
	        }
	    };
	    
	    private RemoveListener mRemoveListener =
	        new RemoveListener() {
	        public void onRemove(int which) {
	        	ListAdapter adapter = getListAdapter();
	        	if (adapter instanceof DragNDropAdapter) {
	        		((DragNDropAdapter)adapter).onRemove(which);
	        		getListView().invalidateViews();
	        	}
	        }
	    };
	    
	    private DragListener mDragListener =
	    	new DragListener() {

	    	int backgroundColor = 0xe0103010;
	    	int defaultBackgroundColor;
	    	
				public void onDrag(int x, int y, ListView listView) {
					// TODO Auto-generated method stub
				}

				public void onStartDrag(View itemView) {
					itemView.setVisibility(View.INVISIBLE);
					defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
					itemView.setBackgroundColor(backgroundColor);
					ImageView iv = (ImageView)itemView.findViewById(R.id.ImageView01);
					if (iv != null) iv.setVisibility(View.INVISIBLE);
				}

				public void onStopDrag(View itemView) {
					itemView.setVisibility(View.VISIBLE);
					itemView.setBackgroundColor(defaultBackgroundColor);
					ImageView iv = (ImageView)itemView.findViewById(R.id.ImageView01);
					if (iv != null) iv.setVisibility(View.VISIBLE);
				}
	    	
	    };
	    
	    public void addItems(Exercise exercise) {
	    	Log.d("ExerciseSequenceFragment-addItems", exercise.getName());
	    	exerciseList.add(exercise);
	    	this.adapter.notifyDataSetChanged();
	    }
	    
	    public void removeItem(Exercise exercise) {
	    	exerciseList.remove(exercise);
	    	this.adapter.notifyDataSetChanged();
	    }
	    
}
