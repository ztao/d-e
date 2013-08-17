package com.appspot.diabeteselsewhere.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.appspot.diabeteselsewhere.R;

public class BeforeYouGoAdapter extends ArrayAdapter<String>{

	private static final String BEFORE_YOU_GO = "Check list of Before You Go";

	private Context context;
	private ArrayList<String> checkList;
	private LayoutInflater layoutInflater;
	private SharedPreferences checkedListPrefs;

	private static final String dtag = "BeforeYouGoAdapter";

	public BeforeYouGoAdapter(Context c, int checkItem,
			ArrayList<String> cl) {
		super(c, checkItem, cl);
		context = c;
		checkList = cl;
		Log.d(dtag,checkList.toString());
		layoutInflater = (LayoutInflater) context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
		checkedListPrefs = getContext().getSharedPreferences(BEFORE_YOU_GO, 0);
		SharedPreferences.Editor checker = checkedListPrefs.edit();
	}

	@Override
	public int getCount() {
		return checkList.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public String getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		CheckListHolder holder;
		Log.d(dtag, "pos = " + pos);
		Log.d(dtag, "check list item: " + checkList.get(pos));
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.before_you_go_item, parent, false);
			holder = new CheckListHolder();
			holder.checkItem = (CheckBox) convertView.findViewById(R.id.bygCheckBox);
			convertView.setTag(holder);
		} else {
			holder = (CheckListHolder) convertView.getTag();
		}
		Boolean checked = checkedListPrefs.getBoolean("No."+pos, false);
		holder.checkItem.setChecked(checked);
		holder.checkItem.setText(checkList.get(pos));
		return convertView;
	}

	class CheckListHolder {
		public CheckBox checkItem;
	}
}
