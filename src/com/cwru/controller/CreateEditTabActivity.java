package com.cwru.controller;

import java.util.Hashtable;

import com.cwru.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

public class CreateEditTabActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

	private TabHost mTabHost;
	private Hashtable<String, TabInfo> mapTabInfo = new Hashtable<String, TabInfo>();
	private TabInfo mLastTab = null;
	
	private class TabInfo {	
		private String tag;
		private Class clss;
		private Bundle args;
		private Fragment fragment;
		
		TabInfo(String tag, Class clazz, Bundle args) {
			this.tag = tag;
			this.clss = clazz;
			this.args = args;
		}
	}
	
	class TabFactory implements TabContentFactory {
		private final Context mContext;
		
		public TabFactory(Context context) {
			mContext = context;
		}

		@Override
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumHeight(0);
			v.setMinimumWidth(0);
			return v;
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate Layout
		setContentView(R.layout.workout_exercise_module);
		// Set up Tab Host
		initializeTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); 
			// Sets the tab as per the saved state
		}
	}
	
	public void onSavedInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}
	
	private void initializeTabHost(Bundle args) {
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();
		TabInfo tabInfo = null;
		addTab(this, mTabHost, mTabHost.newTabSpec("Tab1").setIndicator("Tab 1"), (tabInfo = new TabInfo("Tab1", CreateWorkoutFragment.class, args)));	
		mapTabInfo.put(tabInfo.tag, tabInfo);
		addTab(this, mTabHost, mTabHost.newTabSpec("Tab2").setIndicator("Tab 2"), (tabInfo = new TabInfo("Tab2", CreateExerciseFragment.class, args)));	
		mapTabInfo.put(tabInfo.tag, tabInfo);
		addTab(this, mTabHost, mTabHost.newTabSpec("Tab3").setIndicator("Tab 3"), (tabInfo = new TabInfo("Tab3", EditWorkoutFragment.class, args)));	
		mapTabInfo.put(tabInfo.tag, tabInfo);
		addTab(this, mTabHost, mTabHost.newTabSpec("Tab4").setIndicator("Tab 4"), (tabInfo = new TabInfo("Tab4", EditExerciseFragment.class, args)));	
		mapTabInfo.put(tabInfo.tag, tabInfo);
		
		this.onTabChanged("Tab1");
		mTabHost.setOnTabChangedListener(this);
	}
	
	private static void addTab(CreateEditTabActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		// Attach tab vew factory to the spec
		tabSpec.setContent(activity.new TabFactory(activity));
		String tag = tabSpec.getTag();
		// Check to see if we already have a fragment for this tab, from previous saved sate, if so, 
		// deactivate it because our initial state is that a tab isn't shown
		tabInfo.fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
		if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
			FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
			ft.detach(tabInfo.fragment);
			ft.commit();
			activity.getSupportFragmentManager().executePendingTransactions();
		}
		
		tabHost.addTab(tabSpec);
	}
	
	@Override
	public void onTabChanged(String tag) {
		TabInfo newTab = (TabInfo) this.mapTabInfo.get(tag);
		if (mLastTab != newTab) {
			FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
			if (mLastTab != null) {
				if (mLastTab.fragment != null) {
					ft.detach(mLastTab.fragment);
				}
			}
			if (newTab != null) {
				if (newTab.fragment == null) {
					newTab.fragment = Fragment.instantiate(this, newTab.clss.getName(), newTab.args);
					ft.add(R.id.realtabcontent, newTab.fragment, newTab.tag);
				} else {
					ft.attach(newTab.fragment);
				}
			}
			
			mLastTab = newTab;
			ft.commit();
			this.getSupportFragmentManager().executePendingTransactions();
		}			
	}

	
}
