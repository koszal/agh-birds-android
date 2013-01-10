package info.ogorzalek.birds.activities;

import java.util.List;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.general.Backend;
import info.ogorzalek.birds.general.Routing;
import info.ogorzalek.birds.models.Quiz;
import info.ogorzalek.birds.models.Quiz.OnQuizListResponse;
import info.ogorzalek.birds.models.Quiz.OnQuizResponse;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.Typeface;
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
	TextView recentQuizesLabel;
	LinearLayout recent;
	
	Typeface font;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_launcher);
        
        font = Typeface.createFromAsset(QuizLauncherActivity.this.getAssets(), "fonts/chrysuni.ttf");
           
        startQuiz = (Button) this.findViewById(R.id.quiz_launcher_start_quiz_button);
        recentQuizesLabel = (TextView) this.findViewById(R.id.recent_quizes);
        
        recent = (LinearLayout) this.findViewById(R.id.quiz_launcher_recent_layout);
        
        startQuiz.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Quiz.create(v.getContext(), new OnQuizResponse() {
					
					public void onQuizResponse(Quiz quiz) {
						startActivity(Routing.showQuiz(getApplicationContext(), quiz.id));
					}
					
					public void onError(Exception e) {
						Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
        
        applyTypeface();
        
	}
	
	private void applyTypeface()
    {
    	startQuiz.setTypeface(font);
    	recentQuizesLabel.setTypeface(font);
    }

	@Override
	protected void onResume() {
		super.onResume();
		Quiz.list(getApplicationContext(), new OnQuizListResponse() {
			
			public void onQuizListResponse(List<Quiz> quizes) {
				boolean odd = true;
				recent.removeAllViews();
				for(final Quiz quiz : quizes)
				{
					TextView tv = new TextView(getBaseContext());
					tv.setText(quiz.created_at);
					tv.setTypeface(font);
					if(odd) {
						tv.setBackgroundColor(getResources().getColor(R.color.theme_block_background_gray));
						odd = false;
					} else {
						tv.setBackgroundColor(Color.WHITE);
						odd = true;
					}
					tv.setTextColor(getResources().getColor(R.color.theme_text_gray));
					tv.setPadding(25, 15, 15, 15);
					tv.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							startActivity(Routing.showQuiz(getApplicationContext(), quiz.id));
						}
					});
					recent.addView(tv);
				}
			}
			
			public void onError(Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	
	
}
