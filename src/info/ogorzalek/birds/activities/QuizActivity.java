package info.ogorzalek.birds.activities;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.general.Utils;
import info.ogorzalek.birds.models.Question;
import info.ogorzalek.birds.models.Quiz;
import info.ogorzalek.birds.models.Quiz.OnQuizResponse;
import info.ogorzalek.birds.views.QuizPager;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

			TextView title = (TextView) view.findViewById(R.id.question_title);
			TextView text = (TextView) view.findViewById(R.id.question_text);
			TextView answer1 = (TextView) view.findViewById(R.id.question_answer_1);
			TextView answer2 = (TextView) view.findViewById(R.id.question_answer_2);
			TextView answer3 = (TextView) view.findViewById(R.id.question_answer_3);
			TextView answer4 = (TextView) view.findViewById(R.id.question_answer_4);
			
			title.setTypeface(font);
			text.setTypeface(font);
			answer1.setTypeface(font);
			answer2.setTypeface(font);
			answer3.setTypeface(font);
			answer4.setTypeface(font);
			
			if(quiz != null)
			{
				Question current = quiz.questions.get(position);

				
				title.setText("Question " + (position + 1));
				text.setText(current.question);
				answer1.setText(current.answer1);
				answer2.setText(current.answer2);
				answer3.setText(current.answer3);
				answer4.setText(current.answer4);

				answer1.setOnClickListener(new QuizActivity.OnAnswerSelectedListener(current, 1));
				answer2.setOnClickListener(new QuizActivity.OnAnswerSelectedListener(current, 2));
				answer3.setOnClickListener(new QuizActivity.OnAnswerSelectedListener(current, 3));
				answer4.setOnClickListener(new QuizActivity.OnAnswerSelectedListener(current, 4));
				
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
		
		public OnAnswerSelectedListener(Question question, int answer)
		{
			this.question = question;
			this.answer = answer;
		}
		
		public void onClick(View v) {
			// odznaczyc
//			if(answer == question.user_answer)
//			{
//				question.user_answer = 0;
//				switch(answer) {
//				case 1:
//					unmark(question.answer1_view); break;
//				case 2:
//					unmark(question.answer2_view); break;
//				case 3:
//					unmark(question.answer3_view); break;
//				case 4:
//					unmark(question.answer4_view); break;
//				}
//			} else if(question.user_answer != 0) { // odznaczyc stare, zaznaczyc nowe
//				question.user_answer = answer;
//				switch(question.user_answer) {
//				case 1:
//					unmark(question.answer1_view); break;
//				case 2:
//					unmark(question.answer2_view); break;
//				case 3:
//					unmark(question.answer3_view); break;
//				case 4:
//					unmark(question.answer4_view); break;
//				}
//				mark((TextView) v);
//			} else {
//				mark((TextView) v);
//			}
			
		}
		
		private void mark(TextView v) {
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.theme_green_light));
			v.setTextColor(v.getContext().getResources().getColor(R.color.theme_text_white));
		}
		
		private void unmark(TextView v) {
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.theme_block_background_gray));
			v.setTextColor(v.getContext().getResources().getColor(R.color.theme_text_gray));
		}
		
	}
	
}
