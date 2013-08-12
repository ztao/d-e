package com.appspot.diabeteselsewhere.activity;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.adapter.ActivitiesPagerAdapter;
import com.appspot.diabeteselsewhere.helper.DEJsonParser;
import com.appspot.diabeteselsewhere.helper.DepthPageTransformer;
import com.appspot.diabeteselsewhere.model.EventModel;
import com.appspot.diabeteselsewhere.sub_fragment.CurrentActivityFragment;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class ActivitiesPagerActivity extends FragmentActivity
	implements CurrentActivityFragment.OnTipsUpdatedListener
{	
	private static final String SETTINGS = "my_setting";
	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";
	private static final String USER_DATA = "data of the user";
	private static final String CURRENT_EVENT = "current event subscribed";
	private static final int CHANGE_YEAH = 1;
	private static final int CHANGE_NOPE = 0;
	
	private int NUM_ITEMS;
	private int current_item;
	private final String ACTIVITY_AMOUNT = "Activity Amount";
	private final String ACTIVITY_POSITION = "Activity Position";

	ActivitiesPagerAdapter mAdapter;
	ViewPager mPager;
	private String dtag = "ActivitiesPagerActivity";
	private SharedPreferences mPrefs; 
	private EventModel mEvent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activities_pager);
		// get the activity number of the event and set adapter
		Bundle b = getIntent().getExtras();
		current_item = b.getInt(ACTIVITY_POSITION);
		mPrefs = getSharedPreferences(USER_DATA, 0);
		mEvent = DEJsonParser.eventParser(mPrefs.getString(CURRENT_EVENT, ""));
		NUM_ITEMS = mEvent.activityList.size();	
		mAdapter = new ActivitiesPagerAdapter(getSupportFragmentManager(), NUM_ITEMS, current_item);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		// animation
		mPager.setPageTransformer(true, new DepthPageTransformer());
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				invalidateOptionsMenu();
			}
		});
		mPager.setCurrentItem(current_item);
		// Watch for button clicks.
		final ActionBar actionBar = getActionBar();
		// Specify that the Home button should show an "Up" caret, indicating that touching the
		// button will take the user one step up in the application's hierarchy.
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Bundle b = getIntent().getExtras();
		current_item = b.getInt(ACTIVITY_POSITION);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

		menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

		// Add either a "next" or "finish" button to the action bar, depending on which page
		// is currently selected.
		MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
				(mPager.getCurrentItem() == mAdapter.getCount() - 1)
				? R.string.action_finish
						: R.string.action_next);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, NavigationActivity.class));
			return true;

		case R.id.action_previous:
			// Go to the previous step in the wizard. If there is no previous step,
			// setCurrentItem will do nothing.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			return true;

		case R.id.action_next:
			// Advance to the next step in the wizard. If there is no next step, setCurrentItem
			// will do nothing.
			mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void updateCurrentItem(String newTips, int activityId) {
		mEvent.activityList.get(activityId).tips = newTips;
		mPager.setCurrentItem(activityId);
	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		mPager.setCurrentItem(data.getIntExtra(ACTIVITY_POSITION, 0));
//	}
}