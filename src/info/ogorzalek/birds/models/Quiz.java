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
import info.ogorzalek.birds.general.Backend.OnHttpPostResponseListener;

public class Quiz {

	public static String MODEL_URL = "quiz/";
	
	public long id;
	public String api_user_key;
	public int time_limit;
	public int is_closed;
	public String created_at;
	public List<Question> questions;

	public static Quiz fromJSON(JSONObject json)
	{
		Gson gson = new Gson();
		Quiz bird = gson.fromJson(json.toString(), Quiz.class);
		return bird;
	}
	
	public static void list(Context context, final OnQuizListResponse listener)
	{
		final String url = getUrl();
		
		Backend backend = Backend.getInstance(context);
		backend.get(new OnHttpGetResponseListener() {
			public void onResponse(JSONObject data) {
				try {
					List<Quiz> quizes = new ArrayList<Quiz>();
					MetaResponse metaResponse = MetaResponse.fromJSON(data.getJSONObject("meta"));
					
					JSONArray array = data.getJSONArray("objects");
					for(int i = 0; i < array.length(); i++)
					{
						quizes.add(Quiz.fromJSON(array.getJSONObject(i)));
					}
					
					listener.onQuizListResponse(quizes, metaResponse);
					
				} catch (JSONException e) {
					this.onError(e);
				}
						
			}
			
			public void onError(Exception e) {
				listener.onError(e);
				Log.d("BIRD", url);
			}

			public void onBeforeRequest() {
			}

			public void onAfterRequest() {
			}
			
		}, url);
	}
	
	public static void get(Context context, final OnQuizResponse listener, long id)
	{
		final String url = getUrl(id);
		
		Backend backend = Backend.getInstance(context);
		backend.get(new OnHttpGetResponseListener() {
			
			public void onResponse(JSONObject data) {
				
				Quiz quiz = Quiz.fromJSON(data);
				listener.onQuizResponse(quiz);
				
			}
			
			public void onError(Exception e) {
				listener.onError(e);
			}

			public void onBeforeRequest() {
				// TODO Auto-generated method stub
				
			}

			public void onAfterRequest() {
				// TODO Auto-generated method stub
				
			}
		}, url);
		
	}
	
	public static void create(Context context, final OnQuizResponse listener)
	{
		final String url = getUrl();
		
		Backend backend = Backend.getInstance(context);
		backend.post(new OnHttpPostResponseListener() {
			
			public void onResponse(JSONObject data) {
				
				Quiz quiz = Quiz.fromJSON(data);
				listener.onQuizResponse(quiz);
				
			}
			
			public void onError(Exception e) {
				listener.onError(e);
			}
		}, url);
		
	}
	
	public static class OnQuizResponseAdapter implements OnQuizResponse
	{
		public void onQuizResponse(Quiz bird) {}
		public void onQuizListResponse(List<Quiz> quizes, MetaResponse meta) {}
		public void onError(Exception e) {}
	}
	
	public interface OnQuizResponse
	{
		void onQuizResponse(Quiz bird);
		void onError(Exception e);
	}
	
	public interface OnQuizListResponse {
		void onQuizListResponse(List<Quiz> quizes, MetaResponse meta);
		void onError(Exception e);
	}
	
	public static String getUrl()
	{
		return Backend.BASE_URL + Quiz.MODEL_URL;
	}
	
	public static String getUrl(long id)
	{
		return Backend.BASE_URL + Quiz.MODEL_URL + id;
	}
	
	
	
}
