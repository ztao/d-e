package com.appspot.diabeteselsewhere.sub_fragment;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.activity.ShareTipsActivity;
import com.appspot.diabeteselsewhere.helper.DEJsonParser;
import com.appspot.diabeteselsewhere.model.EventModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CurrentActivityFragment extends Fragment {

	private static final String SETTINGS = "my_setting";
	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";
	private static final String USER_DATA = "data of the user";
	private static final String CURRENT_EVENT = "current event subscribed";
	private static final String CURRENT_ACTIVITY_ID = "Current Activity ID";
	private static final String EDITABLE_TIPS = "Editable Tips";
	
	public static String ARG_PAGE = "page";
	private EventModel mEvent;
	private int mPageNumber;
	private String editableTips;
	private static final String dtag = "CurrentActivityFragment";

	public static Fragment create(int position) {
		CurrentActivityFragment fragment = new CurrentActivityFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_PAGE, position);
		fragment.setArguments(b);
		return fragment;
	}
	
	public CurrentActivityFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
		SharedPreferences mEventData = getActivity().getSharedPreferences(USER_DATA, 0);
		mEvent = DEJsonParser.eventParser(mEventData.getString(CURRENT_EVENT, null));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater
				.inflate(R.layout.current_activity_detail, container, false);
		EventModel.ActivityModel currentActivity = mEvent.activityList.get(mPageNumber);
		((TextView) rootView.findViewById(R.id.currentActivityName)).setText(currentActivity.name);
		((TextView) rootView.findViewById(R.id.startdate)).setText(currentActivity.startDate);
		((TextView) rootView.findViewById(R.id.duration)).setText(currentActivity.duration);
		((TextView) rootView.findViewById(R.id.place)).setText(currentActivity.place.name);
		((TextView) rootView.findViewById(R.id.weather)).setText(currentActivity.place.weather);
		editableTips = currentActivity.tips;
		((TextView) rootView.findViewById(R.id.tips)).setText(editableTips);
		Button shareTips = (Button) rootView.findViewById(R.id.shareTipsButton);
		shareTips.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editableTips = ((TextView) getActivity().findViewById(R.id.tips)).getText().toString();
				//Tips编辑功能
				Bundle b = new Bundle();
				b.putInt(CURRENT_ACTIVITY_ID, mPageNumber);
				b.putString(EDITABLE_TIPS, editableTips);
				Log.d(dtag,"Activity Position: "+mPageNumber);
				Intent mIntent = new Intent(getActivity(),ShareTipsActivity.class);
				mIntent.putExtras(b);
				startActivityForResult(mIntent, mPageNumber);
				getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
			}
		});
		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
		Toast.makeText(getActivity(), "requestCode:"+requestCode+", resultCode:"+resultCode,
				Toast.LENGTH_LONG).show();
		Log.d(dtag,"New Tips: "+data.getStringExtra(EDITABLE_TIPS));
		((TextView) this.getView().findViewById(R.id.tips)).setText(data.getStringExtra(EDITABLE_TIPS));	
	}

}
