package com.appspot.diabeteselsewhere.asynctask;

import java.io.IOException;

import org.json.JSONObject;

import com.appspot.diabeteselsewhere.activity.ShareTipsActivity;
import com.appspot.diabeteselsewhere.helper.DEJsonWriter;
import com.appspot.diabeteselsewhere.helper.DEServerHelper;
import com.appspot.diabeteselsewhere.model.EventModel;

import android.os.AsyncTask;
import android.widget.Toast;

public class ShareTipsWebAPITask extends AsyncTask<String, Integer, Boolean>{
	
	private static String eventUrl = "/event/";
	private static final String SETTINGS = "my_setting";
	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";
	private static final String USER_DATA = "data of the user";
	private static final String CURRENT_EVENT = "current event subscribed";
	
	private ShareTipsActivity activity;
	private EventModel eventModel;

	public ShareTipsWebAPITask(ShareTipsActivity a, EventModel e) {
		activity = a;
		eventModel = e;
	}
	@Override
	protected Boolean doInBackground(String... params) {
		JSONObject eventObject = DEJsonWriter.event2Json(eventModel);
		Boolean status = false;
		try {
			new DEServerHelper(eventObject);
			status = DEServerHelper.uploadToServer(eventUrl+params[0]);
			return status;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	@Override
	protected void onPostExecute(Boolean status) {
		if (status) {
			Toast.makeText(activity, "Successfully shared your tips!", Toast.LENGTH_LONG).show();
		}
	}
}
