package info.ogorzalek.birds.activities;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.views.QuizPager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private long quizId = -1;
	
	private QuizPager pageIndicator;
	private ViewPager pager;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		
		
		QuizPagerAdapter adapter = new QuizPagerAdapter();
		pager = (ViewPager) findViewById(R.id.quiz_pager);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(onPageChangedListener);

		pageIndicator = (QuizPager) this.findViewById(R.id.quiz_page_indicator);
		pageIndicator.setOnTapListener(onTapListener);

	}

	@Override
	protected void onResume() {
		super.onResume();
	
		quizId = getIntent().getLongExtra("quiz_id", -1);
		
		if(quizId == -1)
		{
			
		}
		
	}

	private class QuizPagerAdapter extends PagerAdapter {
		public int getCount() {
			return 10;
		}

		public Object instantiateItem(View collection, int position) {
			LayoutInflater inflater = (LayoutInflater) collection.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View view = inflater.inflate(R.layout.quiz_question, null);
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
}
