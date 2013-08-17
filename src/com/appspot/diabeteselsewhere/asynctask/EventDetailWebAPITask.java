package com.appspot.diabeteselsewhere.asynctask;

import java.util.ArrayList;

import org.json.JSONException;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.helper.DEJsonParser;
import com.appspot.diabeteselsewhere.helper.DEServerHelper;
import com.appspot.diabeteselsewhere.main_fragment.EventDetailFragment;
import com.appspot.diabeteselsewhere.main_fragment.EventSubscriptionFragment;
import com.appspot.diabeteselsewhere.model.EventModel;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class EventDetailWebAPITask extends AsyncTask<String, Integer, String> {

	private static final String SETTINGS = "my_setting";
	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";
	private static final String USER_DATA = "data of the user";
	private static final String CURRENT_EVENT = "current event subscribed";
	private EventDetailFragment edFragment;
	private EventSubscriptionFragment esFragment;
	private Activity context;
	private Dialog progDialog;
	private static final String dtag = "EventDetailWebAPITask";
	
	public EventDetailWebAPITask() {
		
	}
	
	public EventDetailWebAPITask(EventDetailFragment f) {
		edFragment = f;
		context = edFragment.getActivity();
	}
	
	public EventDetailWebAPITask(EventSubscriptionFragment f) {
		esFragment = f;
		context = esFragment.getActivity();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute(); 
		progDialog = ProgressDialog.show(context, "Acquiring", "the specified event", true, false);
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			Log.d(dtag  ,"Background:" + Thread.currentThread().getName());
			String result = DEServerHelper.downloadFromServer(params);
			Log.d(dtag, "Response body:" + result);
			SharedPreferences mData = context.getSharedPreferences(USER_DATA, 0);
			SharedPreferences.Editor mEditor = mData.edit();
			mEditor.putString(CURRENT_EVENT, result);
			mEditor.commit();
			return result;
		} catch (Exception e) {
			return new String();
		}
	}
	
	@Override
	protected void onPostExecute(String result) {

		EventModel eventData = new EventModel();
		Log.d(dtag, "On post execute...");
		Log.d(dtag, "result == null? "+(result == null));
		progDialog.dismiss();
		if (result == null || result.length() == 0) {
			return;
		}		
		eventData = DEJsonParser.eventParser(result);
		if (edFragment != null) {
			Log.d(dtag, "Gonna set activities of the subscribed event");
			edFragment.setActivities(eventData);
		} else if (esFragment != null) {
			Log.d(dtag, "Gonna set subscribed event");
			esFragment.setSubscribedEvent(eventData);
		}
	}

}
