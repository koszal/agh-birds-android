package info.ogorzalek.birds.activities;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParseException;
import com.larvalabs.svgandroid.SVGParser;

import info.ogorzalek.birds.R;
import info.ogorzalek.birds.views.PageIndicator;
import info.ogorzalek.birds.views.QuizPageIndicator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuizActivity extends Activity {
	
	private QuizPageIndicator indicator;
	
	TextView tv;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
          
        tv = (TextView) this.findViewById(R.id.tv);
        
        MyPageAdapter adapter = new MyPageAdapter();
        
        ViewPager pager = (ViewPager) this.findViewById(R.id.pager);
        pager.setOnPageChangeListener(onPageChangeListener);
        pager.setAdapter(adapter);
        
        LinearLayout indicatorLayout = (LinearLayout) this.findViewById(R.id.quiz_page_indicator_layout);
        indicator = new QuizPageIndicator(getApplicationContext());
        indicatorLayout.addView(indicator);
        
	}
	
	private class MyPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 5;
		}

		
		@Override
		public Object instantiateItem(View container, int position) {
			
			LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View view = inflater.inflate(R.layout.quiz_question, null);
			TextView text = (TextView) view.findViewById(R.id.textView1);
			text.setText("question " + position);
			
			((ViewPager) container).addView(view, 0);
			
			
			
			return view;
			
		}
		
		@Override
		public void destroyItem(View view, int position, Object object)
		{
			((ViewPager) view).removeView((View) object);
		}


		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == ((View) arg1);
		}
		
		
		
		
		
	}
	
	OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			//debug.setText("pos: " + position + " posOff: " + positionOffset + " posPix: " + positionOffsetPixels);
			indicator.update(position, positionOffset, positionOffsetPixels);
			tv.setText("pos: " +  position + ", posOff: " + positionOffset + ", posOffPix: " +  positionOffsetPixels);
		}
		
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
}
