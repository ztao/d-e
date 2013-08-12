package com.appspot.diabeteselsewhere.helper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.appspot.diabeteselsewhere.model.EventModel;

public class DEJsonParser {

	private static final String dtag = "DEJsonParser";

	public static ArrayList<EventModel> eventListParser(String result) throws JSONException {
		ArrayList<EventModel> eventsData = new ArrayList<EventModel>();
		JSONArray eventList = new JSONArray(result);
		for(int i=0; i<eventList.length();i++) {
			JSONObject event = eventList.getJSONObject(i);
			String name  = event.getString("Name");
			String brief = event.getString("Brief");
			eventsData.add(new EventModel(name, brief));
		}
		return eventsData;
	}

	public static EventModel eventParser(String result) {
		EventModel eventModel = new EventModel();
		eventModel.activityList = new ArrayList<EventModel.ActivityModel>();		try {
			JSONObject event = new JSONObject(result);
			eventModel.eid = event.getInt("ID");
			eventModel.name = event.getString("Name");
			eventModel.brief = event.getString("Brief");
			eventModel.startDate = event.getString("Start Date");
			eventModel.duration = event.getString("Duration");
			eventModel.generalTips = event.getString("General Tips");
			eventModel.activityList = new ArrayList<EventModel.ActivityModel>();
			JSONArray activities = event.getJSONArray("Activity List");
			for(int i=0; i<activities.length(); ++i) {
				EventModel.ActivityModel am = eventModel.new ActivityModel();
				am.place = eventModel.new PlaceModel();
				am.relation = eventModel.new Relation();
				JSONObject a = activities.getJSONObject(i);
				JSONObject p = a.getJSONObject("Place");
				JSONObject r = a.getJSONObject("Relation");
				am.aid = a.getInt("ID");
				am.name = a.getString("Name");
				am.startDate = a.getString("Start Date");
				am.duration = a.getString("Duration");
				am.place.name = p.getString("Name");
				am.place.location = p.getString("Location");
				am.place.weather = p.getString("Weather");
				am.tips = a.getString("Tips");
				am.relation.previous = r.getString("previous");
				am.relation.next = r.getString("next");
				am.relation.xor = r.getString("xor");
				eventModel.activityList.add(am);
			}
			int bLength = event.getJSONArray("Before You Go").length();
			eventModel.beforeYouGo = new String[bLength];
			List<String> byg = new ArrayList<String>();
			for(int i=0; i<bLength; ++i) {
				byg.add((String) event.getJSONArray("Before You Go").get(i));
				eventModel.beforeYouGo = byg.toArray(new String[0]);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return eventModel;
	}

}
