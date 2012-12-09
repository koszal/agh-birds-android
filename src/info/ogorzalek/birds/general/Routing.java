package info.ogorzalek.birds.general;

import info.ogorzalek.birds.activities.AboutActivity;
import info.ogorzalek.birds.activities.AdvancedSearchActivity;
import info.ogorzalek.birds.activities.BirdActivity;
import info.ogorzalek.birds.activities.BirdListActivity;
import info.ogorzalek.birds.activities.QuizActivity;
import info.ogorzalek.birds.activities.QuizActivity;
import info.ogorzalek.birds.activities.QuizLauncherActivity;
import info.ogorzalek.birds.activities.SettingsActivity;
import android.content.Context;
import android.content.Intent;

public class Routing {

	public static Intent showBird(Context c, long id)
	{
		Intent intent = new Intent(c, BirdActivity.class);
		intent.putExtra("birdId", id);
		return intent;
	}
	
	public static Intent showBirdList(Context c)
	{
		Intent intent = new Intent(c, BirdListActivity.class);
		return intent;
	}
	
	public static Intent showBirdList(Context c, String query)
	{
		Intent intent = new Intent(c, BirdListActivity.class);
		intent.putExtra("query", query);
		return intent;
	}
	
	public static Intent showBirdList(Context c, String query, String order, String family, String genus, String country)
	{
		Intent intent = new Intent(c, BirdListActivity.class);
		if(query != null) 
			intent.putExtra("query", query);
		if(order != null) 
			intent.putExtra("order", order);
		if(family != null) 
			intent.putExtra("family", family);
		if(genus != null) 
			intent.putExtra("genus", genus);
		if(country != null) 
			intent.putExtra("country", country);
		
		return intent;
	}
	
	public static Intent showQuiz(Context c, long id)
	{
		Intent intent = new Intent(c, QuizActivity.class);
		intent.putExtra("quiz_id", id);
		return intent;
	}
	
	public static Intent showQuizLauncher(Context c)
	{
		Intent intent = new Intent(c, QuizLauncherActivity.class);
		return intent;
	}
	
	public static Intent showAbout(Context c)
	{
		Intent intent = new Intent(c, AboutActivity.class);
		return intent;
	}
	
	public static Intent showSettings(Context c)
	{
		Intent intent = new Intent(c, SettingsActivity.class);
		return intent;
	}
	
	public static Intent showAdvancedSearch(Context c)
	{
		Intent intent = new Intent(c, AdvancedSearchActivity.class);
		return intent;
	}
	
	
}
