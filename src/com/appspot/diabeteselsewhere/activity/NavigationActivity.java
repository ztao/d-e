package com.appspot.diabeteselsewhere.activity;

import com.appspot.diabeteselsewhere.R;
import com.appspot.diabeteselsewhere.adapter.DrawerAdapter;
import com.appspot.diabeteselsewhere.main_fragment.AidInformationFragment;
import com.appspot.diabeteselsewhere.main_fragment.BeforeYouGoFragment;
import com.appspot.diabeteselsewhere.main_fragment.DietRecommendationFragment;
import com.appspot.diabeteselsewhere.main_fragment.EventDetailFragment;
import com.appspot.diabeteselsewhere.main_fragment.EventSubscriptionFragment;
import com.appspot.diabeteselsewhere.main_fragment.GeneralTipsFragment;
import com.appspot.diabeteselsewhere.main_fragment.GoSubscribeFragment;
import com.appspot.diabeteselsewhere.model.EventModel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NavigationActivity extends Activity 
implements EventSubscriptionFragment.OnEventSubscribedListener
{

	private static final String SETTINGS = "my_setting";
	private static final String SUBSCRIBED_EVENT_NUMBER = "the number of subscribed event";

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String [] mFeatureTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private EventModel eventModel;

	private Fragment eventSubscription;
	private Fragment eventDetail;
	private Fragment generalTips;
	private Fragment beforeYouGo;
	private Fragment dietRecommendation;
	private Fragment aidInformation;

	private final static String dtag = "NavigationActivity";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);

		mTitle = mDrawerTitle = getTitle();
		mFeatureTitles  = getResources().getStringArray(R.array.features_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
//		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//				R.layout.drawer_list_item, mFeatureTitles));
		mDrawerList.setAdapter(new DrawerAdapter(this,
				R.layout.drawer_list_item, mFeatureTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		selectItem(1);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		EventModel em = getEventModel();
		if (em == null) {
			ft.replace(R.id.content_frame, new GoSubscribeFragment());
		}

		Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.features_array)[position], Toast.LENGTH_LONG).show();
		switch(position) {
		case 1:
			if (eventSubscription == null) {
				eventSubscription = new EventSubscriptionFragment();
			}
			ft.replace(R.id.content_frame, eventSubscription);
			break;        
		case 2:
			if (eventDetail == null) {
				eventDetail = new EventDetailFragment();
			}
			ft.replace(R.id.content_frame, eventDetail);
			break;
		case 3:
			if (em != null & generalTips == null) {  
				generalTips = GeneralTipsFragment.create(em.generalTips);
			}
			if (em != null) {
				ft.replace(R.id.content_frame, generalTips);
			}
			break;
		case 4:
			if (em != null & beforeYouGo == null) {  
				beforeYouGo = BeforeYouGoFragment.create(em.beforeYouGo);
			}
			if (em != null) {
				ft.replace(R.id.content_frame, beforeYouGo);
			}
			break;
		case 5:
			dietRecommendation = DietRecommendationFragment.create();
			ft.replace(R.id.content_frame, dietRecommendation);
			break;
		case 6:
			aidInformation = AidInformationFragment.create();
			ft.replace(R.id.content_frame, aidInformation);
		}
		ft.commit();	

		mDrawerList.setItemChecked(position, true);
		setTitle(mFeatureTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setEventModel(EventModel em) {
		Log.d(dtag, "set Event model");
		eventModel = em;
	}

	@Override
	public EventModel getEventModel() {
		Log.d(dtag , "get Event model");
		return eventModel;
	}

}
