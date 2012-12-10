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
import android.content.SharedPreferences;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.util.Log;
import info.ogorzalek.birds.general.Backend;
import info.ogorzalek.birds.general.Backend.OnHttpResponseListener;

public class ApiKey {

	public static String MODEL_URL = "apiUser/";
	
	public long id;
	public String key;
	public String created_at;
	public String modified_at;

	public static ApiKey fromJSON(JSONObject json)
	{
		Gson gson = new Gson();
		ApiKey item = gson.fromJson(json.toString(), ApiKey.class);
		return item;
	}
	
	public static void register(final Context context, final OnApiKeyResponse listener) {
			final String url = getUrl();
			Backend backend = Backend.instance(context);
			
			backend.post(new OnHttpResponseListener() {
				
				public void onResponse(JSONObject data) {
					ApiKey apiKey = ApiKey.fromJSON(data);
					registerApiKey(context, apiKey);		
					
					listener.onApiKeyResponse(apiKey);
				}
				
				public void onError(Exception e) {
					listener.onError(e);					
				}
			}, url, null);
			
	}
	
	public static void registerApiKey(Context context, ApiKey apiKey) {
		SharedPreferences prefs = context.getSharedPreferences("birds", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("api_key", apiKey.key);
		editor.commit();
	}
	
	public static ApiKey getApiKey(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("birds", Context.MODE_PRIVATE);
		ApiKey apiKey = new ApiKey();
		apiKey.key = prefs.getString("api_key", null);
		return apiKey;
	}
	
	public interface OnApiKeyResponse
	{
		void onApiKeyResponse(ApiKey apiKey);
		void onError(Exception e);
	}
	
	public static String getUrl()
	{
		return Backend.BASE_URL + ApiKey.MODEL_URL;
	}
	
}
