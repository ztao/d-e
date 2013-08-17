package com.appspot.diabeteselsewhere.adapter;


import java.util.ArrayList;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.model.EventModel;
import com.appspot.diabeteselsewhere.model.EventModel.ActivityModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityListAdapter extends ArrayAdapter<EventModel.ActivityModel> {

	private static final String SETTINGS = "my_setting";
	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";

	private LayoutInflater layoutInflater;
	private final Context context;
	private int defaultPosition;
	private final String dtag = "EventListAdapter";
	private ArrayList<EventModel.ActivityModel> activityDataList;

	public ActivityListAdapter (Context c, int resource, ArrayList<EventModel.ActivityModel> activitiesData)
	{
		super(c, resource, activitiesData);
		context = c;
		activityDataList = activitiesData;
		layoutInflater = (LayoutInflater) context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
		SharedPreferences mPref = context.getSharedPreferences(SETTINGS, 0);
		defaultPosition = mPref.getInt(SUBSCRIBED_EVENT_NUMBER, -1);
	}


	@Override
	public int getCount() {
		return activityDataList.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public EventModel.ActivityModel getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		ActivityListViewHolder holder;
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.each_activity_item, parent, false);
			holder = new ActivityListViewHolder();
			holder.activityName = (TextView) convertView.findViewById(R.id.eachActivityName);
			convertView.setTag(holder);
		} else {
			holder = (ActivityListViewHolder) convertView.getTag();
		}
		holder.activityName.setText(activityDataList.get(pos).name);
		Log.d(dtag, "pos: "+pos);
		return convertView;
	}

	public static class ActivityListViewHolder {
		TextView activityName;
	}

}
