package info.ogorzalek.birds.activities;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.general.Utils;
import info.ogorzalek.birds.models.Question;
import info.ogorzalek.birds.models.Question.OnQuestionResponse;
import info.ogorzalek.birds.models.Quiz;
import info.ogorzalek.birds.models.Quiz.OnQuizResponse;
import info.ogorzalek.birds.views.QuizPager;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private long quizId = -1;
	
	private Quiz quiz;
	
	private QuizPager pageIndicator;
	private QuizPagerAdapter adapter;
	private ViewPager pager;

	private ProgressBar progressBar;
	
	private Typeface font;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		
		
		adapter = new QuizPagerAdapter();
		pager = (ViewPager) findViewById(R.id.quiz_pager);
		//pager.setAdapter(adapter);
		pager.setOnPageChangeListener(onPageChangedListener);

		pageIndicator = (QuizPager) this.findViewById(R.id.quiz_page_indicator);
		pageIndicator.setOnTapListener(onTapListener);

		progressBar = (ProgressBar) this.findViewById(R.id.quiz_progress_bar);
		
		font = Utils.getFont(this);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
	
		progressBar.setVisibility(View.VISIBLE);
		
		quizId = getIntent().getLongExtra("quiz_id", -1);
		
		if(quizId != -1)
		{
			Quiz.get(QuizActivity.this, onQuizListener, quizId);
		}
		
	}

	private OnQuizResponse onQuizListener = new OnQuizResponse() {
		
		public void onQuizResponse(Quiz quiz) {
			QuizActivity.this.quiz = quiz;
			pager.setAdapter(adapter);
			progressBar.setVisibility(View.GONE);
		}
		
		public void onError(Exception e) {
			Toast.makeText(QuizActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	};
	
	
	private class QuizPagerAdapter extends PagerAdapter {
		public int getCount() {
			return 10;
		}

		public Object instantiateItem(View collection, int position) {
			LayoutInflater inflater = (LayoutInflater) collection.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View view = inflater.inflate(R.layout.quiz_question, null);
			
			Button finishQuiz = (Button) view.findViewById(R.id.quiz_finish_button);
			finishQuiz.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					quiz.finish(QuizActivity.this, new OnQuizResponse() {
						
						@Override
						public void onQuizResponse(Quiz bird) {
							QuizActivity.this.finish();
						}
						
						@Override
						public void onError(Exception e) {
							Toast.makeText(QuizActivity.this, "Internet connection error!", Toast.LENGTH_SHORT).show();
						}
					});
				}
			});

			TextView title = (TextView) view.findViewById(R.id.question_title);
			TextView text = (TextView) view.findViewById(R.id.question_text);
			TextView answer1 = (TextView) view.findViewById(R.id.question_answer_1);
			TextView answer2 = (TextView) view.findViewById(R.id.question_answer_2);
			TextView answer3 = (TextView) view.findViewById(R.id.question_answer_3);
			TextView answer4 = (TextView) view.findViewById(R.id.question_answer_4);
			
			LinearLayout answersLayout = (LinearLayout) view.findViewById(R.id.question_answers_layout);
			
			ImageView image = (ImageView) view.findViewById(R.id.question_image);
	        //UrlImageViewHelper.setUrlDrawable(image, , R.drawable.ic_launcher);
			
			title.setTypeface(font);
			text.setTypeface(font);
			answer1.setTypeface(font);
			answer2.setTypeface(font);
			answer3.setTypeface(font);
			answer4.setTypeface(font);
			finishQuiz.setTypeface(font);
			
			if(quiz != null)
			{
				Question current = quiz.questions.get(position);

		        UrlImageViewHelper.setUrlDrawable(image, current.media.getResourceUrl(), R.drawable.ic_launcher);
				
				title.setText("Question " + (position + 1));
				text.setText(current.question);
				answer1.setText(current.answer1);
				answer2.setText(current.answer2);
				answer3.setText(current.answer3);
				answer4.setText(current.answer4);
				
				if(quiz.is_closed == 0) {
					if(current.users_answer == 1) 			
						answer1.setBackgroundColor(getResources().getColor(R.color.theme_yellow_light));
					if(current.users_answer == 2) 			
						answer2.setBackgroundColor(getResources().getColor(R.color.theme_yellow_light));
					if(current.users_answer == 3) 			
						answer3.setBackgroundColor(getResources().getColor(R.color.theme_yellow_light));
					if(current.users_answer == 4) 			
						answer4.setBackgroundColor(getResources().getColor(R.color.theme_yellow_light));
					
					answer1.setOnClickListener(new QuizActivity.OnAnswerSelectedListener(current, 1, answersLayout));
					answer2.setOnClickListener(new QuizActivity.OnAnswerSelectedListener(current, 2, answersLayout));
					answer3.setOnClickListener(new QuizActivity.OnAnswerSelectedListener(current, 3, answersLayout));
					answer4.setOnClickListener(new QuizActivity.OnAnswerSelectedListener(current, 4, answersLayout));
				} else {
					finishQuiz.setVisibility(View.GONE);
					if(current.users_answer == 1) {			
						answer1.setBackgroundColor(getResources().getColor(R.color.theme_pink_light));
						answer1.setTextColor(getResources().getColor(R.color.theme_text_white));
					}
					if(current.users_answer == 2) {		
						answer2.setBackgroundColor(getResources().getColor(R.color.theme_pink_light));
						answer2.setTextColor(getResources().getColor(R.color.theme_text_white));
					}
					if(current.users_answer == 3) {			
						answer3.setBackgroundColor(getResources().getColor(R.color.theme_pink_light));
						answer3.setTextColor(getResources().getColor(R.color.theme_text_white));
					}
					if(current.users_answer == 4) {			
						answer4.setBackgroundColor(getResources().getColor(R.color.theme_pink_light));
						answer4.setTextColor(getResources().getColor(R.color.theme_text_white));
					}
					if(current.correct_answer == 1) {			
						answer1.setBackgroundColor(getResources().getColor(R.color.theme_green_light));
						answer1.setTextColor(getResources().getColor(R.color.theme_text_white));
					}
					if(current.correct_answer == 2) {			
						answer2.setBackgroundColor(getResources().getColor(R.color.theme_green_light));
						answer2.setTextColor(getResources().getColor(R.color.theme_text_white));
					}
					if(current.correct_answer == 3) {			
						answer3.setBackgroundColor(getResources().getColor(R.color.theme_green_light));
						answer3.setTextColor(getResources().getColor(R.color.theme_text_white));
					}
					if(current.correct_answer == 4) {			
						answer4.setBackgroundColor(getResources().getColor(R.color.theme_green_light));
						answer4.setTextColor(getResources().getColor(R.color.theme_text_white));
					}
				}
				
			}
			
			((ViewPager) collection).addView(view, 0);
			return view;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == ((View) arg1);
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
		
	}
	
	OnPageChangeListener onPageChangedListener = new OnPageChangeListener() {
		
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			pageIndicator.update(position, positionOffset, positionOffsetPixels);
			
		}
		
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	QuizPager.OnTapListener onTapListener = new QuizPager.OnTapListener() {

		public void onTap(final int position) {
			
			QuizActivity.this.pager.setCurrentItem(position);
			Toast.makeText(QuizActivity.this, "tapped: " + position, Toast.LENGTH_SHORT).show();
		}
	};
	
	private class OnAnswerSelectedListener implements OnClickListener {

		private int answer;
		private Question question;
		private View view;
		
		public OnAnswerSelectedListener(Question question, int answer, View v)
		{
			this.question = question;
			this.answer = answer;
			this.view = v;
		}
		
		public void onClick(View v) {
			question.users_answer = answer;
			question.answer(QuizActivity.this, new OnQuestionResponse() {
				
				@Override
				public void onQuestion(Question question) {
					Toast.makeText(QuizActivity.this, "ok", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onError(Exception e) {
					Toast.makeText(QuizActivity.this, "ok", Toast.LENGTH_SHORT).show();
				}
			}, answer);
			QuizActivity.this.adapter.notifyDataSetChanged();
			redraw();
			
		}
		
		public void redraw() {
			TextView answer1 = (TextView) view.findViewById(R.id.question_answer_1);
			TextView answer2 = (TextView) view.findViewById(R.id.question_answer_2);
			TextView answer3 = (TextView) view.findViewById(R.id.question_answer_3);
			TextView answer4 = (TextView) view.findViewById(R.id.question_answer_4);
			answer1.setBackgroundColor(getResources().getColor(R.color.theme_block_background_gray));
			answer2.setBackgroundColor(getResources().getColor(R.color.theme_block_background_gray));
			answer3.setBackgroundColor(getResources().getColor(R.color.theme_block_background_gray));
			answer4.setBackgroundColor(getResources().getColor(R.color.theme_block_background_gray));
			if(answer == 1) 			
				answer1.setBackgroundColor(getResources().getColor(R.color.theme_yellow_light));
			if(answer == 2) 			
				answer2.setBackgroundColor(getResources().getColor(R.color.theme_yellow_light));
			if(answer == 3) 			
				answer3.setBackgroundColor(getResources().getColor(R.color.theme_yellow_light));
			if(answer == 4) 			
				answer4.setBackgroundColor(getResources().getColor(R.color.theme_yellow_light));
		}
		
	}
	
}
