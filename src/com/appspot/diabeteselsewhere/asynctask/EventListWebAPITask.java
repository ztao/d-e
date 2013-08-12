package com.appspot.diabeteselsewhere.asynctask;

import java.util.ArrayList;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.helper.DEHttpConnection;
import com.appspot.diabeteselsewhere.helper.DEJsonParser;
import com.appspot.diabeteselsewhere.helper.DEServerHelper;
import com.appspot.diabeteselsewhere.main_fragment.EventSubscriptionFragment;
import com.appspot.diabeteselsewhere.model.EventModel;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class EventListWebAPITask extends AsyncTask<String, Integer, Boolean> {

	private EventSubscriptionFragment fragment;
	private Activity context;
	private String dtag = "EventListWebAPITask";
	private Dialog progDialog;
	ArrayList<EventModel> eventsData;

	public EventListWebAPITask(EventSubscriptionFragment f) {
		super();
		//		Log.d(dtag, "start AsyncTask...");
		fragment = f;
		context = fragment.getActivity();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute(); 
		progDialog = ProgressDialog.show(context, "Searching", context.getResources().getString(R.string.looking_for_events) , true, false);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		try {
			Log.d(dtag ,"Background:" + Thread.currentThread().getName());
			Log.d(dtag,"params: " + params[0]);
			String result = DEHttpConnection.downloadFromServer(params);
//			String result = DEServerHelper.downloadFromServer(params);
			Log.d(dtag, "Response body:" + result);
			eventsData = DEJsonParser.eventListParser(result);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			progDialog.dismiss();
			this.fragment.setEvents(eventsData);
		}
	}
}
