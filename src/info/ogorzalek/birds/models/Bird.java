package info.ogorzalek.birds.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import android.content.Context;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.util.Log;
import info.ogorzalek.birds.general.Backend;
import info.ogorzalek.birds.general.Backend.OnHttpResponseListener;

public class Bird {

	public static String MODEL_URL = "bird/";
	
	public long id;
	public String name;
	public String latin_name;
	public String description;
	public String order;
	public String family;
	public String genus;
	public String species;
	public String created_at;
	public String modified_at;
	public String thumbnail;
	public List<Country> countries;
	public List<Media> medias;

	public static Bird fromJSON(JSONObject json)
	{
		Gson gson = new Gson();
		Bird bird = gson.fromJson(json.toString(), Bird.class);
		return bird;
	}
	
	public static void list(Context context, final OnBirdListResponse listener, SearchFilter filter)
	{
		final String url = getUrl(filter);
		
		Backend backend = Backend.instance(context);
		backend.get(new OnHttpResponseListener() {
			public void onResponse(JSONObject data) {
				try {
					List<Bird> birds = new ArrayList<Bird>();
					//MetaResponse metaResponse = MetaResponse.fromJSON(data.getJSONObject("meta"));
					
					JSONArray array = data.getJSONArray("objects");
					for(int i = 0; i < array.length(); i++)
					{
						birds.add(Bird.fromJSON(array.getJSONObject(i)));
					}
					
					listener.onBirdListResponse(birds);
					
				} catch (JSONException e) {
					this.onError(e);
				}
						
			}
			
			public void onError(Exception e) {
				listener.onError(e);
				//Log.d("BIRD", url);
			}
			
		}, url);
	}
	
	public static void get(Context context, final OnBirdResponse listener, int id)
	{
		Backend backend = Backend.instance(context);
		backend.get(new OnHttpResponseListener() {
			
			public void onResponse(JSONObject data) {
				Bird item = Bird.fromJSON(data);
				listener.onBirdResponse(item);
			}
			
			public void onError(Exception e) {
				listener.onError(e);				
			}
		}, getUrl(id));
		
	}
	
	
	public interface OnBirdResponse
	{
		void onBirdResponse(Bird bird);
		void onError(Exception e);
	}
	
	public interface OnBirdListResponse
	{
		void onBirdListResponse(List<Bird> birds);
		void onError(Exception e);
	}
	
	public static String getUrl()
	{
		return Backend.BASE_URL + Bird.MODEL_URL;
	}
	
	public static String getUrl(SearchFilter filter)
	{
		String url = getUrl();
		Map<String, String> map = filter.getFiltersMap();
		
		boolean first = true;
		for(String key : map.keySet()) {
			if(first) {
				url += "?"; 
			} else {
				url += "&";
			}
			url += key + "=" + map.get(key);
		}
		
		return url;
	}
	
	public static String getUrl(int id)
	{
		return Backend.BASE_URL + Bird.MODEL_URL + id;
	}
	
	public String getThumbnail() {
		return Backend.BASE_MEDIA_URL + this.thumbnail;
	}
	
}
