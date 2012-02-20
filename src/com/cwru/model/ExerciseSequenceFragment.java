package com.cwru.model;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.cwru.R;
import com.cwru.utils.DragListener;
import com.cwru.utils.DragNDropAdapter;
import com.cwru.utils.DragNDropListView;
import com.cwru.utils.DropListener;
import com.cwru.utils.RemoveListener;

public class ExerciseSequenceFragment extends ListFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<String> content = new ArrayList<String>(mListContent.length);
		for (int i=0; i < mListContent.length; i++) {
		  	content.add(mListContent[i]);
		}
		    
		setListAdapter(new DragNDropAdapter(this.getActivity(), new int[]{R.layout.exercise_sequence_row}, new int[]{R.id.TextView01}, content));//new DragNDropAdapter(this,content)
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
	    
	    private static String[] mListContent={"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7"};
	
}
