package com.appspot.diabeteselsewhere.activity;

import com.appspot.diabeteselsewhere.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MapActivity extends Activity {
	static final LatLng RBH = new LatLng(50.747335, -1.823632);
	static final LatLng BOOTS = new LatLng(50.753237,-1.838593);
	static final LatLng CLP = new LatLng(50.75264,-1.842369);
	static final String PLACE_TYPE = "Place Type";
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		Bundle b = getIntent().getExtras();
		int TYPE = b.getInt(PLACE_TYPE, 0);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		switch (TYPE) {
		case 0:
			Marker boots = map.addMarker(new MarkerOptions().position(BOOTS)
					.title("Boots")
					.snippet("Hopefully can find something useful.")
					.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.biaozhi)));
			Marker clp = map.addMarker(new MarkerOptions().position(CLP)
					.title("Castle Lane Pharmacy")
					.snippet("Probably can find something useful.")
					.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.biaozhi)));
			break;
		case 1:
			Marker rbh = map.addMarker(new MarkerOptions().position(RBH)
					.title("Bournemouth Diabetes and Endocrine Centre")
					.snippet("Let's Rock!\n Although just a demo ==")
					.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.biaozhi)));
			break;
		}
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(RBH, 2));
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}
}
