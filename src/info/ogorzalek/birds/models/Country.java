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

public class Country {

	
	public long id;
	public String name;
	public String short_name;

	public static Country fromJSON(JSONObject json)
	{
		Gson gson = new Gson();
		Country item = gson.fromJson(json.toString(), Country.class);
		return item;
	}		
	
}
