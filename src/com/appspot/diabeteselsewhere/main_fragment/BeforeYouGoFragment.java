package com.appspot.diabeteselsewhere.main_fragment;

import java.util.ArrayList;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.adapter.BeforeYouGoAdapter;

import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

public class BeforeYouGoFragment extends ListFragment {

	private static final String BEFORE_YOU_GO = "Check list of Before You Go";
	
	public static ArrayList<String> checkList;
	private SharedPreferences checkedListPrefs;
	private SharedPreferences.Editor checker;

	public static BeforeYouGoFragment create (ArrayList<String> cl) {
		BeforeYouGoFragment bygf = new BeforeYouGoFragment();
		checkList = cl;
		
		return bygf;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkedListPrefs = getActivity().getSharedPreferences(BEFORE_YOU_GO, 0);
		checker = checkedListPrefs.edit();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		BeforeYouGoAdapter adapter = new BeforeYouGoAdapter(getActivity(),
				R.layout.before_you_go, checkList);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		CheckBox ckv = (CheckBox) v;
		Boolean isChecked = ckv.isChecked();
		ckv.setChecked(!isChecked);
		checker.putBoolean("Item"+pos, !isChecked);
		checker.commit();
	}

}
