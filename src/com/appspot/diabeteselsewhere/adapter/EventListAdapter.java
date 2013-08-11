package com.appspot.diabeteselsewhere.adapter;

import java.util.ArrayList;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.model.EventModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<EventModel> {

	private static final String SETTINGS = "my_setting";
	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";

	private LayoutInflater layoutInflater;
	private final ArrayList<EventModel> eventDataList;
	private final Context context;
	private int defaultPosition;
	private final String dtag = "EventListAdapter";

	public EventListAdapter (Context c, int resource, int textViewResourceId, int selectedPosistion, ArrayList<EventModel> eventsData)
	{
		super(c, resource, textViewResourceId, eventsData);
		context = c;
		eventDataList = eventsData;
		layoutInflater = (LayoutInflater) context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
		SharedPreferences mPref = context.getSharedPreferences(SETTINGS, 0);
		defaultPosition = mPref.getInt(SUBSCRIBED_EVENT_NUMBER, -1);
	}

	@Override
	public int getCount() {
		return eventDataList.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public EventModel getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		EventListViewHolder holder;
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.each_event_item, parent, false);
			holder = new EventListViewHolder();
			holder.eventName = (TextView) convertView.findViewById(R.id.eventNameView);
			holder.eventBrief = (TextView) convertView.findViewById(R.id.eventBriefView);
			holder.subscribeText = (TextView) convertView.findViewById(R.id.subscribeText);
		} else {
			holder = (EventListViewHolder) convertView.getTag();
		}
		//		Log.d(dtag, eventDataList.get(pos).name);
		holder.eventName.setText(eventDataList.get(pos).name);
		holder.eventBrief.setText(eventDataList.get(pos).brief);
		if (defaultPosition == pos) {
			((ListView) parent).setItemChecked(pos, false);
		}
		return convertView;
	}

	public static class EventListViewHolder {
		TextView eventName;
		TextView eventBrief;
		TextView subscribeText;
	}
}
