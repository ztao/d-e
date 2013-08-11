package com.appspot.diabeteselsewhere.main_fragment;

import java.util.ArrayList;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.activity.ActivitiesPagerActivity;
import com.appspot.diabeteselsewhere.asynctask.EventDetailWebAPITask;
import com.appspot.diabeteselsewhere.model.EventModel;
import com.appspot.diabeteselsewhere.model.EventModel.ActivityModel;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EventDetailFragment extends ListFragment {
	
	private static final String SETTINGS = "my_setting";
	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";
	private static final String EVENT_URL = "/event/";
	private int eventId;
	private String[] activityNames;
	EventModel mEvent;
	private static final String dtag = "EventDetailFragment";
	
	public EventDetailFragment() {
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		eventId = getActivity().getSharedPreferences(SETTINGS, 0).getInt(SUBSCRIBED_EVENT_NUMBER, 0);
		new EventDetailWebAPITask(this).execute(EVENT_URL+eventId);
	}

	public void setActivities(EventModel eventData) {
		mEvent = eventData;
		ArrayList<ActivityModel> activities = mEvent.activityList;
		int aSize = activities.size();
		activityNames = new String[aSize];
		for(int i = 0; i < aSize; i++) {
			activityNames[i] = activities.get(i).name;
			Log.d(dtag, "activity Name "+i+": "+activities.get(i).name);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, activityNames);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Activity parentActivity = getActivity();
		Intent myIntent = new Intent(parentActivity, ActivitiesPagerActivity.class);
		Bundle b = new Bundle();
		b.putInt("Activity Position", position);
		myIntent.putExtras(b);
		startActivity(myIntent);
		Toast.makeText(getActivity(), "No."+position+" "+activityNames[position], Toast.LENGTH_SHORT).show();
		parentActivity.overridePendingTransition(
				R.anim.slide_in_right,R.anim.slide_out_left);
		Log.d(dtag, "Item " + position + " is clicked.");
	}
	
}
