package info.ogorzalek.birds.models;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

public class SearchFilter {

	public String name = null;
	public String order = null;
	public String family = null;
	public String genus = null;
	public String country = null;
	
	public HashMap<String, String> getFiltersMap() {
		HashMap<String, String> map = new HashMap<String, String>();

		if(name != null)
			map.put("name", name);
		if(order != null)
			map.put("order", order);
		if(family != null)
			map.put("family", family);
		if(genus != null)
			map.put("genus", genus);
		if(country != null)
			map.put("country", country);
		
		return map;
	}
	
	public static SearchFilter fromJSON(JSONObject json)
	{
		Gson gson = new Gson();
		SearchFilter bird = gson.fromJson(json.toString(), SearchFilter.class);
		return bird;
	}
	
	public static SearchFilter fromIntent(Bundle extras) {
		
		SearchFilter searchFilter = new SearchFilter();
	
		if(extras.containsKey("name"))
			searchFilter.name = extras.getString("name");
		if(extras.containsKey("order"))
			searchFilter.order = extras.getString("order");
		if(extras.containsKey("family"))
			searchFilter.family = extras.getString("family");
		if(extras.containsKey("genus"))
			searchFilter.genus = extras.getString("genus");
		if(extras.containsKey("country"))
			searchFilter.country = extras.getString("country");
		
		return searchFilter;
	}
	
}
