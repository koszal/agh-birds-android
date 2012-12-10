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

public class Media {

	public static String MODEL_URL = "media/";
	
	public int id;
	public String name;
	public String description;
	public String filename;
	public String mime_type;
	public String created_at;
	public String modified_at;
	public String resource_type;
	public int bird_id;

	public static Media fromJSON(JSONObject json)
	{
		Gson gson = new Gson();
		Media item = gson.fromJson(json.toString(), Media.class);
		return item;
	}	
	
	public interface OnMediaResponse
	{
		void onMediaResponse(Media media);
		void onError(Exception e);
	}
	
	public static String getUrl()
	{
		return Backend.BASE_URL + Media.MODEL_URL;
	}
	
}
