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
import info.ogorzalek.birds.general.Backend.MetaResponse;
import info.ogorzalek.birds.general.Backend.OnHttpGetResponseListener;

public class ApiKey {

	public static String MODEL_URL = "apiKey/";
	
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
