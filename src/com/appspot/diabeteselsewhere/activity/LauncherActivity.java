package com.appspot.diabeteselsewhere.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.appspot.diabeteselsewhere.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class LauncherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);

		//		ImageView logo = (ImageView) findViewById(R.id.welcomeImage);
		//		logo.setImageResource(R.drawable.de_launcher);

		Button startBtn = (Button) findViewById(R.id.startNavigation);
		startBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent startIntent = new Intent(LauncherActivity.this,NavigationActivity.class);
				startActivity(startIntent);
			}

		});
	}

}
