package com.appspot.diabeteselsewhere.model;

import java.util.ArrayList;

public class EventModel {

	public Integer eid;
	public String name;
	public String brief;
	public String startDate;
	public String duration;
	public ArrayList<ActivityModel> activityList;
	public String generalTips; // A kind of article describing what a user need to do before departure
	public ArrayList<String> beforeYouGo; //CheckList
	
	public EventModel() {
		
	}
	
	public EventModel(String n, String b) {
		name = n;
		brief = b;
	}
	
	public class PlaceModel {
		public String name;
		public String location;
		public String weather;
	}

	public class Relation {
		public String previous;
		public String next;
		public String xor;
	}

	public class ActivityModel {
		public int aid;
		public String name;
		public String startDate;
		public String duration;
		public PlaceModel place;
		public String tips;
		public Relation relation;
	}
}
