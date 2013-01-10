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
import info.ogorzalek.birds.general.Backend.OnHttpResponseListener;

public class Question {

	public static String MODEL_URL = "question/";
	
	public int id;
	public String question;
	public String answer1;
	public String answer2;
	public String answer3;
	public String answer4;
	public int correct_answer;
	public int users_answer;
	public int quiz_id;
	public int media_id;
	public Media media;
	

	
	public static Question fromJSON(JSONObject json)
	{
		Gson gson = new Gson();
		Question bird = gson.fromJson(json.toString(), Question.class);
		return bird;
	}
	
	public void answer(Context context, final OnQuestionResponse listener, int answer) {
		
		Backend backend = Backend.instance(context);
		backend.post(new OnHttpResponseListener() {
			
			@Override
			public void onResponse(JSONObject data) {
				Question question = Question.fromJSON(data);
				listener.onQuestion(question);
			}
			
			@Override
			public void onError(Exception e) {
				listener.onError(e);
			}
		}, getAnswerUrl(answer), null);
		
	}
			
	public static String getUrl()
	{
		return Backend.BASE_URL + Question.MODEL_URL;
	}
	
	public static String getUrl(long id)
	{
		return Backend.BASE_URL + Question.MODEL_URL + id;
	}
	
	public String getAnswerUrl(int answer) {
		return Backend.BASE_URL + Question.MODEL_URL + "?answer=" + Integer.toString(answer) + "&question_id=" + Integer.toString(id);
	}
	
	public static interface OnQuestionResponse {
		public void onQuestion(Question question);
		public void onError(Exception e);
	}
	
}
