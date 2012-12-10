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
import android.widget.TextView;
import info.ogorzalek.birds.general.Backend;
import info.ogorzalek.birds.general.Backend.MetaResponse;
import info.ogorzalek.birds.general.Backend.OnHttpGetResponseListener;

public class Question {

	public static String MODEL_URL = "bird/";
	
	public long id;
	public String question;
	public String answer1;
	public String answer2;
	public String answer3;
	public String answer4;
	public int correct_answer;
	public int users_answer;
	public int quiz_id;
	public int media_id;
	
	// layouts


	
	public static Question fromJSON(JSONObject json)
	{
		Gson gson = new Gson();
		Question bird = gson.fromJson(json.toString(), Question.class);
		return bird;
	}
			
	public static String getUrl()
	{
		return Backend.BASE_URL + Question.MODEL_URL;
	}
	
	public static String getUrl(long id)
	{
		return Backend.BASE_URL + Question.MODEL_URL + id;
	}
	
	
	
}
