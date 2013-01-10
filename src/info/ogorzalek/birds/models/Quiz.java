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
import info.ogorzalek.birds.general.Backend.OnHttpResponseListener;

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
		
		Backend backend = Backend.instance(context);
		backend.get(new OnHttpResponseListener() {
			public void onResponse(JSONObject data) {
				try {
					List<Quiz> quizes = new ArrayList<Quiz>();
					
					JSONArray array = data.getJSONArray("objects");
					for(int i = 0; i < array.length(); i++)
					{
						quizes.add(Quiz.fromJSON(array.getJSONObject(i)));
					}
					
					listener.onQuizListResponse(quizes);
					
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
		
		Backend backend = Backend.instance(context);
		backend.get(new OnHttpResponseListener() {
			
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
		
		Backend backend = Backend.instance(context);
		backend.post(new OnHttpResponseListener() {
			
			public void onResponse(JSONObject data) {
				
				Quiz quiz = Quiz.fromJSON(data);
				listener.onQuizResponse(quiz);
				
			}
			
			public void onError(Exception e) {
				listener.onError(e);
			}
		}, url, null);
		
	}
	
	public void finish(Context context, final OnQuizResponse listener) {
		final String url = getFinishUrl();
		
		Backend backend = Backend.instance(context);
		backend.post(new OnHttpResponseListener() {
			
			@Override
			public void onResponse(JSONObject data) {
				Quiz quiz = Quiz.fromJSON(data);
				listener.onQuizResponse(quiz);
			}
			
			@Override
			public void onError(Exception e) {
				listener.onError(e);
			}
		}, url, null);
	}
	
	public static class OnQuizResponseAdapter implements OnQuizResponse
	{
		public void onQuizResponse(Quiz bird) {}
		public void onQuizListResponse(List<Quiz> quizes) {}
		public void onError(Exception e) {}
	}
	
	public interface OnQuizResponse
	{
		void onQuizResponse(Quiz bird);
		void onError(Exception e);
	}
	
	public interface OnQuizListResponse {
		void onQuizListResponse(List<Quiz> quizes);
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
	
	public String getFinishUrl() {
		return Backend.BASE_URL + Quiz.MODEL_URL + "?quiz_id=" + this.id;
	}
	
	
	
}
