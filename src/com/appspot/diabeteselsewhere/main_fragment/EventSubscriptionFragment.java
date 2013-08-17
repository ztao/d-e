package com.appspot.diabeteselsewhere.main_fragment;

import java.util.ArrayList;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.adapter.EventListAdapter;
import com.appspot.diabeteselsewhere.asynctask.EventDetailWebAPITask;
import com.appspot.diabeteselsewhere.asynctask.EventListWebAPITask;
import com.appspot.diabeteselsewhere.model.EventModel;

import android.app.ListFragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventSubscriptionFragment extends ListFragment {
	
	private static final String SETTINGS = "my_setting";
	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";
	private final String EVENT_LIST = "/events";
	private static final String EVENT_URL = "/event/";
	
	private int selectedPosition;
	ArrayList<EventModel> events;
	private final String dtag = "EventSubscriptionFragment";
	
	
	@Override	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new EventListWebAPITask(this).execute(EVENT_LIST);
	}

	public void setEvents(ArrayList<EventModel> eventsData) {
		events = eventsData;
		EventListAdapter adapter = new EventListAdapter(getActivity(),
				R.layout.each_event_item, R.id.eventNameView, selectedPosition, events);
		setListAdapter(adapter);
		ListView eventListView = getListView();
		eventListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		l.setSelection(position);
		Toast.makeText(getActivity(), events.get(position).name+" at position "+position+" was booked, check it on the schedule", Toast.LENGTH_LONG).show();
		Log.d(dtag,"Event "+ events.get(position).name + "at positin "+position+" was selected");
		TextView s = ((TextView) v.findViewById(R.id.subscribeText));
		s.setTypeface(null,Typeface.BOLD);
		s.setTextSize(30);
		subscribeEventAt(position);
	}

	private void subscribeEventAt(int position) {
		SharedPreferences mPref = getActivity().getSharedPreferences(SETTINGS, 0);
		SharedPreferences.Editor editor = mPref.edit();
		int eventID = position+1;
		editor.putInt(SUBSCRIBED_EVENT_NUMBER , position+1);
		editor.commit();
		new EventDetailWebAPITask(this).execute(EVENT_URL+eventID);
	}
		
	public interface OnEventSubscribedListener {
		public void setEventModel(EventModel em);
		public EventModel getEventModel();
	}

	public void setSubscribedEvent(EventModel eventData) {
		Log.d(dtag, "set subscribed event");
		((OnEventSubscribedListener) getActivity()).setEventModel(eventData);
	}
}
