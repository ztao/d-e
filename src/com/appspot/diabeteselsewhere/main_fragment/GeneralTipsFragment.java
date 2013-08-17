package com.appspot.diabeteselsewhere.main_fragment;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.helper.DEJsonParser;
import com.appspot.diabeteselsewhere.model.EventModel;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GeneralTipsFragment extends Fragment {

	private static final String USER_DATA = "data of the user";
	private static final String CURRENT_EVENT = "current event subscribed";

	private final static String dtag = "GeneralTipsFragment";
	public String generalTips = "";
	
	public static GeneralTipsFragment create(String gt) {
		GeneralTipsFragment gtf = new GeneralTipsFragment();
		gtf.generalTips = gt;
		return gtf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.general_tips,
				container, false);
		//		SharedPreferences eventData = getActivity().getSharedPreferences(USER_DATA, 0);
		//		EventModel mEvent = DEJsonParser.eventParser(eventData.getString(CURRENT_EVENT, ""));
		//		Log.d(dtag , "(onCreateView)General Tips:\n"+mEvent.generalTips);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((TextView) getView().findViewById(R.id.general_tips)).setText(generalTips);
	}
}
