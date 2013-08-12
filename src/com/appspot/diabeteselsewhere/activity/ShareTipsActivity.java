package com.appspot.diabeteselsewhere.activity;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.asynctask.ShareTipsWebAPITask;
import com.appspot.diabeteselsewhere.helper.DEJsonParser;
import com.appspot.diabeteselsewhere.helper.DEJsonWriter;
import com.appspot.diabeteselsewhere.model.EventModel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShareTipsActivity extends Activity {

//	private static final String SETTINGS = "my_setting";
//	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";
	private static final String USER_DATA = "data of the user";
	private static final String CURRENT_EVENT = "current event subscribed";
	private static final String CURRENT_ACTIVITY_ID = "Current Activity ID";
	private static final String EDITABLE_TIPS = "Editable Tips";
	private static final int CHANGE_YEAH = 1;
	private static final int CHANGE_NOPE = 0;

	private SharedPreferences mEventData;
	private EventModel mEvent;
	private EditText tips;
	private int ok;
	private int cancel;
	private int currentItem;
	private String editableTips;
	private final static String dtag = "ShareTipsActivity";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_edit_tips);

		final ActionBar actionBar = getActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);

		Bundle b = getIntent().getExtras();
		currentItem = b.getInt(CURRENT_ACTIVITY_ID);
		editableTips = b.getString(EDITABLE_TIPS);
		//		Log.d(dtag, "Current Position: "+currentItem);
		//		Log.d(dtag, "Current Tips: "+editableTips);		
		mEventData = getSharedPreferences(USER_DATA, 0);
		mEvent = DEJsonParser.eventParser(mEventData.getString(CURRENT_EVENT, ""));
		Log.d(dtag, "Event ID: "+mEvent.eid);
		tips = (EditText) findViewById(R.id.edit_tips);
		tips.setText(editableTips);

		Button okButton = (Button) findViewById(R.id.tips_ok);
		ok = okButton.getId();
		Button cancelButton = (Button) findViewById(R.id.tips_cancel);
		cancel = cancelButton.getId();


		View.OnClickListener buttonHandler = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EventModel.ActivityModel a = mEvent.new ActivityModel();
				int statusCode = CHANGE_NOPE;
				int currentButton = ((Button) v).getId();
				if (currentButton == ok) {
					editableTips = ((EditText)findViewById(R.id.edit_tips)).getText().toString();
					Log.d(dtag, "Original Tips: "+editableTips);
					a = mEvent.activityList.get(currentItem);
					//					Log.d(dtag, "Current Item: "+currentItem);
					//					Log.d(dtag,"Activity ID: "+a.aid);
					//					Log.d(dtag, "Event Model:"+DEJsonWriter.event2Json(mEvent).toString());
					a.tips = editableTips;
					SharedPreferences.Editor editor = mEventData.edit();
					editor.commit();
					statusCode = CHANGE_YEAH;
				} else {
					assert(currentButton == cancel);
				}
				Intent mIntent = new Intent(v.getContext(),ActivitiesPagerActivity.class);
				mIntent.putExtra(CURRENT_ACTIVITY_ID, currentItem);
				mIntent.putExtra(EDITABLE_TIPS, editableTips);
				Log.d(dtag, "Current Position is "+currentItem);
				setResult(statusCode, mIntent);
				Log.d(dtag, "Event ID: "+mEvent.eid);
				new ShareTipsWebAPITask(ShareTipsActivity.this, a).execute(""+mEvent.eid);
				//  new ShareTipsWebAPITask(ShareTipsActivity.this, mEvent).execute(""+mEvent.eid);
				finish();
			}
		};
		okButton.setOnClickListener(buttonHandler);
		cancelButton.setOnClickListener(buttonHandler);
	}
}
