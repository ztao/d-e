package com.appspot.diabeteselsewhere.adapter;

import com.appspot.diabeteselsewhere.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] features;

	static class ViewHolder {
		public TextView text;
		public ImageView image;
	}

	public DrawerAdapter(Activity c, int res, String[] f) {
		super(c, res, f);
		context = c;
		features = f;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View drawerView = convertView;
		if (drawerView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			drawerView = inflater.inflate(R.layout.drawer_list_item, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) drawerView.findViewById(R.id.drawerText1);
			viewHolder.image = (ImageView) drawerView
					.findViewById(R.id.drawerImage1);
			drawerView.setTag(viewHolder);
		}
	    ViewHolder holder = (ViewHolder) drawerView.getTag();
	    String s = features[position];
	    holder.text.setText(s);
	    int res = 0;
	    switch(position) {
	    case 0:
	    	res = R.drawable.personal_health_information;
	    	break;
	    case 1:
	    	res = R.drawable.new_event;
	    	break;
	    case 2:
	    	res = R.drawable.my_schedule;	    	
	    	break;
	    case 3:
	    	res = R.drawable.general_tips;	    	
	    	break;
	    case 4:
	    	res = R.drawable.before_you_go;
	    	break;
	    case 5:
	    	res = R.drawable.diet_recommendation;
	    	break;
	    case 6:
	    	res = R.drawable.aid_information;
	    	break;
	    case 7:
	    	res = R.drawable.flight_calculator;
	    	break;
	    case 8:
	    	res = R.drawable.feed_back;
	    	break;
	    case 9:
	    	res = R.drawable.log_out;
	    	break;
	    }
	    holder.image.setImageResource(res);
		return drawerView;
	}
}
