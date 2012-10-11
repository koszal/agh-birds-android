package info.ogorzalek.birds.activities;

import java.util.List;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParseException;
import com.larvalabs.svgandroid.SVGParser;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.general.Backend;
import info.ogorzalek.birds.general.Backend.MetaResponse;
import info.ogorzalek.birds.general.Routing;
import info.ogorzalek.birds.models.Quiz;
import info.ogorzalek.birds.models.Quiz.OnQuizListResponse;
import info.ogorzalek.birds.models.Quiz.OnQuizResponse;
import android.app.Activity;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuizLauncherActivity extends Activity {
	
	Button startQuiz;
	LinearLayout recent;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_launcher);
           
        startQuiz = (Button) this.findViewById(R.id.quiz_launcher_start_quiz_button);
        recent = (LinearLayout) this.findViewById(R.id.quiz_launcher_recent_layout);
        
        startQuiz.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Quiz.create(v.getContext(), new OnQuizResponse() {
					
					public void onQuizResponse(Quiz quiz) {
						startActivity(Routing.showQuiz(getApplicationContext(), quiz.id));
					}
					
					public void onQuizListResponse(List<Quiz> quizes, MetaResponse meta) {						
					}
					
					public void onError(Exception e) {
						Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
        
	}


	@Override
	protected void onResume() {
		super.onResume();
		Quiz.list(getApplicationContext(), new OnQuizListResponse() {
			
			public void onQuizListResponse(List<Quiz> quizes, MetaResponse meta) {
				for(Quiz quiz : quizes)
				{
					TextView tv = new TextView(getBaseContext());
					tv.setText(quiz.created_at);
					recent.addView(tv);
				}
			}
			
			public void onError(Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	
	
}
