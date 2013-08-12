package com.appspot.diabeteselsewhere.asynctask;

import java.io.IOException;

import org.json.JSONObject;

import com.appspot.diabeteselsewhere.activity.ShareTipsActivity;
import com.appspot.diabeteselsewhere.helper.DEJsonWriter;
import com.appspot.diabeteselsewhere.helper.DEServerHelper;
import com.appspot.diabeteselsewhere.model.EventModel;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ShareTipsWebAPITask extends AsyncTask<String, Integer, Boolean>{
	
	private static String eventUrl = "/event/";
	private static final String SETTINGS = "my_setting";
	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";
	private static final String USER_DATA = "data of the user";
	private static final String CURRENT_EVENT = "current event subscribed";
	
	private ShareTipsActivity activity;
	private EventModel eventModel;
	private EventModel.ActivityModel activityModel;
	private static final String dtag = "ShareTipsWebAPITask";

	public ShareTipsWebAPITask(ShareTipsActivity a, EventModel em) {
		activity = a;
		eventModel = em;
	}
	
	public ShareTipsWebAPITask(ShareTipsActivity a, EventModel.ActivityModel am) {
		activity = a;
		activityModel = am;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		JSONObject object = new JSONObject();
		Log.d(dtag, "Tips:" + activityModel.tips);
		object = DEJsonWriter.tips2json(activityModel);
		Log.d(dtag , "Json Object:" + object.toString());
		Boolean status = false;
		try {
			new DEServerHelper(object);
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
