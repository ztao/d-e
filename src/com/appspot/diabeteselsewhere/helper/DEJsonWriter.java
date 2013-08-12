package com.appspot.diabeteselsewhere.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appspot.diabeteselsewhere.model.EventModel;
import com.appspot.diabeteselsewhere.model.EventModel.ActivityModel;

public class DEJsonWriter {

	public static JSONObject event2Json(EventModel e) {
		JSONObject eo = new JSONObject();
		try {
			eo.put("ID", e.eid);
			eo.put("Name", e.name);
			eo.put("Brief", e.brief);
			eo.put("Start Date", e.startDate);
			eo.put("Duration", e.duration);
			JSONArray al = new JSONArray();
			for (ActivityModel a: e.activityList) {
				JSONObject ao = new JSONObject();
				JSONObject po = new JSONObject();
				JSONObject ro = new JSONObject();
				ao.put("ID", a.aid);
				ao.put("Name", a.name);
				ao.put("Start Date", a.startDate);
				ao.put("Duration", a.duration);
				po.put("Name", a.place.name);
				po.put("Location", a.place.location);
				po.put("Weather", a.place.weather);
				ao.put("Place", po);
				ao.put("Tips",a.tips);
				ro.put("previous", a.relation.previous);
				ro.put("next", a.relation.next);
				ro.put("xor", a.relation.xor);
				ao.put("Relation", ro);
				al.put(ao);
			}
			eo.put("Activity List", al);
			eo.put("General Tips", e.generalTips);
			JSONArray byg = new JSONArray();
			for(String b: e.beforeYouGo) {
				byg.put(b);
			}
			eo.put("Before You Go", byg);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return eo;
	}
	
	public static JSONObject tips2json(EventModel.ActivityModel a) {
		JSONObject ao = new JSONObject();
		try {
			ao.put("ID", a.aid);
			ao.put("Tips", a.tips);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ao;
	}
}