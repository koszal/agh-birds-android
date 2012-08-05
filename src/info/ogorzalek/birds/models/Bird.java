package info.ogorzalek.birds.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import android.content.Context;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.util.Log;
import info.ogorzalek.birds.general.Backend;
import info.ogorzalek.birds.general.Backend.MetaResponse;
import info.ogorzalek.birds.general.Backend.OnHttpGetResponseListener;

public class Bird {

	public static String MODEL_URL = "bird/";
	
	public long id;
	public String name;
	public String description;

	public static Bird fromJSON(JSONObject json)
	{
		Gson gson = new Gson();
		Bird bird = gson.fromJson(json.toString(), Bird.class);
		return bird;
	}
	
	public static void list(Context context, final OnBirdResponse listener)
	{
		final String url = getUrl();
		
		Backend backend = Backend.getInstance(context);
		backend.get(new OnHttpGetResponseListener() {
			public void onResponse(JSONObject data) {
				try {
					List<Bird> birds = new ArrayList<Bird>();
					MetaResponse metaResponse = MetaResponse.fromJSON(data.getJSONObject("meta"));
					
					JSONArray array = data.getJSONArray("objects");
					for(int i = 0; i < array.length(); i++)
					{
						birds.add(Bird.fromJSON(array.getJSONObject(i)));
					}
					
					listener.onBirdListResponse(birds, metaResponse);
					
				} catch (JSONException e) {
					this.onError(e);
				}
						
			}
			
			public void onError(Exception e) {
				listener.onError(e);
				Log.d("BIRD", url);
			}
			
		}, url);
	}
	
	public static void get(Context context, OnHttpGetResponseListener listener)
	{
		
	}
	
	public static class OnBirdResponseAdapter implements OnBirdResponse
	{
		public void onBirdResponse(Bird bird) {}
		public void onBirdListResponse(List<Bird> birds, MetaResponse meta) {}
		public void onError(Exception e) {}
	}
	
	public interface OnBirdResponse
	{
		void onBirdResponse(Bird bird);
		void onBirdListResponse(List<Bird> birds, MetaResponse meta);
		void onError(Exception e);
	}
	
	public static String getUrl()
	{
		return Backend.BASE_URL + Bird.MODEL_URL;
	}
	
	public static String getUrl(long id)
	{
		return Backend.BASE_URL + Bird.MODEL_URL + id;
	}
	
}
