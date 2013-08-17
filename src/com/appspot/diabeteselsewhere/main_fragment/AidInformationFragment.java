package com.appspot.diabeteselsewhere.main_fragment;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.activity.MapActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AidInformationFragment extends Fragment {
	
	static final String PLACE_TYPE = "Place Type";

	private final static String dtag = "AidInformationFragment";

	public static Fragment create() {
		AidInformationFragment fragment = new AidInformationFragment();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.aid_information,
				container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Button aidButton0 = (Button) getView().findViewById(R.id.pharmacy);
		Button aidButton1 = (Button) getView().findViewById(R.id.hospital);
		aidButton0.setOnClickListener(new View.OnClickListener() {
			public void onClick(View View) {
				Intent intent = new Intent(getActivity(), MapActivity.class);
				Bundle b = new Bundle();
				b.putInt(PLACE_TYPE, 0);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
		aidButton1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View View) {
				Intent intent = new Intent(getActivity(), MapActivity.class);
				Bundle b = new Bundle();
				b.putInt(PLACE_TYPE, 1);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
	}
}
