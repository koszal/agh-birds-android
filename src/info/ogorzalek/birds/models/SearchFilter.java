package info.ogorzalek.birds.models;

import org.json.JSONObject;

import com.google.gson.Gson;

public class SearchFilter {

	public String name;
	public String order;
	public String family;
	public String genus;
	public String species;
	public String country;
	
	public static SearchFilter fromJSON(JSONObject json)
	{
		Gson gson = new Gson();
		SearchFilter bird = gson.fromJson(json.toString(), SearchFilter.class);
		return bird;
	}
	
	
}
