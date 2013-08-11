package com.appspot.diabeteselsewhere.adapter;

import com.appspot.diabeteselsewhere.sub_fragment.CurrentActivityFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ActivitiesPagerAdapter extends FragmentPagerAdapter {
	
	private int NUM_ITEMS;
	
	public ActivitiesPagerAdapter(FragmentManager fm, int NUM_ITEMS, int pos) {
		super(fm);
		this.NUM_ITEMS = NUM_ITEMS;
	}


	@Override
	public int getCount() {
		return NUM_ITEMS;
	}

	@Override
	public Fragment getItem(int position) {
		return CurrentActivityFragment.create(position);
	}
}